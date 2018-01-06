package com.wust.iot;

public class Test {

    public static void main(String[] args) {
        String s1 = "Hello world";
        String s2 = "Hello world";
        System.out.println(s1==s2);
        System.out.println(s1.equals(s2));


        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 200;
        Integer i4 = 200;

        System.out.println(i1==i2);
        System.out.println(i3==i4);
    }
}
