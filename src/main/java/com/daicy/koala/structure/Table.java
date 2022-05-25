package com.daicy.koala.structure;

import com.daicy.koala.exception.MyException;
import org.apache.calcite.sql.SqlKind;

import java.util.List;

/**
 * Created by changya.dai on 9/9/15.
 */
public interface Table {


    public Table addRow(Object... arg) throws MyException;

    public Table selectRowQuery(String columnName, Object value) throws MyException;

    public Table selectRowQuery(SqlKind sqlKind, Object... value) throws MyException;

    public void createIndex(String columnName) throws MyException;

    public void deleteIndex(String columnName) throws MyException;

    public Table joinTable(Table table, String columnFormerTable, String columnLaterTable) throws MyException;

    public String getTableName();

    public List<Row> getSelectedRows();

    public List<Column> getColumnList();

    public int getColumnIndex(String columnName);

}
