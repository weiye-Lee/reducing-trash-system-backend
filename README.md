# 垃圾回收减量系统

#### 介绍
基于springboot 框架开发的“回收宝小程序”后端，通过查询对数据总量进行统计，生成垃圾减量数据
author by weiye
#### 运行环境
jdk11
mysql 8.0 +
springboot 2.3.4
Lombok
swagger 2
#### 软件架构
|─src
   ├─main
     ├─java
     │  └─com
     │      └─work
     │          ├─lib
     │          └─recycle
     │              ├─common
     │              ├─component
     │              ├─config
     │              ├─controller
     │              │  └─vo
     │              ├─entity
     │              ├─exception
     │              ├─interceptor
     │              ├─repository
     │              │  └─impl
     │              ├─service
     │              └─utils
     └─resources
         └─static



#### 安装教程
1.idea 2020.3
2.执行mvn构建
3.执行src/main/java/com/work/recycle/RecycleApplication.java 启动项
4.修改配置的mysql密码和本地数据库密码

#### 使用说明

you shouldn't change anything

#### 参与贡献

weiye Lee
Tongtong Xia
