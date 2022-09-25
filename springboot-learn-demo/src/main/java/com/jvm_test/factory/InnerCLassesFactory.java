package com.jvm_test.factory;

import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.attribute.InnerCLasses;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.utils.ByteUtil;

import java.util.List;

public class InnerCLassesFactory implements AttributeInfoFactory {

    private byte[] bytes;

    public InnerCLassesFactory(byte[] bytes) {
        this.bytes = bytes;
    }


    @Override
    public AttributeInfo getAttributeInfoInstance() {
        return getInnerCLasses();
    }

    public InnerCLasses getInnerCLasses() {
        InnerCLasses innerCLasses = new InnerCLasses();
        int offset = 0;
        int numberOfClasses = ByteUtil.readByteArrayToIntFromOffset(bytes, offset, AccessFlagConstant.NUMBER_OF_CLASSES);
        offset += AccessFlagConstant.NUMBER_OF_CLASSES;
        innerCLasses.setNumberOfClasses(numberOfClasses);
        List<InnerCLasses.Classes> classesList = innerCLasses.getClasses();
        if (numberOfClasses > 0) {
            for (int i = 0; i < numberOfClasses; i++) {
                int innerClassInfoIndex = ByteUtil.readByteArrayToIntFromOffset(bytes, offset, AccessFlagConstant.INNER_CLASS_INFO_INDEX);
                offset += AccessFlagConstant.INNER_CLASS_INFO_INDEX;
                int outerClassInfoIndex = ByteUtil.readByteArrayToIntFromOffset(bytes, offset, AccessFlagConstant.OUTER_CLASS_INFO_INDEX);
                offset += AccessFlagConstant.OUTER_CLASS_INFO_INDEX;
                int innerNameIndex = ByteUtil.readByteArrayToIntFromOffset(bytes, offset, AccessFlagConstant.INNER_NAME_INDEX);
                offset += AccessFlagConstant.INNER_NAME_INDEX;
                int innerClassAccessFlags = ByteUtil.readByteArrayToIntFromOffset(bytes, offset, AccessFlagConstant.INNER_CLASS_ACCESS_FLAGS);
                offset += AccessFlagConstant.INNER_CLASS_ACCESS_FLAGS;
                InnerCLasses.Classes classes = new InnerCLasses.Classes(innerClassInfoIndex, outerClassInfoIndex, innerNameIndex, innerClassAccessFlags);
                classesList.add(classes);
            }
        }
        return innerCLasses;
    }


}
