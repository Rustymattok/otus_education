package ru.otus.homework;

import java.lang.reflect.Proxy;

public class DemoProxy{

    public void action() {
        TestLogging testLogging = new TestLogging();
        ClassLoader testLoggingClassLoader = testLogging.getClass().getClassLoader();
        Class<?>[] interfaces = testLogging.getClass().getInterfaces();
        TestLoggingInterface proxy = (TestLoggingInterface) Proxy.newProxyInstance(
                testLoggingClassLoader,
                interfaces,
                new TestLoggingInvocationHandler(testLogging)
        );

        proxy.calculation(10);
    }
}
