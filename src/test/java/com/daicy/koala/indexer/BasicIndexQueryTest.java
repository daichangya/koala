package com.daicy.koala.indexer;

import com.daicy.koala.exception.MyException;
import com.daicy.koala.structure.Column;
import com.daicy.koala.structure.Row;
import com.daicy.koala.structure.Table;
import com.daicy.koala.structure.impl.TableImpl;
import com.google.common.collect.Lists;
import org.apache.calcite.sql.SqlKind;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: create by daichangya
 * @version: v1.0
 * @description: com.daicy.koala.indexer
 * @date:19-12-23
 */
public class BasicIndexQueryTest {

    private Table table;

    @Before
    public void start() throws MyException {
        table = new TableImpl("Student", "name", "roll");
        table.addRow("Spyke", 101);
        table.addRow("Kyle", 102);
        table.addRow("Johnson", 103);
        table.createIndex("roll");
    }

    @Test
    public void testEq() throws MyException {

        List<Row> rows = table.selectRowQuery("roll", 102).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 1);
    }


    @Test
    public void testNe() throws MyException {

        List<Row> rows = table.selectRowQuery(SqlKind.NOT_EQUALS, "roll", 102).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 2);
    }

    @Test
    public void testGt() throws MyException {

        List<Row> rows = table.selectRowQuery(SqlKind.GREATER_THAN, "roll", 102).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 1);
    }

    @Test
    public void testGe() throws MyException {

        List<Row> rows = table.selectRowQuery(SqlKind.GREATER_THAN_OR_EQUAL, "roll", 102).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 2);
    }

    @Test
    public void testLt() throws MyException {

        List<Row> rows = table.selectRowQuery(SqlKind.LESS_THAN, "roll", 102).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 1);
    }

    @Test
    public void testBw() throws MyException {

        List<Row> rows = table.selectRowQuery(SqlKind.BETWEEN, "roll", 101, 103).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 3);
    }

    @Test
    public void testIn() throws MyException {

        List<Row> rows = table.selectRowQuery(SqlKind.IN, "roll", Lists.newArrayList(101,103)).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 2);
    }

    @Test
    public void testNin() throws MyException {

        List<Row> rows = table.selectRowQuery(SqlKind.NOT_IN, "roll", Lists.newArrayList(102)).getSelectedRows();
        for (Row row : rows) {
            for (Column column : row.getColumnList()) {
                System.out.print(column.getColumnName() + "-" + row.getColumnValue(column.getColumnName()) + " # ");
            }
            System.out.println();
        }
        assertEquals(rows.size(), 2);
    }
}