package com.jvm_test.struct.attribute.code;

import lombok.Data;

@Data
public class LocalVariableTable extends CodeAttributeTable {

    private int localVariableTableLength;
    private LocalVariableTableInfo localVariableTableInfo;
}

