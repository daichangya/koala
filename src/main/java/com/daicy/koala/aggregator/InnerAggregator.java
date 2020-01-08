package com.daicy.koala.aggregator;

import java.util.Map;

/**
 * Created by zyong on 2017/1/9.
 */
public abstract class InnerAggregator implements Aggregatable {

    protected Map<String, String> query;

    public InnerAggregator() {}

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public void beforeLoad(String[] header) {}
    public void loadBatch(String[] header, String[][] data) {}
    public void afterLoad(){}
}
