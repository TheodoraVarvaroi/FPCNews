package com.frontpagenews.controllers;

import com.frontpagenews.models.AdminModel;
import com.frontpagenews.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminRepository adminRepository;

    @RequestMapping(method= RequestMethod.GET)
    public List<AdminModel> getAllAdmins() {
         return adminRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    public AdminModel createAdmin(@Valid @RequestBody AdminModel admin) {
        return adminRepository.save(admin);
    }

    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public ResponseEntity<AdminModel> getAdminById(@PathVariable("id") String id) {
        AdminModel admin = adminRepository.findOne(id);
        if(admin == null) {
            return new ResponseEntity<AdminModel>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<AdminModel>(admin, HttpStatus.OK);
        }
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    public ResponseEntity<AdminModel> updateAdmin(@Valid @RequestBody AdminModel admin, @PathVariable("id") String id) {
        AdminModel adminData = adminRepository.findOne(id);
        if(adminData == null) {
            return new ResponseEntity<AdminModel>(HttpStatus.NOT_FOUND);
        }
        adminData.setUsername(admin.getUsername());
        adminData.setParola(admin.getParola());
        adminData.setEmail(admin.getEmail());
        AdminModel updatedAdmin = adminRepository.save(adminData);
        return new ResponseEntity<AdminModel>(updatedAdmin, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public void deleteAdmin(@PathVariable("id") String id) {
        adminRepository.delete(id);
    }
}
