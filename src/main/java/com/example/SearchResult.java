package com.example;

import java.io.Serializable;

public class SearchResult implements Serializable {

    private Integer value;

    public SearchResult(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "value='" + value + '\'' +
                '}';
    }

    public Integer getValue() {
        return value;
    }

    public SearchResult setValue(Integer value) {
        this.value = value;
        return this;
    }
}
