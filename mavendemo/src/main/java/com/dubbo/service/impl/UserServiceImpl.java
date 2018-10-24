package com.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.service.UserService;

/**
 * @author diamod
 * @date 2018-09-06:20:52
 */

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName() {
        return "diamond";
    }
}
