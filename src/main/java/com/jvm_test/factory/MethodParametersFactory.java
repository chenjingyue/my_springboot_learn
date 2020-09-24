package com.jvm_test.factory;

import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.attribute.Exceptions;
import com.jvm_test.struct.attribute.MethodParameters;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.utils.ByteUtil;

import java.util.List;

public class MethodParametersFactory implements AttributeInfoFactory {

    private byte[] bytes;

    public MethodParametersFactory(byte[] bytes) {
        this.bytes = bytes;
    }


    @Override
    public AttributeInfo getAttributeInfoInstance() {
        return getMethodParameters();
    }

    public MethodParameters getMethodParameters() {
        int offset = 0;
        MethodParameters methodParameters = new MethodParameters();
        List<MethodParameters.Parameters> parametersList = methodParameters.getParameters();

        int parametersCount = ByteUtil.readByteArrayToIntFromOffset(bytes,offset, AccessFlagConstant.PARAMETERS_COUNT);
        offset += AccessFlagConstant.PARAMETERS_COUNT;
        methodParameters.setParametersCount(parametersCount);
        if (parametersCount > 0) {
            for (int i = 0; i < parametersCount; i++) {
                int nameIndex = ByteUtil.readByteArrayToIntFromOffset(bytes,offset, AccessFlagConstant.NAME_INDEX);
                offset += AccessFlagConstant.NAME_INDEX;
                int accessFlags = ByteUtil.readByteArrayToIntFromOffset(bytes,offset, AccessFlagConstant.ACCESS_FLAGS);
                offset += AccessFlagConstant.ACCESS_FLAGS;
                MethodParameters.Parameters parameters = new MethodParameters.Parameters(nameIndex, accessFlags);
                parametersList.add(parameters);
            }
        }
        return methodParameters;
    }


}
