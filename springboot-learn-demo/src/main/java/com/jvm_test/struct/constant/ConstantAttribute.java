package com.jvm_test.struct.constant;

import lombok.Data;

@Data
public class ConstantAttribute {

    private String name;
    private Object value;

    /**
     * 属性的字节长度
     */
    private int len;

    /**
     * 1:index （索引）;2:number（数值）; 3: string
     */
    private int type;

    /**
     * 索引地址对应的值
     */
    private String indexValue;

    public ConstantAttribute() {
    }

    public ConstantAttribute(String name, int len, int type) {
        this.name = name;
        this.len = len;
        this.type = type;
    }
}
