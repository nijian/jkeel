package com.github.nijian.jkeel.commons.entity.query;

import com.github.nijian.jkeel.commons.ObjectHolder;
import org.junit.Test;

import java.net.URL;

public class QueryTest {

    @Test
    public void testQuery() {

        try {
            URL url = this.getClass().getClassLoader().getResource("query.json");
            QueryRequest queryRequest = ObjectHolder.objectMapper.readValue(url, QueryRequest.class);
            System.out.println(queryRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}