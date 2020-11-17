package com;

import java.math.BigInteger;

public class Term implements DeepClone<Term> {

    private int exponent;
    private BigInteger coefficient;

    public Term(int e, BigInteger c) {
        if (e < 0 || c.compareTo(new BigInteger("0")) == 0)
            throw new IllegalArgumentException("Invalid term");

        exponent = e;
        coefficient = c;
    }

    @Override
    public Term deepClone() {
        return new Term(exponent, coefficient);
    }

    /* Instance methods */
    BigInteger getCoefficient() {
        return coefficient;
    }

    int getExponent() {
        return exponent;
    }

    void setCoefficient(BigInteger d) {
        coefficient = d;
    }

    void setExponent(int a) {
        exponent = a;
    }

    BigInteger eval(BigInteger x) {
        BigInteger result = BigInteger.valueOf(1);
        int n = exponent;
        while (n != 0) {
            result = result.multiply(x);
            n--;
        }
        return (result.multiply(coefficient));
    }

    @Override
    public String toString() {
        String s = "";
        if (coefficient.compareTo(new BigInteger("0")) < 0)
            s += "-";
        else if (coefficient.compareTo(new BigInteger("0")) > 0)
            s += "+";

        if (coefficient.abs().compareTo(new BigInteger("1")) != 0)
            s += coefficient.abs();

        if (exponent > 0) {
            s += "x";
            if (exponent > 1) {
                s += "^" + exponent;
            }
        } else if (coefficient.abs().compareTo(new BigInteger("1")) == 0)
            s += "1";

        return s;
    }


}
