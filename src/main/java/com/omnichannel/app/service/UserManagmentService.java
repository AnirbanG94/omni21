package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.omnichannel.app.model.DTO.HodDTO;
import com.omnichannel.app.model.DTO.OnlyMenuDTO;
import com.omnichannel.app.model.DTO.RoleDTO;
import com.omnichannel.app.model.DTO.UserDto;
import com.omnichannel.app.model.DTO.ViewPrivilegeDTO;
import com.omnichannel.app.model.usermanagement.Privilege;
import com.omnichannel.app.model.usermanagement.Role;
import com.omnichannel.app.model.usermanagement.User;

public interface UserManagmentService {

	public ResponseEntity<String> addUser(UserDto uDTO);

	public List<HodDTO> getHodList();

	public List<Role> getRoleList();

	public List<Privilege> getPrivileges();

	public List<ViewPrivilegeDTO> getViewPrivileges();

	public ResponseEntity<String> updateUser(@Valid UserDto uDTO);

	public List<User> getUser();

	public ResponseEntity<String> deleteUser(Integer id);

	public ResponseEntity<String> userActivation(Integer id);

	public ResponseEntity<String> userDeactivation(Integer id);

	public UserDto getUserById(Integer id);

	public ResponseEntity<String> addRole(RoleDTO uDTO);

	public ResponseEntity<String> editRole(RoleDTO uDTO);

	public RoleDTO getRoleById(Integer id);

	public ResponseEntity<String> deleteRole(Integer id);

	public List<OnlyMenuDTO> getMenu();

	public List<OnlyMenuDTO> getMenuForEmail();

	public List<OnlyMenuDTO> getMenuForApproval();

	public List<OnlyMenuDTO> getMenuForIdentification();

	public Boolean CheckUsernames(String username);

	public Boolean CheckUserEmail(String email);

	public ResponseEntity<String> forgetPass(String username, String email);

}
