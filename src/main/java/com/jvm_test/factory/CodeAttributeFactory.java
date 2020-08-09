package com.jvm_test.factory;

import com.jvm_test.Decompile;
import com.jvm_test.struct.attribute.CodeAttribute;
import com.jvm_test.struct.attribute.code.CodeAttributeTable;
import com.jvm_test.struct.attribute.code.LineNumberTable;
import com.jvm_test.struct.attribute.code.LineNumberTableInfo;
import com.jvm_test.struct.attribute.code.LocalVariableTable;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.struct.constant.ConstantAttribute;
import com.jvm_test.struct.constant.ConstantType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CodeAttributeFactory {

    private byte[] bytes;
    private int offset;

    public CodeAttributeFactory(byte[] bytes) {
        this.bytes = bytes;
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
                byte[] lineNumberTableBytes = getBytes(this.bytes, codeAttrLength);
                // 判断是否LineNumberTable LocalVariableTable属性
                ConstantType constantType = Decompile.CONSTANT_POOL.get(codeAttrNameIndex);
                ConstantAttribute attribute = constantType.getAttrList().get(1);

                if (StringUtils.equalsIgnoreCase("LineNumberTable", (String) attribute.getValue())) {
                    LineNumberTable lineNumberTable = new LineNumberTable();
                    lineNumberTable.setAttributeNameIndex(codeAttrNameIndex);
                    lineNumberTable.setAttributeLength(codeAttrLength);
                    lineNumberTable.setType(1);
                    List<LineNumberTableInfo> lineNumberInfo = lineNumberTable.getLineNumberInfo();

                    int lineNumberTableLength = readByteArrayToIntFromOffset(lineNumberTableBytes, 0,
                            AccessFlagConstant.LINE_NUMBER_TABLE_LENGTH);
                    int offsetTemp = AccessFlagConstant.LINE_NUMBER_TABLE_LENGTH;
                    lineNumberTable.setLineNumberTableLength(lineNumberTableLength);

                    for (int j = 0; j < lineNumberTableLength; j++) {
                        int startPc = readByteArrayToIntFromOffset(lineNumberTableBytes, offsetTemp, AccessFlagConstant.START_PC);
                        offsetTemp += AccessFlagConstant.START_PC;
                        int lineNumber = readByteArrayToIntFromOffset(lineNumberTableBytes, offsetTemp, AccessFlagConstant.LINE_NUMBER);
                        offsetTemp += AccessFlagConstant.LINE_NUMBER;
                        LineNumberTableInfo lineNumberTableInfo = new LineNumberTableInfo(startPc, lineNumber);
                        lineNumberInfo.add(lineNumberTableInfo);
                    }
                    codeAttributeList.add(lineNumberTable);

                } else if (StringUtils.equalsIgnoreCase("LocalVariableTable", (String) attribute.getValue())) {
                    LocalVariableTable localVariableTable = new LocalVariableTable();
                    localVariableTable.setType(2);
                    int localVariableTableLength = readByteArrayToIntFromOffset(lineNumberTableBytes, 0,
                            AccessFlagConstant.LOCAL_VARIABLE_TABLE_LENGTH);
                    int offsetTemp = AccessFlagConstant.LOCAL_VARIABLE_TABLE_LENGTH;
                    localVariableTable.setLocalVariableTableLength(localVariableTableLength);

                    for (int j = 0; j < localVariableTableLength; j++) {
//                    int startPc = readByteArrayToIntFromOffset(lineNumberTableBytes, offsetTemp, AccessFlagConstant.START_PC);
//                    offsetTemp += AccessFlagConstant.START_PC;
//                    int lineNumber = readByteArrayToIntFromOffset(lineNumberTableBytes, offsetTemp, AccessFlagConstant.LINE_NUMBER);
//                    offsetTemp += AccessFlagConstant.LINE_NUMBER;
//                    LineNumberTableInfo lineNumberTableInfo = new LineNumberTableInfo(startPc, lineNumber);
//                    lineNumberInfo.add(lineNumberTableInfo);
                    }

                    codeAttributeList.add(localVariableTable);
                }

//
//            for (int i = 0; i < lineNumberTableLength; i++) {
//                if (StringUtils.equalsIgnoreCase("LineNumberTable", (String) attribute.getValue())) {
//                    LineNumberTable lineNumberTable = new LineNumberTable();
//                    lineNumberTable.setLineNumberTableLength(lineNumberTableLength);
//
//                    int startPc = readByteArrayToIntFromOffset(lineNumberTableBytes, offsetTemp, AccessFlagConstant.START_PC);
//                    offsetTemp += AccessFlagConstant.START_PC;
//                    int lineNumber = readByteArrayToIntFromOffset(lineNumberTableBytes, offsetTemp, AccessFlagConstant.LINE_NUMBER);
//                    offsetTemp += AccessFlagConstant.LINE_NUMBER;
//                    LineNumberTableInfo lineNumberTableInfo = new LineNumberTableInfo(startPc, lineNumber);
//
//                    lineNumberTable.setLineNumberInfo(lineNumberTable);
//                    codeAttributeTable = lineNumberTable;
//
//                    codeAttributeList.add(codeAttributeTable);
//                }
//            }


            }
        }


        return code;
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

    private byte[] getBytesFromOffset(byte[] bytes, int offset, int len) {
        if (len <= 0) {
            return new byte[0];
        }
        if ((offset + len) > bytes.length) {
            return new byte[0];
        }
        byte[] resultBytes = new byte[len];
        for (int i = offset; i < len + offset; i++) {
            resultBytes[i - offset] = bytes[i];
        }
        return resultBytes;
    }


    public int byteArrayToInt(byte[] bytes) {
        int value = 0;
        int len = bytes.length;
        // 由高位到低位
        for (int i = 0; i < len; i++) {
            int shift = (len - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

    public int readByteArrayToInt(byte[] bytes, int len) {
        return byteArrayToInt(getBytes(bytes, len));
    }

    public int readByteArrayToIntFromOffset(byte[] bytes, int offset, int len) {
        return byteArrayToInt(getBytesFromOffset(bytes, offset, len));
    }
}
