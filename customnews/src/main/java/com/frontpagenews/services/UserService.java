package com.frontpagenews.services;

import com.frontpagenews.models.UserModel;

public interface UserService extends CrudService<UserModel>{
    public boolean verifyUser (String username, String password);
    public UserModel getByUsernameAndPassword(String username, String password);
}
