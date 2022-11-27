package com.company;

public class Test {
    public static void main(String[] args) {
        Motion f1 = new Motion();
        // motion disable normalization
        f1.output();
        for(int i=0; i<9; i++) {
            f1.forward();
        }
        for(int i=0; i<5; i++) {
            f1.backward();
        }
        System.out.println("-------------------------------------------------");

        Sensor f2 = new Sensor();
        // sensor enable normalization
        f2.output();
        for(int i=0; i<9; i++) {
            f1.forward();
        }
        for(int i=0; i<5; i++) {
            f1.backward();
        }
    }
}
