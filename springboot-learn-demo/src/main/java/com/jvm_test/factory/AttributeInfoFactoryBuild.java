package com.jvm_test.factory;

import com.jvm_test.struct.constant.MethodAttributeNameConstant;

public class AttributeInfoFactoryBuild {

//    private byte[] bytes;
//    private String indexValue;
//
//    public AttributeInfoFactoryBuild(byte[] bytes, String indexValue) {
//        this.bytes = bytes;
//        this.indexValue = indexValue;
//    }

    public static AttributeInfoFactory getAttributeInfoFactory(byte[] bytes, String indexValue) {
        AttributeInfoFactory instanceFactory = null;
        switch (indexValue) {
            case MethodAttributeNameConstant.CODE:
                instanceFactory = new CodeAttributeFactory(bytes);
                break;
            case MethodAttributeNameConstant.METHOD_PARAMETERS:
                instanceFactory = new MethodParametersFactory(bytes);
                break;
            case MethodAttributeNameConstant.EXCEPTIONS:
                instanceFactory = new ExceptionsFactory(bytes);
                break;
            case MethodAttributeNameConstant.CONSTANT_VALUE:
                instanceFactory = new ConstantValueAttributeFactory(bytes);
                break;
            case MethodAttributeNameConstant.SOURCE_FILE:
                instanceFactory = new SourceFileFactory(bytes);
                break;
            case MethodAttributeNameConstant.INNER_CLASSES:
                instanceFactory = new InnerCLassesFactory(bytes);
                break;
        }
        return instanceFactory;
    }
}
