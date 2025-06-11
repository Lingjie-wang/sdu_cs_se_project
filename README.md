# sdu_cs_se_project
## 项目介绍

- 山东大学计算机科学与技术学院 22 级软件工程项目：人力资源管理系统

- 项目开始时间：2025 / 3 / 17

- 项目结束时间：2025 / 6 / 6


## 团队介绍



## 项目结构

### 目录树

```shell
├─README.md 
├─img           ## 项目图片
├─doc           ## 项目文档
├─hrms-backend  ## 项目后端
├─hrms-frontend ## 项目前端
├─分工说明       ## 项目里程碑追踪表
```

# 后端本地部署运行
## 环境
- Java8 + Maven
- Mysql 5.7
- IDEA
- Spring Boot

## 本地启动
### 数据库
- 运行 `src` 文件夹下的 `hrms.sql` 执行建表语句以及插入语句

### 修改配置文件
- 本地运行时使用的配置文件路径 `src/main/resources/application.yml`
- 修改 Mysql 密码 `spring.datasource.password` 修改成 Mysql 设置的密码

### 运行
- 本地运行 `src/main/java/com/jzq/hrprobackend/HrproBackendApplication.java`

# 前端本地部署运行
## 环境
- Node 16.16.0

## 下载前端需要的依赖
- 终端中运行 `npm install`

## 运行
- 终端中运行 `npm run start:dev`

> PS：第一次进入需要加载较长时间，请耐心等待