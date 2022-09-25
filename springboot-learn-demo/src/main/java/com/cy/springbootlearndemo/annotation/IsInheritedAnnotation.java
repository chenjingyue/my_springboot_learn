package com.cy.springbootlearndemo.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IsInheritedAnnotation {
}
