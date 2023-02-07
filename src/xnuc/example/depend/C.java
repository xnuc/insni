package xnuc.example.depend;

import xnuc.example.A;
import xnuc.anno.Named;

import xnuc.anno.Inject;

@Named("c")
public class C implements Print {

    @Inject
    public A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public C() {
    }

    @Override
    public void print() {
        System.out.println(a.getB().getName());
    }
}
