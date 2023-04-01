package xnuc.example;

@xnuc.ctx.Aspect
public class Caop {
    @xnuc.ctx.Pointcut("xnuc.example.C#run")
    public void log() {
        java.util.logging.Logger.getGlobal().info(String.format("ts:%d", System.currentTimeMillis()));
    }
}