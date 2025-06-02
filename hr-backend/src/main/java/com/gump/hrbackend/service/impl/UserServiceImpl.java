package com.gump.hrbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gump.hrbackend.common.ErrorCode;
import com.gump.hrbackend.exception.BusinessException;
import com.gump.hrbackend.mapper.UserMapper;
import com.gump.hrbackend.model.entity.User;
import com.gump.hrbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gump.hrbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务 业务逻辑实现类
 *
 * @author jzq
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "jzq";

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验用户的账号 密码 校验密码 是否符合要求
        // 1.1 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 1.2 账号长度 >= 4
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        // 1.3 密码长度 >= 8
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 1.4 账号不能包含特殊字符 正则匹配
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 包含非法字符 返回
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号包含非法字符");
        }
        // 1.5 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和校验密码不相同");
        }
        // 1.6 账号不能重复
        // 最后查询数据库中的表 代码性能优化
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号重复");
        }

        // 2. 密码加密 密文存储到数据库中
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));

        // 3. 向数据库中插入新数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return
     */
    @Override
    public User userLogIn(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验用户账户 密码 是否符合要求
        // 1.1 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 1.2 账号长度 >= 4
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度小于 4 位");
        }
        // 1.3 密码长度 >= 8
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度小于 8 位");
        }
        // 1.4 账号不能包含特殊字符 正则匹配
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 包含非法字符 返回
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号包含非法字符");
        }

        // 2. 校验用户密码 和数据库中的密文密码对比
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, user_account can't match user_password");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号不存在或密码错误");
        }

        // 3. 用户信息脱敏 隐藏敏感信息 (不返回) 防止数据库中的字段泄露给前端
        User safetyUser = getSafetyUser(user);

        // 4. 记录用户的登录态 (session) 会话状态 将其存到服务器上
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        // 5. 返回脱敏后的用户信息
        return safetyUser;
    }

    /**
     * 用户信息脱敏 隐藏敏感信息 (不返回) 防止数据库中的字段泄露给前端
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setUserAvatar(originUser.getUserAvatar());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setRegisterTime(originUser.getRegisterTime());
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogOut(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean addUser(User user) {
        // 1. 账号不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", user.getUserAccount());
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 2. 用户密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + user.getUserPassword()).getBytes(StandardCharsets.UTF_8));
        // 3. 向数据库插入数据
        User userToAdd = new User();
        userToAdd.setUserName(user.getUserName());
        userToAdd.setUserAccount(user.getUserAccount());
        userToAdd.setUserPassword(encryptPassword);
        userToAdd.setUserAvatar(user.getUserAvatar());
        userToAdd.setUserRole(user.getUserRole());
        boolean saveResult = this.save(userToAdd);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建用户失败，数据库错误");
        }
        return true;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(User user) {
        User userToUpdate = new User();
        userToUpdate.setId(user.getId());
        if (StringUtils.isNotBlank(user.getUserName())) {
            userToUpdate.setUserName(user.getUserName());
        }
        if (StringUtils.isNotBlank(user.getUserAccount())) {
            // 更新的账号不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_account", user.getUserAccount());
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            userToUpdate.setUserAccount(user.getUserAccount());
        }
        if (StringUtils.isNotBlank(user.getUserPassword())) {
            userToUpdate.setUserPassword(DigestUtils.md5DigestAsHex((SALT + user.getUserPassword()).getBytes(StandardCharsets.UTF_8)));
        }
        if (StringUtils.isNotBlank(user.getUserAvatar())) {
            userToUpdate.setUserAvatar(user.getUserAvatar());
        }
        if (user.getUserRole() != null) {
            userToUpdate.setUserRole(user.getUserRole());
        }
        return userMapper.updateById(userToUpdate) > 0;
    }
}




