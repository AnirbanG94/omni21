package com.omnichannel.app.controller.usermanagment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.model.DTO.HodDTO;
import com.omnichannel.app.model.DTO.OnlyMenuDTO;
import com.omnichannel.app.model.DTO.RoleDTO;
import com.omnichannel.app.model.DTO.UserDto;
import com.omnichannel.app.model.DTO.ViewPrivilegeDTO;
import com.omnichannel.app.model.usermanagement.MyUserDetails;
import com.omnichannel.app.model.usermanagement.Privilege;
import com.omnichannel.app.model.usermanagement.Role;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.service.UserManagmentService;

@RestController
// @RequestMapping("/api/usermanagment")
public class UserManagmentController {

    @Autowired
    UserManagmentService service;

    @GetMapping(value = "/getRoleList")
    public List<Role> getRoleList() {
        return service.getRoleList();
    }

    @GetMapping(value = "/getPrivileges")
    public List<Privilege> getPrivileges() {
        return service.getPrivileges();
    }

    @GetMapping(value = "/getViewPrivileges")
    public List<ViewPrivilegeDTO> getViewPrivileges() {
        return service.getViewPrivileges();
    }

    @GetMapping(value = "/getMenu")
    public List<OnlyMenuDTO> getMenu() {
        return service.getMenu();
    }

    @GetMapping(value = "/getMenuForEmail")
    public List<OnlyMenuDTO> getMenuForEmail() {
        return service.getMenuForEmail();
    }

    @GetMapping(value = "/getMenuForApproval")
    public List<OnlyMenuDTO> getMenuForApproval() {
        return service.getMenuForApproval();
    }

    @GetMapping(value = "/getMenuForIdentification")
    public List<OnlyMenuDTO> getMenuForIdentification() {
        return service.getMenuForIdentification();
    }

    @GetMapping(value = "/getHodList")
    public List<HodDTO> getHodList() {
        return service.getHodList();
    }

    @PostMapping(value = "/addUser", consumes = "application/json")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDto UDTO, HttpServletRequest request) {
        return service.addUser(UDTO);
    }

    @PutMapping(value = "/updateUser", consumes = "application/json")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto UDTO, HttpServletRequest request) {
        return service.updateUser(UDTO);
    }

    @GetMapping(value = "/getUser")
    public List<User> getUser() {
        return service.getUser();
    }

    @GetMapping(value = "/getUserById")
    public UserDto getUserById(@RequestParam Integer id) {
        return service.getUserById(id);
    }

    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam Integer id) {
        return service.deleteUser(id);
    }

    @PutMapping(value = "/userActivation")
    public ResponseEntity<String> userActivation(@RequestParam Integer id) {
        return service.userActivation(id);
    }

    @PutMapping(value = "/userDeactivation")
    public ResponseEntity<String> userDeactivation(@RequestParam Integer id) {
        return service.userDeactivation(id);
    }

    @PostMapping(value = "/addRole", consumes = "application/json")
    public ResponseEntity<String> addRole(@RequestBody RoleDTO UDTO, HttpServletRequest request) {
        return service.addRole(UDTO);
    }

    @PostMapping(value = "/editRole", consumes = "application/json")
    public ResponseEntity<String> editRole(@RequestBody RoleDTO UDTO, HttpServletRequest request) {
        return service.editRole(UDTO);
    }

    @GetMapping(value = "/getRoleById")
    public RoleDTO getRoleById(@RequestParam Integer id) {
        return service.getRoleById(id);
    }

    @DeleteMapping(value = "/deleteRole")
    public ResponseEntity<String> deleteRole(@RequestParam Integer id) {
        return service.deleteRole(id);
    }

    @GetMapping(value = "/CheckUsername")
    public Boolean CheckUsernames(@RequestParam String username) {
        return service.CheckUsernames(username);
    }

    @GetMapping(value = "/CheckUserEmail")
    public Boolean CheckUserEmail(@RequestParam String email) {
        return service.CheckUserEmail(email);
    }

    @PutMapping(value = "/forgetPass")
    public ResponseEntity<String> forgetPass(@RequestParam String username, @RequestParam String email) {
        return service.forgetPass(username, email);
    }

}
