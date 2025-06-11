package com.gump.hrbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gump.hrbackend.model.entity.User;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户服务 业务逻辑接口
 *
 * @author jzq
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录 (单点登录)
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    User userLogIn(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户信息脱敏 隐藏敏感信息 (不返回) 防止数据库中的字段泄露给前端
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogOut(HttpServletRequest request);

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    boolean updateUser(User user);
}
