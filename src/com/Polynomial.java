package com;

import java.math.BigInteger;

public class Polynomial implements DeepClone<Polynomial> {
    private SLinkedList<Term> polynomial;

    public int size() {
        return polynomial.size();
    }

    private Polynomial(SLinkedList<Term> p) {
        polynomial = p;
    }

    public Polynomial() {
        polynomial = new SLinkedList<Term>();
    }

    public Polynomial deepClone() {
        return new Polynomial(polynomial.deepClone());
    }

    public void addTerm(Term t) {
        if (t.getCoefficient().equals(BigInteger.ZERO)) return;
        if (polynomial.isEmpty()) {
            polynomial.addFirst(t);
            return;
        }

        int index = 0;

        for (Term cur : polynomial) {
            if (cur.getExponent() == t.getExponent()) {
                t.setCoefficient(t.getCoefficient().add(cur.getCoefficient()));
                if (t.getCoefficient().equals(BigInteger.ZERO)) polynomial.remove(index);
                else polynomial.set(index, t);
                return;
            }

            if (cur.getExponent() < t.getExponent()) {
                polynomial.add(index, t);
                return;
            }
            index++;
        }
        polynomial.addLast(t);
    }

    public Term getTerm(int index) {
        return polynomial.get(index);
    }

    public static Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        if (p1.polynomial.isEmpty()) {
            result = p2.deepClone();
            return result;
        } else if (p2.polynomial.isEmpty()) {
            result = p1.deepClone();
            return result;
        } else {
            for (Term cur : p1.polynomial) {
                result.addTerm(cur);
            }
            for (Term cur2 : p2.polynomial) {
                result.addTerm(cur2);
            }
            return result;
        }
    }

    public void multiplyTerm(Term t) {
        if (t.getCoefficient().equals(BigInteger.ZERO)) {
            polynomial.clear();
        }
        for (Term cur : polynomial) {
            if (cur.getExponent() != 0) {
                cur.setExponent(cur.getExponent() + t.getExponent());
            } else if (cur.getExponent() == 0 && t.getExponent() != 0) {
                cur.setExponent(1);
            }
            cur.setCoefficient(cur.getCoefficient().multiply(t.getCoefficient()));
        }
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        if (!p1.polynomial.isEmpty() && !p2.polynomial.isEmpty()) {
            for (Term cur : p1.polynomial) {
                for (Term cur2 : p2.polynomial) {
                    result.addTerm(new Term(cur.getExponent() + cur2.getExponent(), cur.getCoefficient().multiply(cur2.getCoefficient())));
                }
            }
        }
        return result;
    }

    public BigInteger eval(BigInteger x) {
        if (polynomial.isEmpty()) {
            return BigInteger.ZERO;
        }

        int bigExp = polynomial.get(0).getExponent();
        BigInteger result = BigInteger.ZERO;

        for (Term cur : polynomial) {
            if (cur.getExponent() == 0) result = result.multiply(x).add(cur.getCoefficient()); //constant
            else {
                if (bigExp - cur.getExponent() == 0) result = result.multiply(x).add(cur.getCoefficient()); //first time
                bigExp = bigExp - cur.getExponent();
                if (bigExp != 0) {
                    int mult = bigExp + 1 - 2;                             // count number of times we need to multiply by.
                    result = result.multiply(x.pow(mult));
                    result = result.multiply(x).add(cur.getCoefficient());
                }
            }
            bigExp = cur.getExponent();
        }

        if (!polynomial.isEmpty()) {                                      // get at end of list
            int test = polynomial.get(polynomial.size() - 1).getExponent();
            if (test > 0) {
                result = result.multiply(x.pow(test));
            }
        }
        return result;
    }


    public boolean isDeepClone(Polynomial p) {
        if (p == null || polynomial == null || p.polynomial == null || this.size() != p.size()) return false;
        int index = 0;
        for (Term term0 : polynomial) {
            Term term1 = p.getTerm(index);

            if (term0.getExponent() != term1.getExponent()
                    || term0.getCoefficient().compareTo(term1.getCoefficient()) != 0
                    || term1 == term0
            ) return false;
            index++;
        }
        return true;
    }

    public void addTermLast(Term t) {
        polynomial.addLast(t);
    }

    @Override
    public String toString() {
        if (polynomial.size() == 0) return "0";
        return polynomial.toString();
    }
}
