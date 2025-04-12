package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TestLoggingInvocationHandler implements InvocationHandler {
    private TestLoggingInterface testLoggingInterface;
    private static final Logger logger = LoggerFactory.getLogger(TestLoggingInterface.class);


    public TestLoggingInvocationHandler(TestLoggingInterface testLoggingInterface) {
        this.testLoggingInterface = testLoggingInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TestLogging testLogging = new TestLogging();
        Class<?> clazz = testLogging.getClass();
        List<Method> testLoggingMethods = List.of(clazz.getMethods());
        List<Method> annotationLogMethods = testLoggingMethods.stream()
                .filter(m -> m.isAnnotationPresent(Log.class))
                .filter(m -> m.getParameterCount() == method.getParameterCount())
                .toList();

        if(!annotationLogMethods.isEmpty()) {
            logger.info("executed method:{}, param {}", method.getName(), String.join(", ", Arrays.stream(args).map(String::valueOf).toList()));
        }

        return method.invoke(testLoggingInterface, args);
    }
}
