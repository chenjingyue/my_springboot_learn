package com.jvm_test.factory;

import com.jvm_test.struct.attribute.AttributeInfo;


public interface AttributeInfoFactory {

//    private byte[] bytes;
//    private String indexValue;

//    public AttributeInfoFactory(byte[] bytes,String indexValue) {
//        this.bytes = bytes;
//        this.indexValue = indexValue;
//    }

    public AttributeInfo getAttributeInfoInstance();

//    public AttributeInfoFactory getAttributeInfoFactory(String indexValue) {
//        AttributeInfoFactoryBuild instanceFactory=null;
//        switch (indexValue){
//            case MethodAttributeNameConstant.CODE:
//                instanceFactory = new CodeAttributeFactory(bytes);
//                break;
//            case MethodAttributeNameConstant.METHOD_PARAMETERS:
////                attributeInfo = getMethodParameters(attributeBytes);;
//                break;
//            case MethodAttributeNameConstant.EXCEPTIONS:
//                ;
//                break;
//        }
//
//        return instanceFactory;
//
//    }

//    @Override
//    public T getInstance() {
//        return null;
//    }

}
