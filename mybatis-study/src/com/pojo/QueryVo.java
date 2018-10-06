package com.pojo;

import java.util.List;

/**
 * @author diamod
 * @date 2018-09-01:14:24
 */


public class QueryVo {
    private User user;
    private List<Integer> ids;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
