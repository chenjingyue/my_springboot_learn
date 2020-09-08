package com.object.size;

import org.openjdk.jol.info.ClassLayout;

public class CountEmptyObjectSize {
    public static int i = 1;

    public static void main(String[] args) {
        CountEmptyObjectSize obj = new CountEmptyObjectSize();

        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
