package ru.courses.main.enums;

import java.util.Objects;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String methodName;

    HttpMethod(String methodName) {
        this.methodName = methodName;
    }

    public static HttpMethod fromText(String httpMethod) {
        for (HttpMethod op : HttpMethod.values()) {
            if (Objects.equals(op.methodName, httpMethod)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Метода " + httpMethod + " не существует");
    }

    public String getMethodName() {
        return this.methodName;
    }
}
