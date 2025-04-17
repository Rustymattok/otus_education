package ru.otus.homework;

import java.lang.reflect.Proxy;

public class DemoProxy{

    public void action() {
        TestLoggingInterface proxy = Ioc.createMyClass();
        proxy.calculation(10);
    }
}
