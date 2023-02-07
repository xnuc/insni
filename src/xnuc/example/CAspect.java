package xnuc.example;

import xnuc.anno.Aspect;
import xnuc.anno.Pointcut;

@Aspect
public class CAspect {

    @Pointcut("xnuc.example.depend.C#print")
    public void log() {
        System.out.println(String.format("ts:%d", System.currentTimeMillis()));
    }
}
