package com.size;

import org.openjdk.jol.info.ClassLayout;

public class CountObjectSize {

    public static void main(String[] args) {
        int[] arr = {0, 1, 2};
        String aa = "";

        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
    }
}
