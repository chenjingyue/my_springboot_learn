package com.test;

public class Test {
    public static void main(String[] args) {
        System.out.println("name");
        show();
    }

    public void test(String str) {
        int a = 10;
        int b = 90;
//        int c = a +b;
        String aa = "bb";

        try {
            String cc = aa + "cc";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static    void show(){

        int a1 = 10;
        {
            int z = 89;
            int b = 88;
            {
                int hhh = 0;
                int aaa = 0;
                int c = 0;
            }
            int ccc = 90;
            int bbb = 0;
        }
        int a = 90;
        int ha = 8;
    }
}
