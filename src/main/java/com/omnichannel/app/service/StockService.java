package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.omnichannel.app.model.purchase.PurchaseOrderHeader;
import com.omnichannel.app.model.stock.GdnHeader;
import com.omnichannel.app.model.stock.GrnHeader;
import com.omnichannel.app.model.stock.MrnHeader;
import com.omnichannel.app.model.stock.OpeningStock;
import com.omnichannel.app.model.stock.PickListHeader;
import com.omnichannel.app.model.stock.StockTransferHeader;

public interface StockService {

    // ============================= GRN Start==================

    public List<GrnHeader> getGRN();

    public GrnHeader getGRNById(Integer id);

    public List<PurchaseOrderHeader> getPOArticleGRN(Integer Id);

    public PurchaseOrderHeader getPODetailForGRN(Integer id);

    public ResponseEntity<String> postGRN(@Valid GrnHeader grn);

    public ResponseEntity<String> updateGRN(@Valid GrnHeader grn);

    // ============================= GRN End==================

    // ============================= GDN End===================

    public List<PurchaseOrderHeader> getPOArticleGDN(Integer Id);

    public PurchaseOrderHeader getPODetailForGDN(Integer id);

    public List<GdnHeader> getGDN();

    public GdnHeader getGDNById(Integer id);

    public ResponseEntity<String> postGDN(@Valid GdnHeader gdn);

    public ResponseEntity<String> updateGDN(@Valid GdnHeader gdn);

    // ============================= GDN End====================

    // ============================= MRN Start====================

    public List<MrnHeader> getMRN();

    public MrnHeader getMRNById(Integer id);

    public ResponseEntity<String> postMRN(@Valid MrnHeader mrn);

    public ResponseEntity<String> updateMRN(@Valid MrnHeader mrn);

    public String deleteMRN(Integer id);

    // ============================= MRN End====================

    // ============================= Pick List Start====================

    public List<PickListHeader> getPickList();

    public ResponseEntity<Object> getArticleDetails(Integer articleId);

    public List<String> getAssetNoByArticle(Integer articleId);

    public PickListHeader getPickListById(Integer id);

    public ResponseEntity<String> postPickList(@Valid PickListHeader list);

    public ResponseEntity<String> updatePickList(@Valid PickListHeader list);

    public String deletePickList(Integer id);

    // ============================= Pick List End====================

    // ============================= Opening Stock START====================

    public List<OpeningStock> getOpeningStock();

    public OpeningStock getOpeningStockById(Integer id);

    public ResponseEntity<String> postOpeningStock(@Valid OpeningStock stock);

    public ResponseEntity<String> updateOpeningStock(@Valid OpeningStock stock);

    public String deleteOpeningStock(Integer id);
    // ============================= Opening Stock End====================

    // ============================= Transfer Out==================
    public List<StockTransferHeader> getPendingStockTransfer();

    public List<PickListHeader> getPickListForStockTransfer(Integer outletId);

    public ResponseEntity<String> postStockTransferOut(@Valid StockTransferHeader stock);

    public ResponseEntity<String> updateStockTransferOut(@Valid StockTransferHeader stock);

    // ============================= Transfer Out==================
    // ============================= Transfer In==================

    public List<StockTransferHeader> getStockTransferIn(Integer outletId);

    public ResponseEntity<String> postStockTransferIn(@Valid StockTransferHeader stock);

    public ResponseEntity<String> updateStockTransferIn(@Valid StockTransferHeader stock);

    // ============================= Transfer IN==================

}
