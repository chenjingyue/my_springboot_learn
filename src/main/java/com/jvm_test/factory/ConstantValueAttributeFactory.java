package com.jvm_test.factory;

import com.jvm_test.Decompile;
import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.attribute.ConstantValue;
import com.jvm_test.struct.attribute.code.*;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.struct.constant.ConstantAttribute;
import com.jvm_test.struct.constant.ConstantType;
import com.jvm_test.utils.ByteUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ConstantValueAttributeFactory implements AttributeInfoFactory {

    private byte[] bytes;

    public ConstantValueAttributeFactory(byte[] bytes) {
        this.bytes = bytes;
    }


    @Override
    public AttributeInfo getAttributeInfoInstance() {
        return getConstantValueAttribute();
    }

    public ConstantValue getConstantValueAttribute() {

        int constantvalueIndex = ByteUtil.readByteArrayToIntFromOffset(bytes, AccessFlagConstant.CONSTANT_VALUE_INDEX);
        ConstantValue constantValue = new ConstantValue(constantvalueIndex);
        return constantValue;
    }




}
