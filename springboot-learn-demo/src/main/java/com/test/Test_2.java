package com.test;

public class Test_2 extends Test_1 {

    private String aa;
    private static String bb = "bbb";
    public Test_2(){
        this.aa = "aa";
        aa = "bb";
    }
    public static void main(String[] args) {
        Test_2  test_2 = new Test_2();
        test_2.test("");
    }

    public void test(String aa) {
        aa = "bb";
    }
}
