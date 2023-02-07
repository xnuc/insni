> insni - 轻量的依赖注入容器

`IoC` 控制反转原则让容器接管实例管理, 指导设计出松耦合优良的程序. 

insni 打造了轻量的依赖注入容器, 管理 Java 程序中的实例. 

## 快速开始

假设存在依赖关系

```mermaid
  graph TD;
      A-->B;
      A-->C;
      C-->A;
```

### 类定义

```java
@Named("a")
public class A {
    @Inject
    public B b;
    @Inject
    public C c;
    // getter and setter
    // constructor
}
@Named("b")
public class B {
    @Value("hello world!")
    public String name;
    // getter and setter
    // constructor
}
@Named("c")
public class C implements Print {
    @Inject
    public A a;
    // getter and setter
    // constructor
    @Override
    public void print() {
        System.out.println(a.getB().getName());
    }
}
@Aspect
public class CAspect {
    @Pointcut("xnuc.example.depend.C#print")
    public void log() {
        System.out.println(String.format("ts:%d", System.currentTimeMillis()));
    }
}
```

### Main

```java
import xnuc.context.Context;
import xnuc.example.depend.Print;

public class Main {
    public static void main(String[] args) throws Exception {
        Context ioc = Context.run(Main.class);
        Print c = (Print) ioc.get("c");
        c.print();
    }
}
```


