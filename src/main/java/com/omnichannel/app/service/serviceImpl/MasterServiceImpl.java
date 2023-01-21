package com.omnichannel.app.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.omnichannel.app.model.DTO.ClusterMapping;
import com.omnichannel.app.model.admin.IdentificationSetup;
import com.omnichannel.app.model.admin.Transactionlog;
import com.omnichannel.app.model.asset.AssetRegistration;
import com.omnichannel.app.model.master.AssetsType;
import com.omnichannel.app.model.master.Bay;
import com.omnichannel.app.model.master.City;
import com.omnichannel.app.model.master.Cluster;
import com.omnichannel.app.model.master.Country;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.master.OutletDocDetails;
import com.omnichannel.app.model.master.Paymentmode;
import com.omnichannel.app.model.master.Reason;
import com.omnichannel.app.model.master.Region;
import com.omnichannel.app.model.master.Schedule;
import com.omnichannel.app.model.master.State;
import com.omnichannel.app.model.master.Tot;
import com.omnichannel.app.model.master.TotDetail;
import com.omnichannel.app.model.master.TotHeader;
import com.omnichannel.app.model.master.Transportation;
import com.omnichannel.app.model.master.TransportationDocDetails;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.model.stock.GrnDetails;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.repository.admin.TransactionlogRepository;
import com.omnichannel.app.repository.master.AssetsTypeRepository;
import com.omnichannel.app.repository.master.BayRepository;
import com.omnichannel.app.repository.master.CityRepository;
import com.omnichannel.app.repository.master.ClusterRepository;
import com.omnichannel.app.repository.master.CountryRepository;
import com.omnichannel.app.repository.master.LocationRepository;
import com.omnichannel.app.repository.master.OutletDocDetailsRepository;
import com.omnichannel.app.repository.master.OutletRepository;
import com.omnichannel.app.repository.master.PaymentmodeRepository;
import com.omnichannel.app.repository.master.ReasonRepository;
import com.omnichannel.app.repository.master.RegionRepository;
import com.omnichannel.app.repository.master.ScheduleRespository;
import com.omnichannel.app.repository.master.StateRepository;
import com.omnichannel.app.repository.master.TotDetailRepository;
import com.omnichannel.app.repository.master.TotHeaderRepository;
import com.omnichannel.app.repository.master.TotRepository;
import com.omnichannel.app.repository.master.TransportationDocDetailsRepository;
import com.omnichannel.app.repository.master.TransportationRepositorty;
import com.omnichannel.app.repository.product.ArticlesRepository;
import com.omnichannel.app.repository.vendor.VendorProductHeaderRepository;
import com.omnichannel.app.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	CountryRepository countryRepository;
	@Autowired
	StateRepository stateRepository;
	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	CityRepository cityRepository;
	@Autowired
	PaymentmodeRepository paymentmodeRepository;
	@Autowired
	RegionRepository regionRepository;
	@Autowired
	AssetsTypeRepository assetsTypeRepository;
	@Autowired
	OutletRepository outletRepository;
	@Autowired
	OutletDocDetailsRepository outletDocDetailsRepository;
	@Autowired
	TransportationRepositorty transportationRepositorty;
	@Autowired
	TransportationDocDetailsRepository transportationDocDetailsRepository;
	@Autowired
	ReasonRepository reasonRepository;
	@Autowired
	BayRepository bayRepository;
	@Autowired
	TransactionlogRepository transactionlogRepository;
	@Autowired
	ScheduleRespository scheduleRepository;
	@Autowired
	ClusterRepository clusterRepository;
	@Autowired
	ArticlesRepository articlesRepository;
	@Autowired
	TotRepository totRepository;
	@Autowired
	VendorProductHeaderRepository vendorProductHeaderRepository;
	@Autowired
	TotHeaderRepository totHeaderRepository;
	@Autowired
	TotDetailRepository totDetailRepository;

	@Override
	public List<Country> getCountry() {
		return countryRepository.findAll();
	}

	@Override
	public List<State> getState() {
		return stateRepository.findAll();
	}

	@Override
	public List<State> getStateByCountryCode(Integer id) {
		return stateRepository.findByCountryCode(id);
	}

	@Override
	public List<Location> getLocation() {
		List<Location> findAll = locationRepository.findAll();

		for (Location loc : findAll) {
			Optional<State> findById = stateRepository.findById(loc.getStateId());
			loc.setStatename(findById.get().getState());
			Optional<Country> findById2 = countryRepository.findById(loc.getCountryId());
			loc.setCountryname(findById2.get().getName());
		}

		List<Location> location = new ArrayList<>();
		for (Location loc : findAll) {
			if (loc.getStatus()) {
				location.add(loc);
			}
		}

		return location;
	}

	@Override
	public ResponseEntity<String> addLocation(Location location) {
		String s = location.getName().toUpperCase();
		if (locationRepository.existsByNameAndStateId(s, location.getStateId())) {
			Optional<Location> findByName = locationRepository.findByName(s);
			if (findByName.get().getStatus()) {
				return new ResponseEntity<String>(location.getName() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.get().setStatus(true);
				Location save = locationRepository.save(findByName.get());
				return new ResponseEntity<String>(save.getName() + " Location re-enable", HttpStatus.CREATED);
			}

		} else {
			location.setStatus(true);
			location.setName(location.getName().toUpperCase());
			Location save = locationRepository.save(location);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity("New " + save.getName() + " Location Added");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save.getName() + " Location Saved", HttpStatus.CREATED);
		}

	}

	@Override
	public ResponseEntity<String> updateLocation(Location location) {
		location.setName(location.getName().toUpperCase());
		Location save = locationRepository.save(location);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("New " + save.getName() + " Location Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(save.getName() + " Location Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteLocation(Integer id) {
		Optional<Location> findById = locationRepository.findById(id);
		Location loc = findById.get();
		loc.setStatus(false);
		locationRepository.save(loc);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getName() + " Location Deleted");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getName() + " Location Delete Successfully", HttpStatus.OK);
	}

	@Override
	public Location getLocationById(Integer id) {
		Optional<Location> findById = locationRepository.findById(id);
		return findById.get();
	}

	@Override
	public Location getLocationByName(String name) {
		Optional<Location> loc = locationRepository.findByName(name);
		if (loc.isPresent()) {
			return loc.get();
		} else {
			return null;
		}
	}

	// ============================= Paymentmode Start==================

	@Override
	public List<Paymentmode> getPaymentmode() {

		List<Paymentmode> findAll = paymentmodeRepository.findAll();
		List<Paymentmode> payment = new ArrayList<>();
		for (Paymentmode pm : findAll) {
			if (pm.getStatus()) {
				payment.add(pm);
			}
		}
		return payment;
	}

	@Override
	public ResponseEntity<String> addPaymentmode(Paymentmode paymentmode) {

		String s = paymentmode.getPaymentmode().toUpperCase();
		if (paymentmodeRepository.existsByPaymentmode(s)) {
			Paymentmode findByName = paymentmodeRepository.findByPaymentmode(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(paymentmode.getPaymentmode() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Paymentmode save = paymentmodeRepository.save(findByName);
				return new ResponseEntity<String>(save.getPaymentmode() + " Payment Mode re-enable",
						HttpStatus.CREATED);
			}

		} else {
			paymentmode.setStatus(true);
			paymentmode.setPaymentmode(paymentmode.getPaymentmode().toUpperCase());
			Paymentmode save = paymentmodeRepository.save(paymentmode);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getPaymentmode() + " Paymentmode Saved");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save.getPaymentmode() + " Paymentmode Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updatePaymentmode(Paymentmode paymentmode) {
		paymentmode.setPaymentmode(paymentmode.getPaymentmode().toUpperCase());
		paymentmode.setStatus(true);
		Paymentmode save = paymentmodeRepository.save(paymentmode);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getPaymentmode() + " Paymentmode Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(save.getPaymentmode() + " Paymentmode Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deletePaymentmode(Integer id) {
		Optional<Paymentmode> findById = paymentmodeRepository.findById(id);
		findById.get().setStatus(false);
		paymentmodeRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Delete Paymentmode");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getPaymentmode() + " Paymentmode Delete Successfully",
				HttpStatus.CREATED);
	}

	@Override
	public Paymentmode getPaymentmodeByid(Integer id) {
		Optional<Paymentmode> findById = paymentmodeRepository.findById(id);
		return findById.get();
	}

	// ============================= Paymentmode Start==================

	// ============================= Region Start==================

	@Override
	public List<Region> getRegion() {
		List<Region> collect = regionRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public ResponseEntity<String> addRegion(Region region) {

		String s = region.getRegion().toUpperCase();
		if (regionRepository.existsByRegion(s)) {
			Region findByName = regionRepository.findByRegion(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(region.getRegion() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Region save = regionRepository.save(findByName);
				return new ResponseEntity<String>(save.getRegion() + " Region re-enable", HttpStatus.CREATED);
			}

		} else {
			region.setStatus(true);
			region.setRegion(s);
			Region save = regionRepository.save(region);
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity("Region Added");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save.getRegion() + " Region Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updateRegion(Region region) {
		region.setStatus(true);
		region.setRegion(region.getRegion().toUpperCase());
		Region save = regionRepository.save(region);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Region Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(save.getRegion() + " Region Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteRegion(Integer id) {
		Optional<Region> findById = regionRepository.findById(id);
		findById.get().setStatus(false);
		regionRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Region Deleted");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getRegion() + " Region Delete Successfully",
				HttpStatus.CREATED);
	}

	@Override
	public Region getRegionByid(Integer id) {
		Optional<Region> findById = regionRepository.findById(id);
		return findById.get();
	}

	// ============================= Region End==================

	// ============================= AssetsType Start==================

	@Override
	public List<AssetsType> getAssetsType() {
		List<AssetsType> findAll = assetsTypeRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return findAll;
	}

	@Override
	public AssetsType getAssetsTypeByid(Integer id) {
		Optional<AssetsType> findById = assetsTypeRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addAssetsType(@Valid AssetsType assetsType) {

		String s = assetsType.getType().toUpperCase();
		if (assetsTypeRepository.existsByType(s)) {
			AssetsType findByName = assetsTypeRepository.findByType(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(findByName.getType() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				AssetsType save = assetsTypeRepository.save(findByName);
				return new ResponseEntity<String>(save.getType() + " AssetsType re-enable", HttpStatus.CREATED);
			}

		} else {
			assetsType.setTypeid(assetsType.getTypeid().toUpperCase());
			assetsType.setType(assetsType.getType().toUpperCase());
			assetsType.setStatus(true);
			assetsTypeRepository.save(assetsType);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity("Assets type Added");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(" AssetsType save Successfully", HttpStatus.CREATED);
		}

	}

	@Override
	public ResponseEntity<String> updateAssetsType(@Valid AssetsType assetsType) {
		assetsType.setTypeid(assetsType.getTypeid().toUpperCase());
		assetsType.setType(assetsType.getType().toUpperCase());
		assetsType.setStatus(true);
		assetsTypeRepository.save(assetsType);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Assets type updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(" AssetsType update Successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteAssetsType(Integer id) {
		Optional<AssetsType> findById = assetsTypeRepository.findById(id);
		findById.get().setStatus(false);
		assetsTypeRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Assets type Deleted");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(" AssetsType Delete Successfully", HttpStatus.CREATED);
	}
	// ============================= AssetsType End==================

	// ============================= Outlet Setup Start==================

	@Override
	public List<Outlet> getOutlet() {
		List<Outlet> collect = outletRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		for (Outlet out : collect) {
			Optional<Location> findById = locationRepository.findById(out.getLocationid());
			out.setLocationname(findById.get().getName());
			Optional<State> state = stateRepository.findById(out.getStateid());
			out.setStatename(state.get().getState());
			Optional<Country> country = countryRepository.findById(out.getCountrycode());
			out.setCountryname(country.get().getName());
			if (out.getType().equals("S")) {
				Optional<Outlet> findById2 = outletRepository.findById(out.getWarehouseid());
				out.setWarehousename(findById2.get().getName());
			}
		}
		return collect;
	}

	@Override
	public List<Outlet> getWarehouse() {
		List<Outlet> collect = outletRepository.findAll().stream()
				.filter(t -> t.getStatus().equals(true) && t.getType().equals("W")).collect(Collectors.toList());
		for (Outlet out : collect) {
			Optional<Location> findById = locationRepository.findById(out.getLocationid());
			out.setLocationname(findById.get().getName());
			Optional<State> state = stateRepository.findById(out.getStateid());
			out.setStatename(state.get().getState());
			Optional<Country> country = countryRepository.findById(out.getCountrycode());
			out.setCountryname(country.get().getName());
		}
		return collect;
	}

	@Override
	public List<Outlet> getOffice() {
		List<Outlet> collect = outletRepository.findAll().stream()
				.filter(t -> t.getStatus().equals(true) && t.getType().equals("O")).collect(Collectors.toList());
		for (Outlet out : collect) {
			Optional<Location> findById = locationRepository.findById(out.getLocationid());
			out.setLocationname(findById.get().getName());
			Optional<State> state = stateRepository.findById(out.getStateid());
			out.setStatename(state.get().getState());
			Optional<Country> country = countryRepository.findById(out.getCountrycode());
			out.setCountryname(country.get().getName());
		}
		return collect;
	}

	@Override
	public List<Outlet> getStore() {
		List<Outlet> collect = outletRepository.findAll().stream()
				.filter(t -> t.getStatus().equals(true) && t.getType().equals("S")).collect(Collectors.toList());
		for (Outlet out : collect) {
			Optional<Location> findById = locationRepository.findById(out.getLocationid());
			out.setLocationname(findById.get().getName());
			Optional<State> state = stateRepository.findById(out.getStateid());
			out.setStatename(state.get().getState());
			Optional<Country> country = countryRepository.findById(out.getCountrycode());
			out.setCountryname(country.get().getName());
			Optional<Outlet> findById2 = outletRepository.findById(out.getWarehouseid());
			out.setWarehousename(findById2.get().getName());
		}
		return collect;
	}

	@Override
	public Outlet getOutletByid(Integer id) {
		Optional<Outlet> findById = outletRepository.findById(id);
		Optional<Location> findById2 = locationRepository.findById(findById.get().getLocationid());
		findById.get().setLocationname(findById2.get().getName());
		Optional<State> state = stateRepository.findById(findById.get().getStateid());
		findById.get().setStatename(state.get().getState());
		Optional<Country> country = countryRepository.findById(findById.get().getCountrycode());
		findById.get().setCountryname(country.get().getName());

		return findById.get();
	}

	@Override
	public ResponseEntity<String> addOutlet(@Valid Outlet outlet) {
		// outletDocDetailsRepository
		outlet.setName(outlet.getName().toUpperCase());
		outlet.setLocationname(outlet.getLocationname().toUpperCase());
		List<OutletDocDetails> docdetails = new ArrayList<>();
		try {
			docdetails = outlet.getDetails();
		} catch (Exception e) {
			System.out.println("List is Empty" + e.getMessage());
		}

		if (outlet.getType().equals("W")) {
			outlet.setWarehouseid(0);
		}
		if (outlet.getType().equals("O")) {
			outlet.setWarehouseid(0);
		}

		if (outletRepository.existsByName(outlet.getName())) {
			Outlet findByName = outletRepository.findByName(outlet.getName());
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(findByName.getName() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Outlet save = outletRepository.save(findByName);
				return new ResponseEntity<String>(save.getName() + " outlet re-enable", HttpStatus.CREATED);
			}

		} else {
			Optional<Location> findByName = locationRepository.findByName(outlet.getLocationname());
			if (findByName.isEmpty()) {
				Location loc = new Location();
				loc.setName(outlet.getLocationname());
				loc.setStateId(outlet.getStateid());
				loc.setCountryId(outlet.getCountrycode());
				loc.setPincode(outlet.getPin());
				loc.setStatus(true);
				Location save = locationRepository.save(loc);

				outlet.setLocationid(save.getId());
				outlet.setStatus(true);
				outlet.setDetails(null);
				@Valid
				Outlet save2 = outletRepository.save(outlet);
				try {
					for (OutletDocDetails outletDocDetails : docdetails) {
						outletDocDetails.setHeader(save2);
					}
					save2.setDetails(docdetails);
					outletRepository.save(save2);
				} catch (Exception e) {
					System.out.println("List is Empty" + e.getMessage());
				}

				String name = SecurityContextHolder.getContext().getAuthentication().getName();
				Transactionlog trlog = new Transactionlog();
				trlog.setUsername(name);
				trlog.setTimestamp(LocalDateTime.now());
				trlog.setActivity("New outlet Added");
				transactionlogRepository.save(trlog);

				return new ResponseEntity<String>(save2.getName() + " outlet save Successfully", HttpStatus.CREATED);

			} else {

				outlet.setLocationid(findByName.get().getId());
				outlet.setStatus(true);
				outlet.setDetails(null);
				@Valid
				Outlet save2 = outletRepository.save(outlet);
				try {
					for (OutletDocDetails outletDocDetails : docdetails) {
						outletDocDetails.setHeader(save2);
					}
					save2.setDetails(docdetails);
					outletRepository.save(save2);
				} catch (Exception e) {
					System.out.println("List is Empty" + e.getMessage());
				}
				return new ResponseEntity<String>(save2.getName() + " outlet save Successfully", HttpStatus.CREATED);

			}
		}
	}

	@Override
	public ResponseEntity<String> updateOutlet(@Valid Outlet outlet) {
		outlet.setName(outlet.getName().toUpperCase());
		outlet.setLocationname(outlet.getLocationname().toUpperCase());
		List<OutletDocDetails> docdetails = new ArrayList<>();
		try {
			docdetails = outlet.getDetails();
			for (OutletDocDetails outletDocDetails : docdetails) {
				outletDocDetails.setHeader(outlet);
			}
			outlet.setDetails(docdetails);
		} catch (Exception e) {
			System.out.println("List is Empty" + e.getMessage());
		}

		if (outlet.getType().equals("W")) {
			outlet.setWarehouseid(0);
		}

		if (outlet.getType().equals("o")) {
			outlet.setWarehouseid(0);
		}

		Optional<Location> findByName = locationRepository.findByName(outlet.getLocationname());
		if (findByName.isEmpty()) {
			Location loc = new Location();
			loc.setName(outlet.getLocationname());
			loc.setStateId(outlet.getStateid());
			loc.setCountryId(outlet.getCountrycode());
			loc.setStatus(true);
			Location save = locationRepository.save(loc);

			outlet.setLocationid(save.getId());
			outlet.setStatus(true);
			outlet.setDetails(docdetails);
			@Valid
			Outlet save2 = outletRepository.save(outlet);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity("Outlet Updated");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save2.getName() + " outlet Update Successfully", HttpStatus.CREATED);

		} else {

			outlet.setLocationid(findByName.get().getId());
			outlet.setStatus(true);
			outlet.setDetails(docdetails);
			@Valid
			Outlet save2 = outletRepository.save(outlet);
			return new ResponseEntity<String>(save2.getName() + " outlet Update Successfully", HttpStatus.CREATED);

		}
	}

	@Override
	public ResponseEntity<String> deleteOutlet(Integer id) {
		Optional<Outlet> findById = outletRepository.findById(id);
		findById.get().setStatus(false);
		outletRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Outlet Deleted");
		transactionlogRepository.save(trlog);
		return new ResponseEntity<String>(" Outlet Delete Successfully", HttpStatus.CREATED);
	}

	@Override
	public String deleteOutletDocument(Integer id) {
		outletDocDetailsRepository.deleteById(id);
		return "Data Deleted";
	}

	// ============================= Outlet Setup End==================

	// ============================= Transportation Setup Start==================

	@Override
	public List<Transportation> getTransportation() {
		List<Transportation> collect = transportationRepositorty.findAll().stream()
				.filter(t -> t.getStatus().equals(true)).collect(Collectors.toList());
		for (Transportation out : collect) {
			Optional<Location> findById = locationRepository.findById(out.getLocationid());
			out.setLocationname(findById.get().getName());
			Optional<State> state = stateRepository.findById(out.getStateid());
			out.setStatename(state.get().getState());
			Optional<Country> country = countryRepository.findById(out.getCountrycode());
			out.setCountryname(country.get().getName());
		}
		return collect;
	}

	@Override
	public Transportation getTransportationByid(Integer id) {
		Optional<Transportation> findById = transportationRepositorty.findById(id);
		Optional<Location> findById2 = locationRepository.findById(findById.get().getLocationid());
		findById.get().setLocationname(findById2.get().getName());
		Optional<State> state = stateRepository.findById(findById.get().getStateid());
		findById.get().setStatename(state.get().getState());
		Optional<Country> country = countryRepository.findById(findById.get().getCountrycode());
		findById.get().setCountryname(country.get().getName());

		return findById.get();
	}

	@Override
	public ResponseEntity<String> addTransportation(@Valid Transportation transportation) {
		transportation.setName(transportation.getName().toUpperCase());
		transportation.setLocationname(transportation.getLocationname().toUpperCase());
		List<TransportationDocDetails> transdetails = new ArrayList<>();
		try {
			transdetails = transportation.getDetails();
		} catch (Exception e) {
			System.out.println("List is Empty" + e.getMessage());
		}

		if (transportationRepositorty.existsByName(transportation.getName())) {
			Transportation findByName = transportationRepositorty.findByName(transportation.getName());
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(findByName.getName() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Transportation save = transportationRepositorty.save(findByName);
				return new ResponseEntity<String>(save.getName() + " Transportation re-enable", HttpStatus.CREATED);
			}

		} else {
			Optional<Location> findByName = locationRepository.findByName(transportation.getLocationname());
			if (findByName.isEmpty()) {
				Location loc = new Location();
				loc.setName(transportation.getLocationname());
				loc.setStateId(transportation.getStateid());
				loc.setCountryId(transportation.getCountrycode());
				loc.setStatus(true);
				Location save = locationRepository.save(loc);

				transportation.setLocationid(save.getId());
				transportation.setStatus(true);
				transportation.setDetails(null);
				@Valid
				Transportation save2 = transportationRepositorty.save(transportation);
				try {
					for (TransportationDocDetails transportationDocDetails : transdetails) {
						transportationDocDetails.setHeader(save2);
					}
					save2.setDetails(transdetails);
					transportationRepositorty.save(save2);
				} catch (Exception e) {
					System.out.println("List is Empty" + e.getMessage());
				}

				String name = SecurityContextHolder.getContext().getAuthentication().getName();
				Transactionlog trlog = new Transactionlog();
				trlog.setUsername(name);
				trlog.setTimestamp(LocalDateTime.now());
				trlog.setActivity("New Transportation Added");
				transactionlogRepository.save(trlog);

				return new ResponseEntity<String>(save2.getName() + " Transportation save Successfully",
						HttpStatus.CREATED);

			} else {

				transportation.setLocationid(findByName.get().getId());
				transportation.setStatus(true);
				transportation.setDetails(null);
				@Valid
				Transportation save2 = transportationRepositorty.save(transportation);
				for (TransportationDocDetails transportationDocDetails : transdetails) {
					transportationDocDetails.setHeader(save2);
				}
				save2.setDetails(transdetails);
				transportationRepositorty.save(save2);
				return new ResponseEntity<String>(save2.getName() + " Transportation save Successfully",
						HttpStatus.CREATED);

			}
		}
	}

	@Override
	public ResponseEntity<String> updateTransportation(@Valid Transportation transportation) {
		transportation.setName(transportation.getName().toUpperCase());
		transportation.setLocationname(transportation.getLocationname().toUpperCase());
		List<TransportationDocDetails> transdetails = new ArrayList<>();
		try {
			transdetails = transportation.getDetails();
			for (TransportationDocDetails transportationDocDetails : transdetails) {
				transportationDocDetails.setHeader(transportation);
			}
			transportation.setDetails(transdetails);
		} catch (Exception e) {
			System.out.println("List is Empty" + e.getMessage());
		}

		Optional<Location> findByName = locationRepository.findByName(transportation.getLocationname());
		if (findByName.isEmpty()) {
			Location loc = new Location();
			loc.setName(transportation.getLocationname());
			loc.setStateId(transportation.getStateid());
			loc.setCountryId(transportation.getCountrycode());
			loc.setStatus(true);
			Location save = locationRepository.save(loc);

			transportation.setLocationid(save.getId());
			transportation.setStatus(true);
			transportation.setDetails(transdetails);
			@Valid
			Transportation save2 = transportationRepositorty.save(transportation);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity("Transportation Updated");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save2.getName() + " Transportation Updated Successfully",
					HttpStatus.CREATED);

		} else {

			transportation.setLocationid(findByName.get().getId());
			transportation.setStatus(true);
			transportation.setDetails(transdetails);
			@Valid
			Transportation save2 = transportationRepositorty.save(transportation);
			return new ResponseEntity<String>(save2.getName() + " Transportation Updated Successfully",
					HttpStatus.CREATED);

		}
	}

	@Override
	public ResponseEntity<String> deleteTransportation(Integer id) {
		Optional<Transportation> findById = transportationRepositorty.findById(id);
		findById.get().setStatus(false);
		transportationRepositorty.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Transportation Deleted");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(" Transportation Delete Successfully", HttpStatus.CREATED);
	}

	@Override
	public String deleteTransportationDocument(Integer id) {
		transportationDocDetailsRepository.deleteById(id);
		return "Data Deleted";
	}

	// ============================= Transportation Setup End==================

	// ============================= Reason Setup Start==================

	@Override
	public List<Reason> getReason() {
		List<Reason> collect = reasonRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Reason getReasonByid(Integer id) {
		Optional<Reason> findById = reasonRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addReason(@Valid Reason reason) {
		reason.setStatus(true);
		reason.setReason(reason.getReason().toUpperCase());
		reasonRepository.save(reason);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Reason Added");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(" Reason Successfully Created", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> updateReason(@Valid Reason reason) {
		reason.setStatus(true);
		reason.setReason(reason.getReason().toUpperCase());
		reasonRepository.save(reason);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Reason Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(" Reason Successfully Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteReason(Integer id) {
		Optional<Reason> findById = reasonRepository.findById(id);
		findById.get().setStatus(false);
		reasonRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Reason Deleted");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(" Reason Delete Successfully", HttpStatus.CREATED);
	}

	// ============================= Reason Setup End==================

	// ============================= Bay Start==================
	@Override
	public List<Bay> getBay() {
		List<Bay> collect = bayRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		for (Bay bay : collect) {
			Optional<Outlet> findById = outletRepository.findById(bay.getOutleId());
			bay.setOutletname(findById.get().getName());
		}
		return collect;
	}

	@Override
	public Bay getBaybyId(Integer id) {
		Optional<Bay> bay = bayRepository.findById(id);
		Optional<Outlet> findById = outletRepository.findById(bay.get().getOutleId());
		bay.get().setOutletname(findById.get().getName());
		return bay.get();
	}

	@Override
	public ResponseEntity<String> addBay(Bay bay) {
		String s = bay.getBayNo().toUpperCase();
		if (bayRepository.existsByBayNo(s)) {
			Bay findByName = bayRepository.findByBayNo(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(bay.getBayNo() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Bay save = bayRepository.save(findByName);
				return new ResponseEntity<String>(save.getBayNo() + " Bay re-enable", HttpStatus.CREATED);
			}
		} else {
			bay.setStatus(true);
			bay.setBayNo(s);
			Bay save = bayRepository.save(bay);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity("Bay Added");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save.getBayNo() + " Bay Saved", HttpStatus.CREATED);
		}

	}

	@Override
	public ResponseEntity<String> updateBay(Bay bay) {
		bay.setStatus(true);
		bay.setBayNo(bay.getBayNo().toUpperCase());
		Bay save = bayRepository.save(bay);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Bay Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(save.getBayNo() + " bay Updated  ", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> deleteBay(Integer id) {
		Optional<Bay> findById = bayRepository.findById(id);
		findById.get().setStatus(false);
		bayRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Bay Deleted");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getBayNo() + " Bay Delete Successfully", HttpStatus.CREATED);
	}

	// ============================= Bay End==================

	// ============================= Schedule Start==================.

	@Override
	public List<Schedule> getSchedule() {
		List<Schedule> collect = scheduleRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Schedule getScheduleByid(Integer id) {
		Optional<Schedule> findById = scheduleRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> postSchedule(@Valid Schedule schedule) {
		String s = schedule.getCode().toUpperCase();
		if (scheduleRepository.existsByCode(s)) {
			Schedule findByName = scheduleRepository.findByCode(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(schedule.getDes() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Schedule save = scheduleRepository.save(findByName);
				return new ResponseEntity<String>(save.getDes() + " Payment Mode re-enable",
						HttpStatus.CREATED);
			}

		} else {
			schedule.setStatus(true);
			schedule.setCode(schedule.getCode().toUpperCase());
			schedule.setDes(schedule.getDes().toUpperCase());
			Schedule save = scheduleRepository.save(schedule);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getDes() + " Schedule Saved");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save.getDes() + " Schedule Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updateSchedule(@Valid Schedule schedule) {
		schedule.setDes(schedule.getDes().toUpperCase());
		schedule.setCode(schedule.getCode().toUpperCase());
		schedule.setStatus(true);
		Schedule save = scheduleRepository.save(schedule);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getDes() + " Schedule Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(save.getDes() + " Schedule Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteSchedule(Integer id) {
		Optional<Schedule> findById = scheduleRepository.findById(id);
		findById.get().setStatus(false);
		scheduleRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Delete Schedule");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getDes() + " Schedule Delete Successfully",
				HttpStatus.CREATED);
	}

	// ============================= Schedule Start==================.

	// ============================= Cluster Start==================

	@Override
	public List<Cluster> getCluster() {

		List<Cluster> collect = clusterRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public ResponseEntity<String> addCluster(Cluster cluster) {

		String s = cluster.getDes().toUpperCase();
		if (clusterRepository.existsByDes(s)) {
			Cluster findByName = clusterRepository.findByDes(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(cluster.getDes() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Cluster save = clusterRepository.save(findByName);
				return new ResponseEntity<String>(save.getDes() + " claster re-enable",
						HttpStatus.CREATED);
			}

		} else {
			cluster.setStatus(true);
			cluster.setDes(cluster.getDes().toUpperCase());
			Cluster save = clusterRepository.save(cluster);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getDes() + " Cluster Saved");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save.getDes() + " Cluster Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updateCluster(Cluster cluster) {
		cluster.setDes(cluster.getDes().toUpperCase());
		cluster.setStatus(true);
		Cluster save = clusterRepository.save(cluster);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getDes() + " Cluster Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(save.getDes() + " Cluster Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteCluster(Integer id) {
		Optional<Cluster> findById = clusterRepository.findById(id);
		findById.get().setStatus(false);
		clusterRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Delete Cluster");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getDes() + " Cluster Delete Successfully",
				HttpStatus.CREATED);
	}

	@Override
	public Cluster getClusterByid(Integer id) {
		Optional<Cluster> findById = clusterRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> clusterMappingForStore(ClusterMapping clusterMapping) {
		Optional<Cluster> findById = clusterRepository.findById(clusterMapping.getClusterId());
		clusterRepository.deleteOutlet(clusterMapping.getClusterId());
		List<Outlet> outlet = new ArrayList<>();
		List<Integer> outletId = clusterMapping.getOutletId();
		if (outletId.isEmpty()) {
			return new ResponseEntity<String>("Cluster Mapped with Store Successfully",
					HttpStatus.CREATED);
		} else {
			for (Integer integer : outletId) {
				Optional<Outlet> store = outletRepository.findById(integer);
				outlet.add(store.get());
			}
			findById.get().setOutlet(outlet);
			Cluster save = clusterRepository.save(findById.get());

			return new ResponseEntity<String>("Cluster Mapped with Store Successfully",
					HttpStatus.CREATED);
		}

	}

	@Override
	public ResponseEntity<String> clusterMappingForArticle(ClusterMapping clusterMapping) {
		Optional<Cluster> findById = clusterRepository.findById(clusterMapping.getClusterId());
		clusterRepository.deleteArticle(clusterMapping.getClusterId());
		List<Articles> article = new ArrayList<>();
		List<Integer> articleId = clusterMapping.getArticleId();
		if (articleId.isEmpty()) {
			return new ResponseEntity<String>("Cluster Mapped with Article",
					HttpStatus.CREATED);
		} else {
			for (Integer integer : articleId) {
				Optional<Articles> art = articlesRepository.findById(integer);
				article.add(art.get());
			}
			findById.get().setArticle(article);
			Cluster save = clusterRepository.save(findById.get());
			return new ResponseEntity<String>("Cluster Mapped with  Article",
					HttpStatus.CREATED);
		}

	}

	@Override
	public ClusterMapping fetchClusterMappingForStore(Integer id) {
		Optional<Cluster> findById = clusterRepository.findById(id);
		ClusterMapping cm = new ClusterMapping();
		cm.setClusterId(findById.get().getId());
		List<Integer> out = new ArrayList<>();
		List<Outlet> outlet = findById.get().getOutlet();
		for (Outlet outlet2 : outlet) {
			out.add(outlet2.getId());
		}
		cm.setOutletId(out);
		return cm;
	}

	@Override
	public ClusterMapping fetchClusterMappingForArticle(Integer id) {
		Optional<Cluster> findById = clusterRepository.findById(id);
		ClusterMapping cm = new ClusterMapping();
		cm.setClusterId(findById.get().getId());
		List<Articles> article = findById.get().getArticle();
		List<Integer> art = new ArrayList<>();
		for (Articles articles : article) {
			art.add(articles.getId());
		}
		cm.setArticleId(art);
		return cm;
	}
	// ============================= Cluster Start==================

	// ============================= City Start==================

	@Override
	public  ResponseEntity<?> getCitybyPincode(Integer pin) {
		List<City> findByPinCode = cityRepository.findByPincode(pin);
		if(findByPinCode.isEmpty()){
			return new ResponseEntity<String>("No Data found against this pin code. Please Contact Admin",HttpStatus.BAD_REQUEST);
			
		}
		else{
			return new ResponseEntity<List<City>>(findByPinCode,HttpStatus.OK);
		}
		
	}

	@Override
	public ResponseEntity<String> addCity(@Valid City city) {
		cityRepository.save(city);
		return new ResponseEntity<String>("NEW City Entered Successfully",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateCity(@Valid City city) {
		cityRepository.save(city);
		return new ResponseEntity<String>("City Details Updated Successfully",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCitybyGlobalSearch(String keyword) {
		List<City> findByPinCode = cityRepository.searchByKeyword(keyword);
		if(findByPinCode.isEmpty()){
			return new ResponseEntity<String>("No Data found against this pin code. Please Contact Admin",HttpStatus.BAD_REQUEST);
		}
		else{
			return new ResponseEntity<List<City>>(findByPinCode,HttpStatus.OK);
		}
	}

	@Override
	public Boolean pincodeAvailability(Integer pin) {
		return cityRepository.existsByPincode(pin);
	}



	// ============================= City End================== //
	
	
	// =================================Terms Of Trade Creation ========================//
	
	 
	@Override
	public ResponseEntity<String> addSaveTotCreation(TotHeader totHeader) {
		totHeader.setStatus(true);
		// totHeader.setValidFrom(totHeader.getValidFrom());
		// totHeader.setValidTo(totHeader.getValidTo());
	    totHeaderRepository.save(totHeader);
	
	
		return new ResponseEntity<String>("New Terms Of Trade Creation Entered Successfully",HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> EditTotCreation(@Valid TotHeader totHeader) {
		totHeader.setStatus(true);
		return new ResponseEntity<String>("New Terms Of Trade Creation Entered Successfully",HttpStatus.OK);
	}
	

	
	@Override
	public TotHeader getTotCreationById(Integer id) {
		Optional<TotHeader>findById = totHeaderRepository.findById(id);
		return findById.get();
	}
	

	
	@Override
	public ResponseEntity<String> deleteTotCreation(Integer id) {
		Optional<TotHeader>findById = totHeaderRepository.findById(id);
		TotHeader totHeader = findById.get();
		totHeader.setStatus(false);
		totHeaderRepository.save(totHeader);
		return new ResponseEntity<String>(findById.get().getValidFor() + "Terms Of Trade Creation Deleted Successfully",HttpStatus.OK);
	}
	
	@Override
	public List<VendorProductHeader> getArticlesByVendorForToT(Integer vendorId) {
		// TODO Auto-generated method stub
		return vendorProductHeaderRepository.getArticleByVendorIdForToT(vendorId);
	}

	@Override
	public List<TotHeader> getTotCreation() {
		List<TotHeader> collect = totHeaderRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		
		/*
		 * for (TotHeader totHeader : collect) { Optional<TotDetail> totDetail =
		 * totDetailRepository.findById(totHeader.getDtl_id());
		 * 
		 * totHeader.setOffInvoice(totDetail.get().getOffInvoice());
		 * //assetRegistration.setAssetsTypeName(assetstype.get().getType()); }
		 */
		return collect ;
	}
	
	@Override
	public List<Tot> getMasterValidFor() {
		// TODO Auto-generated method stub
		return totRepository.findAll();
	}
	

	// =============================== Terms Of Trade Master ================== //
	
	/*
	 * @Override public ResponseEntity<String> postViewPrivilege(@Valid
	 * ViewPrivilege viewPrivilege) {
	 * 
	 * @Valid ViewPrivilege save = viewPrivilegeRepository.save(viewPrivilege);
	 * return new ResponseEntity<String>("Data Saved", HttpStatus.CREATED); }
	 */
	
	
	@Override
	public ResponseEntity<String> addTot(Tot tot) {
		
			tot.setStatus(true);
			System.out.println("+++++++++++++++hellooooooooooooodsgdsds++++++++++++++"+tot);
	        
			
	        Tot save = totRepository.save(tot);



	        return new ResponseEntity<String>(" ToT Setup Successfully Updated", HttpStatus.CREATED);
	    }
	

	@Override
	public ResponseEntity<String> updateTot(Tot tot) {
		tot.setParticulars(tot.getParticulars());
		Tot save = totRepository.save(tot);
		return new ResponseEntity<String>(save.getParticulars() + "Terms Of Trade Updated",HttpStatus.CREATED);
	}


	

	@Override
	public Tot getTotById(Integer id) {
		Optional<Tot> findById = totRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> deleteTot(Integer id) {
		Optional<Tot>findById = totRepository.findById(id);
		Tot tot = findById.get();
		tot.setStatus(false);
		totRepository.save(tot);
		return new ResponseEntity<String>(findById.get().getParticulars() + "Terms Of Trade Deleted Successfully",HttpStatus.OK);
	}

	@Override
	public List<Tot> getTot() {
		List<Tot> collect = totRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	
	}

	@Override
	public List<Tot> getParticulars() {
		// TODO Auto-generated method stub
		return null;
	}




	
	// ======================= Terms Of Trade Creation ==========================//
	


	






}
