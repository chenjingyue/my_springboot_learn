package com.jvm_test;

public class Test001 {


    public static void main(String[] args) {
        System.out.println(Test001_1.count);
//        while (true);
    }

}


  class Test001_1 {

//    public static Test001_1 test001 = new Test001_1();
    public int aaa  = 4;
    public static int count = 3;

    public Test001_1() {
//        System.out.println("aaa:" + aaa);
        count++;
    }

    public Test001_1(int i) {

    }
}


