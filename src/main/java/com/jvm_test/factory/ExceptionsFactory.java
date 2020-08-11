package com.jvm_test.factory;

import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.attribute.Exceptions;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.utils.ByteUtil;

import java.util.List;

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

        Exceptions exceptions = new Exceptions();
        int offset = 0;
        int numberOfExceptions = ByteUtil.readByteArrayToIntFromOffset(bytes, offset, AccessFlagConstant.NUMBER_OF_EXCEPTIONS);
        offset += AccessFlagConstant.NUMBER_OF_EXCEPTIONS;
        exceptions.setNumberOfExceptions(numberOfExceptions);
        List<Integer> exceptionIndexTable1 = exceptions.getExceptionIndexTable();
        if (numberOfExceptions > 0) {
            for (int i = 0; i < numberOfExceptions; i++) {
                exceptionIndexTable1.add(ByteUtil.readByteArrayToIntFromOffset(bytes, offset, AccessFlagConstant.EXCEPTION_INDEX_TABLE));
                offset += AccessFlagConstant.EXCEPTION_INDEX_TABLE;
            }
        }
        return exceptions;
    }


}
