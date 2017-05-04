package com.frontpagenews.repositories;

import com.frontpagenews.models.AdminModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository {
    public List<AdminModel> findAll();
    public AdminModel findOne(String id);
    public AdminModel findByUsername(String username);
    public AdminModel save(AdminModel admin);
    public void delete(String id);
    public long count();
    boolean exists(String id);
}
