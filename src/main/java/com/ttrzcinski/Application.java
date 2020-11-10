package com.ttrzcinski;

import io.micronaut.runtime.Micronaut;

public final class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    /**
     * Hidden constructor as it is a util class.
     */
    private Application() {}
}
