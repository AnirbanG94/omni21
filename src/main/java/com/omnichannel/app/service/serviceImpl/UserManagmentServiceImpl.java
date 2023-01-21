package com.omnichannel.app.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.omnichannel.app.config.EmailConfig;
import com.omnichannel.app.config.RandomPasswordGenerator;
import com.omnichannel.app.model.DTO.HodDTO;
import com.omnichannel.app.model.DTO.MenuDTO;
import com.omnichannel.app.model.DTO.OnlyMenuDTO;
import com.omnichannel.app.model.DTO.RoleDTO;
import com.omnichannel.app.model.DTO.UserDto;
import com.omnichannel.app.model.DTO.ViewPrivilegeDTO;
import com.omnichannel.app.model.admin.EmailSetup;
import com.omnichannel.app.model.admin.Transactionlog;
import com.omnichannel.app.model.extra.EmailEntity;
import com.omnichannel.app.model.usermanagement.AdhocPrivilege;
import com.omnichannel.app.model.usermanagement.AdhocViewPrivilege;
import com.omnichannel.app.model.usermanagement.Privilege;
import com.omnichannel.app.model.usermanagement.Role;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.repository.admin.EmailSetupRepository;
import com.omnichannel.app.repository.admin.TransactionlogRepository;
import com.omnichannel.app.repository.usermanagement.AdhocPrivilegeRepository;
import com.omnichannel.app.repository.usermanagement.AdhocViewPrivilegeRepository;
import com.omnichannel.app.repository.usermanagement.PrivilegeRepository;
import com.omnichannel.app.repository.usermanagement.RoleRepository;
import com.omnichannel.app.repository.usermanagement.UserRepository;
import com.omnichannel.app.repository.usermanagement.ViewPrivilegeRepository;
import com.omnichannel.app.repository.vendor.VendorRegistreationRepository;
import com.omnichannel.app.service.UserManagmentService;

@Service
public class UserManagmentServiceImpl implements UserManagmentService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	ViewPrivilegeRepository viewPrivilegeRepository;

	@Autowired
	AdhocPrivilegeRepository adhocPrivilegeRepository;

	@Autowired
	AdhocViewPrivilegeRepository adhocViewPrivilegeRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	EmailConfig emailService;

	@Autowired
    JavaMailSender emailSender;

	@Autowired
	TransactionlogRepository transactionlogRepository;

	@Autowired
	VendorRegistreationRepository vendorRegistreationRepository;

	@Autowired
	EmailSetupRepository emailSetupRepository;

	@Autowired
	RandomPasswordGenerator randomPasswordGenerator;

	private @NotNull String body1;

	@Override
	public List<Role> getRoleList() {
		List<Role> findAll = roleRepository.findAll();
		List<Role> role = new ArrayList<>();
		for (Role r : findAll) {
			if (r.getStatus()) {
				role.add(r);
			}
		}
		return role;
	}

	@Override
	public List<Privilege> getPrivileges() {
		return privilegeRepository.findAll();
	}

	@Override
	public List<ViewPrivilegeDTO> getViewPrivileges() {
		List<ViewPrivilege> view = viewPrivilegeRepository.findAll();
		List<String> Unique = view.stream().map(urEntity -> urEntity.getMenu()).distinct().collect(Collectors.toList());
		List<ViewPrivilegeDTO> DTO = new ArrayList<>();

		for (String s : Unique) {
			ViewPrivilegeDTO VPDTO = new ViewPrivilegeDTO();

			List<MenuDTO> menu = new ArrayList<>();

			for (ViewPrivilege VP : view) {
				if (VP.getSubmenu().equals(s)) {
					VPDTO.setId(VP.getId());
					VPDTO.setName(VP.getSubmenu());
				}

				if (VP.getMenu().equals(s)) {
					MenuDTO m = new MenuDTO();
					m.setId(VP.getId());
					m.setName(VP.getSubmenu());
					menu.add(m);
				}
				VPDTO.setMenu(menu);
			}
			DTO.add(VPDTO);
		}
		return DTO;
	}

	@Override
	public List<HodDTO> getHodList() {
		List<User> user = userRepository.getHodList();

		List<HodDTO> hod = new ArrayList<>();
		for (User ur : user) {
			if (ur.isAccountNotExpired()) {
				HodDTO dto = new HodDTO();
				dto.setId(ur.getId());
				dto.setName(ur.getName());
				hod.add(dto);
			}

		}
		return hod;
	}

	@Override
	public ResponseEntity<String> addUser(UserDto dto) {
		if (userRepository.existsByUsername(dto.getUsername())) {
			return new ResponseEntity<String>("Username Already Exists !", HttpStatus.NOT_ACCEPTABLE);
		} else if (userRepository.existsByEmail(dto.getEmail())) {
			return new ResponseEntity<String>("Email Id Already Exists !", HttpStatus.NOT_ACCEPTABLE);
		} else {
			String password = dto.getPassword();

			List<String> role = dto.getRole_ids();
			List<String> viewprivilage = dto.getSub_module_id();
			List<String> privilage = dto.getHoc_privileges_id();

			User user = new User();
			user.setUsername(dto.getUsername());
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setMobile(dto.getMobile());
			user.setParentUserId(dto.getHod_id());
			user.setEmployeeId(dto.getEmployeeId());
			user.setProfilePicLink(dto.getProfilePicLink());
			;
			user.setEnabled(true);
			user.setCoId(1);
			user.setLocationId(1);
			user.setHodstatus(dto.isHodstatus());
			user.setAccountNotExpired(true);
			user.setVendorId(0);

			List<Role> roles = new ArrayList<>();
			for (String ids : role) {
				Integer id = Integer.parseInt(ids);
				Optional<Role> findById = roleRepository.findById(id);
				roles.add(findById.get());
			}

			user.setRoles(roles);
			User save = userRepository.save(user);

			for (String ids : privilage) {
				Integer id = Integer.parseInt(ids);
				Optional<Privilege> findById = privilegeRepository.findById(id);
				AdhocPrivilege ahp = new AdhocPrivilege();
				ahp.setName(findById.get().getName());
				ahp.setUser(save);
				adhocPrivilegeRepository.save(ahp);
			}

			for (String ids : viewprivilage) {
				Integer id = Integer.parseInt(ids);
				Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(id);
				AdhocViewPrivilege ahvp = new AdhocViewPrivilege();
				ahvp.setMenu(findById.get().getMenu());
				ahvp.setSubmenu(findById.get().getSubmenu());
				ahvp.setPrivilage(findById.get().getPrivilage());
				ahvp.setUser(save);
				adhocViewPrivilegeRepository.save(ahvp);
			}

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getName() + " User Created");
			transactionlogRepository.save(trlog);

			System.out.println("pocessed!");
			EmailEntity ee = new EmailEntity();
			ee.setRecipient(save.getEmail());
			ee.setSubject("New Account Creat For Omni Channel Retail");
			ee.setMsgBody("Your user id is -> " + save.getUsername() + "<- and password is -> " + password + "<- ");
			emailService.sendSimpleMail(ee);

			return new ResponseEntity<String>("User successfully created!!", HttpStatus.CREATED);
		}

	}

	@Override
	public ResponseEntity<String> updateUser(@Valid UserDto dto) {

		System.out.println("++++++++++++++++" + dto.getProfilePicLink());

		List<AdhocViewPrivilege> ahvprivilage = adhocViewPrivilegeRepository.findUserByUserid(dto.getId());
		for (AdhocViewPrivilege adhocViewPrivilege : ahvprivilage) {
			System.out.println(adhocViewPrivilege.getId() + " deleted");
			adhocViewPrivilegeRepository.deleteByrow(adhocViewPrivilege.getId());
		}
		List<AdhocPrivilege> ahprivilage = adhocPrivilegeRepository.findUserByUserid(dto.getId());
		for (AdhocPrivilege adhocPrivilege : ahprivilage) {
			System.out.println(adhocPrivilege.getId() + " deleted");
			adhocPrivilegeRepository.deleteByrow(adhocPrivilege.getId());
		}

		if (dto.getPassword().equals(null) || dto.getPassword().equals("")) {

			Optional<User> findById2 = userRepository.findById(dto.getId());

			List<String> role = dto.getRole_ids();
			List<String> viewprivilage = dto.getSub_module_id();
			List<String> privilage = dto.getHoc_privileges_id();

			User user = new User();
			user.setId(dto.getId());
			user.setUsername(dto.getUsername());
			user.setPassword(findById2.get().getPassword());
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setMobile(dto.getMobile());
			user.setParentUserId(dto.getHod_id());
			user.setEmployeeId(dto.getEmployeeId());
			user.setProfilePicLink(dto.getProfilePicLink());
			user.setEnabled(true);
			user.setCoId(1);
			user.setLocationId(dto.getLocationId());
			user.setHodstatus(dto.isHodstatus());
			user.setAccountNotExpired(true);
			user.setVendorId(findById2.get().getVendorId());

			List<Role> roles = new ArrayList<>();
			for (String ids : role) {
				Integer id = Integer.parseInt(ids);
				Optional<Role> findById = roleRepository.findById(id);
				roles.add(findById.get());
			}

			user.setRoles(roles);
			User save = userRepository.save(user);

			for (String ids : privilage) {
				Integer id = Integer.parseInt(ids);
				Optional<Privilege> findById = privilegeRepository.findById(id);
				AdhocPrivilege ahp = new AdhocPrivilege();
				ahp.setName(findById.get().getName());
				ahp.setUser(save);
				adhocPrivilegeRepository.save(ahp);
			}

			for (String ids : viewprivilage) {
				Integer id = Integer.parseInt(ids);
				Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(id);
				AdhocViewPrivilege ahvp = new AdhocViewPrivilege();
				ahvp.setMenu(findById.get().getMenu());
				ahvp.setSubmenu(findById.get().getSubmenu());
				ahvp.setPrivilage(findById.get().getPrivilage());
				ahvp.setUser(save);
				adhocViewPrivilegeRepository.save(ahvp);
			}

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getName() + " User details Updated");
			transactionlogRepository.save(trlog);

			System.out.println("pocess!");
			EmailEntity ee = new EmailEntity();
			ee.setRecipient(save.getEmail());
			ee.setSubject("User Details Updated!!");
			ee.setMsgBody("Your Deatils Updated ");
			emailService.sendSimpleMail(ee);

			return new ResponseEntity<String>("User update successfully !!", HttpStatus.CREATED);

		} else {
			String password = dto.getPassword();

			List<String> role = dto.getRole_ids();
			List<String> viewprivilage = dto.getSub_module_id();
			List<String> privilage = dto.getHoc_privileges_id();

			User user = new User();
			user.setId(dto.getId());
			user.setUsername(dto.getUsername());
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setMobile(dto.getMobile());
			user.setParentUserId(dto.getHod_id());
			user.setEmployeeId(dto.getEmployeeId());
			user.setProfilePicLink(dto.getProfilePicLink());
			user.setEnabled(true);
			user.setCoId(1);
			user.setLocationId(1);
			user.setHodstatus(dto.isHodstatus());
			user.setAccountNotExpired(true);

			List<Role> roles = new ArrayList<>();
			for (String ids : role) {
				Integer id = Integer.parseInt(ids);
				Optional<Role> findById = roleRepository.findById(id);
				roles.add(findById.get());
			}

			user.setRoles(roles);
			User save = userRepository.save(user);

			for (String ids : privilage) {
				Integer id = Integer.parseInt(ids);
				Optional<Privilege> findById = privilegeRepository.findById(id);
				AdhocPrivilege ahp = new AdhocPrivilege();
				ahp.setName(findById.get().getName());
				ahp.setUser(save);
				adhocPrivilegeRepository.save(ahp);
			}

			for (String ids : viewprivilage) {
				Integer id = Integer.parseInt(ids);
				Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(id);
				AdhocViewPrivilege ahvp = new AdhocViewPrivilege();
				ahvp.setMenu(findById.get().getMenu());
				ahvp.setSubmenu(findById.get().getSubmenu());
				ahvp.setPrivilage(findById.get().getPrivilage());
				ahvp.setUser(save);
				adhocViewPrivilegeRepository.save(ahvp);
			}
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getName() + " User details Updated");
			transactionlogRepository.save(trlog);

			System.out.println("pocess!");
			EmailEntity ee = new EmailEntity();
			ee.setRecipient(save.getEmail());
			ee.setSubject("User Details Updated!!");
			ee.setMsgBody("Your new user id is -> " + save.getUsername() + "<- and password is -> " + password + "<- ");
			emailService.sendSimpleMail(ee);

			return new ResponseEntity<String>("User update successfully !!", HttpStatus.CREATED);
		}

	}

	@Override
	public List<User> getUser() {
		List<User> findAll = userRepository.findAll();
		List<User> user = new ArrayList<>();
		for (User ur : findAll) {
			if (ur.isAccountNotExpired()) {
				user.add(ur);
			}
		}
		return user;
	}

	@Override
	public UserDto getUserById(Integer id) {
		Optional<User> findbyid = userRepository.findById(id);
		User user = findbyid.get();
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setMobile(user.getMobile());
		dto.setHod_id(user.getParentUserId());
		dto.setEmployeeId(user.getEmployeeId());
		dto.setProfilePicLink(user.getProfilePicLink());
		dto.setLocationId(user.getLocationId());
		dto.setHodstatus(user.isHodstatus());

		Collection<Role> roles = user.getRoles();

		Set<AdhocPrivilege> adhocPrivilege = user.getAdhocPrivilege();

		Set<AdhocViewPrivilege> adhocViewPrivilege = user.getAdhocViewPrivilege();

		List<String> roleids = new ArrayList<>();
		List<String> modulesid = new ArrayList<>();
		List<String> submenu = new ArrayList<>();
		List<String> privilegeids = new ArrayList<>();

		for (Role role : roles) {
			roleids.add(role.getId().toString());
		}

		for (AdhocPrivilege ahp : adhocPrivilege) {

			Optional<Privilege> findByName = privilegeRepository.findByName(ahp.getName());
			if (findByName.isPresent()) {
				if (!privilegeids.contains(findByName.get().getId().toString())) {

					privilegeids.add(findByName.get().getId().toString());
				}
			}
		}

		for (AdhocViewPrivilege ahvp : adhocViewPrivilege) {

			Optional<ViewPrivilege> findBySubmenu = viewPrivilegeRepository.findBySubmenu(ahvp.getSubmenu());
			if (findBySubmenu.isPresent()) {
				if (!submenu.contains(findBySubmenu.get().getId().toString())) {

					submenu.add(findBySubmenu.get().getId().toString());
				}

				List<ViewPrivilege> findByMenu = viewPrivilegeRepository.findByMenu(ahvp.getMenu());

				for (ViewPrivilege vp : findByMenu) {
					if (vp.getMenu().equals(vp.getSubmenu())) {

						if (!modulesid.contains(vp.getId().toString())) {

							modulesid.add(vp.getId().toString());
						}
					}
				}
			}
		}

		dto.setRole_ids(roleids);
		dto.setSub_module_id(submenu);
		dto.setHoc_privileges_id(privilegeids);
		dto.setModule_id(modulesid);

		return dto;
	}

	@Override
	public ResponseEntity<String> deleteUser(Integer id) {
		Optional<User> user = userRepository.findById(id);
		user.get().setAccountNotExpired(false);
		User save = userRepository.save(user.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getName() + " User Account Suspended");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>("User Deleted", HttpStatus.ACCEPTED);
	}

	public ResponseEntity<String> userActivation(Integer id) {
		Optional<User> findById = userRepository.findById(id);
		findById.get().setEnabled(true);
		User save = userRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getName() + " User Account Activated");
		transactionlogRepository.save(trlog);

		EmailEntity ee = new EmailEntity();
		ee.setRecipient(findById.get().getEmail());
		ee.setSubject("Account Reactivation");
		ee.setMsgBody("your Account Has Been Reactivated");
		Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

		return new ResponseEntity<String>("User Activation Successful", HttpStatus.OK);
	}

	public ResponseEntity<String> userDeactivation(Integer id) {
		Optional<User> findById = userRepository.findById(id);
		findById.get().setEnabled(false);
		User save = userRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getName() + " User Account Deactivated");
		transactionlogRepository.save(trlog);

		EmailEntity ee = new EmailEntity();
		ee.setRecipient(findById.get().getEmail());
		ee.setSubject("Account Deactivation");
		ee.setMsgBody("your Account Has Been Deactivated");
		Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

		return new ResponseEntity<String>("User Deactivation Successful", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> addRole(RoleDTO dto) {
		Role role = new Role();
		role.setName(dto.getName().toUpperCase());
		role.setDecription(dto.getDescription());
		role.setStatus(true);

		Collection<Privilege> privileges = new ArrayList<Privilege>();
		List<Integer> pid = dto.getPrivilages();
		for (Integer i : pid) {
			Optional<Privilege> findById = privilegeRepository.findById(i);
			if (findById.isPresent()) {
				privileges.add(findById.get());
			}
		}
		role.setPrivileges(privileges);

		Collection<ViewPrivilege> viewPrivilege = new ArrayList<ViewPrivilege>();
		if (dto.getCustomPermissionsType().equals("full")) {
			List<ViewPrivilege> findAll = viewPrivilegeRepository.findAll();
			viewPrivilege.addAll(findAll);
		} else {
			List<Integer> vpids = dto.getSub_module_id();
			for (Integer i : vpids) {
				Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(i);
				if (findById.isPresent()) {
					viewPrivilege.add(findById.get());
				}
			}
		}

		role.setViewPrivilege(viewPrivilege);

		Role save = roleRepository.save(role);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("New " + save.getName() + " added");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>("New Role " + save.getName() + " has been created", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> editRole(RoleDTO dto) {
		Role role = new Role();
		role.setId(dto.getId());
		role.setName(dto.getName().toUpperCase());
		role.setDecription(dto.getDescription());
		role.setStatus(true);

		Collection<Privilege> privileges = new ArrayList<Privilege>();
		List<Integer> pid = dto.getPrivilages();
		for (Integer i : pid) {
			Optional<Privilege> findById = privilegeRepository.findById(i);
			if (findById.isPresent()) {
				privileges.add(findById.get());
			}
		}
		role.setPrivileges(privileges);

		Collection<ViewPrivilege> viewPrivilege = new ArrayList<ViewPrivilege>();
		if (dto.getCustomPermissionsType().equals("full")) {
			List<ViewPrivilege> findAll = viewPrivilegeRepository.findAll();
			viewPrivilege.addAll(findAll);
		} else {
			List<Integer> vpids = dto.getSub_module_id();
			for (Integer i : vpids) {
				Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(i);
				if (findById.isPresent()) {
					viewPrivilege.add(findById.get());
				}
			}
		}
		role.setViewPrivilege(viewPrivilege);

		Role save = roleRepository.save(role);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getName() + " updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>("Role " + save.getName() + " Updated", HttpStatus.CREATED);
	}

	@Override
	public RoleDTO getRoleById(Integer id) {
		Optional<Role> findById = roleRepository.findById(id);
		Role role = findById.get();
		RoleDTO rdto = new RoleDTO();
		rdto.setId(role.getId());
		rdto.setName(role.getName());
		rdto.setDescription(role.getDecription());

		Collection<Privilege> privileges = findById.get().getPrivileges();
		Collection<ViewPrivilege> viewPrivilege = findById.get().getViewPrivilege();

		List<Integer> privilages = new ArrayList<>();
		for (Privilege pv : privileges) {
			if (!privilages.contains(pv.getId())) {
				privilages.add(pv.getId());
			}

		}

		List<Integer> vprivilege = new ArrayList<>();
		List<Integer> menu = new ArrayList<>();
		for (ViewPrivilege pv : viewPrivilege) {
			if (!vprivilege.contains(pv.getId())) {
				vprivilege.add(pv.getId());
			}

			List<ViewPrivilege> findByMenu = viewPrivilegeRepository.findByMenu(pv.getMenu());
			for (ViewPrivilege pvid : findByMenu) {
				if (pvid.getMenu().equals(pvid.getSubmenu())) {
					if (!menu.contains(pvid.getId())) {
						menu.add(pvid.getId());
					}
				}
			}
		}
		List<ViewPrivilege> findAll = viewPrivilegeRepository.findAll();
		Integer custom = findAll.size();
		if (viewPrivilege.size() < custom) {
			rdto.setCustomPermissionsType("custom");
		} else {
			rdto.setCustomPermissionsType("full");
		}

		rdto.setPrivilages(privilages);
		rdto.setSub_module_id(vprivilege);
		rdto.setModule(menu);

		return rdto;
	}

	@Override
	public ResponseEntity<String> deleteRole(Integer id) {
		Optional<Role> findById = roleRepository.findById(id);
		findById.get().setStatus(false);
		roleRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getName() + " has been deleted");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getName() + " has been deleted", HttpStatus.OK);
	}

	@Override
	public List<OnlyMenuDTO> getMenu() {
		List<ViewPrivilege> findAll = viewPrivilegeRepository.findAll();
		List<OnlyMenuDTO> pvdto = new ArrayList<>();
		for (ViewPrivilege pv : findAll) {
			if (!pv.getMenu().equalsIgnoreCase(pv.getSubmenu())) {
				OnlyMenuDTO dto = new OnlyMenuDTO();
				dto.setId(pv.getId());
				dto.setName(pv.getSubmenu());
				pvdto.add(dto);
			}

		}
		return pvdto;
	}

	@Override
	public List<OnlyMenuDTO> getMenuForEmail() {
		List<ViewPrivilege> findAll = viewPrivilegeRepository
				.findAll()
				.stream().filter(t -> t.getIsEmail().equals(true)).collect(Collectors.toList());
		List<OnlyMenuDTO> pvdto = new ArrayList<>();
		for (ViewPrivilege pv : findAll) {
			if (!pv.getMenu().equalsIgnoreCase(pv.getSubmenu())) {
				OnlyMenuDTO dto = new OnlyMenuDTO();
				dto.setId(pv.getId());
				dto.setName(pv.getSubmenu());
				pvdto.add(dto);
			}

		}
		return pvdto;
	}

	@Override
	public List<OnlyMenuDTO> getMenuForApproval() {
		List<ViewPrivilege> findAll = viewPrivilegeRepository.findAll()
				.stream().filter(t -> t.getIsApproval().equals(true)).collect(Collectors.toList());
		List<OnlyMenuDTO> pvdto = new ArrayList<>();
		for (ViewPrivilege pv : findAll) {
			if (!pv.getMenu().equalsIgnoreCase(pv.getSubmenu())) {
				OnlyMenuDTO dto = new OnlyMenuDTO();
				dto.setId(pv.getId());
				dto.setName(pv.getSubmenu());
				pvdto.add(dto);
			}

		}
		return pvdto;
	}

	@Override
	public List<OnlyMenuDTO> getMenuForIdentification() {
		List<ViewPrivilege> findAll = viewPrivilegeRepository.findAll()
				.stream().filter(t -> t.getIsIdentify().equals(true)).collect(Collectors.toList());
		List<OnlyMenuDTO> pvdto = new ArrayList<>();
		for (ViewPrivilege pv : findAll) {
			if (!pv.getMenu().equalsIgnoreCase(pv.getSubmenu())) {
				OnlyMenuDTO dto = new OnlyMenuDTO();
				dto.setId(pv.getId());
				dto.setName(pv.getSubmenu());
				pvdto.add(dto);
			}

		}
		return pvdto;
	}

	@Override
	public Boolean CheckUsernames(String username) {

		Boolean vendorname = vendorRegistreationRepository.existsByUsername(username);
		if (vendorname) {
			return true;
		} else {
			Boolean existsByUsername = userRepository.existsByUsername(username);
			if (existsByUsername) {
				return true;
			} else {
				return false;
			}
		}

	}

	@Override
	public Boolean CheckUserEmail(String email) {
		Boolean vendorname = vendorRegistreationRepository.existsByEmail1(email);
		if (vendorname) {
			return true;
		} else {
			Boolean existsByUsername = userRepository.existsByEmail(email);
			if (existsByUsername) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public ResponseEntity<String> forgetPass(String username, String email) {
		Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
		if (user.isEmpty()) {
			return new ResponseEntity<String>("User Not Found", HttpStatus.BAD_GATEWAY);
		} else {
			String password = randomPasswordGenerator.generatePassayPassword();
			String encodedpass = passwordEncoder.encode(password);
			user.get().setPassword(encodedpass);
			userRepository.save(user.get());

			EmailSetup mail = emailSetupRepository.findById(1).get();
			String body = mail.getBody1();
			String mess = String.format(body, password);
			try {
				MimeMessage message = emailSender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom("springboottest44@gmail.com");
				helper.setTo(user.get().getEmail());
				helper.setSubject(mail.getSubjectName());
				helper.setText(mess,true);
				emailSender.send(message);

			} catch (Exception e) {
				System.out.println("failed");
			}
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(user.get().getUsername());
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(user.get().getName() + " Requested for Password Change");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>("Password Updated Successfully", HttpStatus.OK);
		}
	}

}
