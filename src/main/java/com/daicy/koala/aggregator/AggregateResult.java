package com.daicy.koala.aggregator;

import com.daicy.koala.structure.Column;

import java.util.List;

/**
 * Created by changya.dai on 2017/1/18.
 */
public class AggregateResult {
    private List<Column> columnList;
    private String[][] data;

    public AggregateResult(List<Column> columnList, String[][] data) {
        this.columnList = columnList;
        this.data = data;
    }

    public AggregateResult() {
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}
