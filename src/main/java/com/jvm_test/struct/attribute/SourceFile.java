package com.jvm_test.struct.attribute;

import lombok.Data;

@Data
public class SourceFile extends AttributeInfo{
    private int sourcefileIndex;

    public SourceFile(int sourcefileIndex) {
        this.sourcefileIndex = sourcefileIndex;
    }
}
