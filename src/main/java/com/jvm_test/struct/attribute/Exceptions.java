package com.jvm_test.struct.attribute;

public class Exceptions extends AttributeInfo {

    /**
     * 异常数量
     */
    private int numberOfExceptions;

    /**
     * 指向常量池中 CONSTANT_Class_info 型常量的索引，代表了该异常类型
     */
    private int exceptionIndexTable;

    public Exceptions(int numberOfExceptions, int exceptionIndexTable) {
        this.numberOfExceptions = numberOfExceptions;
        this.exceptionIndexTable = exceptionIndexTable;
    }
}
