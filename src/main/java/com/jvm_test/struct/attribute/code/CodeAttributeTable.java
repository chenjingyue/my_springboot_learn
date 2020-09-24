package com.jvm_test.struct.attribute.code;

import com.jvm_test.struct.attribute.AttributeInfo;
import lombok.Data;

@Data
public class CodeAttributeTable {

    private int attributeNameIndex ;
    private int attributeLength ;
    /**
     * 1:LineNumberTable; 2:LocalVariableTable
     */
    private int type;
}

