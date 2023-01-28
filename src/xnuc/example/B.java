package xnuc.example;

import xnuc.anno.Named;
import xnuc.anno.Value;

@Named("b")
public class B {

    @Value("hello world!")
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public B() {
    }
}
