package xnuc.example;

import xnuc.anno.Named;
import xnuc.anno.Inject;
import xnuc.example.depend.C;

@Named("a")
public class A {

    @Inject
    public B b;

    @Inject
    public C c;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    public A() {
    }
}
