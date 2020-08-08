package com.jvm_test;

import com.alibaba.fastjson.JSONObject;
import com.jvm_test.constant.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Decompile {

    private static ClassAccessFlag finalClassAccessFlag;

    private static int offset = 0;
    private static Map<Integer, String> JDK_VERSION = new HashMap<>();
    private static List<ConstantType> CONSTANT_POOL = new ArrayList<>();
    private static Map<Integer, ConstantType> CONSTANT_POOL_TYPE = new HashMap<>();
    // 类访问权限
    private static Map<Integer, ClassAccessFlag> CLASS_ACCESS_FLAGS = new LinkedHashMap<>();

    private static short[] class_flags = new short[]{1, 8, 16, 32, 512, 1024, 4096, 8192, 16384};
//    private static String[] field_flags = new String[]{"0001","0002","0004","0008","0010","0020","0200","0400","1000","2000","4000"};

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


//        CONSTANT_POOL.put(1,"CONSTANT_Utf8_info");
//        CONSTANT_POOL.put(3,"CONSTANT_Integer_info");
//        CONSTANT_POOL.put(4,"CONSTANT_Float_info");
//        CONSTANT_POOL.put(5,"CONSTANT_Long_info");
//        CONSTANT_POOL.put(6,"CONSTANT_Double_info");
//        CONSTANT_POOL.put(7,"CONSTANT_Class_info");
//        CONSTANT_POOL.put(8,"CONSTANT_String_info");
//        CONSTANT_POOL.put(9,"CONSTANT_Fieldref_info");
//        CONSTANT_POOL.put(10,"CONSTANT_Methodref_info");
//        CONSTANT_POOL.put(11,"CONSTANT_Interface_Methodref_info");
//        CONSTANT_POOL.put(12,"CONSTANT_NameAndType_info");
//        CONSTANT_POOL.put(15,"CONSTANT_MethodHandle_info");
//        CONSTANT_POOL.put(16,"CONSTANT_MethodType_info");
//        CONSTANT_POOL.put(18,"CONSTANT_InvokeDynamic_info");

        ConstantType methodrefInfo = new ConstantType();
        methodrefInfo.setName("CONSTANT_Methodref_info");
        List attrList = methodrefInfo.getAttrList();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Class name");
        map.put("len", 2);
        attrList.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "Name and type");
        map1.put("len", 2);
        attrList.add(map1);
        CONSTANT_POOL_TYPE.put(10, methodrefInfo);

        ConstantType Utf8Info = new ConstantType();
        Utf8Info.setName("CONSTANT_Utf8_info");
        attrList = Utf8Info.getAttrList();
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Length of byte array");
        map2.put("len", 2);
        attrList.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "String");
        map3.put("len", 1);
        attrList.add(map3);
        CONSTANT_POOL_TYPE.put(1, Utf8Info);

        ConstantType fieldrefInfo = new ConstantType();
        fieldrefInfo.setName("CONSTANT_Fieldref_info");
        attrList = fieldrefInfo.getAttrList();
        Map<String, Object> fieldrefMap1 = new HashMap<>();
        fieldrefMap1.put("name", "Class name");
        fieldrefMap1.put("len", 2);
        attrList.add(fieldrefMap1);
        Map<String, Object> fieldrefMap2 = new HashMap<>();
        fieldrefMap2.put("name", "Name and type");
        fieldrefMap2.put("len", 2);
        attrList.add(fieldrefMap2);
        CONSTANT_POOL_TYPE.put(9, fieldrefInfo);

        ConstantType classInfo = new ConstantType();
        classInfo.setName("CONSTANT_Class_info");
        attrList = classInfo.getAttrList();
        Map<String, Object> classMap1 = new HashMap<>();
        classMap1.put("name", "Class name");
        classMap1.put("len", 2);
        attrList.add(classMap1);
        CONSTANT_POOL_TYPE.put(7, classInfo);

        ConstantType nameAndTypeInfo = new ConstantType();
        nameAndTypeInfo.setName("CONSTANT_NameAndType_info");
        attrList = nameAndTypeInfo.getAttrList();
        Map<String, Object> nameAndTypeMap1 = new HashMap<>();
        nameAndTypeMap1.put("name", "Name");
        nameAndTypeMap1.put("len", 2);
        attrList.add(nameAndTypeMap1);
        Map<String, Object> nameAndTypeMap2 = new HashMap<>();
        nameAndTypeMap2.put("name", "Descriptor");
        nameAndTypeMap2.put("len", 2);
        attrList.add(nameAndTypeMap2);
        CONSTANT_POOL_TYPE.put(12, nameAndTypeInfo);

        ConstantType stringInfo = new ConstantType();
        stringInfo.setName("CONSTANT_String_info");
        attrList = stringInfo.getAttrList();
        Map<String, Object> stringMap1 = new HashMap<>();
        stringMap1.put("name", "String");
        stringMap1.put("len", 2);
        attrList.add(stringMap1);
        CONSTANT_POOL_TYPE.put(8, stringInfo);
    }


    public static void main(String[] args) {
        String classFilePath = "E:\\chenjingyue\\my_springboot_learn\\target\\classes\\com\\jvm_test\\Hello.class";
        File file = new File(classFilePath);
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            // 检查魔数
            checkMagic(bytes);
            // 检查版本
            checkVersion(bytes);
            // 解析常量池
            parseConstant(bytes);
//            System.out.println(JSONObject.toJSONString(CONSTANT_POOL));
            // 类访问控制权限
            accessFlags(bytes);
            // 类名
            thisAndSupperClass(bytes);
            // 接口
            parseInterface(bytes);
            // 成员属性
            parseFields(bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static  void parseMethods(byte[] bytes) {
        int count = readByteArrayToInt(bytes, AccessFlagConstant.METHODS_COUNT_LEN);
        List<MethodInfo> list = new ArrayList<>();
        if(0 < count) {

        }
    }

    public static void parseFields(byte[] bytes) {
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
                if (attributeCount > 0) {
                    List<AttributeInfo> attributeInfoList = fieldInfo.getAttributeInfoList();
                }
                list.add(fieldInfo);
            }
        }
        System.out.println("成员属性个数： "+count);

    }

    public static void parseInterface(byte[] bytes) {
        int interfaceCountLen = 2;
        int interfaceIndexLen = 2;
        int count = readByteArrayToInt(bytes, interfaceCountLen);
        if (count > 0) {
            for (int i = 0; i < count; i++) {
//                byte[] interfaceIndexBytes = new byte[interfaceIndexLen];
//                getBytes(bytes, interfaceIndexBytes);
                int index = readByteArrayToInt(bytes, interfaceIndexLen);
            }
        }
        System.out.println("接口个数： "+count);
    }


    public static void thisAndSupperClass(byte[] bytes) {
        int thisClassLen = 2;
//        byte[] thisClassBytes = new byte[thisClassLen];
//        getBytes(bytes, thisClassBytes);
//        offset += thisClassLen;
        int thisClassIndex = readByteArrayToInt(bytes, thisClassLen);

        int supperClassLen = 2;
//        byte[] supperClassBytes = new byte[supperClassLen];
//        getBytes(bytes, supperClassBytes);
//        offset += supperClassLen;
        int supperClassIndex = readByteArrayToInt(bytes, supperClassLen);
        System.out.println("类名间接引用地址： " + thisClassIndex + "； 父类间接引用地址" + supperClassIndex);
    }

    public static void accessFlags(byte[] bytes) {
        int accessFlagsLen = 2;
        byte[] accessFlagsBytes =getBytes(bytes, accessFlagsLen);
        int flag = byteArrayToInt(accessFlagsBytes);
        List<ClassAccessFlag> list = new ArrayList<>();
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
            finalClassAccessFlag = determine;
            return;
        }
        List<ClassAccessFlag> finalClassAccessFlagList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            List<ClassAccessFlag> tempList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ClassAccessFlag classAccessFlag = list.get(i);
                int flag1 = classAccessFlag.getFlag();
                tempList.add(list.get(i));
                if (flag1 < flag) {
                    int temp = flag1;
                    for (int j = i + 1; j < list.size(); j++) {
                        temp |= list.get(j).getFlag();
                        if (temp == flag) {
                            finalClassAccessFlagList = tempList;
                            return;
                        } else if (temp > flag) {
                            tempList.clear();
                            break;
                        }
                        tempList.add(list.get(j));
                    }
                }
            }
        }
        System.out.println("类访问控制权限：" + bytesToHex(accessFlagsBytes));


    }

    public static void parseConstant(byte[] bytes) {
        int constantPoolCountLen = 2;
        int constantPool = readByteArrayToInt(bytes, constantPoolCountLen);
        System.out.println("常量池大小：" + constantPool);

        for (int c = 0; c < constantPool - 1; c++) {

            ConstantType type = new ConstantType();
            List list = type.getAttrList();

            int tag = bytes[offset] & 0xFF;
            offset += 1;
            ConstantType constantType = CONSTANT_POOL_TYPE.get(tag);
            type.setName(constantType.getName());
            List<Map> attrList = constantType.getAttrList();
            if (1 == tag) {
                int index = 0;
                int length = 1;
                for (Map map : attrList) {
                    int len = (int) map.get("len");
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

                    Map<String, Object> typeMap1 = new HashMap<>();
                    typeMap1.put("name", map.get("name"));
                    typeMap1.put("value", value);
                    list.add(typeMap1);
                }
            } else {
                for (Map map : attrList) {
                    int len = (int) map.get("len");
                    byte[] nameBytes = getBytes(bytes, len);
                    int index = -1;
                    if (tag < 7) {

                    } else {
                        index = byteArrayToInt(nameBytes);
                    }
                    Map<String, Object> typeMap1 = new HashMap<>();
                    typeMap1.put("name", map.get("name"));
                    typeMap1.put("index", index);
                    list.add(typeMap1);
                }
            }
            CONSTANT_POOL.add(type);
        }


    }

    public static void checkVersion(byte[] bytes) {
        int minorVersionLen = 2;
        int majorVersionLen = 2;
        byte[] minorVersionBytes = getBytes(bytes, minorVersionLen);
        System.out.println("次版本号：" + byteArrayToInt(minorVersionBytes));
        byte[] majorVersionBytes = getBytes(bytes, majorVersionLen);
        System.out.println("主版本号：" + JDK_VERSION.getOrDefault(byteArrayToInt(majorVersionBytes), "没有找到JDK版本"));
    }

    private static byte[] getBytes(byte[] bytes, int len) {
        byte[] resultBytes = new byte[len];
        for (int i = 0; i < len; i++) {
            resultBytes[i] = bytes[offset + i];
        }
        offset += len;
        return resultBytes;
    }

    public static int readByteArrayToInt(byte[] bytes, int len) {
        return byteArrayToInt(getBytes(bytes, len));
    }

    public static void checkMagic(byte[] bytes) {
        int byteLen = 4;
        byte[] magicBytes = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            magicBytes[i] = bytes[i];
        }
        String s = bytesToHex(magicBytes);
        System.out.println("is equals 'cafebabe': " + StringUtils.equals("cafebabe", s));
        offset += byteLen;


    }


    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
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
