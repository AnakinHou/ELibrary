package org.tools.fx.library.service;

import java.util.List;
import javax.persistence.EntityManager;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.entity.User;
import org.tools.fx.library.model.Result;

public class UserService {

    public Result login(String encodeUsername, String encodePassword, String extraEncode) {
        EntityManager em = DBHelper.getEM();

        List<User> userList = em
                .createQuery(" select u from User u where u.username=:uname and u.password=:upass ",
                        User.class)
                .setParameter("uname", encodeUsername).setParameter("upass", encodePassword)
                .getResultList();
        em.close();
        if (userList == null || userList.size() ==0 || userList.size() > 1) {
            return new Result(false, "没有查询到用户");
        } else {
            return new Result(true, "成功查询到用户");
        }



    }

}
