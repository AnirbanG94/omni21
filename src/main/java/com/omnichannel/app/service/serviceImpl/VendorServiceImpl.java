package com.omnichannel.app.service.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.omnichannel.app.config.EmailConfig;
import com.omnichannel.app.model.DTO.VendorManufacturerDTO;
import com.omnichannel.app.model.DTO.POBILL.POProduct;
import com.omnichannel.app.model.admin.ApprovalSetup;
import com.omnichannel.app.model.admin.IdentificationSetup;
import com.omnichannel.app.model.admin.Transactionlog;
import com.omnichannel.app.model.extra.EmailEntity;
import com.omnichannel.app.model.master.Country;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.master.Paymentmode;
import com.omnichannel.app.model.master.State;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.model.product.Brand;
import com.omnichannel.app.model.product.Item;
import com.omnichannel.app.model.product.Manufacturer;
import com.omnichannel.app.model.product.UOM;
import com.omnichannel.app.model.usermanagement.Role;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.model.vendor.ShippingNotification;
import com.omnichannel.app.model.vendor.VendorDocDetails;
import com.omnichannel.app.model.vendor.VendorInvitation;
import com.omnichannel.app.model.vendor.VendorProductDetails;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.model.vendor.VendorRegistreation;
import com.omnichannel.app.repository.admin.ApprovalSetupRepository;
import com.omnichannel.app.repository.admin.IdentificationSetupRepository;
import com.omnichannel.app.repository.admin.TransactionlogRepository;
import com.omnichannel.app.repository.master.CountryRepository;
import com.omnichannel.app.repository.master.LocationRepository;
import com.omnichannel.app.repository.master.OutletRepository;
import com.omnichannel.app.repository.master.PaymentmodeRepository;
import com.omnichannel.app.repository.master.StateRepository;
import com.omnichannel.app.repository.product.ArticlesRepository;
import com.omnichannel.app.repository.product.BrandRepository;
import com.omnichannel.app.repository.product.ItemRepository;
import com.omnichannel.app.repository.product.ManufacturerRepository;
import com.omnichannel.app.repository.product.UOMRepository;
import com.omnichannel.app.repository.usermanagement.RoleRepository;
import com.omnichannel.app.repository.usermanagement.UserRepository;
import com.omnichannel.app.repository.usermanagement.ViewPrivilegeRepository;
import com.omnichannel.app.repository.vendor.ShippingNotificationRepository;
import com.omnichannel.app.repository.vendor.VendorInvitationRepository;
import com.omnichannel.app.repository.vendor.VendorProductDetailsRepository;
import com.omnichannel.app.repository.vendor.VendorProductHeaderRepository;
import com.omnichannel.app.repository.vendor.VendorRegistreationRepository;
import com.omnichannel.app.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

	@Value("${access.url}")
	private String ACCESS_URL;

	@Autowired
	CountryRepository countryRepository;
	@Autowired
	StateRepository stateRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	PaymentmodeRepository paymentmodeRepository;
	@Autowired
	ViewPrivilegeRepository viewPrivilegeRepository;
	@Autowired
	ApprovalSetupRepository approvalSetupRepository;

	@Autowired
	ArticlesRepository articlesRepository;

	@Autowired
	VendorInvitationRepository vendorInvitationRepository;

	@Autowired
	VendorRegistreationRepository vendorRegistreationRepository;

	@Autowired
	VendorProductHeaderRepository vendorProductHeaderRepository;

	@Autowired
	VendorProductDetailsRepository vendorProductDetailsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EmailConfig emailService;

	@Autowired
	TransactionlogRepository transactionlogRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	ManufacturerRepository manufacturerRepository;

	@Autowired
	UOMRepository uOMRepository;

	@Autowired
	ShippingNotificationRepository shippingNotificationRepository;

	@Autowired
	OutletRepository outletRepository;

	@Autowired
	IdentificationSetupRepository identificationSetupRepository;

	// ============================= Vendor Invitation Start==================

	@Override
	public List<VendorInvitation> getVendorInvitation() {
		List<VendorInvitation> vendor = vendorInvitationRepository.findAll();
		for (VendorInvitation vendorInvitation : vendor) {
			Optional<User> findByUsername = userRepository.findByUsername(vendorInvitation.getUsername());
			vendorInvitation.setName(findByUsername.get().getName());
		}
		return vendor;
	}

	@Override
	public VendorInvitation getVendorInvitationById(Integer id) {
		Optional<VendorInvitation> findById = vendorInvitationRepository.findById(id);
		Optional<User> findByUsername = userRepository.findByUsername(findById.get().getUsername());
		findById.get().setName(findByUsername.get().getName());
		return findById.get();
	}

	@Override
	public ResponseEntity<String> postVendorInvitation(@Valid VendorInvitation vendorInvitation) {
		String names = SecurityContextHolder.getContext().getAuthentication().getName();
		vendorInvitation.setDate(LocalDate.now());
		vendorInvitation.setTime(LocalTime.now());
		vendorInvitation.setUsername(names);

		Optional<User> findByUsername = userRepository.findByUsername(names);

		String linkString = ACCESS_URL + "/vendor-registration-onboarding" + "?invitee=" + findByUsername.get().getId();
		System.out.println("Sending Email....!");
		EmailEntity ee = new EmailEntity();
		ee.setRecipient(vendorInvitation.getEmail());
		ee.setSubject("Vendor Registration");
		String body = "Dear " + "Vendor"
				+ ",<br/><b>Greetings, Please fill the form for vendor Registration from the</b><br/>link <a href="
				+ linkString + "></a>";
		ee.setMsgBody(body);
		Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

		@Valid
		VendorInvitation save = vendorInvitationRepository.save(vendorInvitation);

		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(names);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Sent Email for vendor registration in " + save.getEmail());
		transactionlogRepository.save(trlog);
		return new ResponseEntity<String>("Email Sent successfully for vendor registration!!", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> updateVendorInvitation(@Valid VendorInvitation vendorInvitation) {
		String names = SecurityContextHolder.getContext().getAuthentication().getName();
		vendorInvitation.setDate(LocalDate.now());
		vendorInvitation.setTime(LocalTime.now());
		vendorInvitation.setUsername(names);

		Optional<User> findByUsername = userRepository.findByUsername(names);

		String linkString = ACCESS_URL + "/vendor-registration-onboarding" + "?invitee=" + findByUsername.get().getId();

		System.out.println("Sending Email....!");
		EmailEntity ee = new EmailEntity();
		ee.setRecipient(vendorInvitation.getEmail());
		ee.setSubject("Vendor Registration");
		ee.setMsgBody("Dear " + "Vendor"
				+ ",<br/><b>Greetings, Please fill the form for vendor Registration from the</b><br/>link <a href="
				+ linkString + "></a>");
		Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

		@Valid
		VendorInvitation save = vendorInvitationRepository.save(vendorInvitation);

		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(names);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Sent Email for vendor registration in " + save.getEmail());
		transactionlogRepository.save(trlog);
		return new ResponseEntity<String>("Email Sent successfully for vendor registration!!", HttpStatus.CREATED);
	}

	// ============================= Vendor Invitation End==================

	// ============================= Vendor Registration Start==================

	@Override
	public List<VendorRegistreation> getVendorRegistration() {

		List<VendorRegistreation> collect = new ArrayList<>();

		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean role = false;
		for (GrantedAuthority grantedAuthority : auth) {
			if (grantedAuthority.toString().equals("ROLE_VENDOR")) {
				role = true;
			}
		}
		if (role) {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<User> findByUsername = userRepository.findByUsername(name);
			Optional<VendorRegistreation> vendor = vendorRegistreationRepository
					.findById(findByUsername.get().getVendorId());

			Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
			Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
			Optional<State> state = stateRepository.findById(vendor.get().getStateid());
			Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.get().getPaym_mode_id());
			vendor.get().setLocationname(loction.get().getName());
			vendor.get().setStatename(state.get().getState());
			vendor.get().setCountryname(country.get().getName());
			vendor.get().setPaymModeName(payment.get().getPaymentmode());

			collect.add(vendor.get());

			return collect;

		} else {
			Boolean admin = false;
			for (GrantedAuthority grantedAuthority : auth) {
				if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
					admin = true;
				}
			}

			if (admin) {
				collect = vendorRegistreationRepository.findAll().stream().collect(Collectors.toList());
				for (VendorRegistreation vendor : collect) {
					Optional<Location> loction = locationRepository.findById(vendor.getLocationid());
					Optional<Country> country = countryRepository.findById(vendor.getCountrycode());
					Optional<State> state = stateRepository.findById(vendor.getStateid());
					Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.getPaym_mode_id());
					vendor.setLocationname(loction.get().getName());
					vendor.setStatename(state.get().getState());
					vendor.setCountryname(country.get().getName());
					vendor.setPaymModeName(payment.get().getPaymentmode());
				}
				return collect;

			} else {

				String name = SecurityContextHolder.getContext().getAuthentication().getName();
				System.out.println("++++++++++++++++++++++++++++++++++++++++++" + name);
				User user = userRepository.findByUsername(name).get();

				System.out.println("++++++++++++++++++++++++++++++++++++++++++" + user.getId());
				List<VendorRegistreation> listVendor = vendorRegistreationRepository.findByInvitee(user.getId());

				if (listVendor.isEmpty()) {

					collect = vendorRegistreationRepository.findAll().stream().collect(Collectors.toList());
					for (VendorRegistreation vendor : collect) {
						Optional<Location> loction = locationRepository.findById(vendor.getLocationid());
						Optional<Country> country = countryRepository.findById(vendor.getCountrycode());
						Optional<State> state = stateRepository.findById(vendor.getStateid());
						Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.getPaym_mode_id());
						vendor.setLocationname(loction.get().getName());
						vendor.setStatename(state.get().getState());
						vendor.setCountryname(country.get().getName());
						vendor.setPaymModeName(payment.get().getPaymentmode());
					}
					return collect;

				} else {
					collect = listVendor;
					for (VendorRegistreation vendor : collect) {
						Optional<Location> loction = locationRepository.findById(vendor.getLocationid());
						Optional<Country> country = countryRepository.findById(vendor.getCountrycode());
						Optional<State> state = stateRepository.findById(vendor.getStateid());
						Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.getPaym_mode_id());
						vendor.setLocationname(loction.get().getName());
						vendor.setStatename(state.get().getState());
						vendor.setCountryname(country.get().getName());
						vendor.setPaymModeName(payment.get().getPaymentmode());
					}
					return collect;
				}

			}

		}
	}

	@Override
	public List<VendorRegistreation> getApprovedVendors() {

		List<VendorRegistreation> collect = new ArrayList<>();

		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean role = false;
		for (GrantedAuthority grantedAuthority : auth) {
			System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_VENDOR")) {
				System.out.println("active vendor");
				role = true;
			}
		}

		if (role) {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<User> findByUsername = userRepository.findByUsername(name);
			Optional<VendorRegistreation> vendor = vendorRegistreationRepository
					.findById(findByUsername.get().getVendorId()).filter(t -> t.getStatus().equals("A"));

			if (vendor.isPresent()) {
				Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
				Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
				Optional<State> state = stateRepository.findById(vendor.get().getStateid());
				Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.get().getPaym_mode_id());
				vendor.get().setLocationname(loction.get().getName());
				vendor.get().setStatename(state.get().getState());
				vendor.get().setCountryname(country.get().getName());
				vendor.get().setPaymModeName(payment.get().getPaymentmode());
				collect.add(vendor.get());
			}
			return collect;

		} else {
			collect = vendorRegistreationRepository.findAll().stream().filter(t -> t.getStatus().equals("A"))
					.collect(Collectors.toList());
			for (VendorRegistreation vendor : collect) {
				Optional<Location> loction = locationRepository.findById(vendor.getLocationid());
				Optional<Country> country = countryRepository.findById(vendor.getCountrycode());
				Optional<State> state = stateRepository.findById(vendor.getStateid());
				Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.getPaym_mode_id());
				vendor.setLocationname(loction.get().getName());
				vendor.setStatename(state.get().getState());
				vendor.setCountryname(country.get().getName());
				vendor.setPaymModeName(payment.get().getPaymentmode());
			}
			return collect;
		}
	}

	@Override
	public VendorRegistreation getVendorForVendor() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> findByUsername = userRepository.findByUsername(name);
		Optional<VendorRegistreation> vendor = vendorRegistreationRepository
				.findById(findByUsername.get().getVendorId());
		Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
		Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
		Optional<State> state = stateRepository.findById(vendor.get().getStateid());
		Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.get().getPaym_mode_id());
		vendor.get().setLocationname(loction.get().getName());
		vendor.get().setStatename(state.get().getState());
		vendor.get().setCountryname(country.get().getName());
		vendor.get().setPaymModeName(payment.get().getPaymentmode());
		return vendor.get();
	}

	@Override
	public ResponseEntity<String> postVendorRegistration(@Valid VendorRegistreation vendorRegistreation) {
		List<VendorDocDetails> details = vendorRegistreation.getDetails();
		vendorRegistreation.setDetails(null);
		String names = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> findByUsername = userRepository.findByUsername(names);
		vendorRegistreation.setInvitee(findByUsername.get().getId());
		vendorRegistreation.setStatus("O");
		@Valid
		VendorRegistreation save = vendorRegistreationRepository.save(vendorRegistreation);
		for (VendorDocDetails vendorDocDetails : details) {
			vendorDocDetails.setHeader(save);
		}
		save.setDetails(details);
		vendorRegistreationRepository.save(save);

		return new ResponseEntity<String>(save.getName() + " vendor created Successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> postVendorRegistrationOnboarding(VendorRegistreation vendorRegistreation) {

		vendorRegistreation.setStatus("R");
		vendorRegistreation.setTrade(false);
		Optional<Location> location = locationRepository.findByName(vendorRegistreation.getLocationname());
		VendorRegistreation save = new VendorRegistreation();
		if (location.isEmpty()) {
			Location loc = new Location();
			loc.setName(vendorRegistreation.getLocationname().toUpperCase());
			loc.setStateId(vendorRegistreation.getStateid());
			loc.setCountryId(vendorRegistreation.getCountrycode());
			loc.setPincode(vendorRegistreation.getPin());
			loc.setStatus(true);
			Location locsave = locationRepository.save(loc);

			vendorRegistreation.setLocationid(locsave.getId());
			save = vendorRegistreationRepository.save(vendorRegistreation);
		} else {
			vendorRegistreation.setLocationid(location.get().getId());
			save = vendorRegistreationRepository.save(vendorRegistreation);
		}

		User venuser = new User();
		venuser.setUsername(save.getUsername());
		venuser.setPassword(passwordEncoder.encode(save.getPassword()));
		venuser.setName(save.getName());
		venuser.setEmail(save.getEmail1());
		venuser.setMobile(save.getMobile());
		venuser.setEnabled(true);
		venuser.setCoId(1);
		venuser.setLocationId(save.getLocationid());
		venuser.setHodstatus(false);
		venuser.setAccountNotExpired(true);
		venuser.setVendorId(save.getId());
		venuser.setProfilePicLink("profilPic.png");
		List<Role> roles = roleRepository.findByName("ROLE_VENDOR");
		venuser.setRoles(roles);
		User saveUser = userRepository.save(venuser);

		System.out.println("pocessed!");
		EmailEntity ee = new EmailEntity();
		ee.setRecipient(saveUser.getEmail());
		ee.setSubject("New Vendor Account Creat For Omni Channel "
				+ "Retail. Please Upload the Documents And process for approval");
		ee.setMsgBody(
				"Your user id is -> " + saveUser.getUsername() + "<- and password is -> " + save.getPassword() + "<- ");
		Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

		return new ResponseEntity<String>(save.getName() + " vendor  registration Successfully", HttpStatus.CREATED);
	}

	@Override
	public VendorRegistreation getVendorRegistrationById(Integer id) {
		Optional<VendorRegistreation> vendor = vendorRegistreationRepository.findById(id);
		Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
		Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
		Optional<State> state = stateRepository.findById(vendor.get().getStateid());
		Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.get().getPaym_mode_id());
		vendor.get().setLocationname(loction.get().getName());
		vendor.get().setStatename(state.get().getState());
		vendor.get().setCountryname(country.get().getName());
		vendor.get().setPaymModeName(payment.get().getPaymentmode());

		return vendor.get();
	}

	@Override
	public ResponseEntity<String> updateVendorRegistration(@Valid VendorRegistreation vendorRegistreation) {
		vendorRegistreation.setStatus("O");
		for (VendorDocDetails Doc : vendorRegistreation.getDetails()) {
			Doc.setHeader(vendorRegistreation);
		}
		VendorRegistreation save = vendorRegistreationRepository.save(vendorRegistreation);
		Boolean existsByUsername = userRepository.existsByUsername(save.getUsername());
		if (existsByUsername) {
			Optional<User> findByUsername = userRepository.findByUsername(save.getUsername());
			findByUsername.get().setEnabled(false);
			userRepository.save(findByUsername.get());
		}
		return new ResponseEntity<String>(save.getName() + " Vendor Deatils update Successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteVendorRegistration(Integer id) {
		Optional<VendorRegistreation> vendor = vendorRegistreationRepository.findById(id);
		vendor.get().setStatus("D");
		vendorRegistreationRepository.save(vendor.get());

		return new ResponseEntity<String>("Vendor Registration cancelled!!", HttpStatus.CREATED);
	}

	// ============================= Vendor Registration End==================

	// ============================= Vendor Approval Start==================

	@Override
	public List<VendorRegistreation> getVendorsForApprove() {
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = false;
		for (GrantedAuthority grantedAuthority : auth) {
			System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
				System.out.println("active admin");
				Roleadmin = true;
			}
		}
		if (Roleadmin) {
			List<VendorRegistreation> collect = vendorRegistreationRepository.findAll().stream()
					.filter(t -> !t.getStatus().equals("R") && !t.getStatus().equals("A") && !t.getStatus().equals("D"))
					.collect(Collectors.toList());
			for (VendorRegistreation vendor : collect) {
				Optional<Location> loction = locationRepository.findById(vendor.getLocationid());
				Optional<Country> country = countryRepository.findById(vendor.getCountrycode());
				Optional<State> state = stateRepository.findById(vendor.getStateid());
				Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.getPaym_mode_id());
				vendor.setLocationname(loction.get().getName());
				vendor.setStatename(state.get().getState());
				vendor.setCountryname(country.get().getName());
				vendor.setPaymModeName(payment.get().getPaymentmode());
			}
			return collect;

		} else {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userRepository.findByUsername(name).get();
			List<VendorRegistreation> vendorlist = vendorRegistreationRepository.findAll().stream()
					.filter(t -> t.getStatus().equals("O") && t.getInvitee().equals(user.getId()))
					.collect(Collectors.toList());

			if (vendorlist.isEmpty()) {

				Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Vendor Approval");
				List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
				for (ApprovalSetup approvalSetup : findByManuid) {
					String username = userRepository.findById(approvalSetup.getUserid()).get().getUsername();
					approvalSetup.setUsername(username);
				}
				Integer level = 0;
				for (ApprovalSetup approvalSetup : findByManuid) {
					if (approvalSetup.getUsername().equals(name)) {
						level = approvalSetup.getApproveorder();
					}
				}
				final Integer apvlvl = level;
				if (level != 0) {
					List<VendorRegistreation> collect = vendorRegistreationRepository.findAll().stream()
							.filter(t -> t.getStatus().equals(apvlvl)).collect(Collectors.toList());
					for (VendorRegistreation vendor : collect) {
						Optional<Location> loction = locationRepository.findById(vendor.getLocationid());
						Optional<Country> country = countryRepository.findById(vendor.getCountrycode());
						Optional<State> state = stateRepository.findById(vendor.getStateid());
						Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.getPaym_mode_id());
						vendor.setLocationname(loction.get().getName());
						vendor.setStatename(state.get().getState());
						vendor.setCountryname(country.get().getName());
						vendor.setPaymModeName(payment.get().getPaymentmode());
					}
					return collect;
				} else {
					return null;
				}

			} else {
				for (VendorRegistreation vendor : vendorlist) {
					Optional<Location> loction = locationRepository.findById(vendor.getLocationid());
					Optional<Country> country = countryRepository.findById(vendor.getCountrycode());
					Optional<State> state = stateRepository.findById(vendor.getStateid());
					Optional<Paymentmode> payment = paymentmodeRepository.findById(vendor.getPaym_mode_id());
					vendor.setLocationname(loction.get().getName());
					vendor.setStatename(state.get().getState());
					vendor.setCountryname(country.get().getName());
					vendor.setPaymModeName(payment.get().getPaymentmode());

				}
				return vendorlist;
			}
		}

	}

	@Override
	public ResponseEntity<String> updateVendorApproval(Integer id) {

		VendorRegistreation vendor = vendorRegistreationRepository.findById(id).get();
		IdentificationSetup identificationSetup = identificationSetupRepository
				.findByMenuAndTradingVendor(37, vendor.getTrade()).get();
		String Prefix = identificationSetup.getPrefix();
		String Sufix = identificationSetup.getSufix();
		String slno = identificationSetup.getSlno();
		String year = identificationSetup.getYear();
		String lengthString = identificationSetup.getNoOflength();
		String sepet;
		if (identificationSetup.getSeparatedBy() != null) {
			sepet = identificationSetup.getSeparatedBy();
		} else {
			sepet = "";
		}

		Integer val = Integer.parseInt(slno);
		System.out.println(val);
		String formattedStr = String.format("%0" + lengthString + "d", val);
		String filString;

		if (identificationSetup.getIsYearUse()) {
			if (Prefix != null && Sufix != null) {
				filString = Prefix + sepet + year + sepet + formattedStr + sepet + Sufix;
			} else if (Prefix == null && Sufix != null) {
				filString = year + sepet + formattedStr + sepet + Sufix;
			} else if (Prefix != null && Sufix == null) {
				filString = Prefix + sepet + year + sepet + formattedStr;
			} else {
				filString = year + sepet + formattedStr;
			}

		} else {
			if (Prefix != null && Sufix != null) {
				filString = Prefix + sepet + formattedStr + sepet + Sufix;
			} else if (Prefix == null && Sufix != null) {
				filString = formattedStr + sepet + Sufix;
			} else if (Prefix != null && Sufix == null) {
				filString = Prefix + sepet + formattedStr;
			} else {
				filString = formattedStr;
			}
		}

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(name).get();

		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = false;
		for (GrantedAuthority grantedAuthority : auth) {
			// System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
				Roleadmin = true;
			}
		}
		if (Roleadmin) {

			if (vendor.getVendor_cd() != null) {
				System.out.println("ALREADY ASSIGN");
			} else {
				vendor.setVendor_cd(filString);
			}

			vendor.setStatus("A");
			VendorRegistreation ven = vendorRegistreationRepository.save(vendor);
			Integer nextVal = val + 1;
			identificationSetup.setSlno(nextVal.toString());
			identificationSetupRepository.save(identificationSetup);

			Boolean existsByUsername = userRepository.existsByUsername(ven.getUsername());
			if (existsByUsername) {
				Optional<User> findByUsername = userRepository.findByUsername(ven.getUsername());
				findByUsername.get().setEnabled(true);
				userRepository.save(findByUsername.get());
				return new ResponseEntity<String>("Vendor Final Approved And User Created", HttpStatus.CREATED);
			} else {

				User venuser = new User();
				venuser.setUsername(ven.getUsername());
				venuser.setPassword(passwordEncoder.encode(ven.getPassword()));
				venuser.setName(ven.getName());
				venuser.setEmail(ven.getEmail1());
				venuser.setMobile(ven.getMobile());
				venuser.setEnabled(true);
				venuser.setCoId(1);
				venuser.setLocationId(ven.getLocationid());
				venuser.setHodstatus(false);
				venuser.setAccountNotExpired(true);
				venuser.setVendorId(ven.getId());
				venuser.setProfilePicLink("profilPic.png");
				List<Role> roles = roleRepository.findByName("ROLE_VENDOR");
				venuser.setRoles(roles);
				User save = userRepository.save(venuser);
				Transactionlog trlog = new Transactionlog();
				trlog.setUsername(name);
				trlog.setTimestamp(LocalDateTime.now());
				trlog.setActivity("Vendor Final Approved and " + save.getName() + " User Created");
				transactionlogRepository.save(trlog);

				System.out.println("pocessed!");
				EmailEntity ee = new EmailEntity();
				ee.setRecipient(save.getEmail());
				ee.setSubject("New Vendor Account Creat For Omni Channel Retail");
				ee.setMsgBody("Your user id is -> " + save.getUsername() + "<- and password is -> " + ven.getPassword()
						+ "<- ");
				Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

				return new ResponseEntity<String>("New user id is ->"
						+ save.getUsername() + "<- and password is -> " + ven.getPassword() + "<-  ",
						HttpStatus.CREATED);
			}
		} else {
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Vendor Approval");
			List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
			Integer level = findByManuid.size();

			if (vendor.getStatus().equals("O") && vendor.getInvitee().equals(user.getId())) {
				if (findByManuid.isEmpty()) {
					if (vendor.getVendor_cd() != null) {
						System.out.println("ALREADY ASSIGN");
					} else {
						vendor.setVendor_cd(filString);
					}

					vendor.setStatus("A");
					VendorRegistreation ven = vendorRegistreationRepository.save(vendor);
					Integer nextVal = val + 1;
					identificationSetup.setSlno(nextVal.toString());
					identificationSetupRepository.save(identificationSetup);

					Boolean existsByUsername = userRepository.existsByUsername(ven.getUsername());
					if (existsByUsername) {
						Optional<User> findByUsername = userRepository.findByUsername(ven.getUsername());
						findByUsername.get().setEnabled(true);
						userRepository.save(findByUsername.get());
						return new ResponseEntity<String>("Vendor Final Approved And User Created", HttpStatus.CREATED);
					} else {

						User venuser = new User();
						venuser.setUsername(ven.getUsername());
						venuser.setPassword(passwordEncoder.encode(ven.getPassword()));
						venuser.setName(ven.getName());
						venuser.setEmail(ven.getEmail1());
						venuser.setMobile(ven.getMobile());
						venuser.setEnabled(true);
						venuser.setCoId(1);
						venuser.setLocationId(ven.getLocationid());
						venuser.setHodstatus(false);
						venuser.setAccountNotExpired(true);
						venuser.setVendorId(ven.getId());
						List<Role> roles = roleRepository.findByName("ROLE_VENDOR");
						venuser.setRoles(roles);
						User save = userRepository.save(venuser);

						Transactionlog trlog = new Transactionlog();
						trlog.setUsername(name);
						trlog.setTimestamp(LocalDateTime.now());
						trlog.setActivity("Vendor Final Approved and " + save.getName() + " User Created");
						transactionlogRepository.save(trlog);

						System.out.println("pocessed!");
						EmailEntity ee = new EmailEntity();
						ee.setRecipient(save.getEmail());
						ee.setSubject("New Vendor Account Creat For Omni Channel Retail");
						ee.setMsgBody("Your user id is -> " + save.getUsername() + "<- and password is -> "
								+ ven.getPassword()
								+ "<- ");
						Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

						return new ResponseEntity<String>("New user id is ->"
								+ save.getUsername() + "<- and password is -> " + ven.getPassword() + "<-  ",
								HttpStatus.CREATED);
					}

				} else {
					vendor.setStatus("1");
					VendorRegistreation ven = vendorRegistreationRepository.save(vendor);
					return new ResponseEntity<String>("Vendor Approved for next approval!!", HttpStatus.CREATED);
				}

			} else {

				if (level.toString().equals(vendor.getStatus())) {

					if (vendor.getVendor_cd() != null) {
						System.out.println("ALREADY ASSIGN");
					} else {
						vendor.setVendor_cd(filString);
					}

					vendor.setStatus("A");
					VendorRegistreation ven = vendorRegistreationRepository.save(vendor);
					Integer nextVal = val + 1;
					identificationSetup.setSlno(nextVal.toString());
					identificationSetupRepository.save(identificationSetup);

					Boolean existsByUsername = userRepository.existsByUsername(ven.getUsername());
					if (existsByUsername) {
						Optional<User> findByUsername = userRepository.findByUsername(ven.getUsername());
						findByUsername.get().setEnabled(true);
						userRepository.save(findByUsername.get());
						return new ResponseEntity<String>("Vendor Final Approved And User Created", HttpStatus.CREATED);
					} else {

						User venuser = new User();
						venuser.setUsername(ven.getUsername());
						venuser.setPassword(passwordEncoder.encode(ven.getPassword()));
						venuser.setName(ven.getName());
						venuser.setEmail(ven.getEmail1());
						venuser.setMobile(ven.getMobile());
						venuser.setEnabled(true);
						venuser.setCoId(1);
						venuser.setLocationId(ven.getLocationid());
						venuser.setHodstatus(false);
						venuser.setAccountNotExpired(true);
						venuser.setVendorId(ven.getId());
						List<Role> roles = roleRepository.findByName("ROLE_VENDOR");
						venuser.setRoles(roles);
						User save = userRepository.save(venuser);

						Transactionlog trlog = new Transactionlog();
						trlog.setUsername(name);
						trlog.setTimestamp(LocalDateTime.now());
						trlog.setActivity("Vendor Final Approved and " + save.getName() + " User Created");
						transactionlogRepository.save(trlog);

						System.out.println("pocessed!");
						EmailEntity ee = new EmailEntity();
						ee.setRecipient(save.getEmail());
						ee.setSubject("New Vendor Account Creat For Omni Channel Retail");
						ee.setMsgBody("Your user id is -> " + save.getUsername() + "<- and password is -> "
								+ ven.getPassword()
								+ "<- ");
						Boolean sendSimpleMail = emailService.sendSimpleMail(ee);

						return new ResponseEntity<String>("New user id is ->"
								+ save.getUsername() + "<- and password is -> " + ven.getPassword() + "<-  ",
								HttpStatus.CREATED);
					}
				} else {
					Integer parseInt = Integer.parseInt(vendor.getStatus());
					Integer aInteger = parseInt + 1;
					vendor.setStatus(aInteger.toString());
					VendorRegistreation ven = vendorRegistreationRepository.save(vendor);
					return new ResponseEntity<String>("Vendor Approved for next approval!!", HttpStatus.CREATED);
				}
			}
		}
	}

	@Override
	public ResponseEntity<String> updateVendorDisapprove(Integer id, String reason) {
		Optional<VendorRegistreation> vendor = vendorRegistreationRepository.findById(id);
		vendor.get().setDisapproveReason(reason);
		vendor.get().setStatus("D");
		vendorRegistreationRepository.save(vendor.get());
		return new ResponseEntity<String>("Vendor Disapproved!!", HttpStatus.CREATED);
	}

	// ============================= Vendor Approval End==================

	// ============================= Vendor Product Start==================

	@Override
	public List<VendorRegistreation> getVendorForProduct() {
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = true;
		for (GrantedAuthority grantedAuthority : auth) {
			System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_VENDOR")) {
				System.out.println("active admin");
				Roleadmin = false;
			}
		}

		if (Roleadmin) {
			List<VendorRegistreation> collect = vendorRegistreationRepository.findAll().stream()
					.filter(t -> t.getStatus().equals("A")).collect(Collectors.toList());
			return collect;
		} else {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<User> findByUsername = userRepository.findByUsername(name);
			Optional<VendorRegistreation> findById = vendorRegistreationRepository
					.findById(findByUsername.get().getVendorId());

			List<VendorRegistreation> collect = new ArrayList<>();

			collect.add(findById.get());

			return collect;
		}

	}

	@Override
	public VendorProductHeader getVendorProductId(Integer id) {
		Optional<VendorProductHeader> vendor = vendorProductHeaderRepository.findById(id);
		Optional<VendorRegistreation> vendorname = vendorRegistreationRepository.findById(vendor.get().getVendorId());
		vendor.get().setVendorName(vendorname.get().getName());
		Optional<Item> item = itemRepository.findById(vendor.get().getItemId());
		vendor.get().setPro_division(item.get().getPro_division());
		vendor.get().setPro_family(item.get().getPro_family());
		vendor.get().setPro_class(item.get().getPro_class());
		vendor.get().setPro_sub_class(item.get().getPro_sub_class());
		Optional<Brand> brand = brandRepository.findById(vendor.get().getBrandId());
		vendor.get().setBrandName(brand.get().getBrandName());
		Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.get().getManufacId());
		vendor.get().setManufName(manufac.get().getManufacname());
		Optional<UOM> uom = uOMRepository.findById(vendor.get().getUomId());
		vendor.get().setUomName(uom.get().getName());
		return vendor.get();
	}

	@Override
	public List<VendorProductHeader> getVendorProduct() {
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = true;
		for (GrantedAuthority grantedAuthority : auth) {
			System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_VENDOR")) {
				System.out.println("active admin");
				Roleadmin = false;
			}
		}

		if (Roleadmin) {
			List<VendorProductHeader> findAll = vendorProductHeaderRepository.findAll().stream()
					.filter(t -> !t.getStatus().equals("D")).collect(Collectors.toList());
			for (VendorProductHeader vendor : findAll) {

				Optional<VendorRegistreation> vendorname = vendorRegistreationRepository.findById(vendor.getVendorId());
				vendor.setVendorName(vendorname.get().getName());
				Optional<Item> item = itemRepository.findById(vendor.getItemId());
				vendor.setPro_division(item.get().getPro_division());
				vendor.setPro_family(item.get().getPro_family());
				vendor.setPro_class(item.get().getPro_class());
				vendor.setPro_sub_class(item.get().getPro_sub_class());
				Optional<Brand> brand = brandRepository.findById(vendor.getBrandId());
				vendor.setBrandName(brand.get().getBrandName());
				Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.getManufacId());
				vendor.setManufName(manufac.get().getManufacname());
				Optional<UOM> uom = uOMRepository.findById(vendor.getUomId());
				vendor.setUomName(uom.get().getName());

			}
			return findAll;
		} else {
			String names = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<User> findByUsername = userRepository.findByUsername(names);
			if (findByUsername.get().getVendorId().equals(0)) {
				return null;
			} else {
				List<VendorProductHeader> findByVendorId = vendorProductHeaderRepository
						.findByVendorId(findByUsername.get().getVendorId()).stream()
						.filter(t -> !t.getStatus().equals("D")).collect(Collectors.toList());
				for (VendorProductHeader vendor : findByVendorId) {

					Optional<VendorRegistreation> vendorname = vendorRegistreationRepository
							.findById(vendor.getVendorId());
					vendor.setVendorName(vendorname.get().getName());
					Optional<Item> item = itemRepository.findById(vendor.getItemId());
					vendor.setPro_division(item.get().getPro_division());
					vendor.setPro_family(item.get().getPro_family());
					vendor.setPro_class(item.get().getPro_class());
					vendor.setPro_sub_class(item.get().getPro_sub_class());
					Optional<Brand> brand = brandRepository.findById(vendor.getBrandId());
					vendor.setBrandName(brand.get().getBrandName());
					Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.getManufacId());
					vendor.setManufName(manufac.get().getManufacname());
					Optional<UOM> uom = uOMRepository.findById(vendor.getUomId());
					vendor.setUomName(uom.get().getName());

				}
				return findByVendorId;
			}
		}
	}

	@Override
	public List<VendorProductHeader> getVendorForProductInBulk(Integer[] id) {
		List<VendorProductHeader> product = new ArrayList<>();
		;
		if (id.length == 0) {
			return product;
		} else {

			for (Integer integer : id) {
				if (integer > 0) {
					System.out.println("id++++++++++++++++++++" + integer);
					Optional<VendorProductHeader> vendor = vendorProductHeaderRepository.findById(integer);
					Optional<VendorRegistreation> vendorname = vendorRegistreationRepository
							.findById(vendor.get().getVendorId());
					vendor.get().setVendorName(vendorname.get().getName());
					Optional<Item> item = itemRepository.findById(vendor.get().getItemId());
					vendor.get().setPro_division(item.get().getPro_division());
					vendor.get().setPro_family(item.get().getPro_family());
					vendor.get().setPro_class(item.get().getPro_class());
					vendor.get().setPro_sub_class(item.get().getPro_sub_class());
					Optional<Brand> brand = brandRepository.findById(vendor.get().getBrandId());
					vendor.get().setBrandName(brand.get().getBrandName());
					Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.get().getManufacId());
					vendor.get().setManufName(manufac.get().getManufacname());
					Optional<UOM> uom = uOMRepository.findById(vendor.get().getUomId());
					vendor.get().setUomName(uom.get().getName());

					product.add(vendor.get());
				}
			}
			return product;
		}

	}

	@Override
	public ResponseEntity<String> postVendorProduct(@Valid VendorProductHeader vendorProductHeader) {
		vendorProductHeader.setStatus("O");
		@Valid
		VendorProductHeader save = vendorProductHeaderRepository.save(vendorProductHeader);

		VendorProductDetails vpd = vendorProductHeader.getTempDetails();
		vpd.setStatus(true);
		vpd.setHeader(save);
		vendorProductDetailsRepository.save(vpd);

		return new ResponseEntity<String>("Vendor Product Save successfully and Open for approval", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> updateVendorProduct(@Valid VendorProductHeader vendorProductHeader) {
		vendorProductHeader.setStatus("O");
		@Valid
		VendorProductHeader save = vendorProductHeaderRepository.save(vendorProductHeader);

		VendorProductDetails vpd = vendorProductHeader.getTempDetails();
		vpd.setStatus(true);
		vpd.setHeader(save);
		vendorProductDetailsRepository.save(vpd);

		return new ResponseEntity<String>("Vendor Product updated successfully and again Open for approval",
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteVendorProduct(Integer id) {
		Optional<VendorProductHeader> findById = vendorProductHeaderRepository.findById(id);
		findById.get().setStatus("D");
		@Valid
		VendorProductHeader save = vendorProductHeaderRepository.save(findById.get());
		return new ResponseEntity<String>("Product Inactive", HttpStatus.CREATED);
	}

	@Override
	public List<VendorProductHeader> getVendorProductByVendorId(Integer id) {
		List<VendorProductHeader> findByVendorId = vendorProductHeaderRepository.findByVendorId(id).stream()
				.filter(t -> t.getStatus().equals("A")).collect(Collectors.toList());
		for (VendorProductHeader vendorProductHeader : findByVendorId) {
			Optional<VendorRegistreation> vendorname = vendorRegistreationRepository
					.findById(vendorProductHeader.getVendorId());
			vendorProductHeader.setVendorName(vendorname.get().getName());
			Optional<Item> item = itemRepository.findById(vendorProductHeader.getItemId());
			vendorProductHeader.setPro_division(item.get().getPro_division());
			vendorProductHeader.setPro_family(item.get().getPro_family());
			vendorProductHeader.setPro_class(item.get().getPro_class());
			vendorProductHeader.setPro_sub_class(item.get().getPro_sub_class());
			Optional<Brand> brand = brandRepository.findById(vendorProductHeader.getBrandId());
			vendorProductHeader.setBrandName(brand.get().getBrandName());
			Optional<Manufacturer> manufac = manufacturerRepository.findById(vendorProductHeader.getManufacId());
			vendorProductHeader.setManufName(manufac.get().getManufacname());
			Optional<UOM> uom = uOMRepository.findById(vendorProductHeader.getUomId());
			vendorProductHeader.setUomName(uom.get().getName());
		}
		return findByVendorId;
	}

	@Override
	public List<VendorProductHeader> getVendorFinalProductByVendorId(Integer id) {
		List<VendorProductHeader> findByVendorId = vendorProductHeaderRepository.findByVendorId(id).stream()
				.filter(t -> t.getStatus().equals("A")).collect(Collectors.toList());
		for (VendorProductHeader vendorProductHeader : findByVendorId) {
			Optional<VendorRegistreation> vendorname = vendorRegistreationRepository
					.findById(vendorProductHeader.getVendorId());
			vendorProductHeader.setVendorName(vendorname.get().getName());
			Optional<Item> item = itemRepository.findById(vendorProductHeader.getItemId());
			vendorProductHeader.setPro_division(item.get().getPro_division());
			vendorProductHeader.setPro_family(item.get().getPro_family());
			vendorProductHeader.setPro_class(item.get().getPro_class());
			vendorProductHeader.setPro_sub_class(item.get().getPro_sub_class());
			Optional<Brand> brand = brandRepository.findById(vendorProductHeader.getBrandId());
			vendorProductHeader.setBrandName(brand.get().getBrandName());
			Optional<Manufacturer> manufac = manufacturerRepository.findById(vendorProductHeader.getManufacId());
			vendorProductHeader.setManufName(manufac.get().getManufacname());
			Optional<UOM> uom = uOMRepository.findById(vendorProductHeader.getUomId());
			vendorProductHeader.setUomName(uom.get().getName());
		}
		return findByVendorId;
	}

	@Override
	public List<POProduct> getVendorFinalProductByVendorIdForPo(Integer id) {
		List<VendorProductHeader> findByVendorId = vendorProductHeaderRepository.findByVendorId(id).stream()
				.filter(t -> t.getStatus().equals("A")).collect(Collectors.toList());
		List<POProduct> pro = new ArrayList<>();
		Integer a = 1;
		for (VendorProductHeader vendorProductHeader : findByVendorId) {
			List<VendorProductDetails> details = vendorProductHeader.getDetails();
			for (VendorProductDetails temp : details) {
				POProduct p = new POProduct();
				if (temp.getStatus()) {
					p.setId(a);
					p.setHdrId(vendorProductHeader.getId());
					p.setDtlId(temp.getId());
					;
					p.setVendorId(vendorProductHeader.getVendorId());
					p.setArticleId(vendorProductHeader.getArticleId());
					p.setArticleName(vendorProductHeader.getArticleName());
					p.setArticleShortName(vendorProductHeader.getArticleShortName());
					p.setEanCD(vendorProductHeader.getEanCD());
					p.setHsnCD(vendorProductHeader.getHsnCD());
					p.setUomId(vendorProductHeader.getUomId());
					Optional<UOM> findById = uOMRepository.findById(vendorProductHeader.getUomId());
					p.setUomName(findById.get().getShortName());
					p.setMrp(temp.getMrp());
					p.setCost(temp.getBasicCost());
					p.setGst_tax(temp.getGstPer());
					p.setChecked_state(false);
				}
				a++;
				pro.add(p);

			}
		}
		return pro;
	}

	@Override
	public VendorProductDetails getProductCostDeatils(Integer id) {
		Optional<VendorProductDetails> findById = vendorProductDetailsRepository.findById(id);
		return findById.get();
	}

	// ============================= Vendor Product End==================

	// ============================= Vendor Product Approval Start==================

	@Override
	public List<VendorProductHeader> getVendorsProductForApprove() {
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = false;
		for (GrantedAuthority grantedAuthority : auth) {
			System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
				System.out.println("active admin");
				Roleadmin = true;
			}
		}

		if (Roleadmin) {
			List<VendorProductHeader> collect = vendorProductHeaderRepository.findAll().stream()
					.filter(t -> !t.getStatus().equals("A") && !t.getStatus().equals("D")).collect(Collectors.toList());

			for (VendorProductHeader vendor : collect) {
				Optional<VendorRegistreation> vendorname = vendorRegistreationRepository.findById(vendor.getVendorId());
				vendor.setVendorName(vendorname.get().getName());
				Optional<Item> item = itemRepository.findById(vendor.getItemId());
				vendor.setPro_division(item.get().getPro_division());
				vendor.setPro_family(item.get().getPro_family());
				vendor.setPro_class(item.get().getPro_class());
				vendor.setPro_sub_class(item.get().getPro_sub_class());
				Optional<Brand> brand = brandRepository.findById(vendor.getBrandId());
				vendor.setBrandName(brand.get().getBrandName());
				Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.getManufacId());
				vendor.setManufName(manufac.get().getManufacname());
				Optional<UOM> uom = uOMRepository.findById(vendor.getUomId());
				vendor.setUomName(uom.get().getName());

			}

			return collect;
		} else {
			String names = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Product Approval");
			List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
			for (ApprovalSetup approvalSetup : findByManuid) {
				Optional<User> user = userRepository.findById(approvalSetup.getUserid());
				approvalSetup.setUsername(user.get().getUsername());
			}

			Integer level = 0;
			for (ApprovalSetup approvalSetup : findByManuid) {
				if (approvalSetup.getUsername().equals(names)) {
					level = approvalSetup.getApproveorder();
				}
			}
			if (level.equals(0)) {
				System.out.println("nulll found");
				return null;
			}

			else if (level.equals(1)) {
				List<VendorProductHeader> collect = vendorProductHeaderRepository.findAll().stream()
						.filter(t -> t.getStatus().equals("O")).collect(Collectors.toList());

				for (VendorProductHeader vendor : collect) {
					Optional<VendorRegistreation> vendorname = vendorRegistreationRepository
							.findById(vendor.getVendorId());
					vendor.setVendorName(vendorname.get().getName());
					Optional<Item> item = itemRepository.findById(vendor.getItemId());
					vendor.setPro_division(item.get().getPro_division());
					vendor.setPro_family(item.get().getPro_family());
					vendor.setPro_class(item.get().getPro_class());
					vendor.setPro_sub_class(item.get().getPro_sub_class());
					Optional<Brand> brand = brandRepository.findById(vendor.getBrandId());
					vendor.setBrandName(brand.get().getBrandName());
					Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.getManufacId());
					vendor.setManufName(manufac.get().getManufacname());
					Optional<UOM> uom = uOMRepository.findById(vendor.getUomId());
					vendor.setUomName(uom.get().getName());

				}

				return collect;
			}

			else {
				Integer actualLevel = level - 1;
				String status = actualLevel.toString();

				List<VendorProductHeader> collect = vendorProductHeaderRepository.findAll().stream()
						.filter(t -> t.getStatus().equals(status)).collect(Collectors.toList());

				for (VendorProductHeader vendor : collect) {
					Optional<VendorRegistreation> vendorname = vendorRegistreationRepository
							.findById(vendor.getVendorId());
					vendor.setVendorName(vendorname.get().getName());
					Optional<Item> item = itemRepository.findById(vendor.getItemId());
					vendor.setPro_division(item.get().getPro_division());
					vendor.setPro_family(item.get().getPro_family());
					vendor.setPro_class(item.get().getPro_class());
					vendor.setPro_sub_class(item.get().getPro_sub_class());
					Optional<Brand> brand = brandRepository.findById(vendor.getBrandId());
					vendor.setBrandName(brand.get().getBrandName());
					Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.getManufacId());
					vendor.setManufName(manufac.get().getManufacname());
					Optional<UOM> uom = uOMRepository.findById(vendor.getUomId());
					vendor.setUomName(uom.get().getName());

				}

				return collect;

			}
		}

	}

	@Override
	public ResponseEntity<String> updateVendorProductApproval(Integer id) {
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = false;
		for (GrantedAuthority grantedAuthority : auth) {
			// System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
				Roleadmin = true;
			}
		}

		if (Roleadmin) {
			Optional<VendorProductHeader> vendor = vendorProductHeaderRepository.findById(id);

			Articles artical = new Articles();
			artical.setName(vendor.get().getArticleName());
			artical.setShortName(vendor.get().getArticleShortName());
			artical.setProductId(vendor.get().getItemId());
			artical.setEanCode(vendor.get().getEanCD());
			artical.setStatus(true);
			artical.setBlockProcurement(false);
			Articles save2 = articlesRepository.save(artical);

			vendor.get().setStatus("A");
			vendor.get().setArticleId(save2.getId());
			VendorProductHeader save = vendorProductHeaderRepository.save(vendor.get());

			return new ResponseEntity<String>("Vendor Product Final Approved!!", HttpStatus.CREATED);
		} else {
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Product Approval");
			List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
			Integer level = findByManuid.size();
			Integer actualLevel = level - 1;
			String status = actualLevel.toString();
			Optional<VendorProductHeader> vendor = vendorProductHeaderRepository.findById(id);
			if (vendor.get().getStatus().equals(status)) {

				Articles artical = new Articles();
				artical.setName(vendor.get().getArticleName());
				artical.setShortName(vendor.get().getArticleShortName());
				artical.setProductId(vendor.get().getItemId());
				artical.setEanCode(vendor.get().getEanCD());
				artical.setStatus(true);
				artical.setBlockProcurement(false);
				Articles save2 = articlesRepository.save(artical);
				vendor.get().setStatus("A");
				vendor.get().setArticleId(save2.getId());
				VendorProductHeader save = vendorProductHeaderRepository.save(vendor.get());
				return new ResponseEntity<String>("Vendor Product Final Approved!!", HttpStatus.CREATED);
			} else if (vendor.get().getStatus().equals("O")) {
				vendor.get().setStatus("1");
				vendorProductHeaderRepository.save(vendor.get());
				return new ResponseEntity<String>("Vendor Product Approved for next approval!!", HttpStatus.CREATED);
			} else {
				Integer temp = Integer.parseInt(vendor.get().getStatus());
				Integer actualTemp = temp + 1;
				String s = actualTemp.toString();
				vendor.get().setStatus(s);
				vendorProductHeaderRepository.save(vendor.get());
				return new ResponseEntity<String>("Vendor Product Approved for next approval!!", HttpStatus.CREATED);
			}
		}

	}

	@Override
	public ResponseEntity<String> updateVendorProductDisapprove(Integer id, String reason) {
		Optional<VendorProductHeader> findById = vendorProductHeaderRepository.findById(id);
		findById.get().setStatus("D");
		findById.get().setReson(reason);
		@Valid
		VendorProductHeader save = vendorProductHeaderRepository.save(findById.get());
		return new ResponseEntity<String>("Vendor Product Disapproved!!", HttpStatus.CREATED);
	}

	// ============================= Vendor Product Approval End==================

	// ============================= ShippingNotification Start==================

	@Override
	public List<ShippingNotification> getShippingNotification() {
		List<ShippingNotification> findAll = shippingNotificationRepository.findAll().stream()
				.filter(t -> t.getStatus().equals(true)).collect(Collectors.toList());
		for (ShippingNotification shippingNotification : findAll) {
			Optional<VendorRegistreation> findById = vendorRegistreationRepository
					.findById(shippingNotification.getVendorId());
			shippingNotification.setName(findById.get().getName());
			Optional<Outlet> findById2 = outletRepository.findById(shippingNotification.getOutletId());
			shippingNotification.setOutletName(findById2.get().getName());
		}
		return findAll;
	}

	@Override
	public ShippingNotification getShippingNotificationByid(Integer id) {
		Optional<ShippingNotification> findById = shippingNotificationRepository.findById(id);
		Optional<VendorRegistreation> findByVendorId = vendorRegistreationRepository
				.findById(findById.get().getVendorId());
		findById.get().setName(findByVendorId.get().getName());
		Optional<Outlet> findById2 = outletRepository.findById(findById.get().getOutletId());
		findById.get().setOutletName(findById2.get().getName());
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addShippingNotification(@Valid ShippingNotification shippingNotification) {
		shippingNotification.setStatus(true);
		shippingNotification.setDate(LocalDate.now());
		shippingNotification.setTime(LocalTime.now());
		@Valid
		ShippingNotification save = shippingNotificationRepository.save(shippingNotification);
		return new ResponseEntity<String>(save.getShipInfo() + " Shipping Notification Created", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> updateShippingNotification(@Valid ShippingNotification shippingNotification) {
		shippingNotification.setStatus(true);
		shippingNotification.setDate(LocalDate.now());
		shippingNotification.setTime(LocalTime.now());
		@Valid
		ShippingNotification save = shippingNotificationRepository.save(shippingNotification);
		return new ResponseEntity<String>(save.getShipInfo() + " Shipping Notification Upadted", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteShippingNotification(Integer id) {
		Optional<ShippingNotification> findById = shippingNotificationRepository.findById(id);
		findById.get().setStatus(false);
		ShippingNotification save = shippingNotificationRepository.save(findById.get());
		return new ResponseEntity<String>(save.getShipInfo() + " Shipping Notification Deleted", HttpStatus.CREATED);
	}

	// ============================= ShippingNotification Start==================

	// ============================= GKM Start==================

	@Override
	public List<VendorProductHeader> getGKMData() {
		List<VendorProductHeader> collect = vendorProductHeaderRepository.findAll().stream()
				.filter(t -> t.getStatus().equals("A") && t.getPriceBased().equals("G")).collect(Collectors.toList());
		for (VendorProductHeader vendor : collect) {
			Optional<VendorRegistreation> vendorname = vendorRegistreationRepository.findById(vendor.getVendorId());
			vendor.setVendorName(vendorname.get().getName());
			Optional<Item> item = itemRepository.findById(vendor.getItemId());
			vendor.setPro_division(item.get().getPro_division());
			vendor.setPro_family(item.get().getPro_family());
			vendor.setPro_class(item.get().getPro_class());
			vendor.setPro_sub_class(item.get().getPro_sub_class());
			Optional<Brand> brand = brandRepository.findById(vendor.getBrandId());
			vendor.setBrandName(brand.get().getBrandName());
			Optional<Manufacturer> manufac = manufacturerRepository.findById(vendor.getManufacId());
			vendor.setManufName(manufac.get().getManufacname());
			Optional<UOM> uom = uOMRepository.findById(vendor.getUomId());
			vendor.setUomName(uom.get().getName());
		}
		return collect;
	}
	// ============================= GKM Start==================

	// ============================= Vendor Manufacturer Start==================
	@Override
	public VendorManufacturerDTO getVenManufData(Integer id) {
		Optional<VendorRegistreation> findById = vendorRegistreationRepository.findById(id);
		VendorManufacturerDTO dto = new VendorManufacturerDTO();
		dto.setVendorId(findById.get().getId());
		Collection<Manufacturer> manufacturer = findById.get().getManufacturer();
		List<Integer> manufiD = new ArrayList<>();
		for (Manufacturer manufacturer2 : manufacturer) {
			manufiD.add(manufacturer2.getId());
		}
		dto.setManufactureId(manufiD);
		return dto;
	}

	@Override
	public ResponseEntity<String> postVenManufMapping(VendorManufacturerDTO vendorManufacturerDTO) {
		Optional<VendorRegistreation> findById = vendorRegistreationRepository
				.findById(vendorManufacturerDTO.getVendorId());
		List<Manufacturer> manuf = new ArrayList<>();
		List<Integer> manufactureId = vendorManufacturerDTO.getManufactureId();
		for (Integer integer : manufactureId) {
			Optional<Manufacturer> findById2 = manufacturerRepository.findById(integer);
			manuf.add(findById2.get());
		}
		findById.get().setManufacturer(manuf);

		vendorRegistreationRepository.save(findById.get());

		return new ResponseEntity<String>("Vendor Manufacturer Mapping Complted", HttpStatus.CREATED);
	}

	@Override
	public List<Manufacturer> getManufactureByVendorId(Integer id) {
		Optional<VendorRegistreation> findById = vendorRegistreationRepository.findById(id);
		List<Manufacturer> manufacturer = (List<Manufacturer>) findById.get().getManufacturer();
		return manufacturer;
	}
	// ============================= Vendor Manufacturer End==================

	@Override
	public ResponseEntity<String> vendorReplacement(Integer oldId, Integer newId) {
		try {
			VendorRegistreation fromVendor = vendorRegistreationRepository.findById(oldId).get();
			VendorRegistreation toVendor = vendorRegistreationRepository.findById(newId).get();
			Collection<Manufacturer> manufacturer = fromVendor.getManufacturer();

			// manufacture detais transfer
			try {
				manufacturerRepository.updateVenManuf(newId, oldId);
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}

			// vendor product transfer
			List<VendorProductHeader> vendorProduct = vendorProductHeaderRepository.findByVendorId(oldId);
			for (VendorProductHeader vendorProductHeader : vendorProduct) {
				vendorProductHeader.setVendorId(newId);
				vendorProductHeaderRepository.save(vendorProductHeader);
			}

			// disable user
			User user = userRepository.findByVendorId(oldId).get();
			user.setEnabled(false);
			user.setAccountNotExpired(false);
			userRepository.save(user);

			// disapprove vendor
			fromVendor.setStatus("D");
			vendorRegistreationRepository.save(fromVendor);

			return new ResponseEntity<String>("vendor transfer completed successfully ", HttpStatus.CREATED);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<String>("Vendor transfer Not Possible Due System Error", HttpStatus.BAD_GATEWAY);
		}

	}

}
