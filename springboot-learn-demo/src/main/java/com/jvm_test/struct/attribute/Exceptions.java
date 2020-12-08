package com.jvm_test.struct.attribute;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Exceptions extends AttributeInfo {

    /**
     * 异常数量
     */
    private int numberOfExceptions;

    /**
     * 指向常量池中 CONSTANT_Class_info 型常量的索引，代表了该异常类型
     */
    private List<Integer> exceptionIndexTable = new ArrayList<>();


}
