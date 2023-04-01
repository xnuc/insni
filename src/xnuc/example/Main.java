package xnuc.example;

public class Main {
    public static void main(String[] args) throws Exception {
        var ioc = xnuc.ctx.Context.run(Main.class);
        new Thread((Runnable) ioc.get("c")).run();
        Thread.sleep(1000);
    }
}
