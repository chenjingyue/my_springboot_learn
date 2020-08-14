package com.jvm_test;

import com.alibaba.fastjson.JSONObject;
import com.jvm_test.factory.AttributeInfoFactory;
import com.jvm_test.factory.AttributeInfoFactoryBuild;
import com.jvm_test.struct.AccessFlagsInfo;
import com.jvm_test.struct.ClassInfo;
import com.jvm_test.struct.attribute.AttributeInfo;
import com.jvm_test.struct.clazz.ClassAccessFlag;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.struct.constant.ConstantAttribute;
import com.jvm_test.struct.constant.ConstantType;
import com.jvm_test.struct.field.FieldInfo;
import com.jvm_test.struct.method.MethodInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Decompile {

    private static ClassAccessFlag finalClassAccessFlag;

    private int offset = 0;
    private static Map<Integer, String> JDK_VERSION = new HashMap<>();
    public static List<ConstantType> CONSTANT_POOL = new ArrayList<>();
    public List<ConstantType> constantPool = new ArrayList<>();
    private static Map<Integer, ConstantType> CONSTANT_POOL_TYPE = new HashMap<>();
    // 类访问权限
    private static Map<Integer, ClassAccessFlag> CLASS_ACCESS_FLAGS = new LinkedHashMap<>();

    private static short[] class_flags = new short[]{1, 8, 16, 32, 512, 1024, 4096, 8192, 16384};
    //    private static String[] field_flags = new String[]{"0001","0002","0004","0008","0010","0020","0200","0400","1000","2000","4000"};
    private ClassInfo classInfo;

    static {
        JDK_VERSION.put(44, "1.0");
        JDK_VERSION.put(45, "1.1");
        JDK_VERSION.put(46, "1.2");
        JDK_VERSION.put(47, "1.3");
        JDK_VERSION.put(48, "1.4");
        JDK_VERSION.put(49, "1.5");
        JDK_VERSION.put(50, "1.6");
        JDK_VERSION.put(51, "1.7");
        JDK_VERSION.put(52, "1.8");


        CLASS_ACCESS_FLAGS.put(1, new ClassAccessFlag(1, AccessFlagConstant.ACC_PUBLIC, AccessFlagConstant.PUBLIC));
        CLASS_ACCESS_FLAGS.put(8, new ClassAccessFlag(8, AccessFlagConstant.ACC_STATIC, AccessFlagConstant.STATIC));
        CLASS_ACCESS_FLAGS.put(16, new ClassAccessFlag(16, AccessFlagConstant.ACC_FINAL, AccessFlagConstant.FINAL));
        CLASS_ACCESS_FLAGS.put(32, new ClassAccessFlag(32, AccessFlagConstant.ACC_SUPER));
        CLASS_ACCESS_FLAGS.put(512, new ClassAccessFlag(512, AccessFlagConstant.ACC_INTERFACE, AccessFlagConstant.INTERFACE));
        CLASS_ACCESS_FLAGS.put(1024, new ClassAccessFlag(1024, AccessFlagConstant.ACC_ABSTRACT, AccessFlagConstant.ABSTRACT));
        CLASS_ACCESS_FLAGS.put(4096, new ClassAccessFlag(4096, AccessFlagConstant.ACC_SYNTHETIC));
        CLASS_ACCESS_FLAGS.put(8192, new ClassAccessFlag(8192, AccessFlagConstant.ACC_ANNOTATION));
        CLASS_ACCESS_FLAGS.put(16384, new ClassAccessFlag(16384, AccessFlagConstant.ACC_ENUM, AccessFlagConstant.ENUM));


        ConstantType methodrefInfo = new ConstantType();
        methodrefInfo.setName("CONSTANT_Methodref_info");
        List<ConstantAttribute> attrList = methodrefInfo.getAttrList();
        ConstantAttribute attribute = new ConstantAttribute();
        attribute.setName("Class name");
        attribute.setType(1);
        attribute.setLen(2);
        attrList.add(attribute);
        attribute = new ConstantAttribute();
        attribute.setName("Name and type");
        attribute.setType(1);
        attribute.setLen(2);
        attrList.add(attribute);
        CONSTANT_POOL_TYPE.put(10, methodrefInfo);

        ConstantType Utf8Info = new ConstantType();
        Utf8Info.setName("CONSTANT_Utf8_info");
        attrList = Utf8Info.getAttrList();
        attribute = new ConstantAttribute();
        attribute.setName("Length of byte array");
        attribute.setLen(2);
        attrList.add(attribute);
        attribute = new ConstantAttribute();
        attribute.setName("String");
        attribute.setLen(1);
        attrList.add(attribute);
        CONSTANT_POOL_TYPE.put(1, Utf8Info);

        ConstantType fieldrefInfo = new ConstantType();
        fieldrefInfo.setName("CONSTANT_Fieldref_info");
        attrList = fieldrefInfo.getAttrList();
        attribute = new ConstantAttribute();
        attribute.setName("Class name");
        attribute.setType(1);
        attribute.setLen(2);
        attrList.add(attribute);
        attribute = new ConstantAttribute();
        attribute.setName("Name and type");
        attribute.setType(1);
        attribute.setLen(2);
        attrList.add(attribute);
        CONSTANT_POOL_TYPE.put(9, fieldrefInfo);

        ConstantType classInfo = new ConstantType();
        classInfo.setName("CONSTANT_Class_info");
        attrList = classInfo.getAttrList();
        attribute = new ConstantAttribute();
        attribute.setName("Class name");
        attribute.setType(1);
        attribute.setLen(2);
        ;
        attrList.add(attribute);
        CONSTANT_POOL_TYPE.put(7, classInfo);

        ConstantType nameAndTypeInfo = new ConstantType();
        nameAndTypeInfo.setName("CONSTANT_NameAndType_info");
        attrList = nameAndTypeInfo.getAttrList();
        attribute = new ConstantAttribute();
        attribute.setName("Name");
        attribute.setType(1);
        attribute.setLen(2);
        ;
        attrList.add(attribute);
        attribute = new ConstantAttribute();
        attribute.setName("Descriptor");
        attribute.setType(1);
        attribute.setLen(2);
        attrList.add(attribute);
        CONSTANT_POOL_TYPE.put(12, nameAndTypeInfo);

        ConstantType stringInfo = new ConstantType();
        stringInfo.setName("CONSTANT_String_info");
        attrList = stringInfo.getAttrList();
        attribute = new ConstantAttribute();
        attribute.setName("String");
        attribute.setType(1);
        attribute.setLen(2);
        attrList.add(attribute);
        CONSTANT_POOL_TYPE.put(8, stringInfo);
    }

    public static void main(String[] args) {

        Decompile decompile = new Decompile();
        decompile.start();

    }


    public void start() {
        classInfo = new ClassInfo();

        long start = System.currentTimeMillis();
        String classFilePath = "E:\\chenjingyue\\my_springboot_learn\\target\\classes\\com\\jvm_test\\Hello.class";
        File file = new File(classFilePath);
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            // 检查魔数
            checkMagic(bytes);
            // 检查版本
            checkVersion(bytes);

            // 解析常量池
            constantPool.add(null);
            parseConstant(bytes);
            classInfo.setConstantPoolCount(constantPool.size());
            classInfo.setConstantPoolList(constantPool);
            // 类访问控制权限
            accessFlags(bytes);
            // 类名
            thisAndSupperClass(bytes);
            // 接口
            parseInterface(bytes);
            // 解析成员属性
            parseFields(bytes);
            // 解析方法
            parseMethods(bytes);
            // 解析类属性
            parseClassAttributes(bytes);

            System.out.println("类字节码解析内容： " + JSONObject.toJSONString(classInfo));
            long end = System.currentTimeMillis();
            System.out.println("使用时间(ms)：" + (end - start) + "(ms)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseClassAttributes(byte[] bytes) {
        int attributeCount = readByteArrayToInt(bytes, AccessFlagConstant.ATTRIBUTE_COUNT);
        List<AttributeInfo> attributeInfoList = new ArrayList<>(attributeCount);
        if (attributeCount > 0) {
            for (int i = 0; i < attributeCount; i++) {
                AttributeInfo attributeInfo = getAttributeInfo(bytes);
                attributeInfoList.add(attributeInfo);
            }
        }
        classInfo.setAttributesCount(attributeCount);
        classInfo.setAttributes(attributeInfoList);
        System.out.println("类属性数量： " + attributeInfoList.size());
        System.out.println(JSONObject.toJSONString(attributeInfoList));
    }

    public void parseMethods(byte[] bytes) {
        int count = readByteArrayToInt(bytes, AccessFlagConstant.METHODS_COUNT_LEN);
        List<MethodInfo> list = new ArrayList<>();
        if (0 < count) {
            for (int i = 0; i < count; i++) {
                MethodInfo methodInfo = new MethodInfo();
                int accessFlags = readByteArrayToInt(bytes, AccessFlagConstant.ACCESS_FLAG);
                methodInfo.setAccessFlags(accessFlags);
                int nameIndex = readByteArrayToInt(bytes, AccessFlagConstant.INDEX);
                methodInfo.setNameIndex(nameIndex);
                int descriptorIndex = readByteArrayToInt(bytes, AccessFlagConstant.INDEX);
                methodInfo.setDescriptorIndex(descriptorIndex);
                int attributeCount = readByteArrayToInt(bytes, AccessFlagConstant.COUNT);
                methodInfo.setAttributesCount(attributeCount);
                List<AttributeInfo> attributeInfoList = methodInfo.getAttributeInfoList();
                if (attributeCount > 0) {
                    for (int j = 0; j < attributeCount; j++) {
                        AttributeInfo attributeInfo = getAttributeInfo(bytes);
                        attributeInfoList.add(attributeInfo);
                    }
                }
                list.add(methodInfo);
            }
        }
        classInfo.setMethodsCount(count);
        classInfo.setMethods(list);
        System.out.println("方法数量： " + count);
//        System.out.println(JSONObject.toJSONString(list));
    }


    public void parseFields(byte[] bytes) {
        int count = readByteArrayToInt(bytes, AccessFlagConstant.FIELDS_COUNT_LEN);
        List<FieldInfo> list = new ArrayList<>();
        if (0 < count) {
            for (int i = 0; i < count; i++) {
                FieldInfo fieldInfo = new FieldInfo();
                int accessFlags = readByteArrayToInt(bytes, AccessFlagConstant.ACCESS_FLAG);
                fieldInfo.setAccessFlags(accessFlags);
                int nameIndex = readByteArrayToInt(bytes, AccessFlagConstant.INDEX);
                fieldInfo.setNameIndex(nameIndex);
                int descriptorIndex = readByteArrayToInt(bytes, AccessFlagConstant.INDEX);
                fieldInfo.setDescriptorIndex(descriptorIndex);
                int attributeCount = readByteArrayToInt(bytes, AccessFlagConstant.COUNT);
                fieldInfo.setAttributesCount(attributeCount);
                List<AttributeInfo> attributeInfoList = fieldInfo.getAttributeInfoList();
                if (attributeCount > 0) {
                    for (int j = 0; j < attributeCount; j++) {
                        AttributeInfo attributeInfo = getAttributeInfo(bytes);

                        attributeInfoList.add(attributeInfo);
                    }
                }
                list.add(fieldInfo);
            }
        }
        classInfo.setFieldsCount(count);
        classInfo.setFields(list);
        System.out.println("成员属性数量： " + count);
    }

    private AttributeInfo getAttributeInfo(byte[] bytes) {
        int attributeNameIndex = readByteArrayToInt(bytes, AccessFlagConstant.ATTRIBUTE_NAME_INDEX);
        int attributeLength = readByteArrayToInt(bytes, AccessFlagConstant.ATTRIBUTE_LENGTH);
        byte[] attributeBytes = getBytes(bytes, attributeLength);

        // 从常量池中找到 index 指向的内容
        ConstantType constantType = constantPool.get(attributeNameIndex);
        ConstantAttribute attribute = constantType.getAttrList().get(1);
        String value = (String) attribute.getValue();

        AttributeInfoFactory attributeInfoFactory = AttributeInfoFactoryBuild.getAttributeInfoFactory(attributeBytes, value);
        AttributeInfo attributeInfoInstance = attributeInfoFactory.getAttributeInfoInstance();
        attributeInfoInstance.setAttributeNameIndex(attributeNameIndex);
        attributeInfoInstance.setAttributeLength(attributeLength);
        return attributeInfoInstance;
    }

    public void parseInterface(byte[] bytes) {
        int interfaceCountLen = 2;
        int interfaceIndexLen = 2;
        int count = readByteArrayToInt(bytes, interfaceCountLen);
        List<Integer> interfaces = new ArrayList<>(count);
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                int index = readByteArrayToInt(bytes, interfaceIndexLen);
                interfaces.add(index);
            }
        }
        classInfo.setInterfacesCount(count);
        classInfo.setInterfaces(interfaces);
        System.out.println("接口数量： " + count);
    }


    public void thisAndSupperClass(byte[] bytes) {
        int thisClassLen = 2;
        int thisClassIndex = readByteArrayToInt(bytes, thisClassLen);

        int supperClassLen = 2;
        int supperClassIndex = readByteArrayToInt(bytes, supperClassLen);
        classInfo.setThisClass(thisClassIndex);
        classInfo.setSuperClass(supperClassIndex);

        System.out.println("类名间接引用地址： " + thisClassIndex + "； 父类间接引用地址" + supperClassIndex);
    }

    public void accessFlags(byte[] bytes) {
        int accessFlagsLen = 2;
        byte[] accessFlagsBytes = getBytes(bytes, accessFlagsLen);
        int flag = byteArrayToInt(accessFlagsBytes);
        //确定访问控制权限值（0x0001）所对应的权限名称(public)
        List<ClassAccessFlag> accessFlags = getAccessFlags(flag);
        String flagStr = getFlagString(accessFlags);
        AccessFlagsInfo accessFlagsInfo = new AccessFlagsInfo();
        accessFlagsInfo.setStrValue(flagStr);
        accessFlagsInfo.setValue(flag);
        accessFlagsInfo.setHexValue(bytesToHex(accessFlagsBytes));
        classInfo.setAccessFlags(accessFlagsInfo);
    }

    private List<ClassAccessFlag> getAccessFlags(int flag) {
        List<ClassAccessFlag> list = new ArrayList<>();
        List<ClassAccessFlag> finalClassAccessFlagList = new ArrayList<>();
        ClassAccessFlag determine = null;
        for (Map.Entry<Integer, ClassAccessFlag> entry : CLASS_ACCESS_FLAGS.entrySet()) {
            Integer key = entry.getKey();
            ClassAccessFlag value = entry.getValue();
            if (key < flag) {
                list.add(value);
            } else if (key == flag) {
                determine = value;
            } else {
                break;
            }
        }
        if (null != determine) {
            finalClassAccessFlagList.add(determine);
            return finalClassAccessFlagList;
        }

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                ClassAccessFlag classAccessFlag = list.get(i);
                int tempFlagTemp = classAccessFlag.getFlag();
                if ((tempFlagTemp & flag) == tempFlagTemp) {
                    finalClassAccessFlagList.add(classAccessFlag);
                }
            }
        }
        return finalClassAccessFlagList;
    }

    /**
     * 将权限值转化成权限字符串
     * 0x0001 -> public
     *
     * @param finalClassAccessFlagList
     * @return
     */
    private String getFlagString(List<ClassAccessFlag> finalClassAccessFlagList) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (ClassAccessFlag classAccessFlag : finalClassAccessFlagList) {
            String name = classAccessFlag.getName();
            if (StringUtils.isNotBlank(name)) {
                sb.append(name).append(" ");
            }
        }
        String flagStr = sb.substring(0, sb.length() - 1);
        flagStr += "]";
        return flagStr;
    }

    public void parseConstant(byte[] bytes) {
        int constantPoolCountLen = 2;
        int constantPool = readByteArrayToInt(bytes, constantPoolCountLen);
        System.out.println("常量池大小：" + constantPool);
        for (int c = 0; c < constantPool - 1; c++) {

            ConstantType type = new ConstantType();
            List<ConstantAttribute> list = type.getAttrList();

            int tag = bytes[offset] & 0xFF;
            offset += 1;
            ConstantType constantType = CONSTANT_POOL_TYPE.get(tag);
            type.setName(constantType.getName());
            List<ConstantAttribute> attrList = constantType.getAttrList();
            if (1 == tag) {
                int index = 0;
                int length = 1;
                for (ConstantAttribute attribute : attrList) {
                    int len = attribute.getLen();
                    Object value;
                    if (index == attrList.size() - 1) {
                        len *= length;
                        byte[] nameBytes = getBytes(bytes, len);
//                        for (int i = 0; i < nameBytes.length; i++) {
//                            nameBytes[i] = bytes[offset + i];
//                        }
                        value = new String(nameBytes);
                    } else {
                        byte[] nameBytes = getBytes(bytes, len);
                        length = byteArrayToInt(nameBytes);
                        value = length;
                    }
                    index++;
                    ConstantAttribute att = new ConstantAttribute();
                    att.setName(attribute.getName());
                    att.setValue(value);
                    list.add(att);
                }
            } else {
                for (ConstantAttribute attribute : attrList) {
                    int len = attribute.getLen();
                    byte[] nameBytes = getBytes(bytes, len);
                    int index = -1;
                    if (tag < 7) {

                    } else {
                        index = byteArrayToInt(nameBytes);
                    }
                    ConstantAttribute att = new ConstantAttribute();
                    att.setName(attribute.getName());
                    att.setValue(index);
                    list.add(att);
                }
            }
            CONSTANT_POOL.add(type);
            this.constantPool.add(type);
        }

    }

    public void checkVersion(byte[] bytes) {
        int minorVersionLen = 2;
        int majorVersionLen = 2;
        byte[] minorVersionBytes = getBytes(bytes, minorVersionLen);
        System.out.println("次版本号：" + byteArrayToInt(minorVersionBytes));
        byte[] majorVersionBytes = getBytes(bytes, majorVersionLen);
        classInfo.setMinorVersion(byteArrayToInt(minorVersionBytes));
        classInfo.setMajorVersion(byteArrayToInt(majorVersionBytes));
        System.out.println("主版本号：" + JDK_VERSION.getOrDefault(byteArrayToInt(majorVersionBytes), "没有找到JDK版本"));
    }

    private byte[] getBytes(byte[] bytes, int len) {
        byte[] resultBytes = new byte[len];
        for (int i = 0; i < len; i++) {
            resultBytes[i] = bytes[offset + i];
        }
        offset += len;
        return resultBytes;
    }

    public int readByteArrayToInt(byte[] bytes, int len) {
        return byteArrayToInt(getBytes(bytes, len));
    }

    public void checkMagic(byte[] bytes) {
        int byteLen = 4;
        byte[] magicBytes = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            magicBytes[i] = bytes[i];
        }
        String s = bytesToHex(magicBytes);
        System.out.println("is equals 'cafebabe': " + StringUtils.equals("cafebabe", s));
        offset += byteLen;
        classInfo.setMagic(byteArrayToInt(magicBytes));

    }


    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        sb.append("0x");
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    /**
     * 字节转十六进制
     *
     * @param b 需要进行转换的byte字节
     * @return 转换后的Hex字符串
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    /**
     * byte数组 转 int
     *
     * @param bytes 需要进行转换的byte字节
     * @return 转换后 int 值
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        int len = bytes.length;
        // 由高位到低位
        for (int i = 0; i < len; i++) {
            int shift = (len - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

}
