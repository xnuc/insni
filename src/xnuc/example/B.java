package xnuc.example;

@xnuc.ctx.Named("b")
public class B {
    @xnuc.ctx.Value("hello world!")
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
