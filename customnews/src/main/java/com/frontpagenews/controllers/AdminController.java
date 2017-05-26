package com.frontpagenews.controllers;

import com.frontpagenews.models.AdminModel;
import com.frontpagenews.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping(method= RequestMethod.GET)
    public List<AdminModel> getAllAdmins() {
         return adminService.getAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    public AdminModel createAdmin(@Valid @RequestBody AdminModel admin) {
        return adminService.save(admin);
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginAdmin(@Valid @RequestBody AdminModel adminModel) {
        AdminModel admin = adminService.getByUsernameAndPassword(adminModel.getUsername(), adminModel.getPassword());
        if(admin != null)
            return new ResponseEntity<String>("{\"OK\":1}", HttpStatus.OK);
        return new ResponseEntity<String>("{\"OK\":0}", HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public ResponseEntity<AdminModel> getAdminById(@PathVariable("id") String id) {
        AdminModel admin = adminService.getById(id);
        if(admin == null) {
            return new ResponseEntity<AdminModel>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<AdminModel>(admin, HttpStatus.OK);
        }
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    public ResponseEntity<AdminModel> updateAdmin(@Valid @RequestBody AdminModel admin, @PathVariable("id") String id) {
        AdminModel adminData = adminService.getById(id);
        if(adminData == null) {
            return new ResponseEntity<AdminModel>(HttpStatus.NOT_FOUND);
        }
        adminData.setUsername(admin.getUsername());
        adminData.setPassword(admin.getPassword());
        adminData.setEmail(admin.getEmail());
        AdminModel updatedAdmin = adminService.save(adminData);
        return new ResponseEntity<AdminModel>(updatedAdmin, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public void deleteAdmin(@PathVariable("id") String id) {
        adminService.delete(id);
    }
}
