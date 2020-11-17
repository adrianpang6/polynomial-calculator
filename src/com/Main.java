package com;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        Polynomial p1 = new Polynomial();

        p1.addTermLast(new Term(5, new BigInteger(Integer.toString(-1))));
        p1.addTermLast(new Term(3, new BigInteger(Integer.toString(2))));
        p1.addTermLast(new Term(1, new BigInteger(Integer.toString(3))));
        p1.addTermLast(new Term(0, new BigInteger(Integer.toString(5))));

        System.out.println(p1);
        String result = p1.eval(new BigInteger("-2")).toString(10);

        String expected = "15";
        System.out.println();
        System.out.println("expected: " + expected);
        System.out.println("result: " + result);


        if (!result.equals(expected)) {
            System.out.println("Failed.");
        } else {
            System.out.println("Passed.");
        }
    }
}
