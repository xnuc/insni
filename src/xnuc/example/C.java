package xnuc.example;

@xnuc.ctx.Named("c")
public class C implements Runnable {
    @xnuc.ctx.Inject
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
    public void run() {
        java.util.logging.Logger.getGlobal().info(String.format("name:%s", a.getB().getName()));
    }
}