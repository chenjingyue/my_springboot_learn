package com.jvm_test.struct.attribute;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MethodParameters extends AttributeInfo {

    private int parametersCount;

    private List<Parameters> parameters = new ArrayList<>();


    @Data
    public static class Parameters {
        private int nameIndex;
        private int accessFlags;

        public Parameters(int nameIndex, int accessFlags) {
            this.nameIndex = nameIndex;
            this.accessFlags = accessFlags;
        }
    }

}
