package com.github.nijian.jkeel.spring;

import com.github.nijian.jkeel.concept.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpringManager implements Manager {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

}
