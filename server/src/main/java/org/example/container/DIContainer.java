package org.example.container;

import org.example.annotation.Inject;
import org.example.constant.Constants;
import org.example.exceptions.CreationInstanceException;
import org.example.exceptions.DependencyNotRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private final Map<Class<?>, Object> dependencyMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DIContainer.class);

    public <T> void register(Class<T> type, Class<? extends T> implementation) {
        dependencyMap.put(type, createInstance(implementation));
    }

    public <T> T resolve(Class<T> type) {
        if (dependencyMap.containsKey(type)) {
            return type.cast(dependencyMap.get(type));
        } else {
            logger.error(Constants.DIContainer.DEPENDENCY_NOT_REGISTERED, type.getName());
            throw new DependencyNotRegisteredException();
        }
    }

    private <T> T createInstance(Class<? extends T> implementation) {
        try {
            Constructor<?>[] constructors = implementation.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.isAnnotationPresent(Inject.class)) {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    Object[] parameters = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        parameters[i] = resolve(parameterTypes[i]);
                    }
                    return implementation.getDeclaredConstructor(parameterTypes).newInstance(parameters);
                }
            }
            return implementation.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            logger.error(Constants.DIContainer.CREATE_INSTANCE_ERROR, implementation.getName(), e);
            throw new CreationInstanceException();
        }
    }
}

