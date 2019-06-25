package javaLI.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrivateReflection {

    public static void main(String[] args) {
        Entity e1 = new Entity();
        System.out.println("Package name: " + e1.getClass().getPackage().getName());
        System.out.println("Full class name: " + e1.getClass().getName());
        System.out.println("Super class name: " + e1.getClass().getSuperclass().getName());
        System.out.println("Class loader name: " + e1.getClass().getClassLoader().getClass().getName());
        try {
            Class<?> clazz = Class.forName("javaLI.reflection.Entity");
            Entity e2 = (Entity) clazz.newInstance();
            Constructor<?>[] constructors = clazz.getConstructors();
            if (constructors.length > 0) {
                Entity e3 = (Entity) constructors[0].newInstance();
            }

            try {
                Field privateField = clazz.getDeclaredField("privateStr");
                privateField.setAccessible(true);
                privateField.set(e2, "private");
                System.out.println("Private field value: " + privateField.get(e2));
            } catch (NoSuchFieldException |
                    IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                Field packageField = clazz.getDeclaredField("packageStr");
                packageField.setAccessible(true);
                packageField.set(e2, "package");
                System.out.println("Package field value: " + packageField.get(e2));
            } catch (NoSuchFieldException |
                    IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                Field protectedField = clazz.getDeclaredField("protectedStr");
                protectedField.setAccessible(true);
                protectedField.set(e2, "protected");
                System.out.println("Protected field value: " + protectedField.get(e2));
            } catch (NoSuchFieldException |
                    IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                Field publicField = clazz.getDeclaredField("publicStr");
                publicField.setAccessible(true);
                publicField.set(e2, "public");
                System.out.println("Private field value: " + publicField.get(e2));
            } catch (NoSuchFieldException |
                    IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                Method[] entityMethods = clazz.getDeclaredMethods();
                for (Method method : entityMethods) {
                    method.setAccessible(true);
                    System.out.println(method + ": " + method.invoke(e2));
                }
            } catch (IllegalAccessException |
                    IllegalArgumentException |
                    InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class Entity {
    private String privateStr;

    String packageStr;

    protected String protectedStr;

    public String publicStr;

    public Entity() {
    }

    private String getPrivateStr() {
        return privateStr;
    }

    String getPackageStr() {
        return packageStr;
    }

    protected String getProtectedStr() {
        return protectedStr;
    }

    public String getPublicStr() {
        return publicStr;
    }
}