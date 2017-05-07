package com.frontpagenews.services;

import com.frontpagenews.models.AdminModel;
import com.frontpagenews.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository repository;

    @Override
    public AdminModel save(AdminModel entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<AdminModel> getAll() {
        return this.repository.findAll();
    }

    @Override
    public AdminModel getById(String id) {return this.repository.findOne(id);}

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }

    public AdminModel getByUsernameAndPassword(String username, String password) {
        return this.repository.findByIdAndPassword(username, password);
    }

    public boolean verifyAdmin (String username, String password) {
        AdminModel admin = getByUsernameAndPassword(username, password);
        if (admin.getUsername().length() > 0) {
            return true;
        };
        return false;
    };
}