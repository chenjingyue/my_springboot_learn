package com.object.size;

import org.openjdk.jol.info.ClassLayout;

public class CountEmptyObjectSize {
    public static int i = 1;

    public String aa = "bbb";

    public static void main(String[] args) {
        CountEmptyObjectSize obj = new CountEmptyObjectSize();
        obj.aa = "aaaa";
        while(true) {

        }

//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }

    public String aa() {
        return "aaa";
    }
}
