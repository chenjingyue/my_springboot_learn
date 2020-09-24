package com.jvm_test.struct;

import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.constant.ConstantType;
import com.jvm_test.struct.field.FieldInfo;
import com.jvm_test.struct.method.MethodInfo;
import lombok.Data;

import java.util.List;

@Data
public class ClassInfo {

    private int magic;

    private int minorVersion;
    private int majorVersion;

    private int constantPoolCount;
    private List<ConstantType> constantPoolList;

    private AccessFlagsInfo accessFlags;

    private int thisClass;
    private int superClass;

    private int interfacesCount;
    private List<Integer> interfaces;

    private int fieldsCount;
    private List<FieldInfo> fields;

    private int methodsCount;
    private List<MethodInfo> methods;


    private int attributesCount;
    private List<AttributeInfo> attributes;


}
