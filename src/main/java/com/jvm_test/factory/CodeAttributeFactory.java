package com.jvm_test.factory;

import com.jvm_test.Decompile;
import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.attribute.code.CodeAttribute;
import com.jvm_test.struct.attribute.code.*;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.struct.constant.ConstantAttribute;
import com.jvm_test.struct.constant.ConstantType;
import com.jvm_test.utils.ByteUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CodeAttributeFactory implements AttributeInfoFactory {

    private byte[] bytes;
    private int offset;

    public CodeAttributeFactory(byte[] bytes) {
        this.bytes = bytes;
    }



    @Override
    public AttributeInfo getAttributeInfoInstance() {
        return getCodeAttribute();
    }

    public CodeAttribute getCodeAttribute() {
        CodeAttribute code = new CodeAttribute();
        final List<CodeAttributeTable> codeAttributeList = code.getCodeAttributeList();

        int maxStack = readByteArrayToInt(bytes, AccessFlagConstant.MAX_STACK);
        code.setMaxStack(maxStack);
        int maxLocals = readByteArrayToInt(bytes, AccessFlagConstant.MAX_LOCALS);
        code.setMaxLocals(maxLocals);
        int codeLength = readByteArrayToInt(bytes, AccessFlagConstant.CODE_LENGTH);
        code.setCodeLength(codeLength);
        byte[] codeBytes = getBytes(this.bytes, codeLength);
        code.setCode(codeBytes);
        int exceptionTableLength = readByteArrayToInt(this.bytes, AccessFlagConstant.EXCEPTION_TABLE_LENGTH);
        code.setExceptionTableLength(exceptionTableLength);
        byte[] exceptionInfoBytes = getBytes(this.bytes, exceptionTableLength);
        code.setExceptionTable(exceptionInfoBytes);
        int attributeCount = readByteArrayToInt(bytes, AccessFlagConstant.ATTRIBUTE_COUNT);
        code.setAttributeCount(attributeCount);
        if (attributeCount > 0) {
            for (int i = 0; i < attributeCount; i++) {
                int codeAttrNameIndex = readByteArrayToInt(bytes, AccessFlagConstant.ATTRIBUTE_NAME_INDEX);
                int codeAttrLength = readByteArrayToInt(bytes, AccessFlagConstant.ATTRIBUTE_LENGTH);
                byte[] codeAttrInfoBytes = getBytes(this.bytes, codeAttrLength);
                // 判断是否LineNumberTable LocalVariableTable属性
                ConstantType constantType = Decompile.CONSTANT_POOL.get(codeAttrNameIndex);
                ConstantAttribute attribute = constantType.getAttrList().get(1);
                String value = (String) attribute.getValue();
                CodeAttributeTable codeAttributeTable = null;
                if (StringUtils.equalsIgnoreCase("LineNumberTable", value)) {
                    codeAttributeTable = getLineNumberTable(codeAttrInfoBytes);
                } else if (StringUtils.equalsIgnoreCase("LocalVariableTable", value)) {
                    codeAttributeTable = getLocalVariableTable(codeAttrInfoBytes);
                }
                if (null != codeAttributeTable) {
                    codeAttributeTable.setAttributeNameIndex(codeAttrNameIndex);
                    codeAttributeTable.setAttributeLength(codeAttrLength);
                    codeAttributeList.add(codeAttributeTable);
                }
            }
        }
        return code;
    }

    private LocalVariableTable getLocalVariableTable(byte[] codeAttrInfoBytes) {
        LocalVariableTable localVariableTable = new LocalVariableTable();
        List<LocalVariableTableInfo> localVariableTableInfoList = localVariableTable.getLocalVariableTableInfo();
        if (null == localVariableTableInfoList) {
            localVariableTableInfoList = new ArrayList<>();
            localVariableTable.setLocalVariableTableInfo(localVariableTableInfoList);
        }
        localVariableTable.setType(2);
        int localVariableTableLength = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, 0,
                AccessFlagConstant.LOCAL_VARIABLE_TABLE_LENGTH);
        int offsetTemp = AccessFlagConstant.LOCAL_VARIABLE_TABLE_LENGTH;
        localVariableTable.setLocalVariableTableLength(localVariableTableLength);

        for (int j = 0; j < localVariableTableLength; j++) {
            int startPc = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, offsetTemp, AccessFlagConstant.START_PC);
            offsetTemp += AccessFlagConstant.START_PC;
            int length = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, offsetTemp, AccessFlagConstant.LENGTH);
            offsetTemp += AccessFlagConstant.LENGTH;
            int nameIndex = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, offsetTemp, AccessFlagConstant.NAME_INDEX);
            offsetTemp += AccessFlagConstant.NAME_INDEX;
            int descriptorIndex = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, offsetTemp, AccessFlagConstant.DESCRIPTOR_INDEX);
            offsetTemp += AccessFlagConstant.DESCRIPTOR_INDEX;
            int index = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, offsetTemp, AccessFlagConstant.INDEX);
            offsetTemp += AccessFlagConstant.INDEX;

            LocalVariableTableInfo localVariableTableInfo = new LocalVariableTableInfo(startPc, length, nameIndex, descriptorIndex, index);
            localVariableTableInfoList.add(localVariableTableInfo);
        }
        return localVariableTable;
    }

    private LineNumberTable getLineNumberTable(byte[] codeAttrInfoBytes) {
        LineNumberTable lineNumberTable = new LineNumberTable();
        List<LineNumberTableInfo> lineNumberInfo = lineNumberTable.getLineNumberInfo();

        lineNumberTable.setType(1);
        int lineNumberTableLength = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, 0,
                AccessFlagConstant.LINE_NUMBER_TABLE_LENGTH);
        int offsetTemp = AccessFlagConstant.LINE_NUMBER_TABLE_LENGTH;
        lineNumberTable.setLineNumberTableLength(lineNumberTableLength);

        for (int j = 0; j < lineNumberTableLength; j++) {
            int startPc = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, offsetTemp, AccessFlagConstant.START_PC);
            offsetTemp += AccessFlagConstant.START_PC;
            int lineNumber = ByteUtil.readByteArrayToIntFromOffset(codeAttrInfoBytes, offsetTemp, AccessFlagConstant.LINE_NUMBER);
            offsetTemp += AccessFlagConstant.LINE_NUMBER;
            LineNumberTableInfo lineNumberTableInfo = new LineNumberTableInfo(startPc, lineNumber);
            lineNumberInfo.add(lineNumberTableInfo);
        }
        return lineNumberTable;
    }

    private byte[] getBytes(byte[] bytes, int len) {
        if (len <= 0) {
            return new byte[0];
        }
        byte[] resultBytes = new byte[len];
        for (int i = 0; i < len; i++) {
            resultBytes[i] = bytes[offset + i];
        }
        offset += len;
        return resultBytes;
    }




    public int readByteArrayToInt(byte[] bytes, int len) {
        return ByteUtil.byteArrayToInt(getBytes(bytes, len));
    }

}
