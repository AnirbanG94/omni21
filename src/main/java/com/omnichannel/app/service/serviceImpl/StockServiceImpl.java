package com.omnichannel.app.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.omnichannel.app.model.DTO.OpenStockDTO;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.purchase.PurchaseOrderDetails;
import com.omnichannel.app.model.purchase.PurchaseOrderHeader;
import com.omnichannel.app.model.stock.GdnDetails;
import com.omnichannel.app.model.stock.GdnHeader;
import com.omnichannel.app.model.stock.GrnDetails;
import com.omnichannel.app.model.stock.GrnHeader;
import com.omnichannel.app.model.stock.MrnDetails;
import com.omnichannel.app.model.stock.MrnHeader;
import com.omnichannel.app.model.stock.OpeningStock;
import com.omnichannel.app.model.stock.PickListDetails;
import com.omnichannel.app.model.stock.PickListHeader;
import com.omnichannel.app.model.stock.StockTransferDetails;
import com.omnichannel.app.model.stock.StockTransferHeader;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.repository.master.OutletRepository;
import com.omnichannel.app.repository.product.ArticlesRepository;
import com.omnichannel.app.repository.product.UOMRepository;
import com.omnichannel.app.repository.purchase.PurchaseOrderDetailsRepository;
import com.omnichannel.app.repository.purchase.PurchaseOrderHeaderRepository;
import com.omnichannel.app.repository.stock.GdnDetailsRepository;
import com.omnichannel.app.repository.stock.GdnHeaderRepository;
import com.omnichannel.app.repository.stock.GrnDetailsRepository;
import com.omnichannel.app.repository.stock.GrnHeaderRepository;
import com.omnichannel.app.repository.stock.MrnDetailsRepository;
import com.omnichannel.app.repository.stock.MrnHeaderRepository;
import com.omnichannel.app.repository.stock.OpeningStockRepository;
import com.omnichannel.app.repository.stock.PickListDetailsRepository;
import com.omnichannel.app.repository.stock.PickListHeaderRepository;
import com.omnichannel.app.repository.stock.StockTransferDetailsRepository;
import com.omnichannel.app.repository.stock.StockTransferHeaderRepository;
import com.omnichannel.app.repository.vendor.VendorProductHeaderRepository;
import com.omnichannel.app.repository.vendor.VendorRegistreationRepository;
import com.omnichannel.app.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    GrnHeaderRepository grnHeaderRepository;

    @Autowired
    GrnDetailsRepository grnDetailsRepository;

    @Autowired
    GdnHeaderRepository gdnHeaderRepository;

    @Autowired
    GdnDetailsRepository gdnDetailsRepository;

    @Autowired
    MrnHeaderRepository mrnHeaderRepository;

    @Autowired
    MrnDetailsRepository mrnDetailsRepository;

    @Autowired
    OpeningStockRepository openingStockRepository;

    @Autowired
    PickListDetailsRepository pickListDetailsRepository;

    @Autowired
    PickListHeaderRepository pickListHeaderRepository;

    @Autowired
    StockTransferHeaderRepository stockTransferHeaderRepository;

    @Autowired
    StockTransferDetailsRepository stockTransferDetailsRepository;

    @Autowired
    VendorProductHeaderRepository vendorProductHeaderRepository;

    @Autowired
    OutletRepository outletRepository;

    @Autowired
    ArticlesRepository articlesRepository;

    @Autowired
    UOMRepository uOMRepository;

    @Autowired
    VendorRegistreationRepository vendorRegistreationRepository;

    @Autowired
    PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

    @Autowired
    PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

    // ============================= GRN Start==================

    @Override
    public List<PurchaseOrderHeader> getPOArticleGRN(Integer Id) {
        List<PurchaseOrderHeader> po = purchaseOrderHeaderRepository.findByGrnFlagAndOutletIdAndStatusAndActive(true,
                Id, "A", true);
        return po;
    }

    @Override
    public PurchaseOrderHeader getPODetailForGRN(Integer id) {
        PurchaseOrderHeader po = purchaseOrderHeaderRepository.findById(id).get();
        po.setVendorName(vendorRegistreationRepository.findById(po.getVendorId()).get().getName());
        po.setOutletName(outletRepository.findById(po.getOutletId()).get().getName());
        List<PurchaseOrderDetails> details = purchaseOrderDetailsRepository.getPODetails(id);
        Double totBasicAmt = 0.0;
        Double totSgstAmt = 0.0;
        Double totCgstAmt = 0.0;
        Double totIgstAmt = 0.0;
        Double totNetAmt = 0.0;
        for (PurchaseOrderDetails detail : details) {
            detail.setBasicAmt(detail.getCp() * detail.getQty());
            totBasicAmt = totBasicAmt + detail.getBasicAmt();
            if (detail.getIgstAmt() == null) {
                detail.setCgstAmt(detail.getBasicAmt() * ((detail.getTaxPer() / 2) / 100));
                detail.setSgstAmt(detail.getBasicAmt() * ((detail.getTaxPer() / 2) / 100));
                detail.setNetAmt(detail.getBasicAmt() + detail.getCgstAmt() + detail.getSgstAmt());
                totSgstAmt = totSgstAmt + detail.getSgstAmt();
                totCgstAmt = totCgstAmt + detail.getCgstAmt();
                totNetAmt = totNetAmt + detail.getNetAmt();
            } else {
                detail.setIgstAmt(detail.getBasicAmt() * (detail.getTaxPer() / 100));
                detail.setNetAmt(detail.getBasicAmt() + detail.getIgstAmt());
                totNetAmt = totNetAmt + detail.getNetAmt();
                totIgstAmt = totIgstAmt + detail.getIgstAmt();
            }
            detail.setUomName(uOMRepository.findById(detail.getUomId()).get().getName());
            detail.setArticleName(articlesRepository.findById(detail.getArticleId()).get().getName());

        }
        po.setDetails(details);
        po.setTotBasicAmt(totBasicAmt);
        po.setTotCgstAmt(totCgstAmt);
        po.setTotIgstAmt(totIgstAmt);
        po.setTotSgstAmt(totSgstAmt);
        po.setTotNetAmt(totNetAmt);
        return po;
    }

    @Override
    public List<GrnHeader> getGRN() {
        List<GrnHeader> findByActive = grnHeaderRepository.findByActive(true);
        for (GrnHeader grnHeader : findByActive) {
            grnHeader.setVendorName(vendorRegistreationRepository.findById(grnHeader.getVendorId()).get().getName());
            grnHeader.setOutletName(outletRepository.findById(grnHeader.getOutletId()).get().getName());

            for (GrnDetails details : grnHeader.getDetails()) {
                details.setUomName(uOMRepository.findById(details.getUomId()).get().getName());
                details.setArticleName(articlesRepository.findById(details.getArticleId()).get().getName());
            }
        }
        return findByActive;
    }

    @Override
    public GrnHeader getGRNById(Integer id) {
        GrnHeader grnHeader = grnHeaderRepository.findById(id).get();
        grnHeader.setVendorName(vendorRegistreationRepository.findById(grnHeader.getVendorId()).get().getName());
        grnHeader.setOutletName(outletRepository.findById(grnHeader.getOutletId()).get().getName());
        Optional<PurchaseOrderHeader> po = purchaseOrderHeaderRepository.findById(grnHeader.getPoId());
        grnHeader.setPoNo(po.get().getPoNo());
        grnHeader.setPoDt(po.get().getPoDt());

        for (GrnDetails details : grnHeader.getDetails()) {
            details.setUomName(uOMRepository.findById(details.getUomId()).get().getName());
            details.setArticleName(articlesRepository.findById(details.getArticleId()).get().getName());
        }
        return grnHeader;
    }

    @Override
    public ResponseEntity<String> postGRN(@Valid GrnHeader grn) {
        if (grn.getDetails() != null) {
            grn.setActive(true);
            List<GrnDetails> GRNDetails = grn.getDetails();
            grn.setDetails(null);
            GrnHeader GRN = grnHeaderRepository.save(grn);
            for (GrnDetails grnDetails : GRNDetails) {
                grnDetails.setHeader(GRN);
            }
            GRN.setDetails(GRNDetails);
            GrnHeader save = grnHeaderRepository.save(GRN);

            List<PurchaseOrderDetails> details = purchaseOrderDetailsRepository.getPODetails(save.getPoId());
            if (details.isEmpty()) {
                PurchaseOrderHeader findById = purchaseOrderHeaderRepository.findById(save.getPoId()).get();
                findById.setGdnFlag(false);
                findById.setGrnFlag(false);
                purchaseOrderHeaderRepository.save(findById);
            }
            return new ResponseEntity<String>("GRN Data Saved", HttpStatus.OK);

        } else {
            return new ResponseEntity<String>("Error in Saving GRN Data", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> updateGRN(@Valid GrnHeader grn) {
        if (grn.getDetails() != null) {
            grn.setActive(true);
            List<GrnDetails> GRNDetails = grn.getDetails();
            grn.setDetails(null);
            GrnHeader GRN = grnHeaderRepository.save(grn);
            for (GrnDetails grnDetails : GRNDetails) {
                grnDetails.setHeader(GRN);
            }
            GRN.setDetails(GRNDetails);
            GrnHeader save = grnHeaderRepository.save(GRN);

            List<PurchaseOrderDetails> details = purchaseOrderDetailsRepository.getPODetails(save.getPoId());
            if (details.isEmpty()) {
                PurchaseOrderHeader findById = purchaseOrderHeaderRepository.findById(save.getPoId()).get();
                findById.setGdnFlag(false);
                findById.setGrnFlag(false);
                purchaseOrderHeaderRepository.save(findById);
            } else {
                PurchaseOrderHeader findById = purchaseOrderHeaderRepository.findById(save.getPoId()).get();
                findById.setGdnFlag(true);
                findById.setGrnFlag(true);
                purchaseOrderHeaderRepository.save(findById);
            }
            return new ResponseEntity<String>("GRN Data Saved", HttpStatus.OK);

        } else {
            return new ResponseEntity<String>("Error in Saving GRN Data", HttpStatus.BAD_REQUEST);
        }
    }

    // ============================= GRN End==================

    // ============================= GDN Start==================

    @Override
    public List<PurchaseOrderHeader> getPOArticleGDN(Integer Id) {
        List<PurchaseOrderHeader> po = purchaseOrderHeaderRepository.findByGdnFlagAndOutletIdAndStatusAndActive(true,
                Id, "A", true);
        return po;
    }

    @Override
    public PurchaseOrderHeader getPODetailForGDN(Integer id) {
        PurchaseOrderHeader po = purchaseOrderHeaderRepository.findById(id).get();
        po.setVendorName(vendorRegistreationRepository.findById(po.getVendorId()).get().getName());
        po.setOutletName(outletRepository.findById(po.getOutletId()).get().getName());
        List<PurchaseOrderDetails> details = purchaseOrderDetailsRepository.getPODetails(id);
        Double totBasicAmt = 0.0;
        Double totSgstAmt = 0.0;
        Double totCgstAmt = 0.0;
        Double totIgstAmt = 0.0;
        Double totNetAmt = 0.0;
        for (PurchaseOrderDetails detail : details) {
            detail.setBasicAmt(detail.getCp() * detail.getQty());
            totBasicAmt = totBasicAmt + detail.getBasicAmt();
            if (detail.getIgstAmt() == null) {
                detail.setCgstAmt(detail.getBasicAmt() * ((detail.getTaxPer() / 2) / 100));
                detail.setSgstAmt(detail.getBasicAmt() * ((detail.getTaxPer() / 2) / 100));
                detail.setNetAmt(detail.getBasicAmt() + detail.getCgstAmt() + detail.getSgstAmt());
                totSgstAmt = totSgstAmt + detail.getSgstAmt();
                totCgstAmt = totCgstAmt + detail.getCgstAmt();
                totNetAmt = totNetAmt + detail.getNetAmt();
            } else {
                detail.setIgstAmt(detail.getBasicAmt() * (detail.getTaxPer() / 100));
                detail.setNetAmt(detail.getBasicAmt() + detail.getIgstAmt());
                totNetAmt = totNetAmt + detail.getNetAmt();
                totIgstAmt = totIgstAmt + detail.getIgstAmt();
            }
            detail.setUomName(uOMRepository.findById(detail.getUomId()).get().getName());
            detail.setArticleName(articlesRepository.findById(detail.getArticleId()).get().getName());

        }
        po.setDetails(details);
        po.setTotBasicAmt(totBasicAmt);
        po.setTotCgstAmt(totCgstAmt);
        po.setTotIgstAmt(totIgstAmt);
        po.setTotSgstAmt(totSgstAmt);
        po.setTotNetAmt(totNetAmt);
        return po;
    }

    @Override
    public List<GdnHeader> getGDN() {
        List<GdnHeader> findByActive = gdnHeaderRepository.findByActive(true);
        for (GdnHeader gdnHeader : findByActive) {
            gdnHeader.setVendorName(vendorRegistreationRepository.findById(gdnHeader.getVendorId()).get().getName());
            gdnHeader.setOutletName(outletRepository.findById(gdnHeader.getOutletId()).get().getName());

            for (GdnDetails details : gdnHeader.getDetails()) {
                details.setUomName(uOMRepository.findById(details.getUomId()).get().getName());
                details.setArticleName(articlesRepository.findById(details.getArticleId()).get().getName());
            }
        }
        return findByActive;
    }

    @Override
    public GdnHeader getGDNById(Integer id) {
        GdnHeader gdnHeader = gdnHeaderRepository.findById(id).get();
        gdnHeader.setVendorName(vendorRegistreationRepository.findById(gdnHeader.getVendorId()).get().getName());
        gdnHeader.setOutletName(outletRepository.findById(gdnHeader.getOutletId()).get().getName());
        for (GdnDetails details : gdnHeader.getDetails()) {
            details.setUomName(uOMRepository.findById(details.getUomId()).get().getName());
            details.setArticleName(articlesRepository.findById(details.getArticleId()).get().getName());
        }
        return gdnHeader;
    }

    @Override
    public ResponseEntity<String> postGDN(@Valid GdnHeader gdn) {
        if (gdn.getDetails() != null) {
            gdn.setActive(true);
            List<GdnDetails> GRNDetails = gdn.getDetails();
            gdn.setDetails(null);
            GdnHeader GRN = gdnHeaderRepository.save(gdn);
            for (GdnDetails gdnDetails : GRNDetails) {
                gdnDetails.setHeader(GRN);
            }
            GRN.setDetails(GRNDetails);
            GdnHeader save = gdnHeaderRepository.save(GRN);

            List<PurchaseOrderDetails> details = purchaseOrderDetailsRepository.getPODetails(save.getPoId());
            if (details.isEmpty()) {
                PurchaseOrderHeader findById = purchaseOrderHeaderRepository.findById(save.getPoId()).get();
                findById.setGdnFlag(false);
                findById.setGdnFlag(false);
                purchaseOrderHeaderRepository.save(findById);
            }
            return new ResponseEntity<String>("GDN Data Saved", HttpStatus.OK);

        } else {
            return new ResponseEntity<String>("Error in Saving GRN Data", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> updateGDN(@Valid GdnHeader gdn) {
        if (gdn.getDetails() != null) {
            gdn.setActive(true);
            List<GdnDetails> GRNDetails = gdn.getDetails();
            gdn.setDetails(null);
            GdnHeader GRN = gdnHeaderRepository.save(gdn);
            for (GdnDetails gdnDetails : GRNDetails) {
                gdnDetails.setHeader(GRN);
            }
            GRN.setDetails(GRNDetails);
            GdnHeader save = gdnHeaderRepository.save(GRN);

            List<PurchaseOrderDetails> details = purchaseOrderDetailsRepository.getPODetails(save.getPoId());
            if (details.isEmpty()) {
                PurchaseOrderHeader findById = purchaseOrderHeaderRepository.findById(save.getPoId()).get();
                findById.setGdnFlag(false);
                findById.setGdnFlag(false);
                purchaseOrderHeaderRepository.save(findById);
            } else {
                PurchaseOrderHeader findById = purchaseOrderHeaderRepository.findById(save.getPoId()).get();
                findById.setGdnFlag(true);
                findById.setGdnFlag(true);
                purchaseOrderHeaderRepository.save(findById);
            }
            return new ResponseEntity<String>("GDN Data Saved", HttpStatus.OK);

        } else {
            return new ResponseEntity<String>("Error in Saving GRN Data", HttpStatus.BAD_REQUEST);
        }
    }
    // ============================= GDN End==================

    // ============================= MRN Start====================

    @Override
    public List<MrnHeader> getMRN() {
        List<MrnHeader> findByStatus = mrnHeaderRepository.findByStatus(true);
        for (MrnHeader mrnHeader : findByStatus) {
            mrnHeader.setOutletName(outletRepository.findById(mrnHeader.getOutletId()).get().getName());
        }
        return findByStatus;
    }

    @Override
    public ResponseEntity<Object> getArticleDetails(Integer articleId) {
        Optional<VendorProductHeader> findByArticleId = vendorProductHeaderRepository.findByArticleId(articleId);
        findByArticleId.get().setUomName(uOMRepository.findById(findByArticleId.get().getUomId()).get().getName());
        return new ResponseEntity<Object>(findByArticleId.get(), HttpStatus.OK);
    }

    @Override
    public MrnHeader getMRNById(Integer id) {
        MrnHeader mrn = mrnHeaderRepository.findById(id).get();
        mrn.setOutletName(outletRepository.findById(mrn.getOutletId()).get().getName());
        for (MrnDetails detail : mrn.getDetails()) {
            detail.setUomName(uOMRepository.findById(detail.getUomId()).get().getName());
            detail.setArticleName(articlesRepository.findById(detail.getArticleId()).get().getName());
        }
        return mrn;
    }

    @Override
    public ResponseEntity<String> postMRN(@Valid MrnHeader mrn) {
        List<MrnDetails> details = mrn.getDetails();
        mrn.setStatus(true);
        mrn.setDetails(null);
        @Valid
        MrnHeader save = mrnHeaderRepository.save(mrn);
        for (MrnDetails detail : details) {
            detail.setActive(true);
            detail.setHeader(save);
        }
        save.setDetails(details);
        mrnHeaderRepository.save(save);
        return new ResponseEntity<String>("MRN Data Saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateMRN(@Valid MrnHeader mrn) {
        List<MrnDetails> details = mrn.getDetails();
        mrn.setStatus(true);
        mrn.setDetails(null);
        @Valid
        MrnHeader save = mrnHeaderRepository.save(mrn);
        for (MrnDetails detail : details) {
            detail.setActive(true);
            detail.setHeader(save);
        }
        save.setDetails(details);
        mrnHeaderRepository.save(save);
        return new ResponseEntity<String>("MRN Data Updated", HttpStatus.OK);
    }

    @Override
    public String deleteMRN(Integer id) {
        MrnHeader mrn = mrnHeaderRepository.findById(id).get();
        mrn.setStatus(false);
        for (MrnDetails detail : mrn.getDetails()) {
            detail.setActive(false);
        }
        mrnHeaderRepository.save(mrn);
        return "Material Rejection Note Data Deleted";
    }

    // ============================= MRN End====================

    // ============================= Pick List Start====================

    @Override
    public List<PickListHeader> getPickList() {
        List<PickListHeader> findByStatus = pickListHeaderRepository.findByStatus(true);
        for (PickListHeader pick : findByStatus) {
            pick.setFromName(outletRepository.findById(pick.getFromId()).get().getName());
            Outlet outlet = outletRepository.findById(pick.getToId()).get();
            pick.setToName(outlet.getName());
            pick.setToType(outlet.getType());
        }
        return findByStatus;
    }

    @Override
    public List<String> getAssetNoByArticle(Integer articleId) {
        List<String> assetNoForPickList = pickListHeaderRepository.getAssetNoForPickList(articleId);
        return assetNoForPickList;
    }

    @Override
    public PickListHeader getPickListById(Integer id) {
        PickListHeader pick = pickListHeaderRepository.findById(id).get();
        pick.setFromName(outletRepository.findById(pick.getFromId()).get().getName());
        Outlet outlet = outletRepository.findById(pick.getToId()).get();
        pick.setToName(outlet.getName());
        pick.setToType(outlet.getType());
        for (PickListDetails detail : pick.getDetails()) {
            detail.setUomName(uOMRepository.findById(detail.getUomId()).get().getName());
            detail.setArticleName(articlesRepository.findById(detail.getArticleId()).get().getName());
        }

        return pick;
    }

    @Override
    public ResponseEntity<String> postPickList(@Valid PickListHeader list) {
        List<PickListDetails> details = list.getDetails();
        list.setDetails(null);
        @Valid
        PickListHeader save = pickListHeaderRepository.save(list);
        for (PickListDetails pickListDetails : details) {
            pickListDetails.setHeader(save);
        }
        pickListHeaderRepository.save(save);
        return new ResponseEntity<String>("Pick List Data Saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updatePickList(@Valid PickListHeader list) {
        List<PickListDetails> details = list.getDetails();
        list.setDetails(null);
        @Valid
        PickListHeader save = pickListHeaderRepository.save(list);
        for (PickListDetails pickListDetails : details) {
            pickListDetails.setHeader(save);
        }
        pickListHeaderRepository.save(save);
        return new ResponseEntity<String>("Pick List Data Updated", HttpStatus.OK);
    }

    @Override
    public String deletePickList(Integer id) {
        PickListHeader pick = pickListHeaderRepository.findById(id).get();
        pick.setStatus(false);
        for (PickListDetails pickListDetails : pick.getDetails()) {
            pickListDetails.setStatus(false);
        }
        pickListHeaderRepository.save(pick);
        return "Pick List Data Deleted";
    }
    // ============================= Pick List End====================

    // ============================= Opening Stock START====================

    @Override
    public List<OpeningStock> getOpeningStock() {
        List<OpeningStock> openingstcok = openingStockRepository.findByActive(true);
        for (OpeningStock stock : openingstcok) {
            stock.setOutletName(outletRepository.findById(stock.getOutletId()).get().getName());
            stock.setUomName(uOMRepository.findById(stock.getUomId()).get().getName());
            stock.setArticleName(articlesRepository.findById(stock.getArticleId()).get().getName());
        }
        return openingstcok;
    }

    @Override
    public OpeningStock getOpeningStockById(Integer id) {
        OpeningStock stock = openingStockRepository.findById(id).get();
        stock.setOutletName(outletRepository.findById(stock.getOutletId()).get().getName());
        stock.setUomName(uOMRepository.findById(stock.getUomId()).get().getName());
        stock.setArticleName(articlesRepository.findById(stock.getArticleId()).get().getName());
        return stock;
    }

    @Override
    public ResponseEntity<String> postOpeningStock(@Valid OpeningStock stock) {
        List<OpenStockDTO> detail = stock.getDetail();
        for (OpenStockDTO dto : detail) {
            OpeningStock op = new OpeningStock();
            op.setOutletId(stock.getArticleId());
            op.setYearCd(stock.getYearCd());
            op.setArticleId(dto.getArticleId());
            op.setUomId(dto.getUomId());
            op.setQty(dto.getQty());
            op.setVal(dto.getVal());
            op.setActive(true);
            openingStockRepository.save(op);
        }

        return new ResponseEntity<String>("Stock Data Saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateOpeningStock(@Valid OpeningStock stock) {
        openingStockRepository.save(stock);
        return new ResponseEntity<String>("Stock Data Updated", HttpStatus.OK);
    }

    @Override
    public String deleteOpeningStock(Integer id) {
        OpeningStock stock = openingStockRepository.findById(id).get();
        stock.setActive(false);
        openingStockRepository.save(stock);
        return "Opening Stock Entry Deleted";
    }
    // ============================= Opening Stock End====================

    // ============================= Transfer Out==================
    @Override
    public List<StockTransferHeader> getPendingStockTransfer() {
        List<StockTransferHeader> findByTransferFlagAndStatus = stockTransferHeaderRepository
                .findByTransferFlagAndStatus(true, true);
        for (StockTransferHeader stock : findByTransferFlagAndStatus) {
            stock.setTransferFromName(outletRepository.findById(stock.getTransferFromId()).get().getName());
            stock.setTransferToName(outletRepository.findById(stock.getTransferToId()).get().getName());
        }
        return findByTransferFlagAndStatus;
    }

    @Override
    public List<PickListHeader> getPickListForStockTransfer(Integer outletId) {
        List<PickListHeader> picklist = pickListHeaderRepository.findByFromIdAndStatus(outletId, true);
        for (PickListHeader pick : picklist) {
            pick.setFromName(outletRepository.findById(pick.getFromId()).get().getName());
            Outlet outlet = outletRepository.findById(pick.getToId()).get();
            pick.setToName(outlet.getName());
            pick.setToType(outlet.getType());
            for (PickListDetails detail : pick.getDetails()) {
                detail.setUomName(uOMRepository.findById(detail.getUomId()).get().getName());
                detail.setArticleName(articlesRepository.findById(detail.getArticleId()).get().getName());
            }
        }
        return picklist;
    }

    @Override
    public ResponseEntity<String> postStockTransferOut(@Valid StockTransferHeader stock) {
        List<StockTransferDetails> details = stock.getDetails();
        stock.setStatus(true);
        stock.setTransferFlag(true);
        stock.setDetails(null);
        @Valid
        StockTransferHeader save = stockTransferHeaderRepository.save(stock);
        for (StockTransferDetails stockTransferDetails : details) {
            stockTransferDetails.setHeader(save);
        }
        stockTransferHeaderRepository.save(save);
        return new ResponseEntity<String>("Stock Transfer Data Saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateStockTransferOut(@Valid StockTransferHeader stock) {
        List<StockTransferDetails> details = stock.getDetails();
        stock.setStatus(true);
        stock.setTransferFlag(true);
        stock.setDetails(null);
        @Valid
        StockTransferHeader save = stockTransferHeaderRepository.save(stock);
        for (StockTransferDetails stockTransferDetails : details) {
            stockTransferDetails.setHeader(save);
        }
        stockTransferHeaderRepository.save(save);
        return new ResponseEntity<String>("Stock Transfer Data Updated", HttpStatus.OK);
    }
    // ============================= Transfer Out==================

    // ============================= Transfer In==================

    @Override
    public List<StockTransferHeader> getStockTransferIn(Integer outletId) {
        List<StockTransferHeader> stocktrans = stockTransferHeaderRepository
                .findByTransferToIdAndTransferFlagAndStatus(outletId, true, true);
        for (StockTransferHeader stock : stocktrans) {
            stock.setTransferFromName(outletRepository.findById(stock.getTransferFromId()).get().getName());
            stock.setTransferToName(outletRepository.findById(stock.getTransferToId()).get().getName());
            for (StockTransferDetails detail : stock.getDetails()) {
                detail.setUomName(uOMRepository.findById(detail.getUomId()).get().getName());
                detail.setArticleName(articlesRepository.findById(detail.getArticleId()).get().getName());
            }
        }
        return stocktrans;
    }

    @Override
    public ResponseEntity<String> postStockTransferIn(@Valid StockTransferHeader stock) {
        List<StockTransferDetails> details = stock.getDetails();
        stock.setStatus(true);
        stock.setTransferFlag(false);
        stock.setDetails(null);
        @Valid
        StockTransferHeader save = stockTransferHeaderRepository.save(stock);
        for (StockTransferDetails stockTransferDetails : details) {
            stockTransferDetails.setHeader(save);
        }
        stockTransferHeaderRepository.save(save);
        return new ResponseEntity<String>("Stock Transfer Data Saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateStockTransferIn(@Valid StockTransferHeader stock) {
        List<StockTransferDetails> details = stock.getDetails();
        stock.setStatus(true);
        stock.setTransferFlag(false);
        stock.setDetails(null);
        @Valid
        StockTransferHeader save = stockTransferHeaderRepository.save(stock);
        for (StockTransferDetails stockTransferDetails : details) {
            stockTransferDetails.setHeader(save);
        }
        stockTransferHeaderRepository.save(save);
        return new ResponseEntity<String>("Stock Transfer Data Updated", HttpStatus.OK);
    }

    // ============================= Transfer IN==================

}
