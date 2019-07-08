# MockToutiao

****
	
|Author|Yi Liu|
|---|---
|E-mail|liuyi95@hotmail.com


****
## 目录
* [项目介绍](#项目介绍)
    * 项目来源
    * 开发环境
    * 使用技术栈
    * 项目结构
* [必要条件](#必要条件)
* [用法](#用法)

### 项目介绍
------
__项目来源__  
  该项目基于牛客网中级课程头条咨询网站，由于Spring Boot新版已不支持velocity，故前端模板引擎采用thymeleaf。
	
__开发环境__  
  macOS Mojave 10.14.5  
  IntelliJ IDEA Version 2018.2.3  
  Java(TM) SE Runtime Environment (build 1.8.0_131-b11)  
  Mysql Server version: 5.7.19 MySQL Community Server (GPL)  
  Redis version=5.0.1  
	
__使用技术栈__  
  Spring + Spring MVC + Spring Boot + Thymeleaf + MyBatis + MySQL + Redis  

__项目结构__
```
├── HELP.md
├── README.md
├── UploadImages	//上传图片保存位置
├── logs	//日志位置
│   ├── debug
│   └── error
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── liuyi
│   │   │           └── toutiao
│   │   │               ├── Interceptor
│   │   │               │   ├── LoginRequiredInterceptor.java
│   │   │               │   └── PassportInterceptor.java
│   │   │               ├── ToutiaoApplication.java
│   │   │               ├── async
│   │   │               │   ├── EventConsumer.java
│   │   │               │   ├── EventHandler.java
│   │   │               │   ├── EventModel.java
│   │   │               │   ├── EventProducer.java
│   │   │               │   ├── EventType.java
│   │   │               │   └── handler
│   │   │               │       ├── LikeHandler.java
│   │   │               │       └── LoginExceptionHandler.java
│   │   │               ├── configuration
│   │   │               │   └── ToutiaoWebConfiguration.java
│   │   │               ├── controller
│   │   │               │   ├── CommentController.java
│   │   │               │   ├── HomeController.java
│   │   │               │   ├── LikeController.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── MessageController.java
│   │   │               │   └── NewsController.java
│   │   │               ├── dao
│   │   │               │   ├── CommentDAO.java
│   │   │               │   ├── LoginTicketDAO.java
│   │   │               │   ├── MessageDAO.java
│   │   │               │   ├── NewsDAO.java
│   │   │               │   └── UserDAO.java
│   │   │               ├── model
│   │   │               │   ├── Comment.java
│   │   │               │   ├── EntityType.java
│   │   │               │   ├── HostHolder.java
│   │   │               │   ├── LoginTicket.java
│   │   │               │   ├── Message.java
│   │   │               │   ├── News.java
│   │   │               │   ├── User.java
│   │   │               │   └── ViewObject.java
│   │   │               ├── service
│   │   │               │   ├── CommentService.java
│   │   │               │   ├── LikeService.java
│   │   │               │   ├── MessageService.java
│   │   │               │   ├── NewsService.java
│   │   │               │   └── UserService.java
│   │   │               └── util
│   │   │                   ├── JedisAdapter.java
│   │   │                   ├── MD5Util.java
│   │   │                   ├── RedisKeyUtil.java
│   │   │                   └── ToutiaoUtil.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── com
│   │       │   └── liuyi
│   │       │       └── toutiao
│   │       │           └── dao
│   │       │               └── NewsDAO.xml
│   │       ├── log4j.properties
│   │       ├── mybatis-config.xml
│   │       ├── static
│   │       │   ├── fonts
│   │       │   ├── images
│   │       │   ├── scripts
│   │       │   └── styles
│   │       └── templates
│   │           ├── detail.html
│   │           ├── footer.html
│   │           ├── header.html
│   │           ├── home.html
│   │           ├── letter.html
│   │           └── letterDetail.html
│   └── test
│       ├── Resources
│       │   └── init-schema.sql
│       └── java
│           └── com
│               └── liuyi
│                   └── toutiao
│                       ├── InitDatabaseTest.java
│                       └── ToutiaoApplicationTests.java
├── target
└── toutiao.iml

```
### 必要条件
------

### 用法
------
