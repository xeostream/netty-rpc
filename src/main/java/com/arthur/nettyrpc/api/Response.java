package com.arthur.nettyrpc.api;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = -952658025095331025L;

    private String requestId;

    private T result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
