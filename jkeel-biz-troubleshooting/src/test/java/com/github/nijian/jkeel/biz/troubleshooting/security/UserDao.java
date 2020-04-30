package com.github.nijian.jkeel.biz.troubleshooting.security;

import com.github.nijian.jkeel.concept.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getUser(final String orgId, String username){
        String sql = "SELECT u.username name, u.password pass, a.authority role FROM "+
                "comp_users u INNER JOIN comp_authorities a on u.username=a.username WHERE "+
                "u.enabled =1 and u.username = ?";

        User user = (User)jdbcTemplate.queryForObject(sql, new Object[]{username},
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User(orgId);
//                        user.setUsername(rs.getString("name"));
//                        user.setPassword(rs.getString("pass"));
//                        user.setRole(rs.getString("role"));
                        return user;
                    }
                });
        return user;
    }
}