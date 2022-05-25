package com.daicy.koala.structure.impl;

import com.daicy.koala.exception.MyException;
import com.daicy.koala.structure.Column;
import com.daicy.koala.structure.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by changya.dai on 9/10/15.
 */
public class RowImpl implements Row {

    private static final Logger logger = LoggerFactory.getLogger(RowImpl.class);


    private static final String COLUMN_INDEX_EXCEEDED = "Total column present are : " ;
    private static final String NO_SUCH_COLUMN_EXIST = "No such column exist by name : " ;


    private List<Object> rowData;
    private List<Column> columnList;

    public RowImpl(List<Column> columnList) {
        this.columnList = columnList;
        this.rowData = new ArrayList<Object>(columnList.size());
    }


    private Column getColumnByName(String columnName) throws MyException {
        for(Column column:columnList){
            if(column.getColumnName().equalsIgnoreCase(columnName))
                return column;
        }

        throw new MyException(NO_SUCH_COLUMN_EXIST+columnName );
    }

    public void addRow(List<Object> rowData){
        this.rowData.addAll(rowData);
    }

    public void addData(int columnIndex , Object value){
        rowData.add(columnIndex, value);
    }


    public void addData(String column , Object value){

        int pos = 0;
        try {
            pos = getColumnByName(column).getPosition();
        } catch (MyException e) {
            logger.error("addData",e);
        }
        rowData.add(pos, value);
    }

    public void setData(String column, Object value) {
        int pos = 0;
        try {
            pos = getColumnByName(column).getPosition();
        } catch (MyException e) {
            logger.error("setData", e);
        }
        rowData.set(pos, value);
    }



    @Override
    public Object getColumnValue(int index){
        if(index > columnList.size())
            logger.error("getColumnValue error", new MyException(COLUMN_INDEX_EXCEEDED+index));
        return rowData.get(index);
    }

    @Override
    public Object getColumnValue(String columnName) {
        Column column = null;
        try {
            column = getColumnByName(columnName);
        } catch (MyException e) {
            logger.error(" error ", e);
        }
        return rowData.get(column.getPosition());
    }

    @Override
    public List<Object> getAllColumnValue(){
        return this.rowData;
    }

    public List<Object> getRowData() {
        return rowData;
    }

    public void setRowData(List<Object> rowData) {
        this.rowData = rowData;
    }

    @Override
    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }


}
