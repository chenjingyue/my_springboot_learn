package com.jvm_test.struct.attribute.code;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineNumberTable extends CodeAttributeTable{

    private int lineNumberTableLength;
    private List<LineNumberTableInfo> lineNumberInfo = new ArrayList<>();

}

