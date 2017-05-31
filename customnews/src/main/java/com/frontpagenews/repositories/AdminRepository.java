package com.frontpagenews.repositories;

import com.frontpagenews.models.AdminModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends MongoRepository<AdminModel, String> {
    public List<AdminModel> findAll();
    public AdminModel findOne(String id);
    public AdminModel findByUsernameAndPassword(String username, String password);
    public AdminModel findByUsername(String username);
    public AdminModel findByToken(String token);
    public AdminModel save(AdminModel admin);
    public void delete(String id);
    public long count();
    boolean exists(String id);
}
