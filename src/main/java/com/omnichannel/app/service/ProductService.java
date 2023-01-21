package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

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

public interface ProductService {

	// ============================= Item Start==================

	public List<Item> getItem();

	public Item getItemByid(Integer id);

	public ResponseEntity<String> addItem(ItemDto item);

	public ResponseEntity<String> updateItem(@Valid Item item);

	public ResponseEntity<String> deleteItem(Integer id);
	
	public List<String> getProDivision();

	public List<String> getProFamily();

	public List<String> getProClass();
	
	public List<String> getProSubClass();
    public List<AssetDivFamily> getProDivFamily();

	// ============================= Item End==================

	// ============================= Brand Start==================

	public Brand getBrand(Integer id);

	public List<Brand> getBrand();

	public ResponseEntity<String> addBrand(@Valid Brand brand);

	public ResponseEntity<String> updateBrand(@Valid Brand brand);

	public ResponseEntity<String> deleteBrand(Integer id);

//============================= Brand End==================

	// ============================= Manufacturer Start==================

	public List<Manufacturer> getManufacturer();

	public Manufacturer getManufacturerById(Integer id);

	public ResponseEntity<String> addManufacturer(Manufacturer manufacturer);

	public ResponseEntity<String> updateManufacturer(Manufacturer manufacturer);

	public ResponseEntity<String> deleteManufacturer(Integer id);

//============================= Manufacturer End==================

	// ============================= Packtype Start==================

	public List<Packtype> getPacktype();

	public Packtype getPacktypeById(Integer id);

	public ResponseEntity<String> addPacktype(Packtype packtype);

	public ResponseEntity<String> updatePacktype(Packtype packtype);

	public ResponseEntity<String> deletePacktype(Integer id);

//============================= Packtype End==================

	// ============================= Purchase Group Start==================

	public List<PurchaseGroup> getPurchaseGroup();

	public PurchaseGroup getPurchaseGroupById(Integer id);

	public ResponseEntity<String> savePurchaseGroup(PurchaseGroup purchaseGroup);

	public ResponseEntity<String> updatePurchaseGroup(@Valid PurchaseGroup purchaseGroup);

	public ResponseEntity<String> deletePurchaseGroup(Integer id);

//============================= Purchase Group End==================
	
	//============================= UOM Start==================
	
	public List<UOM> getUOMDetails();
	public ResponseEntity<String> saveUOMDetails(UOM uom);
	public ResponseEntity<String> updateUOM(@Valid UOM uom);
	public ResponseEntity<String> deleteUOM(Integer id);
	public UOM getUOMDetailsById(Integer id);
	
//============================= End End==================
	
	//============================= ProductArticles Start==================
	
	public Articles getArticlesById(Integer id);
	public List<Articles> getArticles();
	public ResponseEntity<String> deleteArticles(Integer id);
	public List<Location> getStoreLocation();
//============================= ProductArticles End==================


	
	//============================= Article Status Start==================
	
	public List<ArticleStatus> getArticleStatus();

	public ArticleStatus getArticleStatusById(Integer id);

	public ResponseEntity<String> postArticleStatus(ArticleStatus articleStatus);

	public ResponseEntity<String> updateArticleStatus(ArticleStatus articleStatus);
	
	//============================= Article Status End==================

	



}
