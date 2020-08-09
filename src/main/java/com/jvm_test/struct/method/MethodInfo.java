package com.jvm_test.struct.method;

import com.jvm_test.struct.attribute.AttributeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MethodInfo {

    private int accessFlags;
    private int nameIndex;
    private int descriptorIndex ;
    private int attributesCount ;

    private List<AttributeInfo> attributeInfoList = new ArrayList<>();

}
