package top.maniy.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author liuzonghua
 * @Package top.maniy.filter
 * @Description:自定义role拦截，定义：如果有一个权限存在就可以跳转
 * @date 2018/10/9 12:57
 */
public class RolesOrFilter extends AuthorizationFilter{

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject =getSubject(servletRequest,servletResponse);
        String[] roles =(String[])  o;
        if(roles ==null || roles.length == 0){
            return true;
        }
        for (String role:roles){
            if(subject.hasRole(role)){
                return  true;
            }
        }
        return false;
    }
}
