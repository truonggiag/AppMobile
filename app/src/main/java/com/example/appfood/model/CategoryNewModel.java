package com.example.appfood.model;

import java.util.List;

public class CategoryNewModel {
    boolean success;
    String message;
    List<CategoryNew> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryNew> getResult() {
        return result;
    }

    public void setResult(List<CategoryNew> result) {
        this.result = result;
    }
}
