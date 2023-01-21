package com.omnichannel.app.controller.product;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.model.ItemDTO.AssetDivFamily;
import com.omnichannel.app.model.ItemDTO.ItemDto;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.product.ArticleStatus;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.model.product.Brand;
import com.omnichannel.app.model.product.Item;
import com.omnichannel.app.model.product.Manufacturer;
import com.omnichannel.app.model.product.Packtype;
import com.omnichannel.app.model.product.PurchaseGroup;
import com.omnichannel.app.model.product.UOM;
import com.omnichannel.app.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/master")
public class ProductController {
    /**
     * 
     * 
     * @author Rajsekhar Acharya
     */
    @Autowired
    ProductService productService;

    // ============================= Item Start==================

    @Operation(summary = "API Item Data Fetch")
    @GetMapping(value = "/getItem")
    public List<Item> getItem() {
        return productService.getItem();
    }

    @Operation(summary = "API Item Data Fetch By Id")
    @GetMapping(value = "/getItemByid")
    public Item getItemByid(@RequestParam Integer id) {
        return productService.getItemByid(id);
    }

    @Operation(summary = "API Item Data Save")
    @PostMapping(value = "/postItem", consumes = "application/json")
    public ResponseEntity<String> addItem(@RequestBody ItemDto item) {
        return productService.addItem(item);
    }

    @Operation(summary = "API Item Data Modify")
    @PutMapping(value = "/updateItem", consumes = "application/json")
    public ResponseEntity<String> updateItem(@Valid @RequestBody Item item) {
        return productService.updateItem(item);
    }

    @Operation(summary = "API Item Data Deleted")
    @DeleteMapping(value = "/deleteItem")
    public ResponseEntity<String> deleteItem(@RequestParam Integer id) {
        return productService.deleteItem(id);
    }

    @Operation(summary = "API Item Division Data Fetch")
    @GetMapping(value = "/getProDivision")
    public List<String> getProDivision() {
        return productService.getProDivision();
    }

    @Operation(summary = "API Item Family Data Fetch")
    @GetMapping(value = "/getProFamily")
    public List<String> getProFamily() {
        return productService.getProFamily();
    }

    @Operation(summary = "API Item Class Data Fetch")
    @GetMapping(value = "/getProClass")
    public List<String> getProClass() {
        return productService.getProClass();
    }

    @Operation(summary = "API Item Sub Class Data Fetch")
    @GetMapping(value = "/getProSubClass")
    public List<String> getProSubClass() {
        return productService.getProSubClass();
    }

    @Operation(summary = "API Item DISTINCT Division and Family Data Fetch")
    @GetMapping(value = "/getProDivFamily")
    public List<AssetDivFamily> getProDivFamily() {
        return productService.getProDivFamily();
    }

    // ============================= Item End==================

    // ============================= Brand Start==================

    @GetMapping(value = "/getBrandId")
    public Brand getBrandId(@RequestParam Integer id) {
        return productService.getBrand(id);
    }

    @GetMapping(value = "/getBrand")
    public List<Brand> getBrand() {
        return productService.getBrand();
    }

    @PostMapping(value = "/postBrand", consumes = "application/json")
    public ResponseEntity<String> addBrand(@Valid @RequestBody Brand brand) {
        return productService.addBrand(brand);
    }

    @PutMapping(value = "/updateBrand", consumes = "application/json")
    public ResponseEntity<String> updateBrand(@Valid @RequestBody Brand brand) {
        return productService.updateBrand(brand);
    }

    @DeleteMapping(value = "/deleteBrand")
    public ResponseEntity<String> deleteBrand(@RequestParam Integer id) {
        return productService.deleteBrand(id);
    }

    // ============================= Brand End==================

    // ============================= Manufacturer Start==================

    @Operation(summary = "API Manufacturer Data Fetch")
    @GetMapping(value = "/getManufacturer")
    public List<Manufacturer> getManufacturer() {
        return productService.getManufacturer();
    }

    @Operation(summary = "API Packtype Data Fetch")
    @GetMapping(value = "/getManufacturerById")
    public Manufacturer getManufacturerById(@RequestParam Integer id) {
        return productService.getManufacturerById(id);
    }

    @Operation(summary = "API Manufacturer Data Save")
    @PostMapping(value = "/postManufacturer", consumes = "application/json")
    public ResponseEntity<String> addManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        return productService.addManufacturer(manufacturer);
    }

    @Operation(summary = "API Manufacturer Data Modify")
    @PutMapping(value = "/updateManufacturer", consumes = "application/json")
    public ResponseEntity<String> updateManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        return productService.updateManufacturer(manufacturer);
    }

    @Operation(summary = "API Manufacturer Data Deleted")
    @DeleteMapping(value = "/deleteManufacturer")
    public ResponseEntity<String> deleteManufacturer(@RequestParam Integer id) {
        return productService.deleteManufacturer(id);
    }

    // ============================= Manufacturer End==================

    // ============================= Packtype Start==================

    @Operation(summary = "API Packtype Data Fetch")
    @GetMapping(value = "/getPacktype")
    public List<Packtype> getPacktypes() {
        return productService.getPacktype();
    }

    @Operation(summary = "API Packtype Data Fetch")
    @GetMapping(value = "/getPacktypeById")
    public Packtype getPacktypeById(@RequestParam Integer id) {
        return productService.getPacktypeById(id);
    }

    @Operation(summary = "API Packtype Data Save")
    @PostMapping(value = "/postPacktype", consumes = "application/json")
    public ResponseEntity<String> addPacktype(@Valid @RequestBody Packtype packtype) {
        return productService.addPacktype(packtype);
    }

    @Operation(summary = "API Packtype Data Modify")
    @PutMapping(value = "/updatePacktype", consumes = "application/json")
    public ResponseEntity<String> updatePacktype(@Valid @RequestBody Packtype packtype) {
        return productService.updatePacktype(packtype);
    }

    @Operation(summary = "API Packtype Data Deleted")
    @DeleteMapping(value = "/deletePacktype")
    public ResponseEntity<String> deletePacktype(@RequestParam Integer id) {
        return productService.deletePacktype(id);
    }

    // ============================= Packtype End==================

    // ============================= Purchase Group Start==================

    @GetMapping(value = "/getPurchaseGroup")
    public List<PurchaseGroup> getPurchaseGroup() {
        return productService.getPurchaseGroup();

    }

    @GetMapping(value = "/getPurchaseGroupById")
    public PurchaseGroup getPurchaseGroupById(@RequestParam Integer id) {
        return productService.getPurchaseGroupById(id);
    }

    @PostMapping(value = "/savePurchaseGroup", consumes = "application/json")
    public ResponseEntity<String> savePurchaseGroup(@RequestBody PurchaseGroup purchaseGroup) {
        System.out.println(purchaseGroup);
        return productService.savePurchaseGroup(purchaseGroup);

    }

    @PutMapping(value = "/updatePurchaseGroup", consumes = "application/json")
    public ResponseEntity<String> updatePurchaseGroup(@Valid @RequestBody PurchaseGroup purchaseGroup) {
        return productService.updatePurchaseGroup(purchaseGroup);
    }

    @DeleteMapping(value = "/deletePurchaseGroup")
    public ResponseEntity<String> deletePurchaseGroup(@RequestParam Integer id) {
        return productService.deletePurchaseGroup(id);
    }

    // ============================= Purchase Group End==================

    // ============================= UOM Start==================

    @GetMapping(value = "/getUOMDetails")
    public List<UOM> getUOMDetails() {
        return productService.getUOMDetails();

    }

    @GetMapping(value = "/getUOMDetailsById")
    public UOM getUOMDetailsById(@RequestParam Integer id) {
        return productService.getUOMDetailsById(id);
    }

    @PostMapping(value = "/saveUOMDetails", consumes = "application/json")
    public ResponseEntity<String> saveUOMDetails(@RequestBody UOM uom) {
        return productService.saveUOMDetails(uom);

    }

    @PutMapping(value = "/updateUOM", consumes = "application/json")
    public ResponseEntity<String> updateUOM(@Valid @RequestBody UOM uom) {
        return productService.updateUOM(uom);
    }

    @DeleteMapping(value = "/deleteUOM")
    public ResponseEntity<String> deleteUOM(@RequestParam Integer id) {
        return productService.deleteUOM(id);
    }

    // ============================= UOM End==================

    // ============================= Articles Start==================

    @GetMapping(value = "/getArticlesById")
    public Articles getArticlesById(@RequestParam Integer id) {
        return productService.getArticlesById(id);
    }

    @GetMapping(value = "/getStoreLocation")
    public List<Location> getStoreLocation() {
        return productService.getStoreLocation();
    }

    @GetMapping(value = "/getArticles")
    public List<Articles> getArticles() {
        return productService.getArticles();
    }

    @DeleteMapping(value = "/deleteArticles")
    public ResponseEntity<String> deleteArticles(@RequestParam Integer id) {
        return productService.deleteArticles(id);
    }

    // ============================= Articles End==================

    // ============================= Article Status Start==================

    @GetMapping(value = "/getArticleStatus")
    public List<ArticleStatus> getArticleStatus() {
        return productService.getArticleStatus();
    }

    @GetMapping(value = "/getArticleStatusById")
    public ArticleStatus getArticleStatusById(@RequestParam Integer id) {
        return productService.getArticleStatusById(id);
    }

    @PostMapping(value = "/postArticleStatus")
    ResponseEntity<String> postArticleStatus(@RequestBody ArticleStatus articleStatus) {
        return productService.postArticleStatus(articleStatus);
    }

    @PutMapping(value = "/updateArticleStatus")
    ResponseEntity<String> updateArticleStatus(@RequestBody ArticleStatus articleStatus) {
        return productService.updateArticleStatus(articleStatus);
    }

    // ============================= Article Status End==================

}
