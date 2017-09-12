package com.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewProductFilter {
    private String titleContains;

    @JsonProperty("title_contains")
    public String getTitleContains() {
        return titleContains;
    }

    public void setTitleContains(String titleContains) {
        this.titleContains = titleContains;
    }
}
