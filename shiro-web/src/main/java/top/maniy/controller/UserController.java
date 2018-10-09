package top.maniy.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.maniy.vo.User;

/**
 * @author liuzonghua
 * @Package top.maniy.controller
 * @Description:
 * @date 2018/10/6 15:36
 */
@Controller
public class UserController {
    @RequestMapping(value = "/subLogin" ,method = RequestMethod.POST,
    produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =new UsernamePasswordToken(user.getUsername(),
                user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return  e.getMessage();
        }
        if(subject.hasRole("admin")){
            if (subject.isPermitted("user:select")){
                return "权限为user:select";
            }
            return "有admin权限";
        }
        return "无admin权限";
    }

    /**
     * 通过注解配置授权
     * 用户角色必须具备admin权限才能访问
     * @return
     */
    @RequiresRoles("admin")
    //@RequiresPermissions("xxxx")
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    public String TestRole(){
        return "testRole success";
    }

    /**
     * 通过注解配置授权
     * 用户角色必须具备admin权限才能访问
     * @return
     */
    @RequiresRoles("admin1")
    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    @ResponseBody
    public String TestRole1(){
        return "testRole1 success";
    }

    @RequestMapping(value = "/testRole2",method = RequestMethod.GET)
    @ResponseBody
    public String TestRole2(){
        return "testRole2 success";
    }
    @RequestMapping(value = "/testRole3",method = RequestMethod.GET)
    @ResponseBody
    public String TestRole3(){
        return "testRole3 success";
    }

    @RequestMapping(value = "/testPerms",method = RequestMethod.GET)
    @ResponseBody
    public String TestPerms(){
        return "testPerms success";
    }
    @RequestMapping(value = "/testPerms1",method = RequestMethod.GET)
    @ResponseBody
    public String TestPerms1(){
        return "testPerms1 success";
    }
}
