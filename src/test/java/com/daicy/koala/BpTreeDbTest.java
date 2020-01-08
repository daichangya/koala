package com.daicy.koala;

import org.junit.Test;

import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

/**
 * @author: create by daichangya
 * @version: v1.0
 * @description: com.daicy.koala
 * @date:19-12-23
 */
public class BpTreeDbTest {

    @Test
    public void subMap() {
        BpTreeDb bpTree = new BpTreeDb(20,3);
        for (int i = 0; i < 100; i++) {
            bpTree.put(i,i);
        }
        StringWriter stringWriter = new StringWriter();
        bpTree.printXml(stringWriter);
        System.out.println(stringWriter);
        Set<Map.Entry<Integer,Integer>> sets = bpTree.headMap(10).entrySet();
        for (Map.Entry<Integer, Integer> set : sets) {
            System.out.println(set.getKey());
        }
    }
}