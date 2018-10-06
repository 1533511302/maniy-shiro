package top.maniy.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author liuzonghua
 * @Package top.maniy.test
 * @Description:
 * @date 2018/10/6 14:17
 */
public class CustomRealmTest {
    @Test
    public void testAUthentication() {

        CustomRealm customRealm = new CustomRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher= new HashedCredentialsMatcher();
        //加密的方法 md5
        matcher.setHashAlgorithmName("md5");
        //加密的次数 1次
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("maniy", "123456");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermissions("user:add","user:delete");
    }
}
