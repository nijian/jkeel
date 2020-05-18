package com.github.nijian.jkeel.commons.entity.query;

import com.github.nijian.jkeel.commons.ObjectHolder;
import org.junit.Test;

import java.net.URL;

public class QueryTest {

    @Test
    public void testQuery() {

        try {
            URL url = this.getClass().getClassLoader().getResource("query.json");
            Query query = ObjectHolder.objectMapper.readValue(url, Query.class);
            System.out.println(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}