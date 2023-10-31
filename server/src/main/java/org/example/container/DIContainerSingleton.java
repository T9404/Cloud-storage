package org.example.container;

public class DIContainerSingleton {
    private static DIContainer instance;

    public static DIContainer getInstance() {
        if (instance == null) {
            instance = new DIContainer();
        }
        return instance;
    }
}
