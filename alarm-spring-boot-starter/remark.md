

# 通过注解实现自动发送告警信息

# @Alarm(title = "自制警告",templateId = "errorTemp", successNotice = true)
title: 标题  
    若是邮件方式，为邮件的主题
templateId： 模板id,要与application.yaml中配置的一致
successNotice：  正常状态是否需要通知

# application.yaml配置
spring:
    alarm:
        template:   # 模板属性
            enabled: true   # 是否启用此配置模板
            source: FILE    # 模板内容从哪个途径读取  FILE：从yaml配置文件中读取
            templates:  # 模板具体配置
                errorTemp:  # 模板1 模板id,与注解中配置的id匹配
                    id: errorTemp   
                    name: 模板名称
                    content: "标题：#{title}<br>状态：<font color=\"#{stateColor}\">#{state}</font><br>异常信息：<br>#{exceptionInfo}"
                errorTemp1: # 模板2
                    id: errorTemp1
                    name: 模板名称1
                    content: 模板内容1
        mail:   # 邮件配置   告警信息通过邮件发送
            enabled: true   #   开启使用邮件
            useSpringbootMail: true    # 使用spring-boot-starter-mail整合的邮件功能 开启则需要配置spring.mail相关配置
            to: 409316036@qq.com    # 接受人
            from: 1544552008@qq.com # 发送人
            host: smtp.qq.com
            port: 25
            username: 用户名
            password: 密码
    mail:   # 使用spring-boot-starter-mail整合的邮件功能 需要配置此属性
        host: smtp.qq.com
        username: 1544552008@qq.com
        password: ohklkskrsckribbi



# maven将项目发布到本地仓库
mvn package -Dmaven.test.skip=true

mvn install:install-file -Dfile=jar路径 -DgroupId=待填 -DartifactId=待填 -Dversion=版本号 -Dpackaging=jar
例： mvn install:install-file -Dfile=D:\workSpace\ideaWorkSpace\mall-server\mallplus-third\target\mallplus-third-1.1.0.jar  -DgroupId=com.cgmcomm.mallplus -DartifactId=mallplus-third  -Dversion=1.1.0  -Dpackaging=jar
