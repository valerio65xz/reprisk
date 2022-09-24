package com.reprisk.companiesnews.model;

import javax.validation.constraints.NotBlank;

public final class Paths {

    @NotBlank
    private final String pathOfArticles;
    @NotBlank
    private final String pathOfDataset;

    public Paths(String pathOfArticles, String pathOfDataset) {
        this.pathOfArticles = pathOfArticles;
        this.pathOfDataset = pathOfDataset;
    }

    public String getPathOfArticles() {
        return pathOfArticles;
    }

    public String getPathOfDataset() {
        return pathOfDataset;
    }

}
