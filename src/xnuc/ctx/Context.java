package xnuc.ctx;

public class Context {

    public java.util.Map<String, Object> instances;
    public java.util.Map<String, Object> unreadyInstances;
    public java.util.Map<String, Class<?>> defineds;

    public Context() {
        this.instances = new java.util.HashMap<>();
        this.unreadyInstances = new java.util.HashMap<>();
        this.defineds = new java.util.HashMap<>();
    }

    public static Context run(Class<?> clz) throws Exception {
        return new Context().init(clz);
    }

    public Context init(Class<?> clz) throws Exception {
        var pkg = clz.getPackage().getName();
        var resources = clz.getClassLoader().getResources(pkg.replace(".", "/"));
        var clzList = new java.util.ArrayList<Class<?>>();

        if (!resources.hasMoreElements())
            return this;
        var file = new java.io.File(resources.nextElement().getFile());
        if (!file.isDirectory())
            return this;
        subdir(pkg, file, clzList);

        for (var c : clzList) {
            if (c.getAnnotation(Named.class) != null) {
                defineds.put(c.getAnnotation(Named.class).value(), c);
            }
        }
        return this;
    }

    private void subdir(String pkg, java.io.File file, java.util.List<Class<?>> clzes) throws Exception {
        for (java.io.File f : file.listFiles()) {
            if (f.isFile()) {
                var clsName = String.format("%s.%s", pkg, f.getName().substring(0, f.getName().lastIndexOf(".")));
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
        var clz = defineds.get(objName);

        unreadyInstances.put(objName, clz.getDeclaredConstructor().newInstance());
        instances.put(objName, ready(unreadyInstances.get(objName), clz));
        unreadyInstances.remove(objName);

        return instances.get(objName);
    }

    private Object ready(Object rto, Class<?> clz) throws Exception {
        for (java.lang.reflect.Field field : clz.getFields()) {
            if (field.getAnnotation(Value.class) != null) {
                var descriptor = new java.beans.PropertyDescriptor(field.getName(), clz);
                descriptor.getWriteMethod().invoke(rto, field.getAnnotation(Value.class).value());
            }
            if (field.getAnnotation(Inject.class) != null) {
                var descriptor = new java.beans.PropertyDescriptor(field.getName(), clz);
                descriptor.getWriteMethod().invoke(rto, get(field.getName()));
            }
        }
        return rto;
    }
}
