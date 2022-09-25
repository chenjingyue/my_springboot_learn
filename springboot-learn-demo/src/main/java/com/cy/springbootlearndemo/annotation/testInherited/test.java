package com.cy.springbootlearndemo.annotation.testInherited;

import com.cy.springbootlearndemo.annotation.testInherited.classs.MyInheritedClass;
import com.cy.springbootlearndemo.annotation.testInherited.interfaces.IInheritedInterface;
import com.cy.springbootlearndemo.annotation.testInherited.interfaces.IInheritedInterfaceChild;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        testInherited();
//        System.out.println(InheritedBase.class.isAnnotationPresent(Inherited.class));
    }


    /**
     * 得到以下结论：
     * 类继承关系中@Inherited的作用
     * 类继承关系中，子类会继承父类使用的注解中被@Inherited修饰的注解
     *
     * 接口继承关系中@Inherited的作用
     * 接口继承关系中，子接口不会继承父接口中的任何注解，不管父接口中使用的注解有没有被@Inherited修饰
     *
     * 类实现接口关系中@Inherited的作用
     * 类实现接口时不会继承任何接口中定义的注解
     */
    public static void testInherited() {
        {
            Annotation[] annotations = MyInheritedClass.class.getAnnotations();
            System.out.println("---------");
            System.out.println(Arrays.asList(annotations));
        }
        {
            Annotation[] annotations = MyInheritedClassUseInterface.class.getAnnotations();
            System.out.println("---------");
            System.out.println(Arrays.asList(annotations));
        }
        {
            Annotation[] annotations = IInheritedInterface.class.getAnnotations();
            System.out.println("---------");
            System.out.println(Arrays.asList(annotations));
        }
        {
            Annotation[] annotations = IInheritedInterfaceChild.class.getAnnotations();
            System.out.println("---------");
            System.out.println(Arrays.asList(annotations));
        }
    }
}
