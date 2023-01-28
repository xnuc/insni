package xnuc.example;

import xnuc.context.Context;
import xnuc.example.depend.C;

public class Main {
    public static void main(String[] args) throws Exception {
        Context ioc = Context.run(Main.class);
        C c = (C) ioc.get("c");
        System.out.println(c.getA().getB().getName());
    }
}
