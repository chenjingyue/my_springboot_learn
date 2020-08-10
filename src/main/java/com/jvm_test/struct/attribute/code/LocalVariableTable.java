package com.jvm_test.struct.attribute.code;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocalVariableTable extends CodeAttributeTable {

    private int localVariableTableLength;
    private List<LocalVariableTableInfo> localVariableTableInfo = new ArrayList<>();
}

