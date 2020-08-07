package com.jvm_test.constant;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ConstantType {

    private String name ;

    private List<Map> attrList = new ArrayList<>();


}
