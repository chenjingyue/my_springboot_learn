package com.jvm_test.struct.attribute;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InnerCLasses extends AttributeInfo{
    private int numberOfClasses;

    private List<Classes> classes = new ArrayList();

    @Data
    public static class Classes {

        private int innerClassInfoIndex;
        private int outerClassInfoIndex;
        private int innerNameIndex;
        private int innerClassAccessFlags;

        public Classes(int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex, int innerClassAccessFlags) {
            this.innerClassInfoIndex = innerClassInfoIndex;
            this.outerClassInfoIndex = outerClassInfoIndex;
            this.innerNameIndex = innerNameIndex;
            this.innerClassAccessFlags = innerClassAccessFlags;
        }
    }

}
