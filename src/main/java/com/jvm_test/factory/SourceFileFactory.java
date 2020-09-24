package com.jvm_test.factory;

import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.attribute.SourceFile;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.utils.ByteUtil;

public class SourceFileFactory implements AttributeInfoFactory {

    private byte[] bytes;

    public SourceFileFactory(byte[] bytes) {
        this.bytes = bytes;
    }


    @Override
    public AttributeInfo getAttributeInfoInstance() {
        return getSourceFile();
    }

    public SourceFile getSourceFile() {
        int sourcefileIndex = ByteUtil.readByteArrayToIntFromOffset(bytes, AccessFlagConstant.SOURCEFILE_INDEX);
        SourceFile sourceFile = new SourceFile(sourcefileIndex);
        return sourceFile;
    }


}
