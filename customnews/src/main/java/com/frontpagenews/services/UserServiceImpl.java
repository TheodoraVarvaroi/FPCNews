package com.frontpagenews.services;

import com.frontpagenews.models.UserModel;
import com.frontpagenews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserModel save(UserModel entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<UserModel> getAll() {
        return this.repository.findAll();
    }

    @Override
    public UserModel getById(String id) {return this.repository.findOne(id);}

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }

    public UserModel getByUsernameAndPassword(String username, String password) {
        return this.repository.findByIdAndPassword(username, password);
    }

    public boolean verifyUser (String username, String password) {
        UserModel admin = getByUsernameAndPassword(username, password);
        if (admin.getUsername().length() > 0) {
            return true;
        };
        return false;
    };
}