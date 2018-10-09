package top.maniy.vo;

/**
 * @author liuzonghua
 * @Package top.maniy.vo
 * @Description:
 * @date 2018/10/6 15:39
 */
public class User {

    private String username;
    private String password;
    private boolean rememberMe;

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
