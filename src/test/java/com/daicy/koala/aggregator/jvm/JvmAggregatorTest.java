package com.daicy.koala.aggregator.jvm;

import com.daicy.koala.aggregator.AggregateResult;
import com.daicy.koala.exception.MyException;
import com.daicy.koala.structure.Column;
import com.daicy.koala.structure.Row;
import com.daicy.koala.structure.Table;
import com.daicy.koala.structure.impl.ColumnImpl;
import com.daicy.koala.structure.impl.TableImpl;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: create by daichangya
 * @version: v1.0
 * @description: com.daicy.koala.aggregator.jvm
 * @date:20-1-8
 */
public class JvmAggregatorTest {


    private Table table;

    @Before
    public void start() throws MyException {
        table = new TableImpl("Student", "name", "roll");
        table.addRow("Spyke", 101);
        table.addRow("Kyle", 102);
        table.addRow("Johnson", 103);
        table.addRow("Kyle", 104);
        table.addRow("Koala", 104);
        table.createIndex("roll");
        table.createIndex("name");
    }


    @Test
    public void queryAggData() throws Exception {
        List<Column> aggColumnIndexList = Lists.newArrayList(new ColumnImpl("roll","sum"));
        List<Column> groupByColumnList = Lists.newArrayList(new ColumnImpl("name"));
        AggregateResult aggregateResult = new JvmAggregator(table).queryAggData(aggColumnIndexList,groupByColumnList);
        for(String[] strings:aggregateResult.getData()){
            for (String str:strings){
                System.out.println(str);
            }
        }
    }

    @Test
    public void testEq() throws MyException {
        Table tableB = table.selectRowQuery("name","Kyle");
        List<Column> aggColumnIndexList = Lists.newArrayList(new ColumnImpl("roll","sum"));
        List<Column> groupByColumnList = Lists.newArrayList(new ColumnImpl("name"));
        AggregateResult aggregateResult = new JvmAggregator(tableB).queryAggData(aggColumnIndexList,groupByColumnList);
        for(String[] strings:aggregateResult.getData()){
            for (String str:strings){
                System.out.println(str);
            }
        }
    }

}