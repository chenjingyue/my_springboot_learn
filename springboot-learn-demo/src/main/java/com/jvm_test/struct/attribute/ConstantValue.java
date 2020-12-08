package com.jvm_test.struct.attribute;

import lombok.Data;

@Data
public class ConstantValue extends AttributeInfo {
    private int constantvalueIndex;

    public ConstantValue(int constantvalueIndex) {
        this.constantvalueIndex = constantvalueIndex;
    }
}
