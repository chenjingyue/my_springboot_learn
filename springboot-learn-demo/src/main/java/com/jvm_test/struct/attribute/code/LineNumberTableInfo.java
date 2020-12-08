package com.jvm_test.struct.attribute.code;

import lombok.Data;

@Data
public class LineNumberTableInfo {

    /**
     * 字节码行号
     */
    private int startPc;
    /**
     * Java源码行号
     */
    private int lineNumber;


    public LineNumberTableInfo(int startPc, int lineNumber) {
        this.startPc = startPc;
        this.lineNumber = lineNumber;
    }
}
