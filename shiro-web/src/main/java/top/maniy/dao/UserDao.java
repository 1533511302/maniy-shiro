package top.maniy.dao;

import top.maniy.vo.User;

import java.util.List;

/**
 * @author liuzonghua
 * @Package top.maniy.dao
 * @Description:
 * @date 2018/10/6 22:37
 */
public interface UserDao {
    User getUserByUserName(String username);

    List<String> queryRolesByUserName(String userName);

    List<String> queryPermissionByUserName(String userName);
}
