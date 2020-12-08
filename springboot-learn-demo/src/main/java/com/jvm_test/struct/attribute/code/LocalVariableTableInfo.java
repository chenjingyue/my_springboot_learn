package com.jvm_test.struct.attribute.code;

import lombok.Data;

@Data
public class LocalVariableTableInfo {

    /**
     * 字节码行号
     */
    private int startPc;
    private int length;
    private int nameIndex;
    private int descriptorIndex;
    private int index;

    public LocalVariableTableInfo(int startPc, int length, int nameIndex, int descriptorIndex, int index) {
        this.startPc = startPc;
        this.length = length;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.index = index;
    }
}
