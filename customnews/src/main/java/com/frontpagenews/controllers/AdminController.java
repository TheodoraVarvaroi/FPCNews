package com.frontpagenews.controllers;

import com.frontpagenews.models.AdminModel;
import com.frontpagenews.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

//    @Configuration
//    @EnableWebMvc
//    public class WebConfig extends WebMvcConfigurerAdapter {
//
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:8081");
//        }
//    }

    @RequestMapping(method= RequestMethod.GET)
    public List<AdminModel> getAllAdmins() {
         return adminService.getAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> register(@Valid @RequestBody AdminModel admin, @RequestHeader("Token") String token) {
        admin.setPassword(generateHash(admin.getPassword()));
        AdminModel adminT = adminService.getByToken(token);
        if (adminT != null) {
            adminService.save(admin);
            return new ResponseEntity<String>("{\"OK\": 1}", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("{\"OK\": 1, \"error\": \"Permission denied\"}", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<String> loginAdmin(@Valid @RequestBody AdminModel adminModel) {
        AdminModel admin = adminService.getByUsernameAndPassword(adminModel.getUsername(), generateHash(adminModel.getPassword()));
        String token = UUID.randomUUID().toString();
        if(admin != null) {
            admin.setToken(token);
            adminService.save(admin);
            return new ResponseEntity<String>("{\"OK\":1, \"token\": \"" + token + "\"}", HttpStatus.OK);
        }
        return new ResponseEntity<String>("{\"OK\":0}", HttpStatus.OK);
    }

    @RequestMapping(value="{username}", method=RequestMethod.GET)
    public ResponseEntity<AdminModel> getAdminByUsername(@PathVariable("username") String username) {
        AdminModel admin = adminService.getByUsername(username);
        if(admin == null) {
            return new ResponseEntity<AdminModel>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<AdminModel>(admin, HttpStatus.OK);
        }
    }

    @RequestMapping(value="{username}", method=RequestMethod.PUT)
    public ResponseEntity<String> updateAdmin(@Valid @RequestBody AdminModel admin, @PathVariable("username") String username, @RequestHeader("Token") String token) {
        AdminModel adminData = adminService.getByUsername(username);
        if(adminData == null) {
            return new ResponseEntity<String>("{\"OK\": 1, \"error\": \"Admin doesn't exist\"}", HttpStatus.NOT_FOUND);
        }
        adminData.setUsername(admin.getUsername());
        if (! admin.getPassword().equals(adminData.getPassword()))
            adminData.setPassword(generateHash(admin.getPassword()));
        else
            adminData.setPassword(admin.getPassword());
        adminData.setEmail(admin.getEmail());
        AdminModel adminT = adminService.getByToken(token);
        if (adminT != null) {
            adminService.save(adminData);
            return new ResponseEntity<String>("{\"OK\": 1}", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("{\"OK\": 1, \"error\": \"Permission denied\"}", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value="{username}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteAdmin(@PathVariable("username") String username, @RequestHeader("Token") String token) {
        AdminModel admin = adminService.getByUsername(username);
        if(admin == null) {
            return new ResponseEntity<String>("{\"OK\": 1, \"error\": \"Admin doesn't exist\"}", HttpStatus.NOT_FOUND);
        }
        AdminModel adminT = adminService.getByToken(token);
        if (adminT != null) {
            adminService.delete(admin.getId());
            return new ResponseEntity<String>("{\"OK\": 1}", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("{\"OK\": 1, \"error\": \"Permission denied\"}", HttpStatus.UNAUTHORIZED);
        }
    }

    public static String generateHash(String input) {
        input = "secret_secret" + input;
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            //
        }
        return hash.toString();
    }

}
