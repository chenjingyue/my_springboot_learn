package com.example.my_springboot_learn.base.config;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * @Description: [描述该类的功能]
 * @Author: ZhangDingHui
 * @Date: Created int 下午4:21 2019/7/10
 */
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
//@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class Swagger2Config {

    @Bean(name = "initRestApi")
    public Docket initRestApi() {
        ParameterBuilder tokenBuilder = new ParameterBuilder();
        tokenBuilder.name("token").description("token凭证").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        ParameterBuilder platformType = new ParameterBuilder();
        platformType.name("platformType").description("平台类型").modelRef(new ModelRef("string")).parameterType("header").required(true).build();

        List<Parameter> aParameters = Lists.newArrayList();
        aParameters.add(tokenBuilder.build());
        aParameters.add(platformType.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(aParameters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.my_springboot_learn.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("【manager】API文档")
                .description("by 哈哈哈")
                .contact(new Contact("哈哈哈", null, null))
                .version("版本号:v1.0")
                .build();
    }
}
