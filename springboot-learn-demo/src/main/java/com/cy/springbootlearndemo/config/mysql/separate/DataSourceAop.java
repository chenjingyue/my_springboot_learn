package com.cy.springbootlearndemo.config.mysql.separate;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
//@Component
public class DataSourceAop {

    @Pointcut("!@annotation(com.cy.springbootlearndemo.config.anno.Master) " +
            "&& (execution(* com.cy.springbootlearndemo.mapper..*.select*(..)) " +
            "|| execution(* com.cy.springbootlearndemo.mapper..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.cy.springbootlearndemo.config.anno.Master) " +
            "|| execution(* com.cy.springbootlearndemo.mapper..*.insert*(..)) " +
            "|| execution(* com.cy.springbootlearndemo.mapper..*.add*(..)) " +
            "|| execution(* com.cy.springbootlearndemo.mapper..*.update*(..)) " +
            "|| execution(* com.cy.springbootlearndemo.mapper..*.edit*(..)) " +
            "|| execution(* com.cy.springbootlearndemo.mapper..*.delete*(..)) " +
            "|| execution(* com.cy.springbootlearndemo.mapper..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.slave();
//        DBContextHolder.master();
    }


    /**
     * 另一种写法：if...else...  判断哪些需要读从数据库，其余的走主数据库
     */
//    @Before("execution(* com.cy.springbootlearndemo.mapper.impl.*.*(..))")
//    public void before(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
//            DBContextHolder.slave();
//        }else {
//            DBContextHolder.master();
//        }
//    }
}
