package com.size;

import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CountEmptyObjectSize {


    private static HashMap<Integer,Integer> map = new HashMap<>();

    static {
        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        map.put(4,4);
    }
    public static int i = 1;

    public String aa = "bbb";
    public String ccc = "bbb";

    public static void main(String[] args) throws InterruptedException {
        CountEmptyObjectSize obj = new CountEmptyObjectSize();
        Class<CountEmptyObjectSize> countEmptyObjectSizeClass = CountEmptyObjectSize.class;
        obj.aa = "aaaa";
        for (int i = 0; i < 1000; i++) {
            TimeUnit.SECONDS.sleep(5);
            String aa = obj.aa(obj,i);
            System.out.println(aa);
        }
//        while(true) {
//
//        }

//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }

    public String aa(CountEmptyObjectSize  p1, int i) {
        return p1.aa + "-aaa:"+ i;
    }

    public String getAa() {
        return aa;
    }

    @Override
    public String toString() {
        return "CountEmptyObjectSize{" +
                "aa='" + aa + '\'' +
                ", ccc='" + ccc + '\'' +
                '}';
    }
}
