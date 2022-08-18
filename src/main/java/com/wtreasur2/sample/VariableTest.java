package com.wtreasur2.sample;

public class VariableTest {
    public int test(int a, int b) {
        a = 1;
        b = 2;
        int c = 3;
        int d = 4;
        int e = 4;
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        System.out.println(System.identityHashCode(c));
        System.out.println(System.identityHashCode(d));
        System.out.println(System.identityHashCode(e));
        return a + b;
    }

    public static void main(String[] args) {
        VariableTest v = new VariableTest();
        System.out.println(v.test(5, 6));
    }
}
