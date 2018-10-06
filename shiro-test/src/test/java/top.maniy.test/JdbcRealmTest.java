package top.maniy.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author liuzonghua
 * @Package top.maniy.test
 * @Description:
 * @date 2018/10/5 20:54
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //jdbcReal可以设置权限开关，默认是false 需要手动改为true
        jdbcRealm.setPermissionsLookupEnabled(true);


        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken("Mark","123456");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkRoles("admin");

        subject.checkPermission("user:select");


    }


    @Test
    public void testAuthentication2(){

        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //jdbcReal可以设置权限开关，默认是false 需要手动改为true
        jdbcRealm.setPermissionsLookupEnabled(true);
        //自己写sql查询语句
        String sql="select password from test_user where user_name=?";
        jdbcRealm.setAuthenticationQuery(sql);
        String roleSql ="select role_name from test_user_roles where user_name=?";
        jdbcRealm.setUserRolesQuery(roleSql);
        String  perSql="select permission from test_roles_permissions where role_name = ?";
        jdbcRealm.setPermissionsQuery(perSql);
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken("maniy","123456");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        subject.checkRole("admin");

        subject.checkPermission("user:select");


    }
}
