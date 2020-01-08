package com.daicy.koala.aggregator.jvm;


import com.daicy.koala.structure.Column;
import com.daicy.koala.structure.Row;
import com.daicy.koala.structure.impl.RowImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by yfyuan on 2017/1/20.
 */
public class AggregateCollector<T> implements Collector<Row, Row, Double[]> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Column> valueList;
    private List<Collector> collectors;

    public static <T> AggregateCollector<T> getCollector(List<Column> valueList) {
        return new AggregateCollector(valueList);
    }

    private AggregateCollector(List<Column> valueList) {
        this.valueList = valueList;
        this.collectors = new ArrayList<>(valueList.size());
        valueList.stream().forEach(e -> collectors.add(null));
        IntStream.range(0, valueList.size()).forEach(i -> collectors.set(i, newCollector(valueList.get(i))));
    }

    private double toDouble(Object o) {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }else {
            double result = 0;
            try {
                result = Double.parseDouble((String) o);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private Collector newCollector(Column columnIndex) {
        switch (columnIndex.getAggType()) {
            case "sum":
                return Collectors.summingDouble(this::toDouble);
            case "avg":
                return Collectors.averagingDouble(this::toDouble);
            case "max":
                return Collectors.maxBy(Comparator.comparingDouble(this::toDouble));
            case "min":
                return Collectors.minBy(Comparator.comparingDouble(this::toDouble));
            case "distinct":
                return new CardinalityCollector();
            default:
                return Collectors.counting();
        }
    }

    @Override
    public Supplier<Row> supplier() {
        //new value row array
        return () -> {
            RowImpl row = new RowImpl(valueList);
            IntStream.range(0, valueList.size()).forEach(i -> row.addData(i,collectors.get(i).supplier().get()));
            return row;
        };
    }

    @Override
    public BiConsumer<Row, Row> accumulator() {
        return (row, e) ->
                IntStream.range(0, row.getColumnList().size()).forEach(i -> {
                    collectors.get(i).accumulator().accept(row.getColumnValue(i), e.getColumnValue(valueList.get(i).getPosition()));
                });

    }

    @Override
    public BinaryOperator<Row> combiner() {
        return (a, b) -> {
            RowImpl result = (RowImpl) a;
            IntStream.range(0, a.getColumnList().size()).forEach(i -> result.setData(valueList.get(i).getColumnName(),
                    collectors.get(i).combiner().apply(a.getColumnValue(i), b.getColumnValue(i))));
            return a;
        };
    }

    @Override
    public Function<Row, Double[]> finisher() {
        return (row) -> {
            Double[] result = new Double[row.getColumnList().size()]; //TODO new?
            IntStream.range(0, row.getColumnList().size()).forEach(i -> {
                Object r = collectors.get(i).finisher().apply(row.getColumnValue(i));
                if (r instanceof Double) {
                    result[i] = (Double) r;
                } else if (r instanceof Long) {
                    result[i] = ((Long) r).doubleValue();
                } else if (r instanceof Integer) {
                    result[i] = ((Integer) r).doubleValue();
                } else if (r instanceof Optional) {
                    result[i] = toDouble(((Optional) r).get());
                }

            });
            return result;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
