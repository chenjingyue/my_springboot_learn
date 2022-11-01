package com.cy.springbootlearndemo;

import com.cy.springbootlearndemo.config.HelloImportBeanDefinitionRegistrar;
import com.cy.springbootlearndemo.config.MyTypeExcludeFilter;
import com.cy.springbootlearndemo.mapper.BaseMapper;
import com.cy.springbootlearndemo.model.QueryCondition;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.observables.AsyncOnSubscribe;
import rx.observables.SyncOnSubscribe;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@ComponentScan(excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeExcludeFilter.class)})
//@MapperScan(basePackages = "com.cy.springbootlearndemo.mapper", markerInterface = BaseMapper.class)
@EnableSwagger2
//@EnableFeignClients
//@EnableDiscoveryClient
//@Import(HelloImportBeanDefinitionRegistrar.class)
public class SpringbootLearnDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLearnDemoApplication.class, args);

//		AnnotationConfigApplicationContext ac =
//				new AnnotationConfigApplicationContext(MySpringbootLearnApplication.class);
//        Observable<String> defer = Observable.defer(new Func0<Observable<String>>() { // #defer(...)
//            @Override
//            public Observable<String> call() {
//                String name = Math.random() > 0.5 ? "小明" : "小贾"; // 随机名字
//                return Observable.just(name);
//            }
//        });
//        defer.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//        });
//
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//
//            }
//        });
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//	@Bean
//	public ServletWebServerFactory servletWebServerFactory() {
//		TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
//		tomcatServletWebServerFactory.setPort(8989);
//		return tomcatServletWebServerFactory;
//	}

}
