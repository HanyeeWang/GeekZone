package com.hanyee.geekzone.model.source.remote.http.api;


import java.util.List;

/**
 * Created by Hanyee on 16/10/20.
 */
public class GankResponse<T> {

    private boolean error;
    private T results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
