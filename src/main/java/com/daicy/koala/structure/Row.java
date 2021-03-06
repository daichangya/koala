package com.daicy.koala.structure;

import com.daicy.koala.exception.MyException;

import java.util.List;

/**
 * Created by amd on 9/10/15.
 */
public interface Row {

    public Object getColumnValue(int index) throws MyException;

    public Object getColumnValue(String columnName) throws MyException;

    public List<Object> getAllColumnValue();

    public List<Column> getColumnList();
}
