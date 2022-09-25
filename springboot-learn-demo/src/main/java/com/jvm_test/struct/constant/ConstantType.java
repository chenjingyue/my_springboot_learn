package com.jvm_test.struct.constant;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ConstantType {

    private String name ;

    private int type;

    private List<ConstantAttribute> attrList = new ArrayList<>();


}
