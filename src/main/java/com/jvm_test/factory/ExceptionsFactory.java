package com.jvm_test.factory;

import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.attribute.ConstantValue;
import com.jvm_test.struct.attribute.Exceptions;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.utils.ByteUtil;

public class ExceptionsFactory implements AttributeInfoFactory {

    private byte[] bytes;

    public ExceptionsFactory(byte[] bytes) {
        this.bytes = bytes;
    }


    @Override
    public AttributeInfo getAttributeInfoInstance() {
        return getExceptions();
    }

    public Exceptions getExceptions() {

        int numberOfExceptions = ByteUtil.readByteArrayToIntFromOffset(bytes, AccessFlagConstant.NUMBER_OF_EXCEPTIONS);
        if (numberOfExceptions > 0) {
            for (int i = 0; i < numberOfExceptions; i++) {
                int exceptionIndexTable = ByteUtil.readByteArrayToIntFromOffset(bytes, AccessFlagConstant.EXCEPTION_INDEX_TABLE);

            }
        }
        Exceptions constantValue = new Exceptions(1,2);
        return constantValue;
    }


}
