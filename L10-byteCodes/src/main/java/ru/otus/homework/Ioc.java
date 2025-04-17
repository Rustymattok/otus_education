package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {
    }

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new LogInvocationHandler(new TestLogging());
        return (TestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class LogInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;
        private final List<Integer> annotationLogMethods;

        LogInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
            Class<?> clazz = myClass.getClass();
            List<Method> testLoggingMethods = List.of(clazz.getMethods());

            this.annotationLogMethods = testLoggingMethods.stream()
                    .filter(m -> m.isAnnotationPresent(Log.class))
                    .map(Method::getParameterCount)
                    .toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(annotationLogMethods.contains(method.getParameterCount())) {
                logger.info("executed method:{}, param {}", method.getName(), String.join(", ", Arrays.stream(args).map(String::valueOf).toList()));
            }

            return method.invoke(myClass, args);
        }
    }

}
