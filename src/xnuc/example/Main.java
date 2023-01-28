package xnuc.example;

import xnuc.context.Context;

public class Main {
    public static void main(String[] args) throws Exception {
        Context ioc = Context.run(Main.class);
        A a = (A) ioc.get("a");
        System.out.println(a.getC().getA().getB().getName());
    }
}
