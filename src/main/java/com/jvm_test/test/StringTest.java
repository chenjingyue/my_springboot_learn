package com.jvm_test.test;

public class StringTest {

    public static void main(String[] args) {
//        test01();
//        test02();
//        test03();
//        test04();

//        test05();
//        test06();
//        test07();
        test08();
    }

    public static void test01() {
        String s2 = "1" + "1";
//        s1.intern();

        String s3 = "11";
        System.out.println(s3 == s2);
    }

    public static void test02() {
        String s1 = "1";
        String s2 = "1";

        String s4 = s1 + s2;
        s4.intern();

        String s3 = "11";
        System.out.println(s3 == s4);
    }

    public static void test03() {
        String s1 = "1";
        String s2 = new String("1");

        String s3 = s1+s2;

        String s4 = "11";

        System.out.println(s1 == s2);
    }

    public static void test04() {
        // 创建两个String对象，一个char[] ,会将String放到常量池中
        String s2 = new String("11");

        String s1 = "11";
//        s2.intern();

        System.out.println(s1 == s2);
    }

    public static void test05() {
        String s1 = "11";

        String s2 = "1";
        String s3 = "1";
        String s4 = s2 + s3;
//        s4.intern();

        System.out.println(s1 == s4);
    }

    // 底层调用StringBuilder.toString() 方法 new 一个String 对象，但是不会放到常量池中，只生成一个String对象和一个char[]
    public static void test06() {
        String s2 = "1";
        String s3 = "1";
        String s4 = s2 + s3;

        String s1 = "11";

        System.out.println(s1 == s4);
    }

    public static void test07() {
        String s1 = "11";
        String s2 = new String("11");

        System.out.println(s1 == s2);
    }

    public static  void test08() {
        String s1 = new String("真帅");
        String s2 = "子牙" + new String("真帅");
        while (true){

        }

//        String s3 = "子牙真帅";
////        System.out.println(s2 == s3);
//
//        String ss = "子牙";
//        String ss1 = "真帅";
    }
}
