package xnuc.example;

import xnuc.context.Context;
import xnuc.example.depend.Print;

public class Main {

    public static void main(String[] args) throws Exception {
        Context ioc = Context.run(Main.class);
        Print c = (Print) ioc.get("c");
        c.print();
    }
}
