package xnuc.example;

@xnuc.ctx.Named("a")
public class A {
    @xnuc.ctx.Inject
    public B b;
    @xnuc.ctx.Inject
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