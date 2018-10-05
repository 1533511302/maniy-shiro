package top.maniy.test;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author liuzonghua
 * @Package top.maniy.test
 * @Description:
 * @date 2018/10/4 18:00
 */
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm =new SimpleAccountRealm();
    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("mark","123456","admin","user1");
    }

    @Test
    public void testAUthentication(){
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken("mark","123456");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        subject.isAuthenticated();

//        subject.logout();
//        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        subject.checkRoles("admin","user1");
    }
}
