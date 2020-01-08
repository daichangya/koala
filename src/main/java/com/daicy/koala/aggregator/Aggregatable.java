package com.daicy.koala.aggregator;

import com.daicy.koala.structure.Column;

import java.util.List;

/**
 * Created by yfyuan on 2017/1/13.
 */
public interface Aggregatable {


//    /**
//     * The data provider that support DataSource side Aggregation must implement this method.
//     *
//     * @return
//     */
//    String[] getColumn() throws Exception;

    /**
     * The data provider that support DataSource side Aggregation must implement this method.
     *
     * @param ac aggregate configuration
     * @return
     */
    AggregateResult queryAggData(List<Column> aggColumnIndexList, List<Column> groupByColumnList);

}
