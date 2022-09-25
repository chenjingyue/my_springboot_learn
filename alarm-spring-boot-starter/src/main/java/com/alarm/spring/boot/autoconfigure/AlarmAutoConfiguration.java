package com.alarm.spring.boot.autoconfigure;

import com.alarm.spring.boot.autoconfigure.aop.AlarmAspect;
import com.alarm.spring.boot.autoconfigure.config.MailConfig;
import com.alarm.spring.boot.autoconfigure.config.TemplateConfig;
import com.alarm.spring.boot.autoconfigure.service.JavaMailExecute;
import com.alarm.spring.boot.autoconfigure.service.MailExecute;
import com.alarm.spring.boot.autoconfigure.service.MailWarnService;
import com.alarm.spring.boot.autoconfigure.service.SpringbootMailExecute;
import com.alarm.spring.boot.autoconfigure.template.AlarmFactoryExecute;
import com.alarm.spring.boot.autoconfigure.template.AlarmTemplateProvider;
import com.alarm.spring.boot.autoconfigure.template.YamlAlarmTemplateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@AutoConfigureAfter(MailSenderAutoConfiguration.class)
public class AlarmAutoConfiguration  {


    // 邮件服务装载
    @Configuration
    @ConditionalOnProperty(prefix = MailConfig.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(MailConfig.class)
    public static class MailConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public MailWarnService mailWarnService(MailExecute mailExecute) {
            MailWarnService mailWarnService = new MailWarnService();
            mailWarnService.setMailExecute(mailExecute);
//            mailWarnService.init(mailConfig,javaMailSender);
            AlarmFactoryExecute.addAlarmWarnService(mailWarnService);
            return mailWarnService;
        }


        @Bean
        @ConditionalOnProperty(prefix = MailConfig.PREFIX, name = "useSpringbootMail", havingValue = "true")
        @ConditionalOnBean(JavaMailSenderImpl.class)
        public SpringbootMailExecute springbootMailExecute(MailConfig mailConfig, JavaMailSenderImpl javaMailSender) {
            SpringbootMailExecute mailExecute = new SpringbootMailExecute();
            mailExecute.setMailConfig(mailConfig);
            mailExecute.setJavaMailSender(javaMailSender);
            System.out.println("-----------调用springbootMailExecute--------------");
            return mailExecute;
        }

        @Bean
        @ConditionalOnMissingBean(MailExecute.class)
        public JavaMailExecute javaMailExecute(MailConfig mailConfig) {
            JavaMailExecute mailExecute = new JavaMailExecute();
            mailExecute.setMailConfig(mailConfig);
            System.out.println("-----------调用javaMailExecute--------------");
            return mailExecute;
        }


    }


    // 消息模板配置装载
    @Configuration
    @ConditionalOnProperty(prefix = TemplateConfig.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(TemplateConfig.class)
    /**
     *  也可以在TemplateConfig上用 @Component 注入到容器中。
     *  当通过jar包方式导入到项目时，自动扫描基本上不会去扫描jar包中的类(@ComponentScan 自动扫描是项目的包路径，不会配置jar包中的包路径)
     *  假如扫描到了，但是这个类也不一定就需要注入到容器当中去，所以通过条件注解去控制是否注入。
     *  @EnableConfigurationProperties 只能写在类上面，在@Bean的方法外嵌套一个类。
     */
    public static class TemplateConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AlarmTemplateProvider alarmService(TemplateConfig templateConfig) {
            return new YamlAlarmTemplateProvider(templateConfig);
        }
    }

    @Bean
    public AlarmAspect alarmAspect(@Autowired(required = false) AlarmTemplateProvider alarmService) {
        return new AlarmAspect(alarmService);
    }
}
