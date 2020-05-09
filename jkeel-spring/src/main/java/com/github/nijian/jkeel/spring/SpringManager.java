package com.github.nijian.jkeel.spring;

import com.github.nijian.jkeel.concept.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class SpringManager implements Manager {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PersistenceContext(unitName = "JPAUnit")
    EntityManager entityManager;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
