package com.resid.huseynov.service;

import com.resid.huseynov.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getByUsername(String username);
}
