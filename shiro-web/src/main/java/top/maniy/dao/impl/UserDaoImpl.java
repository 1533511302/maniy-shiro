package top.maniy.dao.impl;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import top.maniy.dao.UserDao;
import top.maniy.vo.User;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author liuzonghua
 * @Package top.maniy.dao.impl
 * @Description:
 * @date 2018/10/6 22:37
 */
@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public User getUserByUserName(String username) {
        String sql ="select username,password from users where username =?";
        List<User> list =jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user =new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    public List<String> queryRolesByUserName(String userName) {
        String sql ="select role_name from user_roles where username =?";
        return jdbcTemplate.query(sql,new String[]{userName}, new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
               return resultSet.getString("role_name");
            }
        });
    }

    public List<String> queryPermissionByUserName(String userName) {
        String sql ="select permission from roles_permissions where role_name=?";
        return jdbcTemplate.query(sql,new String[]{userName},new RowMapper<String>(){
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");
            }
        });

    }
}
