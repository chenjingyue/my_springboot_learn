package com.size;

import org.openjdk.jol.info.ClassLayout;

public class CountSimpleObjectSize {
//     int[] arr = {0, 1, 2};
//    private int[] arr1 = {0, 1, 2};
    Object obj = new Object();

    public static void main(String[] args) {
        //24B = 8B + 8B + 0B + 8B + 0B
        //16B = 8B + 4B + 0B + 4B + 0B
        CountSimpleObjectSize test1 = new CountSimpleObjectSize();

        System.out.printf(ClassLayout.parseInstance(test1).toPrintable());
    }
}
