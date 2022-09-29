package com.alarm.spring.boot.autoconfigure.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(PandaScannerRegistrar.class)
public @interface PandaScan {

    String[] value() default {};
    String name() default "";

    String[] basePackages() default {};

    Class annotationClass() default Annotation.class;
}
