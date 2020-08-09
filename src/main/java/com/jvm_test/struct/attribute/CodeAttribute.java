package com.jvm_test.struct.attribute;

import com.jvm_test.struct.attribute.code.CodeAttributeTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CodeAttribute extends AttributeInfo {
//    private int attributeNameIndex;
//    private int attributeLength;

    private int maxStack;
    private int maxLocals;
    private int codeLength;
    private Object code;

    private int exceptionTableLength;
    private Object exceptionTable;

    private int attributeCount;


    private List<CodeAttributeTable> codeAttributeList = new ArrayList();


}
