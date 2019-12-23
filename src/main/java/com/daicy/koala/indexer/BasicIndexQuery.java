/*
 * Copyright (c) 2006 and onwards Makoto Yui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daicy.koala.indexer;

import com.google.common.collect.Maps;
import org.apache.calcite.sql.SqlKind;
import org.checkerframework.checker.units.qual.K;

import java.util.*;

public abstract class BasicIndexQuery implements IndexQuery {


    protected final SqlKind _operator;
    protected final Object[] _operands;

    // ---------------------------------------

    public BasicIndexQuery(final SqlKind op, final Object... operands) {
        this._operator = op;
        this._operands = operands;
    }

    @Override
    public SqlKind getOperator() {
        return _operator;
    }

    @Override
    public Object[] getOperands() {
        return _operands;
    }

    @Override
    public Object getOperand(int index) {
        assert (index >= 0) : index;
        assert (index < _operands.length) : "operand[" + index + "] not found: " + _operands;
        return _operands[index];
    }

    abstract SortedMap resultMap();

    public List getIdList() {
        List resultList = new LinkedList();
        SortedMap resultMap = resultMap();
        if (null != resultMap) {
            for (Object value : resultMap.values()) {
                resultList.addAll((Collection) value);
            }
        }
        return resultList;
    }

    public static final class IndexConditionEQ extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionEQ(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.EQUALS, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.EQUALS;
        }

        @Override
        SortedMap resultMap() {
            SortedMap resultMap = Maps.newTreeMap();
            Object value = indexMap.get(getOperand(0)).get(getOperand(1));
            if (null != value) {
                resultMap.put(getOperand(1), value);
            }
            return resultMap;
        }
    }

    public static final class IndexConditionNE extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionNE(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.NOT_EQUALS, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.NOT_EQUALS;
        }

        @Override
        SortedMap resultMap() {
            SortedMap resultMap = Maps.newTreeMap(indexMap.get(getOperand(0)));
            if (null != resultMap) {
                resultMap.remove(getOperand(1));
            }
            return resultMap;
        }
    }

    public static final class IndexConditionGT extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionGT(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.GREATER_THAN, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.GREATER_THAN;
        }

        @Override
        public SortedMap resultMap() {
            SortedMap resultMap = Maps.newTreeMap(indexMap.get(getOperand(0)).tailMap(getOperand(1)));
            if (null != resultMap) {
                resultMap.remove(getOperand(1));
            }
            return resultMap;
        }
    }

    public static final class IndexConditionGE extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionGE(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.GREATER_THAN_OR_EQUAL, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.GREATER_THAN_OR_EQUAL;
        }

        @Override
        public SortedMap resultMap() {
            SortedMap resultMap = indexMap.get(getOperand(0)).tailMap(getOperand(1));
            return resultMap;
        }
    }

    public static final class IndexConditionLT extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionLT(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.LESS_THAN, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.LESS_THAN;
        }

        @Override
        public SortedMap resultMap() {
            SortedMap resultMap = indexMap.get(getOperand(0)).headMap(getOperand(1));
            return resultMap;
        }
    }

    public static final class IndexConditionLE extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionLE(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.LESS_THAN_OR_EQUAL, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.LESS_THAN_OR_EQUAL;
        }

        @Override
        public SortedMap resultMap() {
            SortedMap sortedMap = indexMap.get(getOperand(0));
            Object operand = getOperand(1);
            SortedMap resultMap = Maps.newTreeMap(sortedMap.headMap(operand));
            resultMap.put(operand, sortedMap.get(operand));
            return resultMap;
        }
    }

    public static final class IndexConditionBW extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionBW(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.BETWEEN, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.BETWEEN;
        }

        @Override
        public SortedMap resultMap() {
            SortedMap sortedMap = indexMap.get(getOperand(0));
            SortedMap resultMap = Maps.newTreeMap(sortedMap.subMap(getOperand(1), getOperand(2)));
            Object operand = getOperand(2);
            resultMap.put(operand, sortedMap.get(operand));
            return resultMap;
        }
    }

    public static final class IndexConditionIN extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionIN(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.IN, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.IN;
        }

        @Override
        SortedMap resultMap() {
            SortedMap resultMap = Maps.newTreeMap();
            SortedMap sortedMap = indexMap.get(getOperand(0));
            List list = (List) getOperand(1);
            for (int i = 0; i < list.size(); i++) {
                Object value = sortedMap.get(list.get(i));
                if (null != value) {
                    resultMap.put(list.get(i), value);
                }
            }
            return resultMap;
        }
    }

    public static final class IndexConditionNIN extends BasicIndexQuery {

        protected final HashMap<String, SortedMap> indexMap;

        public IndexConditionNIN(final HashMap<String, SortedMap> indexMap, final Object... operands) {
            super(SqlKind.NOT_IN, operands);
            this.indexMap = indexMap;
        }

        @Override
        public SqlKind getOperator() {
            return SqlKind.NOT_IN;
        }

        @Override
        SortedMap resultMap() {
            SortedMap resultMap = Maps.newTreeMap(indexMap.get(getOperand(0)));
            List list = (List) getOperand(1);
            for (int i = 0; i < list.size(); i++) {
                Object value = resultMap.remove(list.get(i));
            }
            return resultMap;
        }
    }

}
