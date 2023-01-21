package com.omnichannel.app.service.serviceImpl;

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

import com.omnichannel.app.model.DTO.PromoArticle;
import com.omnichannel.app.model.admin.ApprovalSetup;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.model.product.Item;
import com.omnichannel.app.model.promo.GeneralPromo;
import com.omnichannel.app.model.promo.Promo;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.model.vendor.VendorProductDetails;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.model.vendor.VendorRegistreation;
import com.omnichannel.app.repository.admin.ApprovalSetupRepository;
import com.omnichannel.app.repository.master.LocationRepository;
import com.omnichannel.app.repository.product.ArticlesRepository;
import com.omnichannel.app.repository.product.ItemRepository;
import com.omnichannel.app.repository.promo.GeneralPromoRepository;
import com.omnichannel.app.repository.promo.PromoRepository;
import com.omnichannel.app.repository.usermanagement.UserRepository;
import com.omnichannel.app.repository.usermanagement.ViewPrivilegeRepository;
import com.omnichannel.app.repository.vendor.VendorProductHeaderRepository;
import com.omnichannel.app.repository.vendor.VendorRegistreationRepository;
import com.omnichannel.app.service.PromoService;

@Service
public class PromoServiceImpl implements PromoService {
	@Autowired
	PromoRepository promoRepository;
	
	@Autowired
	GeneralPromoRepository generalPromoRepository;

	@Autowired
	VendorRegistreationRepository vendorRegistreationRepository;
	
	@Autowired
	VendorProductHeaderRepository vendorProductHeaderRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	ArticlesRepository articlesRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ViewPrivilegeRepository viewPrivilegeRepository;

	@Autowired
	ApprovalSetupRepository approvalSetupRepository;

	// ============================= Promo Start==================

	@Override
	public List<Promo> getPromoForVendor() {
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Boolean Roleadmin = true;
		for (GrantedAuthority grantedAuthority : auth) {
			System.out.println(grantedAuthority);
			if (grantedAuthority.toString().equals("ROLE_VENDOR")) {
				System.out.println("deactive admin");
				Roleadmin = false;
			}
		}
		if (Roleadmin) {
			List<Promo> collect = promoRepository.findAll().stream()
					.filter(t -> t.getStatus().equals(true) && !t.getVendorId().equals(0)).collect(Collectors.toList());
			for (Promo promo : collect) {
				if (!promo.getVendorId().equals(0)) {
					Optional<VendorRegistreation> findById = vendorRegistreationRepository
							.findById(promo.getVendorId());
					promo.setVenName(findById.get().getName());
				}
				Optional<VendorProductHeader> vendorPro = vendorProductHeaderRepository.findByArticleIdAndVendorId(promo.getArticleId(),promo.getVendorId());
				promo.setArticalName(vendorPro.get().getArticleName());
				promo.setDetails(vendorPro.get().getDetails());
			}
			return collect;
		} else {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println(name);
			Optional<User> findByUsername = userRepository.findByUsername(name);
			System.out.println(findByUsername.get().getVendorId());
			List<Promo> collect = promoRepository.findAll().stream().filter(
					t -> t.getVendorId().equals(findByUsername.get().getVendorId()) && t.getStatus().equals(true))
					.collect(Collectors.toList());

			for (Promo promo : collect) {
				if (!promo.getVendorId().equals(0)) {
					Optional<VendorRegistreation> findById = vendorRegistreationRepository
							.findById(promo.getVendorId());
					promo.setVenName(findById.get().getName());
				}

				Optional<VendorProductHeader> vendorPro = vendorProductHeaderRepository.findByArticleIdAndVendorId(promo.getArticleId(),promo.getVendorId());
				promo.setArticalName(vendorPro.get().getArticleName());
				promo.setDetails(vendorPro.get().getDetails());

			}
			return collect;
		}
	}

	@Override
	public List<Promo> getPromoForCustomer() {
		List<Promo> collect = promoRepository.findAll().stream()
				.filter(t -> t.getStatus().equals(true) && t.getVenApproval().equals("A") || t.getVenApproval().equals("N")  && !t.getSelfApproval().equals("A")&& !t.getSelfApproval().equals("D")).collect(Collectors.toList());
		for (Promo promo : collect) {
			if (!promo.getVendorId().equals(0)) {
				Optional<VendorRegistreation> findById = vendorRegistreationRepository.findById(promo.getVendorId());
				promo.setVenName(findById.get().getName());
			}
			Optional<VendorProductHeader> vendorPro = vendorProductHeaderRepository.findById(promo.getArticleId());
			promo.setArticalName(vendorPro.get().getArticleName());
			promo.setDetails(vendorPro.get().getDetails());
		}
		return collect;
	}

	@Override
	public Promo getPromoByid(Integer id) {
		Optional<Promo> promo = promoRepository.findById(id);
		Optional<VendorProductHeader> vendorPro = vendorProductHeaderRepository.findById(promo.get().getArticleId());
		promo.get().setArticalName(vendorPro.get().getArticleName());
		promo.get().setDetails(vendorPro.get().getDetails());
		return promo.get();
	}

	@Override
	public ResponseEntity<String> postPromoForVendor(@Valid Promo promo) {
	    Optional<VendorRegistreation> vendor = vendorRegistreationRepository.findById(promo.getVendorId());
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> findByUsername = userRepository.findByUsername(name);
		
		if(findByUsername.get().getVendorId().equals(vendor.get().getId()) || findByUsername.get().getId().equals(vendor.get().getInvitee())) {
		    promo.setVendorId(findByUsername.get().getVendorId());
            promo.setVenApproval("O");
            promo.setSelfApproval("N");
            promo.setStatus(true);
            @Valid
            Promo save = promoRepository.save(promo);
            return new ResponseEntity<String>("Promo Offer Created and Request for Approval", HttpStatus.CREATED);
		}
		else {
		    return new ResponseEntity<String>("Only vendor Or Invitee can Authorize to Creat Promo", HttpStatus.BAD_GATEWAY);
		}

	}

	@Override
	public ResponseEntity<String> postPromoForCustomer(@Valid Promo promo) {
		promo.setVendorId(0);
		promo.setSelfApproval("O");
		promo.setVenApproval("N");
		promo.setStatus(true);
		@Valid
		Promo save = promoRepository.save(promo);
		return new ResponseEntity<String>("Promo Offer Created and Request for Approval", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> updatePromoForVendor(@Valid Promo promo) {
	       Optional<VendorRegistreation> vendor = vendorRegistreationRepository.findById(promo.getVendorId());
	        String name = SecurityContextHolder.getContext().getAuthentication().getName();
	        Optional<User> findByUsername = userRepository.findByUsername(name);
	        
	        if(findByUsername.get().getVendorId().equals(vendor.get().getId()) || findByUsername.get().getId().equals(vendor.get().getInvitee())) {
	            promo.setVendorId(findByUsername.get().getVendorId());
	            promo.setVenApproval("O");
	            promo.setStatus(true);
	            @Valid
	            Promo save = promoRepository.save(promo);
	            return new ResponseEntity<String>("Promo Offer Updated and Request for Approval", HttpStatus.CREATED);
	        }
	        else {
	            return new ResponseEntity<String>("Only vendor Or Invitee can Authorize to Creat Promo", HttpStatus.BAD_GATEWAY);
	        }
	}

	@Override
	public ResponseEntity<String> updatePromoForCustomer(@Valid Promo promo) {
		promo.setSelfApproval("O");
		promo.setStatus(true);
		@Valid
		Promo save = promoRepository.save(promo);
		return new ResponseEntity<String>("Promo Offer Created and Request for Approval", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deletePromo(Integer id) {
		Optional<Promo> promo = promoRepository.findById(id);
		promo.get().setStatus(false);
		Promo save = promoRepository.save(promo.get());
		return new ResponseEntity<String>("Promo Deleted Successfully", HttpStatus.CREATED);
	}
	
    @Override
    public PromoArticle getArticleDetailsForPromo(Integer id) {
        Optional<VendorProductHeader> findById = vendorProductHeaderRepository.findById(id);
        PromoArticle pa = new PromoArticle();
        pa.setPriceBase(findById.get().getPriceBased());
        List<VendorProductDetails> details = findById.get().getDetails();
        List<Double> mrp = new ArrayList<>();
        for (VendorProductDetails vendorProductDetails : details) {
            mrp.add(vendorProductDetails.getMrp());
            pa.setGst(vendorProductDetails.getGstPer());
        }
        pa.setMrp(mrp);
        return pa;
    }

	// ============================= Promo End==================

	// =============================Vendor Promo Approval Start==================

	@Override
	public List<Promo> getVendorPromoForApproval() {
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
			List<Promo> collect = promoRepository.findAll().stream()
					.filter(t -> t.getStatus().equals(true) && !t.getVendorId().equals(0)
							&& !t.getVenApproval().equals("A") && !t.getVenApproval().equals("D"))
					.collect(Collectors.toList());
			for (Promo promo : collect) {
				if (!promo.getVendorId().equals(0)) {
					Optional<VendorRegistreation> findById = vendorRegistreationRepository
							.findById(promo.getVendorId());
					promo.setVenName(findById.get().getName());
				}

				Optional<Articles> article = articlesRepository.findById(promo.getArticleId());
				promo.setArticalName(article.get().getName());
				Optional<Item> item = itemRepository.findById(article.get().getProductId());
				promo.setItemName(item.get().getPro_division() + "-" + item.get().getPro_family() + "-"
						+ item.get().getPro_class() + "-" + item.get().getPro_sub_class());
			}
			return collect;
		}

		else {
			String names = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Vendor Promo Approval");
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
			} else if (level.equals(1)) {
				List<Promo> collect = promoRepository.findAll().stream().filter(
						t -> t.getStatus().equals(true) && !t.getVendorId().equals(0) && t.getVenApproval().equals("O"))
						.collect(Collectors.toList());
				for (Promo promo : collect) {
					if (!promo.getVendorId().equals(0)) {
						Optional<VendorRegistreation> findById = vendorRegistreationRepository
								.findById(promo.getVendorId());
						promo.setVenName(findById.get().getName());
					}

					Optional<Articles> article = articlesRepository.findById(promo.getArticleId());
					Optional<Item> item = itemRepository.findById(article.get().getProductId());
					promo.setItemName(item.get().getPro_division() + "-" + item.get().getPro_family() + "-"
							+ item.get().getPro_class() + "-" + item.get().getPro_sub_class());
				}
				return collect;
			}

			else {
				Integer actualLevel = level - 1;
				String status = actualLevel.toString();
				List<Promo> collect = promoRepository.findAll().stream().filter(t -> t.getStatus().equals(true)
						&& !t.getVendorId().equals(0) && t.getVenApproval().equals(status))
						.collect(Collectors.toList());
				for (Promo promo : collect) {
					if (!promo.getVendorId().equals(0)) {
						Optional<VendorRegistreation> findById = vendorRegistreationRepository
								.findById(promo.getVendorId());
						promo.setVenName(findById.get().getName());
					}

					Optional<Articles> article = articlesRepository.findById(promo.getArticleId());
					Optional<Item> item = itemRepository.findById(article.get().getProductId());
					promo.setItemName(item.get().getPro_division() + "-" + item.get().getPro_family() + "-"
							+ item.get().getPro_class() + "-" + item.get().getPro_sub_class());
				}
				return collect;

			}

		}
	}

	@Override
	public ResponseEntity<String> promoVendorApproval(Integer id) {
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

			Optional<Promo> promo = promoRepository.findById(id);
			promo.get().setVenApproval("A");
			Promo save = promoRepository.save(promo.get());
			return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);

		} else {
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Customer Promo Approval");
			List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
			Integer level = findByManuid.size();
			Integer actualLevel = level - 1;
			String status = actualLevel.toString();
			Optional<Promo> promo = promoRepository.findById(id);
			if (promo.get().getVenApproval().equals(status)) {
				promo.get().setVenApproval("A");
				Promo save = promoRepository.save(promo.get());
				return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);
			} else if (promo.get().getVenApproval().equals("O")) {
				promo.get().setVenApproval("1");
				Promo save = promoRepository.save(promo.get());
				return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);
			} else {
				Integer temp = Integer.parseInt(promo.get().getVenApproval());
				Integer actualTemp = temp + 1;
				String s = actualTemp.toString();
				promo.get().setVenApproval(s);
				Promo save = promoRepository.save(promo.get());
				return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);
			}

		}

	}

	@Override
	public ResponseEntity<String> promoVendorDisapproval(Integer id,String reason) {
		Optional<Promo> promo = promoRepository.findById(id);
		promo.get().setVenApproval("D");
		promo.get().setVenDisReason(reason);
		Promo save = promoRepository.save(promo.get());
		return new ResponseEntity<String>("Promo Disapproved Successfully", HttpStatus.CREATED);
	}

	// =============================Vendor Promo Approval end==================

	// =============================Customer Promo Approval Start==================

	@Override
	public List<Promo> getCustomerPromoForApproval() {

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
			List<Promo> collect = promoRepository.findAll().stream()
					.filter(t -> t.getStatus().equals(true)
							&& !t.getSelfApproval().equals("A") && !t.getSelfApproval().equals("D") && !t.getSelfApproval().equals("N") )
					.collect(Collectors.toList());
			for (Promo promo : collect) {
				if (!promo.getVendorId().equals(0)) {
					Optional<VendorRegistreation> findById = vendorRegistreationRepository
							.findById(promo.getVendorId());
					promo.setVenName(findById.get().getName());
				}

				Optional<Articles> article = articlesRepository.findById(promo.getArticleId());
				promo.setArticalName(article.get().getName());
				Optional<Item> item = itemRepository.findById(article.get().getProductId());
				promo.setItemName(item.get().getPro_division() + "-" + item.get().getPro_family() + "-"
						+ item.get().getPro_class() + "-" + item.get().getPro_sub_class());
			}
			return collect;
		}

		else {
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
			} else if (level.equals(1)) {
				List<Promo> collect = promoRepository.findAll().stream().filter(
						t -> t.getStatus().equals(true) && !t.getSelfApproval().equals("O"))
						.collect(Collectors.toList());
				for (Promo promo : collect) {
					if (!promo.getVendorId().equals(0)) {
						Optional<VendorRegistreation> findById = vendorRegistreationRepository
								.findById(promo.getVendorId());
						promo.setVenName(findById.get().getName());
					}

					Optional<Articles> article = articlesRepository.findById(promo.getArticleId());
					Optional<Item> item = itemRepository.findById(article.get().getProductId());
					promo.setItemName(item.get().getPro_division() + "-" + item.get().getPro_family() + "-"
							+ item.get().getPro_class() + "-" + item.get().getPro_sub_class());
				}
				return collect;
			}

			else {
				Integer actualLevel = level - 1;
				String status = actualLevel.toString();
				List<Promo> collect = promoRepository.findAll().stream().filter(t -> t.getStatus().equals(true)
						&& !t.getSelfApproval().equals(status)).collect(Collectors.toList());
				for (Promo promo : collect) {
					if (!promo.getVendorId().equals(0)) {
						Optional<VendorRegistreation> findById = vendorRegistreationRepository
								.findById(promo.getVendorId());
						promo.setVenName(findById.get().getName());
					}

					Optional<Articles> article = articlesRepository.findById(promo.getArticleId());
					Optional<Item> item = itemRepository.findById(article.get().getProductId());
					promo.setItemName(item.get().getPro_division() + "-" + item.get().getPro_family() + "-"
							+ item.get().getPro_class() + "-" + item.get().getPro_sub_class());
				}
				return collect;
			}
		}
	}

	@Override
	public ResponseEntity<String> promoCustomerApproval(Integer id) {
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

			Optional<Promo> promo = promoRepository.findById(id);
			promo.get().setSelfApproval("A");
			Promo save = promoRepository.save(promo.get());
			return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);

		} else {
			Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Vendor Approval");
			List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
			Integer level = findByManuid.size();
			Integer actualLevel = level - 1;
			String status = actualLevel.toString();
			Optional<Promo> promo = promoRepository.findById(id);
			if (promo.get().getSelfApproval().equals(status)) {
				promo.get().setSelfApproval("A");
				Promo save = promoRepository.save(promo.get());
				return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);
			} else if (promo.get().getSelfApproval().equals("O")) {
				promo.get().setSelfApproval("1");
				Promo save = promoRepository.save(promo.get());
				return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);
			} else {
				Integer temp = Integer.parseInt(promo.get().getVenApproval());
				Integer actualTemp = temp + 1;
				String s = actualTemp.toString();
				promo.get().setSelfApproval(s);
				Promo save = promoRepository.save(promo.get());
				return new ResponseEntity<String>("Promo Approved Successfully", HttpStatus.CREATED);
			}

		}
	}

	@Override
	public ResponseEntity<String> promoCustomerDisapproval(Integer id,String reason) {
		Optional<Promo> promo = promoRepository.findById(id);
		promo.get().setSelfApproval("D");
		promo.get().setSelfDisReason(reason);
		Promo save = promoRepository.save(promo.get());
		return new ResponseEntity<String>("Promo Disapproved Successfully", HttpStatus.CREATED);
	}

	// =============================Customer Promo Approval end==================
    
    // ============================= General Promo Start==================
	
    @Override
    public List<GeneralPromo> getGeneralPromo() {
        return generalPromoRepository.findAll().stream()
        .filter(t -> t.getStatus().equals(true)).collect(Collectors.toList());
    }

    @Override
    public GeneralPromo getGeneralPromoByid(Integer id) {
        Optional<GeneralPromo> findById = generalPromoRepository.findById(id);
        return findById.get();
    }

    @Override
    public ResponseEntity<String> postGeneralPromo(@Valid GeneralPromo generalPromo) {
        generalPromo.setStatus(true);
        @Valid
        GeneralPromo save = generalPromoRepository.save(generalPromo);
        return new ResponseEntity<String>("Genaral Promo Save Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateGeneralPromo(@Valid GeneralPromo generalPromo) {
        generalPromo.setStatus(true);
        @Valid
        GeneralPromo save = generalPromoRepository.save(generalPromo);
        return new ResponseEntity<String>("Genaral Promo update Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteGeneralPromo(Integer id) {
        Optional<GeneralPromo> findById = generalPromoRepository.findById(id);
        findById.get().setStatus(false);
        generalPromoRepository.save(findById.get());
        return new ResponseEntity<String>("Genaral Promo Deleted", HttpStatus.CREATED);
    }
    // ============================= General Promo Start==================

}
