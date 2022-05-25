package com.daicy.koala.aggregator.jvm;

import com.daicy.koala.aggregator.AggregateResult;
import com.daicy.koala.aggregator.InnerAggregator;
import com.daicy.koala.structure.Column;
import com.daicy.koala.structure.Row;
import com.daicy.koala.structure.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by changya.dai on 2017/1/18.
 */
public class JvmAggregator extends InnerAggregator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Table table;

    public static final String NULL_STRING = "#NULL";

    public JvmAggregator(Table table) {
        this.table = table;
    }

    @Override
    public AggregateResult queryAggData(List<Column> aggColumnIndexList, List<Column> groupByColumnList) {
        List<Row> rowList = table.getSelectedRows();
        groupByColumnList.forEach(e -> e.setPosition(table.getColumnIndex(e.getColumnName())));
        aggColumnIndexList.forEach(e -> e.setPosition(table.getColumnIndex(e.getColumnName())));
        Map<Dimensions, Double[]> grouped = rowList.stream()
                .collect(Collectors.groupingBy(row -> {
                    String[] ds = groupByColumnList.stream().map(
                            d -> row.getColumnValue(d.getPosition())
                    ).toArray(String[]::new);
                    return new Dimensions(ds);
                }, AggregateCollector.getCollector(aggColumnIndexList)));

        String[][] result = new String[grouped.keySet().size()][groupByColumnList.size() + aggColumnIndexList.size()];
        int i = 0;
        for (Dimensions d : grouped.keySet()) {
            result[i++] = Stream.concat(Arrays.stream(d.dimensions), Arrays.stream(grouped.get(d)).map(e -> e.toString())).toArray(String[]::new);
        }
        int dimSize = groupByColumnList.size();
        for (String[] row : result) {
            IntStream.range(0, dimSize).forEach(d -> {
                if (row[d] == null) row[d] = NULL_STRING;
            });
        }
        return new AggregateResult(aggColumnIndexList, result);
    }

    private class Dimensions {
        private String[] dimensions;

        public Dimensions(String[] dimensions) {
            this.dimensions = dimensions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Dimensions that = (Dimensions) o;

            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(dimensions, that.dimensions);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(dimensions);
        }
    }

}

