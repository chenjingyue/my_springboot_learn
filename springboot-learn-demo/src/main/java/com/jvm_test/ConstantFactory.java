package com.jvm_test;

import com.jvm_test.struct.clazz.ClassAccessFlag;
import com.jvm_test.struct.constant.AccessFlagConstant;
import com.jvm_test.struct.constant.ConstantAttribute;
import com.jvm_test.struct.constant.ConstantType;

import java.util.*;

public class ConstantFactory {

    public static Map<Integer, String> JDK_VERSION = new HashMap<>();
    public static List<ConstantType> CONSTANT_POOL = new ArrayList<>();
    public static Map<Integer, ConstantType> CONSTANT_POOL_TYPE = new HashMap<>();
    // 类访问权限
    public static Map<Integer, ClassAccessFlag> CLASS_ACCESS_FLAGS = new LinkedHashMap<>();

    public static short[] class_flags = new short[]{1, 8, 16, 32, 512, 1024, 4096, 8192, 16384};
    //    private static String[] field_flags = new String[]{"0001","0002","0004","0008","0010","0020","0200","0400","1000","2000","4000"};

    static {
        intiJdkVersion();

        initClassAccessFlags();

        initConstantType();
    }

    public static void initConstantType() {
        ConstantType methodrefInfo = new ConstantType();
        methodrefInfo.setName("CONSTANT_Methodref_info");
        List<ConstantAttribute> attrList = methodrefInfo.getAttrList();
        attrList.add(new ConstantAttribute("Class name",2,1));
        attrList.add(new ConstantAttribute("Name and type",2,1));
        CONSTANT_POOL_TYPE.put(10, methodrefInfo);

        ConstantType Utf8Info = new ConstantType();
        Utf8Info.setName("CONSTANT_Utf8_info");
        attrList = Utf8Info.getAttrList();
        attrList.add(new ConstantAttribute("Length of byte array",2,2));
        attrList.add(new ConstantAttribute("String",1,3));
        CONSTANT_POOL_TYPE.put(1, Utf8Info);

        ConstantType fieldrefInfo = new ConstantType();
        fieldrefInfo.setName("CONSTANT_Fieldref_info");
        attrList = fieldrefInfo.getAttrList();
        attrList.add(new ConstantAttribute("Class name",2,1));
        attrList.add(new ConstantAttribute("Name and type",2,1));
        CONSTANT_POOL_TYPE.put(9, fieldrefInfo);

        ConstantType classInfo = new ConstantType();
        classInfo.setName("CONSTANT_Class_info");
        attrList = classInfo.getAttrList();
        attrList.add(new ConstantAttribute("Class name",2,1));
        CONSTANT_POOL_TYPE.put(7, classInfo);

        ConstantType nameAndTypeInfo = new ConstantType();
        nameAndTypeInfo.setName("CONSTANT_NameAndType_info");
        attrList = nameAndTypeInfo.getAttrList();
        attrList.add(new ConstantAttribute("Name",2,1));
        attrList.add(new ConstantAttribute("Descriptor",2,1));
        CONSTANT_POOL_TYPE.put(12, nameAndTypeInfo);

        ConstantType stringInfo = new ConstantType();
        stringInfo.setName("CONSTANT_String_info");
        attrList = stringInfo.getAttrList();
        attrList.add(new ConstantAttribute("String",2,1));
        CONSTANT_POOL_TYPE.put(8, stringInfo);
    }

    public static void initClassAccessFlags() {
        CLASS_ACCESS_FLAGS.put(1, new ClassAccessFlag(1, AccessFlagConstant.ACC_PUBLIC, AccessFlagConstant.PUBLIC));
        CLASS_ACCESS_FLAGS.put(8, new ClassAccessFlag(8, AccessFlagConstant.ACC_STATIC, AccessFlagConstant.STATIC));
        CLASS_ACCESS_FLAGS.put(16, new ClassAccessFlag(16, AccessFlagConstant.ACC_FINAL, AccessFlagConstant.FINAL));
        CLASS_ACCESS_FLAGS.put(32, new ClassAccessFlag(32, AccessFlagConstant.ACC_SUPER));
        CLASS_ACCESS_FLAGS.put(512, new ClassAccessFlag(512, AccessFlagConstant.ACC_INTERFACE, AccessFlagConstant.INTERFACE));
        CLASS_ACCESS_FLAGS.put(1024, new ClassAccessFlag(1024, AccessFlagConstant.ACC_ABSTRACT, AccessFlagConstant.ABSTRACT));
        CLASS_ACCESS_FLAGS.put(4096, new ClassAccessFlag(4096, AccessFlagConstant.ACC_SYNTHETIC));
        CLASS_ACCESS_FLAGS.put(8192, new ClassAccessFlag(8192, AccessFlagConstant.ACC_ANNOTATION));
        CLASS_ACCESS_FLAGS.put(16384, new ClassAccessFlag(16384, AccessFlagConstant.ACC_ENUM, AccessFlagConstant.ENUM));
    }

    public static void intiJdkVersion() {
        JDK_VERSION.put(44, "1.0");
        JDK_VERSION.put(45, "1.1");
        JDK_VERSION.put(46, "1.2");
        JDK_VERSION.put(47, "1.3");
        JDK_VERSION.put(48, "1.4");
        JDK_VERSION.put(49, "1.5");
        JDK_VERSION.put(50, "1.6");
        JDK_VERSION.put(51, "1.7");
        JDK_VERSION.put(52, "1.8");
    }
}
