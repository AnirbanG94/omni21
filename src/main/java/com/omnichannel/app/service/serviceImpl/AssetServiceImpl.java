 package com.omnichannel.app.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.omnichannel.app.model.admin.ApplicationSetup;
import com.omnichannel.app.model.admin.ApprovalSetup;
import com.omnichannel.app.model.asset.AssetBookingDetails;
import com.omnichannel.app.model.asset.AssetBookingHeader;
import com.omnichannel.app.model.asset.AssetExecution;
import com.omnichannel.app.model.asset.AssetRegistration;
import com.omnichannel.app.model.master.AssetsType;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.model.vendor.VendorRegistreation;
import com.omnichannel.app.repository.admin.ApplicationSetupRepository;
import com.omnichannel.app.repository.admin.ApprovalSetupRepository;
import com.omnichannel.app.repository.asset.AssetBookingDetailsRepository;
import com.omnichannel.app.repository.asset.AssetBookingHeaderRepository;
import com.omnichannel.app.repository.asset.AssetExecutionRepository;
import com.omnichannel.app.repository.asset.AssetRegistrationRepository;
import com.omnichannel.app.repository.master.AssetsTypeRepository;
import com.omnichannel.app.repository.master.OutletRepository;
import com.omnichannel.app.repository.product.ArticlesRepository;
import com.omnichannel.app.repository.usermanagement.UserRepository;
import com.omnichannel.app.repository.usermanagement.ViewPrivilegeRepository;
import com.omnichannel.app.repository.vendor.VendorRegistreationRepository;
import com.omnichannel.app.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    AssetRegistrationRepository assetRegistrationRepository;
    @Autowired
    AssetBookingHeaderRepository assetBookingHeaderRepository;
    @Autowired
    AssetBookingDetailsRepository assetBookingDetailsRepository;
    @Autowired
    AssetExecutionRepository assetExecutionRepository;

    @Autowired
    ViewPrivilegeRepository viewPrivilegeRepository;

    @Autowired
    ApprovalSetupRepository approvalSetupRepository;

    @Autowired
    VendorRegistreationRepository Vendor;

    @Autowired
    ArticlesRepository Article;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OutletRepository outletRepository;

    @Autowired
    AssetsTypeRepository assetsTypeRepository;

    @Autowired
    ApplicationSetupRepository applicationSetupRepository;

    @Autowired
    VendorRegistreationRepository vendorRegistreationRepository;

    // ============================= ASSET Registration Start==================

    @Override
    public List<AssetRegistration> getAssetRegistration() {
        List<AssetRegistration> collect = assetRegistrationRepository.findAll().stream()
                .filter(t -> t.getStatus().equals(true)).collect(Collectors.toList());
        for (AssetRegistration assetRegistration : collect) {
            Optional<Outlet> outlet = outletRepository.findById(assetRegistration.getOutletId());
            Optional<AssetsType> assetstype = assetsTypeRepository.findById(assetRegistration.getAssetsTypeId());
            assetRegistration.setOutletName(outlet.get().getName());
            assetRegistration.setAssetsTypeName(assetstype.get().getType());
        }
        return collect;
    }

    @Override
    public ResponseEntity<String> postAssetRegistration(@Valid AssetRegistration assetRegistration) {
        assetRegistration.setStatus(true);
        assetRegistrationRepository.save(assetRegistration);
        return new ResponseEntity<String>("Asset Registered Successfully", HttpStatus.CREATED);
    }

    @Override
    public AssetRegistration getAssetRegistrationById(Integer id) {
        Optional<AssetRegistration> findById = assetRegistrationRepository.findById(id);
        Optional<Outlet> findById2 = outletRepository.findById(findById.get().getOutletId());
        Optional<AssetsType> assetstype = assetsTypeRepository.findById(findById.get().getAssetsTypeId());
        findById.get().setOutletType(findById2.get().getType());
        findById.get().setOutletName(findById2.get().getName());
        findById.get().setAssetsTypeName(assetstype.get().getType());
        return findById.get();
    }

    @Override
    public ResponseEntity<String> updateAssetRegistration(@Valid AssetRegistration assetRegistration) {
        assetRegistration.setStatus(true);
        assetRegistrationRepository.save(assetRegistration);
        return new ResponseEntity<String>("Asset Updated Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteAssetRegistration(Integer id) {
        Optional<AssetRegistration> findById = assetRegistrationRepository.findById(id);
        findById.get().setStatus(false);
        assetRegistrationRepository.save(findById.get());
        return new ResponseEntity<String>("Asset Deleted Successfully", HttpStatus.CREATED);
    }

    @Override
    public List<AssetRegistration> getAssetByOutletType(String type) {
        List<AssetBookingHeader> booking = assetBookingHeaderRepository
                .findAll().stream().filter(t -> t.getStatus().equals("A")).collect(Collectors.toList());

        List<AssetRegistration> assetByOutletType = assetRegistrationRepository.AssetByOutletType(type);

        if (assetByOutletType.isEmpty()) {
            return assetByOutletType;
        } else {
            for (AssetRegistration assetRegistration : assetByOutletType) {
                Optional<Outlet> outlet = outletRepository.findById(assetRegistration.getOutletId());
                Optional<AssetsType> assetstype = assetsTypeRepository.findById(assetRegistration.getAssetsTypeId());
                assetRegistration.setOutletName(outlet.get().getName());
                assetRegistration.setOutletType(outlet.get().getType());
                assetRegistration.setAssetsTypeName(assetstype.get().getType());
            }
            for (AssetBookingHeader assetBookingHeader : booking) {
                assetByOutletType.removeIf(t -> t.getId().equals(assetBookingHeader.getAssetRegId()));
            }
            return assetByOutletType;
        }

    }

    // ============================= ASSET Registration End==================

    // ================================== Asset Booking Start ===================

    @Override
    public ResponseEntity<?> getUserForAssetBooking() {
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        Boolean Roleadmin = false;
        for (GrantedAuthority grantedAuthority : auth) {
            System.out.println(grantedAuthority);
            if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
                Roleadmin = true;
                break;
            }
        }
        String names = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<Integer, String> vendor = new HashMap<>();

        if (Roleadmin) {
            List<User> allAictiveList = userRepository.getAllAictiveList();
            for (User user : allAictiveList) {
                vendor.put(user.getId(), user.getName());
            }
            return new ResponseEntity<Map<Integer, String>>(vendor, HttpStatus.OK);
        } else {
            User user = userRepository.findByUsername(names).get();
            if (user.getVendorId() != 0) {
                vendor.put(user.getId(), user.getName());
                return new ResponseEntity<Map<Integer, String>>(vendor, HttpStatus.OK);
            } else {
                vendor.put(user.getId(), user.getName());
                List<VendorRegistreation> findByInvitee = vendorRegistreationRepository.findByInvitee(user.getId());
                if (!findByInvitee.isEmpty()) {
                    for (VendorRegistreation vendorRegistreation : findByInvitee) {
                        User vendorTemp = userRepository
                                .findByVendorIdAndEnabledAndAccountNotExpired(vendorRegistreation.getId(), true, true)
                                .get();
                        vendor.put(vendorTemp.getId(), vendorTemp.getName());
                    }

                }
                return new ResponseEntity<Map<Integer, String>>(vendor, HttpStatus.OK);
            }
        }

    }

    @Override
    public AssetBookingHeader getAssetBookingId(Integer id) {
        Optional<AssetBookingHeader> booking = assetBookingHeaderRepository.findById(id);
        AssetRegistration asset = getAssetRegistrationById(booking.get().getAssetRegId());
        booking.get().setCost(asset.getCost());
        booking.get().setGst(asset.getGst());
        booking.get().setAssetsTypeName(asset.getAssetsTypeName());
        booking.get().setOutletName(asset.getOutletName());
        booking.get().setOutletType(asset.getOutletType());
        booking.get().setProductFamily(asset.getProductFamily());
        booking.get().setAssetNo(asset.getAssetNo());
        booking.get().setVisibilityType(asset.getVisibilityType());
        Optional<VendorRegistreation> findById = Vendor.findById(booking.get().getVendorId());
        booking.get().setVendorName(findById.get().getName());
        List<AssetBookingDetails> details = booking.get().getDetails();
        for (AssetBookingDetails assetBookingDetails : details) {
            Optional<Articles> findById2 = Article.findById(assetBookingDetails.getArticleId());
            assetBookingDetails.setArticleName(findById2.get().getName());
            assetBookingDetails.setEanCode(findById2.get().getEanCode());
        }
        return booking.get();
    }

    @Override
    public List<AssetBookingHeader> getAssetBooking() {
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        Boolean Roleadmin = true;
        for (GrantedAuthority grantedAuthority : auth) {
            System.out.println(grantedAuthority);
            if (grantedAuthority.toString().equals("ROLE_VENDOR")) {
                System.out.println("active vendor");
                Roleadmin = false;
                break;
            }
        }
        if (Roleadmin) {
            List<AssetBookingHeader> findAll = assetBookingHeaderRepository.findAll();
            for (AssetBookingHeader booking : findAll) {

                AssetRegistration asset = getAssetRegistrationById(booking.getAssetRegId());
                booking.setCost(asset.getCost());
                booking.setGst(asset.getGst());
                booking.setAssetsTypeName(asset.getAssetsTypeName());
                booking.setAssetNo(asset.getAssetNo());
                booking.setOutletName(asset.getOutletName());
                booking.setOutletType(asset.getOutletType());
                booking.setProductFamily(asset.getProductFamily());
                Optional<VendorRegistreation> findById = Vendor.findById(booking.getVendorId());
                booking.setVendorName(findById.get().getName());

                List<AssetBookingDetails> details = booking.getDetails();
                for (AssetBookingDetails assetBookingDetails : details) {
                    Optional<Articles> findById2 = Article.findById(assetBookingDetails.getArticleId());
                    assetBookingDetails.setArticleName(findById2.get().getName());
                    assetBookingDetails.setEanCode(findById2.get().getEanCode());
                }
            }
            return findAll;
        } else {
            String names = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = userRepository.findByUsername(names);
            List<AssetBookingHeader> findAll = assetBookingHeaderRepository
                    .findAll().stream()
                    .filter(t -> t.getVendorId().equals(user.get().getVendorId())).collect(Collectors.toList());
            for (AssetBookingHeader booking : findAll) {
                AssetRegistration asset = getAssetRegistrationById(booking.getAssetRegId());
                booking.setCost(asset.getCost());
                booking.setGst(asset.getGst());
                booking.setAssetsTypeName(asset.getAssetsTypeName());
                booking.setOutletName(asset.getOutletName());
                booking.setOutletType(asset.getOutletType());
                booking.setProductFamily(asset.getProductFamily());
                Optional<VendorRegistreation> findById = Vendor.findById(booking.getVendorId());
                booking.setVendorName(findById.get().getName());
            }
            return findAll;
        }
    }

    @Override
    public ResponseEntity<String> postAssetBooking(AssetBookingHeader assetBookingHeader) {
        assetBookingHeader.setStatus("O");
        assetBookingHeader.setActive(true);
        List<AssetBookingDetails> details = assetBookingHeader.getDetails();
        assetBookingHeader.setDetails(null);
        AssetBookingHeader save = assetBookingHeaderRepository.save(assetBookingHeader);
        for (AssetBookingDetails assetBookingDetails : details) {
            assetBookingDetails.setHeader(save);
            assetBookingDetailsRepository.save(assetBookingDetails);
        }
        return new ResponseEntity<String>("Asset Booked Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateAssetBooking(@Valid AssetBookingHeader assetBookingHeader) {
        assetBookingHeader.setStatus("O");
        assetBookingHeader.setActive(true);
        List<AssetBookingDetails> details = assetBookingHeader.getDetails();
        assetBookingHeader.setDetails(null);
        AssetBookingHeader save = assetBookingHeaderRepository.save(assetBookingHeader);
        for (AssetBookingDetails assetBookingDetails : details) {
            assetBookingDetails.setHeader(save);
            assetBookingDetailsRepository.save(assetBookingDetails);
        }
        return new ResponseEntity<String>("Asset Booking Update Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteAssetBookingDetails(Integer id) {
        assetBookingDetailsRepository.deleteById(id);
        return new ResponseEntity<String>("Asset Booking Entry Deleted", HttpStatus.CREATED);
    }

    @Override
    public List<Articles> getArticleForAssetBookingByVendorId(String family, Integer vendorId) {
        List<Articles> articleForAsser = Article.articleForAsser(vendorId, family);
        return articleForAsser;
    }

    // ================================== Asset Booking End ===================

    // ============================= Asset Booking Approval Start==================

    @Override
    public List<AssetBookingHeader> getAssetsVisibilityBookingForApprove() {
        List<AssetBookingHeader> findAll = new ArrayList<>();
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        Boolean Roleadmin = false;
        for (GrantedAuthority grantedAuthority : auth) {
            System.out.println(grantedAuthority);
            if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
                System.out.println("active vendor");
                Roleadmin = true;
                break;
            }
        }
        if (Roleadmin) {
            findAll = assetBookingHeaderRepository.findForApproval();

            for (AssetBookingHeader booking : findAll) {
                AssetRegistration asset = getAssetRegistrationById(booking.getAssetRegId());
                booking.setCost(asset.getCost());
                booking.setGst(asset.getGst());
                booking.setAssetsTypeName(asset.getAssetsTypeName());
                booking.setOutletName(asset.getOutletName());
                booking.setOutletType(asset.getOutletType());
                booking.setAssetNo(asset.getAssetNo());
                booking.setProductFamily(asset.getProductFamily());
                Optional<VendorRegistreation> findById = Vendor.findById(booking.getVendorId());
                booking.setVendorName(findById.get().getName());
            }
        } else {
            String names = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Asset Booking Approval");
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
                findAll = assetBookingHeaderRepository.findForLevelApproval("O");

                for (AssetBookingHeader booking : findAll) {
                    AssetRegistration asset = getAssetRegistrationById(booking.getAssetRegId());
                    booking.setCost(asset.getCost());
                    booking.setGst(asset.getGst());
                    booking.setAssetsTypeName(asset.getAssetsTypeName());
                    booking.setOutletName(asset.getOutletName());
                    booking.setOutletType(asset.getOutletType());
                    booking.setAssetNo(asset.getAssetNo());
                    booking.setProductFamily(asset.getProductFamily());
                    Optional<VendorRegistreation> findById = Vendor.findById(booking.getVendorId());
                    booking.setVendorName(findById.get().getName());
                }

            } else {
                Integer actualLevel = level - 1;
                String status = actualLevel.toString();
                findAll = assetBookingHeaderRepository.findForLevelApproval(status);

                for (AssetBookingHeader booking : findAll) {
                    AssetRegistration asset = getAssetRegistrationById(booking.getAssetRegId());
                    booking.setCost(asset.getCost());
                    booking.setGst(asset.getGst());
                    booking.setAssetsTypeName(asset.getAssetsTypeName());
                    booking.setOutletName(asset.getOutletName());
                    booking.setOutletType(asset.getOutletType());
                    booking.setAssetNo(asset.getAssetNo());
                    booking.setProductFamily(asset.getProductFamily());
                    Optional<VendorRegistreation> findById = Vendor.findById(booking.getVendorId());
                    booking.setVendorName(findById.get().getName());
                }

            }

        }
        return findAll;
    }

    @Override
    public ResponseEntity<String> updateAssetBookingApproval(Integer id, Double discount) {
        Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        Boolean Roleadmin = false;
        for (GrantedAuthority grantedAuthority : auth) {
            System.out.println(grantedAuthority);
            if (grantedAuthority.toString().equals("ROLE_ADMIN")) {
                System.out.println("active vendor");
                Roleadmin = true;
                break;
            }
        }
        if (Roleadmin) {
            Optional<AssetBookingHeader> findById = assetBookingHeaderRepository.findById(id);
            if (findById.get().getStatus().equals("O")) {
                findById.get().setDiscount(discount);
                Optional<AssetRegistration> asset = assetRegistrationRepository
                        .findById(findById.get().getAssetRegId());
                Double gst = asset.get().getGst();
                Double amount = findById.get().getAmount();
                Double discDouble = amount * ((100 - discount) / 100);
                Double totalDouble = discDouble * ((100 + gst) / 100);
                String format = String.format("%.2f", totalDouble);
                double parseDouble = Double.parseDouble(format);
                findById.get().setTotalAmount(parseDouble);
            }
            findById.get().setStatus("A");
            assetBookingHeaderRepository.save(findById.get());
            return new ResponseEntity<String>("Asset Booking Approved By Admin!!", HttpStatus.CREATED);
        } else {
            Optional<ViewPrivilege> SUBMENU = viewPrivilegeRepository.findBySubmenu("Asset Booking Approval");
            List<ApprovalSetup> findByManuid = approvalSetupRepository.findByManuid(SUBMENU.get().getId());
            Integer level = findByManuid.size();
            Integer actualLevel = level - 1;
            String status = actualLevel.toString();
            Optional<AssetBookingHeader> asset = assetBookingHeaderRepository.findById(id);
            if (asset.get().getStatus().equals(status)) {
                asset.get().setStatus("A");
                assetBookingHeaderRepository.save(asset.get());
                return new ResponseEntity<String>("Asset Booking Final Approved!!", HttpStatus.CREATED);
            } else if (asset.get().getStatus().equals("O")) {
                Optional<AssetRegistration> findById = assetRegistrationRepository
                        .findById(asset.get().getAssetRegId());
                asset.get().setStatus("1");
                asset.get().setDiscount(discount);
                Double gst = findById.get().getGst();
                Double amount = asset.get().getAmount();
                Double discDouble = amount * ((100 - discount) / 100);
                Double totalDouble = discDouble * ((100 + gst) / 100);
                String format = String.format("%.2f", totalDouble);
                double parseDouble = Double.parseDouble(format);
                asset.get().setTotalAmount(parseDouble);
                assetBookingHeaderRepository.save(asset.get());
                return new ResponseEntity<String>("Asset Booking Approved for next approval!!", HttpStatus.CREATED);
            } else {
                Integer temp = Integer.parseInt(asset.get().getStatus());
                Integer actualTemp = temp + 1;
                String s = actualTemp.toString();
                asset.get().setStatus(s);
                assetBookingHeaderRepository.save(asset.get());
                return new ResponseEntity<String>("Asset Booking for next approval!!", HttpStatus.CREATED);
            }
        }

    }

    @Override
    public ResponseEntity<String> updateAssetBookingDisapprove(Integer id, String reason) {
        Optional<AssetBookingHeader> findById = assetBookingHeaderRepository.findById(id);
        findById.get().setStatus("D");
        findById.get().setReason(reason);
        assetBookingHeaderRepository.save(findById.get());
        return new ResponseEntity<String>("Asset Booking Approved Rejected", HttpStatus.CREATED);
    }

    // ============================= Asset Booking Approval End==================

    // ============================= Asset Execution Start==================

    @Override
    public List<AssetExecution> getAssetExecution() {
        List<AssetExecution> collect = assetExecutionRepository
                .findAll().stream().filter(t -> t.getStatus().equals(true)).collect(Collectors.toList());
        for (AssetExecution assetExecution : collect) {
            AssetBookingHeader booking = getAssetBookingId(assetExecution.getAssetbookingId());
            assetExecution.setVendorName(booking.getVendorName());
            assetExecution.setAssetNo(booking.getAssetNo());
            assetExecution.setAssetTypeName(booking.getAssetsTypeName());
            assetExecution.setBookingUpto(booking.getEndMth());
            assetExecution.setProductFamily(booking.getProductFamily());
        }
        return collect;
    }

    @Override
    public ResponseEntity<String> postAssetExecution(@Valid AssetExecution assetExecution) {
        assetExecution.setStatus(true);
        assetExecutionRepository.save(assetExecution);
        return new ResponseEntity<String>("Asset Execution Saved", HttpStatus.CREATED);
    }

    @Override
    public AssetExecution getAssetExecutionById(Integer id) {
        Optional<AssetExecution> findById = assetExecutionRepository.findById(id);
        AssetBookingHeader booking = getAssetBookingId(findById.get().getAssetbookingId());
        findById.get().setVendorName(booking.getVendorName());
        findById.get().setAssetNo(booking.getAssetNo());
        findById.get().setAssetTypeName(booking.getAssetsTypeName());
        findById.get().setBookingUpto(booking.getEndMth());
        findById.get().setProductFamily(booking.getProductFamily());
        return findById.get();
    }

    @Override
    public ResponseEntity<String> updateAssetExecution(AssetExecution assetExecution) {
        assetExecution.setStatus(true);
        assetExecutionRepository.save(assetExecution);
        return new ResponseEntity<String>("Asset Execution Updated", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteAssetExecution(Integer id) {
        Optional<AssetExecution> findById = assetExecutionRepository.findById(id);
        findById.get().setStatus(false);
        assetExecutionRepository.save(findById.get());
        return new ResponseEntity<String>("Asset Execution Deleted", HttpStatus.CREATED);
    }

    @Override
    public List<AssetBookingHeader> getAssetsBookingByVendorIdForExecution(Integer id) {
        List<AssetBookingHeader> assetbooking = assetBookingHeaderRepository.findByVendorId(id)
                .stream().filter(t -> t.getStatus().equals("A")).collect(Collectors.toList());
        List<AssetExecution> collect = assetExecutionRepository
                .findAll().stream().filter(t -> t.getStatus().equals(true)).collect(Collectors.toList());
        for (AssetExecution assetExecution : collect) {
            assetbooking.removeIf(t -> t.getId().equals(assetExecution.getAssetbookingId()));
        }

        for (AssetBookingHeader booking : assetbooking) {
            AssetRegistration asset = getAssetRegistrationById(booking.getAssetRegId());
            booking.setCost(asset.getCost());
            booking.setAssetsTypeName(asset.getAssetsTypeName());
            booking.setOutletName(asset.getOutletName());
            booking.setOutletType(asset.getOutletType());
            booking.setProductFamily(asset.getProductFamily());
            Optional<VendorRegistreation> findById = Vendor.findById(booking.getVendorId());
            booking.setVendorName(findById.get().getName());
        }

        return assetbooking;
    }

    @Override
    public String getAssetGST() {
        ApplicationSetup applicationSetup = applicationSetupRepository.findByKey("VISIBILITY ASSETS TAX RATE (%)")
                .get();
        return applicationSetup.getValue();
    }

    // ============================= Asset Execution End==================

}
