package com.jvm_test.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestStackFrame {

    private static int index = 0;

    public static void main(String[] args) {
        try {

            long fact = fact(6000);
            System.out.println("index1: "+index);
        } catch (Throwable throwable) {
            System.out.println("index2: "+index);
            throw throwable;
        }
    }

    public static int fact(int n) {   //求n的阶乘
        index++;
        if (n < 0)
            return 0;
        else if (n == 0)
            return 1;
        else if (n == 1)
            return 1;
        else
            return n * fact(n - 1);
    }
}
