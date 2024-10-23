package com.fsa.cursus.model.response;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;
    private Map<String, String> error;
    private Map<String, Object> metadata;

    public void ok() {
        this.status = "SUCCESS";
    }

    public void ok(T data) {
        this.status = "SUCCESS";
        this.data = data;
    }

    public void ok(String message, T data) {
        this.status = "SUCCESS";
        this.message = message;
        this.data = data;
    }

    public void ok(HashMap<String, Object> metadata) {
        this.status = "SUCCESS";
        this.metadata = metadata;
    }

    public void ok(T data, HashMap<String, Object> metadata) {
        this.status = "SUCCESS";
        this.data = data;
        this.metadata = metadata;
    }

    public void error(String message) {
        this.status = "ERROR";
        this.message = message;
    }

    public void error(String message, Map<String, String> error) {
        this.status = "ERROR";
        this.message = message;
        this.error = error;
    }


}
