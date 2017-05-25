package com.frontpagenews.controllers;

import com.frontpagenews.models.UserModel;
import com.frontpagenews.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method=RequestMethod.GET)
    public List<UserModel> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@Valid @RequestBody UserModel user) {
        UserModel usr = userService.getByUsernameAndPassword(user.getUsername(), user.getPassword());

        if(usr != null)
            return new ResponseEntity<String>("{\"OK\":1}", HttpStatus.OK);
        return new ResponseEntity<String>("{\"OK\":0}", HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") String id) {
        UserModel user = userService.getById(id);
        if(user == null) {
            return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<UserModel>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    public ResponseEntity<UserModel> updateUser(@Valid @RequestBody UserModel user, @PathVariable("id") String id) {
        UserModel userData = userService.getById(id);
        if(userData == null) {
            return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND);
        }
        userData.setUsername(user.getUsername());
        userData.setPassword(user.getPassword());
        userData.setEmail(user.getEmail());
        UserModel updatedUser = userService.save(userData);
        return new ResponseEntity<UserModel>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") String id) {
        userService.delete(id);
    }
}
