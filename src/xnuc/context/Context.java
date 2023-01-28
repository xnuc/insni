package xnuc.context;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Enumeration;
import java.lang.reflect.Field;
import java.beans.PropertyDescriptor;

import xnuc.anno.Value;
import xnuc.anno.Named;
import xnuc.anno.Inject;

public class Context {

    public HashMap<String, Object> instances;
    public HashMap<String, Object> unreadyInstances;
    public HashMap<String, Class<?>> defineds;

    public Context() {
        this.instances = new HashMap<>();
        this.unreadyInstances = new HashMap<>();
        this.defineds = new HashMap<>();
    }

    public static Context run(Class<?> clz) throws Exception {
        return new Context().init(clz);
    }

    public Context init(Class<?> clz) throws Exception {
        String pkg = clz.getPackage().getName();
        Enumeration<URL> resources = clz.getClassLoader().getResources(pkg.replace(".", "/"));
        List<Class<?>> clzList = new ArrayList<>();

        if (!resources.hasMoreElements())
            return this;
        File file = new File(resources.nextElement().getFile());
        if (!file.isDirectory())
            return this;
        subdir(pkg, file, clzList);

        for (Class<?> c : clzList) {
            if (c.getAnnotation(Named.class) != null) {
                defineds.put(c.getAnnotation(Named.class).value(), c);
            }
        }
        return this;
    }

    private void subdir(String pkg, File file, List<Class<?>> clzes) throws Exception {
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                String clsName = String.format("%s.%s", pkg, f.getName().substring(0, f.getName().lastIndexOf(".")));
                clzes.add(Class.forName(clsName));
            }
            if (f.isDirectory())
                subdir(String.format("%s.%s", pkg, f.getName()), f, clzes);
        }
    }

    public Object get(String objName) throws Exception {
        if (instances.get(objName) != null)
            return instances.get(objName);
        if (unreadyInstances.get(objName) != null)
            return unreadyInstances.get(objName);

        if (defineds.get(objName) == null)
            throw new Exception(String.format("objName %s undefined", objName));
        Class<?> clz = defineds.get(objName);

        unreadyInstances.put(objName, clz.getDeclaredConstructor().newInstance());
        instances.put(objName, ready(unreadyInstances.get(objName), clz));
        unreadyInstances.remove(objName);

        return instances.get(objName);
    }

    private Object ready(Object rto, Class<?> clz) throws Exception {
        for (Field field : clz.getFields()) {
            if (field.getAnnotation(Value.class) != null) {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clz);
                descriptor.getWriteMethod().invoke(rto, field.getAnnotation(Value.class).value());
            }
            if (field.getAnnotation(Inject.class) != null) {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clz);
                descriptor.getWriteMethod().invoke(rto, get(field.getName()));
            }
        }
        return rto;
    }
}
