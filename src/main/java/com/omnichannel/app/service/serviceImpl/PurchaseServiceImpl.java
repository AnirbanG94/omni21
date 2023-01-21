package com.omnichannel.app.service.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.omnichannel.app.model.DTO.POBILL.PODetails;
import com.omnichannel.app.model.DTO.POBILL.POHeader;
import com.omnichannel.app.model.admin.ApprovalSetup;
import com.omnichannel.app.model.admin.Company;
import com.omnichannel.app.model.admin.IdentificationSetup;
import com.omnichannel.app.model.admin.Transactionlog;
import com.omnichannel.app.model.master.Country;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.master.State;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.model.product.UOM;
import com.omnichannel.app.model.purchase.PurchaseBillDetails;
import com.omnichannel.app.model.purchase.PurchaseBillHeader;
import com.omnichannel.app.model.purchase.PurchaseOrderDetails;
import com.omnichannel.app.model.purchase.PurchaseOrderHeader;
import com.omnichannel.app.model.purchase.PurchaseTerms;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.model.vendor.VendorRegistreation;
import com.omnichannel.app.repository.admin.ApprovalSetupRepository;
import com.omnichannel.app.repository.admin.CompanyRepository;
import com.omnichannel.app.repository.admin.IdentificationSetupRepository;
import com.omnichannel.app.repository.admin.TransactionlogRepository;
import com.omnichannel.app.repository.master.CountryRepository;
import com.omnichannel.app.repository.master.LocationRepository;
import com.omnichannel.app.repository.master.OutletRepository;
import com.omnichannel.app.repository.master.StateRepository;
import com.omnichannel.app.repository.product.ArticlesRepository;
import com.omnichannel.app.repository.product.UOMRepository;
import com.omnichannel.app.repository.purchase.PurchaseBillDetailsRepository;
import com.omnichannel.app.repository.purchase.PurchaseBillHeaderRepository;
import com.omnichannel.app.repository.purchase.PurchaseOrderDetailsRepository;
import com.omnichannel.app.repository.purchase.PurchaseOrderHeaderRepository;
import com.omnichannel.app.repository.purchase.PurchaseTermsRepository;
import com.omnichannel.app.repository.usermanagement.UserRepository;
import com.omnichannel.app.repository.usermanagement.ViewPrivilegeRepository;
import com.omnichannel.app.repository.vendor.VendorProductDetailsRepository;
import com.omnichannel.app.repository.vendor.VendorProductHeaderRepository;
import com.omnichannel.app.repository.vendor.VendorRegistreationRepository;
import com.omnichannel.app.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

	@Autowired
	PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

	@Autowired
	PurchaseBillHeaderRepository purchaseBillHeaderRepository;

	@Autowired
	PurchaseBillDetailsRepository purchaseBillDetailsRepository;

	@Autowired
	VendorProductHeaderRepository vendorProductHeaderRepository;

	@Autowired
	VendorProductDetailsRepository vendorProductDetailsRepository;

	@Autowired
	PurchaseTermsRepository purchaseTermsRepository;

	@Autowired
	TransactionlogRepository transactionlogRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	OutletRepository outletRepository;

	@Autowired
	VendorRegistreationRepository vendorRegistreationRepository;

	@Autowired
	ArticlesRepository articlesRepository;

	@Autowired
	UOMRepository uOMRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ViewPrivilegeRepository viewPrivilegeRepository;

	@Autowired
	ApprovalSetupRepository approvalSetupRepository;

	@Autowired
	CountryRepository countryRepository;
	@Autowired
	StateRepository stateRepository;
	@Autowired
	LocationRepository locationRepository;

	@Autowired
	IdentificationSetupRepository identificationSetupRepository;

	// ============================= Purchase Order Start==================

	@Override
	public List<PurchaseOrderHeader> getDetailsPurchaseOrder() {
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = date1.withDayOfMonth(1);

		String fromDate = date2.toString();
		String toDate = date1.toString();

		List<PurchaseOrderHeader> collect = purchaseOrderHeaderRepository.findPOByDate(fromDate, toDate).stream()
				.filter(t -> t.getActive().equals(true)).collect(Collectors.toList());
		for (PurchaseOrderHeader purchaseOrderHeader : collect) {
			List<PurchaseOrderDetails> details = purchaseOrderHeader.getDetails();
			details.removeIf(t -> t.getActive().equals(false));
			Optional<Company> company = companyRepository.findById(purchaseOrderHeader.getCompanyId());
			Optional<Outlet> outlet = outletRepository.findById(purchaseOrderHeader.getOutletId());
			Optional<VendorRegistreation> vendor = vendorRegistreationRepository
					.findById(purchaseOrderHeader.getVendorId());
			purchaseOrderHeader.setCompany(company.get());
			purchaseOrderHeader.setVendorRegistreation(vendor.get());
			purchaseOrderHeader.setOutlet(outlet.get());

			for (PurchaseOrderDetails purchaseOrderDetails : purchaseOrderHeader.getDetails()) {
				Optional<Articles> article = articlesRepository.findById(purchaseOrderDetails.getArticleId());
				Optional<UOM> uom = uOMRepository.findById(purchaseOrderDetails.getUomId());
				purchaseOrderDetails.setArticles(article.get());
				purchaseOrderDetails.setUomName(uom.get().getName());
			}
		}

		return collect;
	}

	@Override
	public List<PurchaseOrderHeader> getPurchaseOrder() {

		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = true;
		for (GrantedAuthority grantedAuthority : auth) {
			// System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_VENDOR")) {
				Roleadmin = false;
			}
		}
		if (Roleadmin) {

			List<PurchaseOrderHeader> collect = purchaseOrderHeaderRepository.findAll().stream()
					.filter(t -> t.getActive().equals(true)).collect(Collectors.toList());
			for (PurchaseOrderHeader purchaseOrderHeader : collect) {
				List<PurchaseOrderDetails> details = purchaseOrderHeader.getDetails();
				details.removeIf(t -> t.getActive().equals(false));

				Optional<Company> com = companyRepository.findById(purchaseOrderHeader.getCompanyId());
				Optional<Location> loc1 = locationRepository.findById(com.get().getLocationid());
				com.get().setLocationname(loc1.get().getName());
				Optional<State> state1 = stateRepository.findById(com.get().getStateid());
				com.get().setStatename(state1.get().getState());
				Optional<Country> country1 = countryRepository.findById(com.get().getCountrycode());
				com.get().setCountryname(country1.get().getName());
				purchaseOrderHeader.setCompany(com.get());

				Optional<Outlet> out = outletRepository.findById(purchaseOrderHeader.getOutletId());
				Optional<Location> loc2 = locationRepository.findById(out.get().getLocationid());
				out.get().setLocationname(loc2.get().getName());
				Optional<State> state2 = stateRepository.findById(out.get().getStateid());
				out.get().setStatename(state2.get().getState());
				Optional<Country> country2 = countryRepository.findById(out.get().getCountrycode());
				out.get().setCountryname(country2.get().getName());
				purchaseOrderHeader.setOutlet(out.get());

				Optional<VendorRegistreation> vendor = vendorRegistreationRepository
						.findById(purchaseOrderHeader.getVendorId());
				Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
				Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
				Optional<State> state = stateRepository.findById(vendor.get().getStateid());
				vendor.get().setLocationname(loction.get().getName());
				vendor.get().setStatename(state.get().getState());
				vendor.get().setCountryname(country.get().getName());
				purchaseOrderHeader.setVendorRegistreation(vendor.get());
			}
			return collect;

		} else {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<User> findByUsername = userRepository.findByUsername(name);
			List<PurchaseOrderHeader> collect = purchaseOrderHeaderRepository.findAll().stream().filter(
					t -> t.getActive().equals(true) && t.getVendorId().equals(findByUsername.get().getVendorId()))
					.collect(Collectors.toList());

			for (PurchaseOrderHeader purchaseOrderHeader : collect) {
				List<PurchaseOrderDetails> details = purchaseOrderHeader.getDetails();
				details.removeIf(t -> t.getActive().equals(false));

				Optional<Company> com = companyRepository.findById(purchaseOrderHeader.getCompanyId());
				Optional<Location> loc1 = locationRepository.findById(com.get().getLocationid());
				com.get().setLocationname(loc1.get().getName());
				Optional<State> state1 = stateRepository.findById(com.get().getStateid());
				com.get().setStatename(state1.get().getState());
				Optional<Country> country1 = countryRepository.findById(com.get().getCountrycode());
				com.get().setCountryname(country1.get().getName());
				purchaseOrderHeader.setCompany(com.get());

				Optional<Outlet> out = outletRepository.findById(purchaseOrderHeader.getOutletId());
				Optional<Location> loc2 = locationRepository.findById(out.get().getLocationid());
				out.get().setLocationname(loc2.get().getName());
				Optional<State> state2 = stateRepository.findById(out.get().getStateid());
				out.get().setStatename(state2.get().getState());
				Optional<Country> country2 = countryRepository.findById(out.get().getCountrycode());
				out.get().setCountryname(country2.get().getName());
				purchaseOrderHeader.setOutlet(out.get());

				Optional<VendorRegistreation> vendor = vendorRegistreationRepository
						.findById(purchaseOrderHeader.getVendorId());
				Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
				Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
				Optional<State> state = stateRepository.findById(vendor.get().getStateid());
				vendor.get().setLocationname(loction.get().getName());
				vendor.get().setStatename(state.get().getState());
				vendor.get().setCountryname(country.get().getName());
				purchaseOrderHeader.setVendorRegistreation(vendor.get());
			}
			return collect;

		}

	}

	@Override
	public List<PurchaseOrderHeader> getPurchaseOrderByMonth(String fromDate, String todate) {
		List<PurchaseOrderHeader> collect = purchaseOrderHeaderRepository.findPOByDate(fromDate, todate).stream()
				.filter(t -> t.getActive().equals(true)).collect(Collectors.toList());
		for (PurchaseOrderHeader purchaseOrderHeader : collect) {
			List<PurchaseOrderDetails> details = purchaseOrderHeader.getDetails();
			details.removeIf(t -> t.getActive().equals(false));
			Optional<Company> company = companyRepository.findById(purchaseOrderHeader.getCompanyId());
			Optional<Outlet> outlet = outletRepository.findById(purchaseOrderHeader.getOutletId());
			Optional<VendorRegistreation> vendor = vendorRegistreationRepository
					.findById(purchaseOrderHeader.getVendorId());
			purchaseOrderHeader.setCompany(company.get());
			purchaseOrderHeader.setVendorRegistreation(vendor.get());
			purchaseOrderHeader.setOutlet(outlet.get());

			for (PurchaseOrderDetails purchaseOrderDetails : purchaseOrderHeader.getDetails()) {
				Optional<Articles> article = articlesRepository.findById(purchaseOrderDetails.getArticleId());
				Optional<UOM> uom = uOMRepository.findById(purchaseOrderDetails.getUomId());
				purchaseOrderDetails.setArticles(article.get());
				purchaseOrderDetails.setUomName(uom.get().getName());
			}
		}

		return collect;
	}

	@Override
	public POHeader getPurchaseOrderByid(Integer id) {
		POHeader hed = new POHeader();
		List<PODetails> dets = new ArrayList<>();
		Optional<PurchaseOrderHeader> findById = purchaseOrderHeaderRepository.findById(id);
		List<PurchaseOrderDetails> details = findById.get().getDetails();
		for (PurchaseOrderDetails purchaseOrderDetails : details) {
			if (purchaseOrderDetails.getActive()) {
				PODetails det = new PODetails();
				det.setPoDetailId(purchaseOrderDetails.getId());
				det.setArticleId(purchaseOrderDetails.getArticleId());
				det.setUomId(purchaseOrderDetails.getUomId());
				det.setHsnCD(purchaseOrderDetails.getHsnCd());
				det.setEanCD(purchaseOrderDetails.getEanCd());
				det.setMrp(purchaseOrderDetails.getMrp());
				det.setQuantity(purchaseOrderDetails.getQty());
				det.setCost(purchaseOrderDetails.getCp());
				det.setBasic_cost(purchaseOrderDetails.getBasicAmt());
				det.setGst_tax(purchaseOrderDetails.getSgstAmt());
				det.setSgst(purchaseOrderDetails.getSgstAmt());
				det.setCgst(purchaseOrderDetails.getCgstAmt());
				det.setIgst(purchaseOrderDetails.getIgstAmt());
				det.setNet_amount(purchaseOrderDetails.getNetAmt());

				Optional<Articles> findById2 = articlesRepository.findById(purchaseOrderDetails.getArticleId());
				det.setArticleName(findById2.get().getName());

				Optional<VendorProductHeader> venproduct = vendorProductHeaderRepository
						.findByArticleIdAndVendorIdAndEanCD(purchaseOrderDetails.getArticleId(),
								findById.get().getVendorId(), purchaseOrderDetails.getEanCd());

				det.setDetails(venproduct.get().getDetails());

				dets.add(det);
			}

		}

		hed.setPoHeaderId(findById.get().getId());
		hed.setDeliv_dt(findById.get().getDelvDt());
		hed.setExp_dt(findById.get().getExpiryDt());
		hed.setBilling_id(findById.get().getCompanyId());
		hed.setShip_id(findById.get().getOutletId());
		hed.setVendorId(findById.get().getVendorId());
		hed.setTotBasicAmt(findById.get().getTotBasicAmt());
		hed.setTotCgstAmt(findById.get().getTotCgstAmt());
		hed.setTotSgstAmt(findById.get().getTotSgstAmt());
		hed.setTotIgstAmt(findById.get().getTotIgstAmt());
		hed.setTotNetAmt(findById.get().getTotNetAmt());
		hed.setNote(findById.get().getNote());
		hed.setPaymTerms(findById.get().getPaymTerms());
		hed.setPoDt(findById.get().getPoDt());
		hed.setPoNo(findById.get().getPoNo());
		hed.setProduct_details(dets);
		return hed;
	}

	@Override
	public List<PurchaseOrderHeader> getPurchaseOrderByOutletId(Integer id) {
		List<PurchaseOrderHeader> findByOutletIdAndStatus = purchaseOrderHeaderRepository
				.findByOutletIdAndStatusAndActive(id,
						"A", true);
		for (PurchaseOrderHeader purchaseOrderHeader : findByOutletIdAndStatus) {
			purchaseOrderHeader.setVendorName(
					vendorRegistreationRepository.findById(purchaseOrderHeader.getVendorId()).get().getName());
			purchaseOrderHeader
					.setOutletName(outletRepository.findById(purchaseOrderHeader.getOutletId()).get().getName());
		}

		return findByOutletIdAndStatus;
	}

	@Override
	public List<PurchaseOrderHeader> getPurchaseOrderByVendorId(Integer id) {
		return purchaseOrderHeaderRepository.findPOByVendorId(id);
	}

	@Override
	public PurchaseOrderHeader getPurchaseOrderByidForView(Integer id) {
		Optional<PurchaseOrderHeader> product = purchaseOrderHeaderRepository.findById(id);

		product.get().getDetails().removeIf(t -> t.getActive().equals(false));

		List<PurchaseOrderDetails> details = product.get().getDetails();
		for (PurchaseOrderDetails purchaseOrderDetails : details) {
			Optional<Articles> findById2 = articlesRepository.findById(purchaseOrderDetails.getArticleId());
			purchaseOrderDetails.setArticleName(findById2.get().getName());
			Optional<UOM> findById = uOMRepository.findById(purchaseOrderDetails.getUomId());
			purchaseOrderDetails.setUomName(findById.get().getShortName());

		}

		Optional<Company> com = companyRepository.findById(product.get().getCompanyId());
		Optional<Location> loc1 = locationRepository.findById(com.get().getLocationid());
		com.get().setLocationname(loc1.get().getName());
		Optional<State> state1 = stateRepository.findById(com.get().getStateid());
		com.get().setStatename(state1.get().getState());
		Optional<Country> country1 = countryRepository.findById(com.get().getCountrycode());
		com.get().setCountryname(country1.get().getName());
		product.get().setCompany(com.get());

		Optional<Outlet> out = outletRepository.findById(product.get().getOutletId());
		Optional<Location> loc2 = locationRepository.findById(out.get().getLocationid());
		out.get().setLocationname(loc2.get().getName());
		Optional<State> state2 = stateRepository.findById(out.get().getStateid());
		out.get().setStatename(state2.get().getState());
		Optional<Country> country2 = countryRepository.findById(out.get().getCountrycode());
		out.get().setCountryname(country2.get().getName());
		product.get().setOutlet(out.get());

		Optional<VendorRegistreation> vendor = vendorRegistreationRepository.findById(product.get().getVendorId());
		Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
		Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
		Optional<State> state = stateRepository.findById(vendor.get().getStateid());
		vendor.get().setLocationname(loction.get().getName());
		vendor.get().setStatename(state.get().getState());
		vendor.get().setCountryname(country.get().getName());
		product.get().setVendorRegistreation(vendor.get());

		return product.get();
	}

	@Override
	public PurchaseOrderHeader getPurchaseOrderByidForAsset(Integer id) {
		PurchaseOrderHeader product = purchaseOrderHeaderRepository.findById(id).get();
		product.setVendorName(vendorRegistreationRepository.findById(product.getVendorId()).get().getName());
		product.setOutletName(outletRepository.findById(product.getOutletId()).get().getName());

		for (PurchaseOrderDetails purchaseOrderDetails : product.getDetails()) {
			Optional<Articles> findById2 = articlesRepository.findById(purchaseOrderDetails.getArticleId());
			purchaseOrderDetails.setArticleName(findById2.get().getName());
			Optional<UOM> findById = uOMRepository.findById(purchaseOrderDetails.getUomId());
			purchaseOrderDetails.setUomName(findById.get().getShortName());
		}

		return product;
	}

	@Override
	public ResponseEntity<String> postPurchaseOrder(@Valid POHeader pOHeader) {
		PurchaseOrderHeader header = new PurchaseOrderHeader();
		LocalDate ld = LocalDate.now();
		header.setPoDt(ld.toString());
		header.setVendorId(pOHeader.getVendorId());
		header.setCompanyId(pOHeader.getBilling_id());
		header.setOutletId(pOHeader.getShip_id());
		header.setDelvDt(pOHeader.getDeliv_dt());
		header.setExpiryDt(pOHeader.getExp_dt());
		header.setPaymTerms(pOHeader.getPaymTerms());
		header.setNote(pOHeader.getNote());
		header.setTotBasicAmt(pOHeader.getTotBasicAmt());
		header.setTotSgstAmt(pOHeader.getTotSgstAmt());
		header.setTotCgstAmt(pOHeader.getTotCgstAmt());
		header.setTotIgstAmt(pOHeader.getTotIgstAmt());
		header.setTotNetAmt(pOHeader.getTotNetAmt());
		header.setStatus("O");
		header.setActive(true);
		header.setGrnFlag(true);
		header.setGdnFlag(true);
		@Valid
		PurchaseOrderHeader save = purchaseOrderHeaderRepository.save(header);

		List<PODetails> details = pOHeader.getProduct_details();
		for (PODetails poDetails : details) {
			PurchaseOrderDetails det = new PurchaseOrderDetails();
			det.setArticleId(poDetails.getArticleId());
			det.setHsnCd(poDetails.getHsnCD());
			det.setEanCd(poDetails.getEanCD());
			det.setUomId(poDetails.getUomId());
			det.setMrp(poDetails.getMrp());
			det.setQty(poDetails.getQuantity());
			det.setCp(poDetails.getCost());
			det.setBasicAmt(poDetails.getBasic_cost());
			det.setSgstAmt(poDetails.getSgst());
			det.setCgstAmt(poDetails.getCgst());
			det.setIgstAmt(poDetails.getIgst());
			det.setNetAmt(poDetails.getNet_amount());
			det.setTaxPer(poDetails.getGst_tax());
			det.setActive(true);
			det.setHeader(save);
			PurchaseOrderDetails save2 = purchaseOrderDetailsRepository.save(det);
		}
		return new ResponseEntity<String>("Purchase Order Created Successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> updatePurchaseOrder(@Valid POHeader pOHeader) {
		Optional<PurchaseOrderHeader> head = purchaseOrderHeaderRepository.findById(pOHeader.getPoHeaderId());

		PurchaseOrderHeader header = head.get();
		header.setVendorId(pOHeader.getVendorId());
		header.setCompanyId(pOHeader.getBilling_id());
		header.setOutletId(pOHeader.getShip_id());
		header.setDelvDt(pOHeader.getDeliv_dt());
		header.setExpiryDt(pOHeader.getExp_dt());
		header.setPaymTerms(pOHeader.getPaymTerms());
		header.setNote(pOHeader.getNote());
		header.setTotBasicAmt(pOHeader.getTotBasicAmt());
		header.setTotSgstAmt(pOHeader.getTotSgstAmt());
		header.setTotCgstAmt(pOHeader.getTotCgstAmt());
		header.setTotIgstAmt(pOHeader.getTotIgstAmt());
		header.setTotNetAmt(pOHeader.getTotNetAmt());
		header.setStatus("O");
		header.setActive(true);
		header.setGrnFlag(true);
		header.setGdnFlag(true);
		@Valid
		PurchaseOrderHeader save = purchaseOrderHeaderRepository.save(header);

		Optional<PurchaseOrderHeader> findById = purchaseOrderHeaderRepository.findById(save.getId());

		List<PurchaseOrderDetails> details2 = findById.get().getDetails();
		for (PurchaseOrderDetails purchaseOrderDetails : details2) {
			purchaseOrderDetails.setActive(false);
			PurchaseOrderDetails save2 = purchaseOrderDetailsRepository.save(purchaseOrderDetails);
		}

		List<PODetails> details = pOHeader.getProduct_details();
		for (PODetails poDetails : details) {
			PurchaseOrderDetails det = new PurchaseOrderDetails();
			det.setId(poDetails.getPoDetailId());
			det.setArticleId(poDetails.getArticleId());
			det.setHsnCd(poDetails.getHsnCD());
			det.setEanCd(poDetails.getEanCD());
			det.setUomId(poDetails.getUomId());
			det.setMrp(poDetails.getMrp());
			det.setQty(poDetails.getQuantity());
			det.setCp(poDetails.getCost());
			det.setBasicAmt(poDetails.getBasic_cost());
			det.setSgstAmt(poDetails.getSgst());
			det.setCgstAmt(poDetails.getCgst());
			det.setIgstAmt(poDetails.getIgst());
			det.setNetAmt(poDetails.getNet_amount());
			det.setActive(true);
			det.setHeader(save);
			PurchaseOrderDetails save2 = purchaseOrderDetailsRepository.save(det);
		}
		return new ResponseEntity<String>("Purchase Order Updated Successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deletePurchaseOrder(Integer id) {
		Optional<PurchaseOrderHeader> findById = purchaseOrderHeaderRepository.findById(id);
		findById.get().setActive(false);
		findById.get().setStatus("D");
		PurchaseOrderHeader save = purchaseOrderHeaderRepository.save(findById.get());
		return new ResponseEntity<String>(save.getPoNo() + " Purchase Order Deleted Successfully", HttpStatus.CREATED);
	}

	// ============================= Purchase Order Start==================

	// ============================= Purchase Order Approval Start==================

	@Override
	public List<PurchaseOrderHeader> getPurchaseOrderForApproval() {
		List<PurchaseOrderHeader> collect = new ArrayList<>();
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
			collect = purchaseOrderHeaderRepository.findForApproval();

		} else {
			String names = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Purchase Order Approval");
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
				System.out.println("Not authorize for approval");
			} else if (level.equals(1)) {
				collect = purchaseOrderHeaderRepository.findForLevelApproval("O");
			} else {
				Integer actualLevel = level - 1;
				String status = actualLevel.toString();
				collect = purchaseOrderHeaderRepository.findForLevelApproval(status);
			}
		}
		return collect;
	}

	@Override
	public ResponseEntity<String> approvePurchaseOrder(Integer id) {
		// authority finder
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = false;
		for (GrantedAuthority grantedAuthority : auth) {
			if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
				Roleadmin = true;
			}
		}
		// serial number generator
		IdentificationSetup identificationSetup = identificationSetupRepository.findByMenu(52).get();
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

		if (Roleadmin) {
			Optional<PurchaseOrderHeader> po = purchaseOrderHeaderRepository.findById(id);

			if (po.get().getPoNo() != null) {
				System.out.println("ALREADY ASSIGN");
			} else {
				po.get().setPoNo(filString);
			}
			po.get().setStatus("A");
			purchaseOrderHeaderRepository.save(po.get());
			Integer nextVal = val + 1;
			identificationSetup.setSlno(nextVal.toString());
			identificationSetupRepository.save(identificationSetup);
			return new ResponseEntity<String>("Purchase Order Approved By Admin!!", HttpStatus.CREATED);

		} else {
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Purchase Order Approval");
			List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
			Integer level = findByManuid.size();
			Integer actualLevel = level - 1;
			String status = actualLevel.toString();
			Optional<PurchaseOrderHeader> po = purchaseOrderHeaderRepository.findById(id);
			if (po.get().getStatus().equals(status)) {

				if (po.get().getPoNo() != null) {
					System.out.println("ALREADY ASSIGN");
				} else {
					po.get().setPoNo(filString);
				}

				po.get().setStatus("A");
				purchaseOrderHeaderRepository.save(po.get());
				Integer nextVal = val + 1;
				identificationSetup.setSlno(nextVal.toString());
				identificationSetupRepository.save(identificationSetup);

				return new ResponseEntity<String>("Purchase Order Final Approved!!", HttpStatus.CREATED);
			} else if (po.get().getStatus().equals("O")) {
				po.get().setStatus("1");
				purchaseOrderHeaderRepository.save(po.get());
				return new ResponseEntity<String>("Purchase Order Approved for next approval!!", HttpStatus.CREATED);
			} else {
				Integer temp = Integer.parseInt(po.get().getStatus());
				Integer actualTemp = temp + 1;
				String s = actualTemp.toString();
				po.get().setStatus(s);
				purchaseOrderHeaderRepository.save(po.get());
				return new ResponseEntity<String>("Vendor Approved for next approval!!", HttpStatus.CREATED);
			}
		}
	}

	@Override
	public ResponseEntity<String> disapprovePurchaseOrder(Integer id, String reason) {
		Optional<PurchaseOrderHeader> po = purchaseOrderHeaderRepository.findById(id);
		po.get().setDisapproveReason(reason);
		po.get().setStatus("D");
		purchaseOrderHeaderRepository.save(po.get());
		return new ResponseEntity<String>("Vendor Disapproved!!", HttpStatus.CREATED);
	}

	// ============================= Purchase Order Approval End==================

	// ============================= Purchase Bill Start==================

	@Override
	public List<PurchaseBillHeader> getPurchaseBill() {
		List<PurchaseBillHeader> collect = purchaseBillHeaderRepository.findAll().stream()
				.filter(t -> t.getActive().equals(true)).collect(Collectors.toList());
		for (PurchaseBillHeader purchaseBillHeader : collect) {
			List<PurchaseBillDetails> details = purchaseBillHeader.getDetails();
			details.removeIf(t -> t.getActive().equals(false));

			Optional<Company> com = companyRepository.findById(purchaseBillHeader.getCompanyId());
			Optional<Location> loc1 = locationRepository.findById(com.get().getLocationid());
			com.get().setLocationname(loc1.get().getName());
			Optional<State> state1 = stateRepository.findById(com.get().getStateid());
			com.get().setStatename(state1.get().getState());
			Optional<Country> country1 = countryRepository.findById(com.get().getCountrycode());
			com.get().setCountryname(country1.get().getName());
			purchaseBillHeader.setCompany(com.get());

			Optional<Outlet> out = outletRepository.findById(purchaseBillHeader.getOutlateId());
			Optional<Location> loc2 = locationRepository.findById(out.get().getLocationid());
			out.get().setLocationname(loc2.get().getName());
			Optional<State> state2 = stateRepository.findById(out.get().getStateid());
			out.get().setStatename(state2.get().getState());
			Optional<Country> country2 = countryRepository.findById(out.get().getCountrycode());
			out.get().setCountryname(country2.get().getName());
			purchaseBillHeader.setOutlet(out.get());

			Optional<VendorRegistreation> vendor = vendorRegistreationRepository
					.findById(purchaseBillHeader.getVendorId());
			Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
			Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
			Optional<State> state = stateRepository.findById(vendor.get().getStateid());
			vendor.get().setLocationname(loction.get().getName());
			vendor.get().setStatename(state.get().getState());
			vendor.get().setCountryname(country.get().getName());
			purchaseBillHeader.setVendorRegistreation(vendor.get());
		}
		return collect;
	}

	@Override
	public List<PurchaseOrderHeader> getPurchaseOrderForPurchaseBill() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBillHeader> getDetailsPurchaseBill() {
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = date1.withDayOfMonth(1);

		String fromDate = date2.toString();
		String toDate = date1.toString();

		List<PurchaseBillHeader> collect = purchaseBillHeaderRepository.findBillByDate(fromDate, toDate).stream()
				.filter(t -> t.getActive().equals(true)).collect(Collectors.toList());
		for (PurchaseBillHeader purchaseBillHeader : collect) {
			Optional<Company> company = companyRepository.findById(purchaseBillHeader.getCompanyId());
			Optional<Outlet> outlet = outletRepository.findById(purchaseBillHeader.getOutlateId());
			Optional<VendorRegistreation> vendor = vendorRegistreationRepository
					.findById(purchaseBillHeader.getVendorId());
			purchaseBillHeader.setCompany(company.get());
			purchaseBillHeader.setVendorRegistreation(vendor.get());
			purchaseBillHeader.setOutlet(outlet.get());

			for (PurchaseBillDetails purchaseBillDetails : purchaseBillHeader.getDetails()) {
				Optional<Articles> article = articlesRepository.findById(purchaseBillDetails.getArticleId());
				Optional<UOM> uom = uOMRepository.findById(purchaseBillDetails.getUomId());
				purchaseBillDetails.setArticles(article.get());
				purchaseBillDetails.setUomName(uom.get().getName());
			}
		}

		return collect;
	}

	@Override
	public List<PurchaseBillHeader> getPurchaseBillByMonth(String fromDate, String todate) {
		List<PurchaseBillHeader> collect = purchaseBillHeaderRepository.findBillByDate(fromDate, todate).stream()
				.filter(t -> t.getActive().equals(true)).collect(Collectors.toList());
		for (PurchaseBillHeader purchaseBillHeader : collect) {
			Optional<Company> company = companyRepository.findById(purchaseBillHeader.getCompanyId());
			Optional<Outlet> outlet = outletRepository.findById(purchaseBillHeader.getOutlateId());
			Optional<VendorRegistreation> vendor = vendorRegistreationRepository
					.findById(purchaseBillHeader.getVendorId());
			purchaseBillHeader.setCompany(company.get());
			purchaseBillHeader.setVendorRegistreation(vendor.get());
			purchaseBillHeader.setOutlet(outlet.get());

			for (PurchaseBillDetails purchaseBillDetails : purchaseBillHeader.getDetails()) {
				Optional<Articles> article = articlesRepository.findById(purchaseBillDetails.getArticleId());
				Optional<UOM> uom = uOMRepository.findById(purchaseBillDetails.getUomId());
				purchaseBillDetails.setArticles(article.get());
				purchaseBillDetails.setUomName(uom.get().getName());
			}
		}

		return collect;
	}

	@Override
	public PurchaseBillHeader getPurchaseBillByid(Integer id) {
		Optional<PurchaseBillHeader> findById = purchaseBillHeaderRepository.findById(id);
		return findById.get();
	}

	@Override
	public PurchaseBillHeader getPurchaseBillByidForView(Integer id) {
		Optional<PurchaseBillHeader> product = purchaseBillHeaderRepository.findById(id);

		product.get().getDetails().removeIf(t -> t.getActive().equals(false));

		List<PurchaseBillDetails> details = product.get().getDetails();
		for (PurchaseBillDetails purchaseBillDetails : details) {
			Optional<Articles> findById2 = articlesRepository.findById(purchaseBillDetails.getArticleId());
			purchaseBillDetails.setArticleName(findById2.get().getName());
		}

		Optional<Company> com = companyRepository.findById(product.get().getCompanyId());
		Optional<Location> loc1 = locationRepository.findById(com.get().getLocationid());
		com.get().setLocationname(loc1.get().getName());
		Optional<State> state1 = stateRepository.findById(com.get().getStateid());
		com.get().setStatename(state1.get().getState());
		Optional<Country> country1 = countryRepository.findById(com.get().getCountrycode());
		com.get().setCountryname(country1.get().getName());
		product.get().setCompany(com.get());

		Optional<Outlet> out = outletRepository.findById(product.get().getOutlateId());
		Optional<Location> loc2 = locationRepository.findById(out.get().getLocationid());
		out.get().setLocationname(loc2.get().getName());
		Optional<State> state2 = stateRepository.findById(out.get().getStateid());
		out.get().setStatename(state2.get().getState());
		Optional<Country> country2 = countryRepository.findById(out.get().getCountrycode());
		out.get().setCountryname(country2.get().getName());
		product.get().setOutlet(out.get());

		Optional<VendorRegistreation> vendor = vendorRegistreationRepository.findById(product.get().getVendorId());
		Optional<Location> loction = locationRepository.findById(vendor.get().getLocationid());
		Optional<Country> country = countryRepository.findById(vendor.get().getCountrycode());
		Optional<State> state = stateRepository.findById(vendor.get().getStateid());
		vendor.get().setLocationname(loction.get().getName());
		vendor.get().setStatename(state.get().getState());
		vendor.get().setCountryname(country.get().getName());
		product.get().setVendorRegistreation(vendor.get());

		return product.get();
	}

	@Override
	public ResponseEntity<String> postPurchaseBill(@Valid POHeader pOHeader) {

		PurchaseBillHeader header = new PurchaseBillHeader();
		LocalDate ld = LocalDate.now();
		header.setPurcDt(ld.toString());

		IdentificationSetup identificationSetup = identificationSetupRepository.findByMenu(53).get();
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

		header.setPurcNo(filString);

		header.setPoNo(pOHeader.getPoNo());
		header.setPoDt(pOHeader.getPoDt());
		header.setBillDt(pOHeader.getBillDate());
		header.setBillNo(pOHeader.getBillNo());
		header.setVendorId(pOHeader.getVendorId());
		header.setCompanyId(pOHeader.getBilling_id());
		header.setOutlateId(pOHeader.getShip_id());
		header.setTotBasicAmt(pOHeader.getTotBasicAmt());
		header.setTotSgstAmt(pOHeader.getTotSgstAmt());
		header.setTotCgstAmt(pOHeader.getTotCgstAmt());
		header.setTotIgstAmt(pOHeader.getTotIgstAmt());
		header.setTotNetAmt(pOHeader.getTotNetAmt());
		header.setActive(true);
		@Valid
		PurchaseBillHeader save = purchaseBillHeaderRepository.save(header);

		Integer nextVal = val + 1;
		identificationSetup.setSlno(nextVal.toString());
		identificationSetupRepository.save(identificationSetup);

		List<PODetails> details = pOHeader.getProduct_details();
		for (PODetails poDetails : details) {
			PurchaseBillDetails det = new PurchaseBillDetails();
			det.setArticleId(poDetails.getArticleId());
			det.setHsnCd(poDetails.getHsnCD());
			det.setEanCd(poDetails.getEanCD());
			det.setUomId(poDetails.getUomId());
			det.setMrp(poDetails.getMrp());
			det.setQty(poDetails.getQuantity());
			det.setCp(poDetails.getCost());
			det.setBasicAmt(poDetails.getBasic_cost());
			det.setSgstAmt(poDetails.getSgst());
			det.setCgstAmt(poDetails.getCgst());
			det.setIgstAmt(poDetails.getIgst());
			det.setNetAmt(poDetails.getNet_amount());
			det.setTaxPer(poDetails.getGst_tax());
			det.setActive(true);
			det.setHeader(save);
			PurchaseBillDetails save2 = purchaseBillDetailsRepository.save(det);
		}
		return new ResponseEntity<String>(save.getPoNo() + " Purchase Bill Created Successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> updatePurchaseBill(@Valid PurchaseBillHeader purchaseBillHeader) {
		purchaseBillHeader.setActive(true);
		@Valid
		PurchaseBillHeader save = purchaseBillHeaderRepository.save(purchaseBillHeader);

		List<PurchaseBillDetails> pod = purchaseBillHeader.getBillDetails();
		for (PurchaseBillDetails purchaseBillDetails : pod) {
			purchaseBillDetails.setActive(true);
			purchaseBillDetails.setHeader(save);
			purchaseBillDetailsRepository.save(purchaseBillDetails);
		}
		return new ResponseEntity<String>(save.getPoNo() + " Purchase Bill Updated Successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deletePurchaseBill(Integer id) {
		Optional<PurchaseBillHeader> findById = purchaseBillHeaderRepository.findById(id);
		findById.get().setActive(false);
		PurchaseBillHeader save = purchaseBillHeaderRepository.save(findById.get());
		return new ResponseEntity<String>(save.getPoNo() + " Purchase Bill Deleted Successfully", HttpStatus.CREATED);
	}
	// ============================= Purchase Bill End==================

	// ============================= PurchaseTerms Start==================

	@Override
	public List<PurchaseTerms> getPurchaseTerms() {

		List<PurchaseTerms> collect = purchaseTermsRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public PurchaseTerms getPurchaseTermsByid(Integer id) {
		Optional<PurchaseTerms> findById = purchaseTermsRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addPurchaseTerms(PurchaseTerms purchaseTerms) {

		String s = purchaseTerms.getTerms().toUpperCase();
		if (purchaseTermsRepository.existsByTerms(s)) {
			PurchaseTerms findByName = purchaseTermsRepository.findByTerms(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(purchaseTerms.getTerms() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				PurchaseTerms save = purchaseTermsRepository.save(findByName);
				return new ResponseEntity<String>(save.getTerms() + " Purchase Terms re-enable", HttpStatus.CREATED);
			}

		} else {
			purchaseTerms.setStatus(true);
			purchaseTerms.setTerms(purchaseTerms.getTerms().toUpperCase());
			PurchaseTerms save = purchaseTermsRepository.save(purchaseTerms);

			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getTerms() + " Purchase Terms Saved");
			transactionlogRepository.save(trlog);

			return new ResponseEntity<String>(save.getTerms() + " Purchase Terms Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updatePurchaseTerms(PurchaseTerms purchaseTerms) {
		purchaseTerms.setTerms(purchaseTerms.getTerms().toUpperCase());
		purchaseTerms.setStatus(true);
		PurchaseTerms save = purchaseTermsRepository.save(purchaseTerms);

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getTerms() + " PurchaseTerms Updated");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(save.getTerms() + " Purchase Terms Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deletePurchaseTerms(Integer id) {
		Optional<PurchaseTerms> findById = purchaseTermsRepository.findById(id);
		findById.get().setStatus(false);
		purchaseTermsRepository.save(findById.get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("Delete PurchaseTerms");
		transactionlogRepository.save(trlog);

		return new ResponseEntity<String>(findById.get().getTerms() + " Purchase Terms Delete Succesufully",
				HttpStatus.CREATED);
	}

	// ============================= PurchaseTerms Start==================

}
