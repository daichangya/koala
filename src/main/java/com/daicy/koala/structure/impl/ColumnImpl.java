package com.daicy.koala.structure.impl;

import com.daicy.koala.structure.Column;

/**
 * Created by changya.dai on 9/9/15.
 */
public class ColumnImpl implements Column {

    String columnName;
    String dataType;
    Object defaultValue;
    Boolean isIndexed;
    Boolean isId;
    int position;
    private String aggType = "";

    public ColumnImpl(String columnName, String dataType, Object defaultValue, Boolean isIndexed, Boolean isId, int position) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.isIndexed = isIndexed;
        this.isId = isId;
        this.position=position;
    }

    public ColumnImpl(String columnName) {
        this.columnName = columnName;
    }

    public ColumnImpl(String columnName, String aggType) {
        this.columnName = columnName;
        this.aggType = aggType;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Boolean getIsIndexed() {
        return isIndexed;
    }

    public void setIsIndexed(Boolean isIndexed) {
        this.isIndexed = isIndexed;
    }

    @Override
    public Boolean getIsId() {
        return isId;
    }

    public void setIsId(Boolean isId) {
        this.isId = isId;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getAggType() {
        return aggType;
    }

    public void setAggType(String aggType) {
        this.aggType = aggType;
    }
}
