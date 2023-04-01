# insni

> insni - 轻量的依赖注入容器

## ioc容器

假设存在依赖关系。

```mermaid
  graph TD;
      A-->B;
      A-->C;
      C-->A;
```

### 快速开始

- DI
    ```java
    @xnuc.ctx.Named("a")
    public class A {
        @xnuc.ctx.Inject
        public B b;
        @xnuc.ctx.Inject
        public C c;
        // getter and setter
        // constructor
    }

    @xnuc.ctx.Named("b")
    public class B {
        @xnuc.ctx.Value("hello world!")
        public String name;
        // getter and setter
        // constructor
    }

    @xnuc.ctx.Named("c")
    public class C implements Runnable {
        @xnuc.ctx.Inject
        public A a;
        // getter and setter
        // constructor
        @Override
        public void run() {
            java.util.logging.Logger.getGlobal().info(String.format("name:%s", a.getB().getName()));
        }
    }
    ```

- IOC
    ```java
    public class Main {
        public static void main(String[] args) throws Exception {
            var ioc = xnuc.ctx.Context.run(Main.class);
            new Thread((Runnable) ioc.get("c")).run();
            Thread.sleep(1000);
        }
    }
    ```
    
    输出：
    ```
    Apr 01, 2023 7:59:54 AM xnuc.example.C run
    INFO: name:hello world!
    ```

## aop代理

假设存在代理关系。

```mermaid
  graph TD;
      Caop-->C;
```

### 快速开始

- Proxy
    ```java
    @xnuc.ctx.Named("c")
    public class C implements Runnable {
        @xnuc.ctx.Inject
        public A a;
        // getter and setter
        // constructor
        @Override
        public void run() {
            java.util.logging.Logger.getGlobal().info(String.format("name:%s", a.getB().getName()));
        }
    }
    ```

    ```java
    @xnuc.ctx.Aspect
    public class Caop {
        @xnuc.ctx.Pointcut("xnuc.example.C#run")
        public void log() {
            java.util.logging.Logger.getGlobal().info(String.format("ts:%d", System.currentTimeMillis()));
        }
    }
    ```

- AOP
    ```java
    public class Main {
        public static void main(String[] args) throws Exception {
            var ioc = xnuc.ctx.Context.run(Main.class);
            new Thread((Runnable) ioc.get("c")).run();
            Thread.sleep(1000);
        }
    }
    ```

    输出：
    ```
    Apr 01, 2023 8:14:55 AM xnuc.example.Caop log
    INFO: ts:1680336895604
    Apr 01, 2023 8:14:55 AM xnuc.example.C run
    INFO: name:hello world!
    ```
