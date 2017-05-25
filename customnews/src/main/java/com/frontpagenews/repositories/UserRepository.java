package com.frontpagenews.repositories;

import com.frontpagenews.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    public List<UserModel> findAll();
    public UserModel findOne(String id);
    public UserModel findByUsernameAndPassword(String username, String password);
    public UserModel save(UserModel user);
    public void delete(String id);
    public long count();
    boolean exists(String id);
}