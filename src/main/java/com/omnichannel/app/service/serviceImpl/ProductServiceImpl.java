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

import com.omnichannel.app.model.ItemDTO.AssetDivFamily;
import com.omnichannel.app.model.ItemDTO.ItemDto;
import com.omnichannel.app.model.ItemDTO.NewItem;
import com.omnichannel.app.model.ItemDTO.ProSubClass;
import com.omnichannel.app.model.admin.Transactionlog;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.product.ArticleStatus;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.model.product.Brand;
import com.omnichannel.app.model.product.Item;
import com.omnichannel.app.model.product.Manufacturer;
import com.omnichannel.app.model.product.Packtype;
import com.omnichannel.app.model.product.PurchaseGroup;
import com.omnichannel.app.model.product.UOM;
import com.omnichannel.app.repository.admin.TransactionlogRepository;
import com.omnichannel.app.repository.master.LocationRepository;
import com.omnichannel.app.repository.master.OutletRepository;
import com.omnichannel.app.repository.product.ArticleStatusRepository;
import com.omnichannel.app.repository.product.ArticlesRepository;
import com.omnichannel.app.repository.product.BrandRepository;
import com.omnichannel.app.repository.product.ItemRepository;
import com.omnichannel.app.repository.product.ManufacturerRepository;
import com.omnichannel.app.repository.product.PacktypeRepository;
import com.omnichannel.app.repository.product.PurchaseGroupRepository;
import com.omnichannel.app.repository.product.UOMRepository;
import com.omnichannel.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	BrandRepository brandRepository;
	@Autowired
	ManufacturerRepository manufacturerRepository;
	@Autowired
	PacktypeRepository packtypeRepository;
	@Autowired
	PurchaseGroupRepository purchaseGroupRepository;
	@Autowired
	UOMRepository uOMRepository;
	@Autowired
	ArticlesRepository articlesRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	OutletRepository outletRepository;

	@Autowired
	ArticleStatusRepository articleStatusRepository;
	
	@Autowired
	TransactionlogRepository transactionlogRepository;

	// ============================= Item Start==================

	@Override
	public List<Item> getItem() {
		List<Item> collect = itemRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Item getItemByid(Integer id) {
		Optional<Item> findById = itemRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addItem(ItemDto item) {
		int a=0;
		List<NewItem> product = item.getNew_items();
		for (NewItem nw : product) {
			List<ProSubClass> subcls = nw.getPro_sub_class();
			for (ProSubClass psc : subcls) {
				Item it = new Item();
				it.setPro_division(nw.getPro_division().toUpperCase());
				it.setPro_family(nw.getPro_family().toUpperCase());
				it.setPro_class(nw.getPro_class().toUpperCase());
				it.setPro_sub_class(psc.getSub_class().toUpperCase());
				it.setStatus(true);
				try {
					itemRepository.save(it);
				}
				catch (Exception e) {
					System.out.println("Already In the database"+e);
					a++;
				}
			}
		}
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity("New Item Added");
		transactionlogRepository.save(trlog);
		
		if(a==0) {
			return new ResponseEntity<String>("Item save Successfully", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>("Item save Successfully but some entry are already in database", HttpStatus.CREATED);
		}
		
	}

	@Override
	public ResponseEntity<String> updateItem(@Valid Item item) {
		item.setPro_class(item.getPro_class().toUpperCase());
		item.setPro_division(item.getPro_division().toUpperCase());
		item.setPro_family(item.getPro_family().toUpperCase());
		item.setPro_sub_class(item.getPro_sub_class().toUpperCase());
		item.setStatus(true);
		try {
			@Valid
			Item save = itemRepository.save(item);
			
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity("Old Item Updated");
			transactionlogRepository.save(trlog);
			
			return new ResponseEntity<String>("Item update Successfully", HttpStatus.CREATED);
		}
		catch (Exception e) {
			System.out.println("Already In the database"+e);
			return new ResponseEntity<String>("Already in database", HttpStatus.FORBIDDEN);
		}
		
	}

	@Override
	public ResponseEntity<String> deleteItem(Integer id) {
		Optional<Item> findById = itemRepository.findById(id);
		findById.get().setStatus(false);
		Item save = itemRepository.save(findById.get());
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getPro_sub_class()+" Item Deleted");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>("Item Deleted Successfully", HttpStatus.CREATED);
	}

	@Override
	public List<String> getProDivision() {
		List<Item> collect = itemRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		List<String> Unique = collect.stream().map(urEntity -> urEntity.getPro_division()).distinct()
				.collect(Collectors.toList());
		return Unique;
	}

	@Override
	public List<String> getProFamily() {
		List<Item> collect = itemRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		List<String> Unique = collect.stream().map(urEntity -> urEntity.getPro_family()).distinct()
				.collect(Collectors.toList());
		return Unique;
	}

	@Override
	public List<String> getProClass() {
		List<Item> collect = itemRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		List<String> Unique = collect.stream().map(urEntity -> urEntity.getPro_class()).distinct()
				.collect(Collectors.toList());
		return Unique;
	}

	@Override
	public List<String> getProSubClass() {
		List<Item> collect = itemRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		List<String> Unique = collect.stream().map(urEntity -> urEntity.getPro_sub_class()).distinct()
				.collect(Collectors.toList());
		return Unique;
	}
	
    @Override
    public List<AssetDivFamily> getProDivFamily() {
        return itemRepository.distinctDivFamily();
    }

	// ============================= Item End==================

	// ============================= Brand Start==================

	@Override
	public List<Brand> getBrand() {
		List<Brand> collect = brandRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Brand getBrand(Integer id) {
		Optional<Brand> findById = brandRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addBrand(Brand brand) {
		String s = brand.getBrandName().toUpperCase();
		if (brandRepository.existsByBrandName(s)) {
			Brand findByName = brandRepository.findByBrandName(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(brand.getBrandName() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Brand save = brandRepository.save(findByName);
				return new ResponseEntity<String>(save.getBrandName() + " Brand re-enable", HttpStatus.CREATED);
			}
		} else {
			brand.setStatus(true);
			brand.setBrandName(s);
			Brand save = brandRepository.save(brand);
			
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getBrandName() + "Brand Saved");
			transactionlogRepository.save(trlog);
			
			return new ResponseEntity<String>(save.getBrandName() + " Brand Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updateBrand(Brand brand) {
		brand.setStatus(true);
		brand.setBrandName(brand.getBrandName().toUpperCase());
		Brand save = brandRepository.save(brand);
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getBrandName() + "Brand updated");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(save.getBrandName() + " Brand Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteBrand(Integer id) {
		Optional<Brand> findById = brandRepository.findById(id);
		findById.get().setStatus(false);
		brandRepository.save(findById.get());
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getBrandName() + " Brand Deleted");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(findById.get().getBrandName() + " Brand Delete Successfully",
				HttpStatus.CREATED);
	}

	// ============================= Brand End==================

	// ============================= Manufacturer Start==================

	@Override
	public List<Manufacturer> getManufacturer() {
		List<Manufacturer> collect = manufacturerRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Manufacturer getManufacturerById(Integer id) {
		Optional<Manufacturer> findById = manufacturerRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addManufacturer(Manufacturer manufacturer) {

		String s = manufacturer.getManufacname().toUpperCase();
		if (manufacturerRepository.existsByManufacname(s)) {
			Manufacturer findByName = manufacturerRepository.findByManufacname(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(manufacturer.getManufacname() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Manufacturer save = manufacturerRepository.save(findByName);
				return new ResponseEntity<String>(save.getManufacname() + " Manufacturer re-enable",
						HttpStatus.CREATED);
			}

		} else {
			manufacturer.setStatus(true);
			manufacturer.setManufacname(s);
			Manufacturer save = manufacturerRepository.save(manufacturer);
			
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getManufacname() + " Manufacturer Saved");
			transactionlogRepository.save(trlog);
			
			return new ResponseEntity<String>(save.getManufacname() + " Manufacturer Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updateManufacturer(Manufacturer manufacturer) {
		manufacturer.setStatus(true);
		manufacturer.setManufacname(manufacturer.getManufacname().toUpperCase());
		Manufacturer save = manufacturerRepository.save(manufacturer);
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getManufacname() + " Manufacturer Updated");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(save.getManufacname() + " Manufacturer Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteManufacturer(Integer id) {
		Optional<Manufacturer> findById = manufacturerRepository.findById(id);
		findById.get().setStatus(false);
		manufacturerRepository.save(findById.get());
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getManufacname() + " Manufacturer Deleted");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(findById.get().getManufacname() + " Manufacturer Delete Successfully",
				HttpStatus.CREATED);
	}
	// ============================= Manufacturer End==================

	// ============================= Packtype Start==================

	@Override
	public List<Packtype> getPacktype() {
		List<Packtype> collect = packtypeRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Packtype getPacktypeById(Integer id) {
		Optional<Packtype> findById = packtypeRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> addPacktype(Packtype packtype) {

		String s = packtype.getPacktypename().toUpperCase();
		if (packtypeRepository.existsByPacktypename(s)) {
			Packtype findByName = packtypeRepository.findByPacktypename(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(packtype.getPacktypename() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				Packtype save = packtypeRepository.save(findByName);
				return new ResponseEntity<String>(save.getPacktypename() + " Packtype re-enable", HttpStatus.CREATED);
			}

		} else {
			packtype.setStatus(true);
			packtype.setPacktypename(s);
			Packtype save = packtypeRepository.save(packtype);
			
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getPacktypename() + " Packtype Added");
			transactionlogRepository.save(trlog);
			
			return new ResponseEntity<String>(save.getPacktypename() + " Packtype Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updatePacktype(Packtype packtype) {
		packtype.setStatus(true);
		packtype.setPacktypename(packtype.getPacktypename().toUpperCase());
		Packtype save = packtypeRepository.save(packtype);
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getPacktypename() + " Packtype Updated");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(save.getPacktypename() + " Packtype Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deletePacktype(Integer id) {
		Optional<Packtype> findById = packtypeRepository.findById(id);
		findById.get().setStatus(false);
		packtypeRepository.save(findById.get());
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getPacktypename() + " Packtype Deleted");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(findById.get().getPacktypename() + " Packtype Delete Successfully",
				HttpStatus.CREATED);
	}

	// ============================= Packtype End==================

	// ============================= Purchase Group Start==================

	@Override
	public List<PurchaseGroup> getPurchaseGroup() {

		List<PurchaseGroup> collect = purchaseGroupRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public PurchaseGroup getPurchaseGroupById(Integer id) {
		Optional<PurchaseGroup> findById = purchaseGroupRepository.findById(id);
		return findById.get();
	}

	@Override
	public ResponseEntity<String> savePurchaseGroup(PurchaseGroup purchaseGroup) {
		String s = purchaseGroup.getName().toUpperCase();
		if (purchaseGroupRepository.existsByName(s)) {
			PurchaseGroup findByName = purchaseGroupRepository.findByName(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(purchaseGroup.getName() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				PurchaseGroup save = purchaseGroupRepository.save(findByName);
				return new ResponseEntity<String>(save.getName() + " Purchase Group re-enable", HttpStatus.CREATED);
			}

		} else {
			purchaseGroup.setStatus(true);
			purchaseGroup.setName(s);
			PurchaseGroup save = purchaseGroupRepository.save(purchaseGroup);
			
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getName() + " Purchase Group added");
			transactionlogRepository.save(trlog);
			
			return new ResponseEntity<String>(save.getName() + " Purchase Group Saved", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> updatePurchaseGroup(@Valid PurchaseGroup purchaseGroup) {
		purchaseGroup.setStatus(true);
		purchaseGroup.setName(purchaseGroup.getName().toUpperCase());
		PurchaseGroup save = purchaseGroupRepository.save(purchaseGroup);
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getName() + " Purchase Group Updated");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(save.getName() + " Purchase Group Updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deletePurchaseGroup(Integer id) {
		Optional<PurchaseGroup> findById = purchaseGroupRepository.findById(id);
		findById.get().setStatus(false);
		purchaseGroupRepository.save(findById.get());
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getName() + " Packtype Delete Successfully");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(findById.get().getName() + " Packtype Delete Successfully",
				HttpStatus.CREATED);
	}

	// ============================= Purchase Group End==================

	// ============================= UOM Start==================

	@Override
	public List<UOM> getUOMDetails() {
		List<UOM> collect = uOMRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public UOM getUOMDetailsById(Integer id) {
		Optional<UOM> uomResponse = uOMRepository.findById(id);
		UOM uom = uomResponse.get();
		return uom;
	}

	@Override
	public ResponseEntity<String> saveUOMDetails(UOM uom) {
		String s = uom.getName().toUpperCase();
		if (uOMRepository.existsByName(s)) {
			UOM findByName = uOMRepository.findByName(s);
			if (findByName.getStatus()) {
				return new ResponseEntity<String>(uom.getName() + " already exists in the database",
						HttpStatus.BAD_REQUEST);
			} else {
				findByName.setStatus(true);
				UOM save = uOMRepository.save(findByName);
				return new ResponseEntity<String>(save.getShortName() + " UOM re-enable", HttpStatus.CREATED);
			}
		} else {
			uom.setStatus(true);
			uom.setShortName(uom.getShortName().toUpperCase());
			uom.setName(s);
			UOM save = uOMRepository.save(uom);
			
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			Transactionlog trlog = new Transactionlog();
			trlog.setUsername(name);
			trlog.setTimestamp(LocalDateTime.now());
			trlog.setActivity(save.getShortName() + " UOM added");
			transactionlogRepository.save(trlog);
			
			return new ResponseEntity<String>(save.getShortName() + " UOM Saved", HttpStatus.CREATED);
		}

	}

	@Override
	public ResponseEntity<String> updateUOM(UOM uom) {
		uom.setStatus(true);
		uom.setName(uom.getName().toUpperCase());
		uom.setShortName(uom.getShortName().toUpperCase());
		UOM save = uOMRepository.save(uom);
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getShortName() + " UOM updated");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(save.getShortName() + "UOM Updated  ", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> deleteUOM(Integer id) {
		Optional<UOM> findById = uOMRepository.findById(id);
		findById.get().setStatus(false);
		uOMRepository.save(findById.get());
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getShortName() + "UOM Deleted");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(findById.get().getShortName() + "UOM Delete Successfully",
				HttpStatus.CREATED);
	}

	// ============================= UOM End==================

	// ============================= Articles Start==================

	@Override
	public List<Articles> getArticles() {
		List<Articles> collect = articlesRepository.findAll();
		for (Articles art : collect) {
			Optional<Item> item = itemRepository.findById(art.getProductId());
			art.setPro_class(item.get().getPro_class());
			art.setPro_division(item.get().getPro_division());
			art.setPro_family(item.get().getPro_family());
			art.setPro_sub_class(item.get().getPro_sub_class());
		}
		return collect;

	}

	@Override
	public Articles getArticlesById(Integer id) {
		Optional<Articles> findById = articlesRepository.findById(id);
		Optional<Item> item = itemRepository.findById(findById.get().getProductId());
		findById.get().setPro_class(item.get().getPro_class());
		findById.get().setPro_division(item.get().getPro_division());
		findById.get().setPro_family(item.get().getPro_family());
		findById.get().setPro_sub_class(item.get().getPro_sub_class());
		return findById.get();
	}

	@Override
	public ResponseEntity<String> deleteArticles(Integer id) {
		Optional<Articles> findById = articlesRepository.findById(id);
		findById.get().setStatus(false);
		articlesRepository.save(findById.get());
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(findById.get().getName() + "Articles Delete Successfully");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>(findById.get().getName() + "Articles Delete Successfully",
				HttpStatus.CREATED);
	}


	@Override
	public List<Location> getStoreLocation() {
		List<Outlet> collect = outletRepository.findAll().stream().filter(t -> t.getType().equals("S"))
				.collect(Collectors.toList());
		List<Location> loc = new ArrayList<>();
		for (Outlet outlet : collect) {
			Optional<Location> findById = locationRepository.findById(outlet.getLocationid());
			loc.add(findById.get());
		}
		return loc;
	}
	// ============================= Articles End==================

	// ============================= Article Status Start==================
	@Override
	public List<ArticleStatus> getArticleStatus() {
		List<ArticleStatus> findAll = articleStatusRepository.findAll();
		for (ArticleStatus articleStatus : findAll) {
			Optional<Item> item = itemRepository.findById(articleStatus.getProductid());
			articleStatus.setPro_class(item.get().getPro_class());
			articleStatus.setPro_division(item.get().getPro_division());
			articleStatus.setPro_family(item.get().getPro_family());
			articleStatus.setPro_sub_class(item.get().getPro_sub_class());
			Optional<Articles> findById2 = articlesRepository.findById(articleStatus.getArticleid());
			articleStatus.setArticleName(findById2.get().getName());
		}
		return findAll;
	}
	
	@Override
	public ArticleStatus getArticleStatusById(Integer id) {
		Optional<ArticleStatus> findById = articleStatusRepository.findById(id);
		Optional<Item> item = itemRepository.findById(findById.get().getProductid());
		findById.get().setPro_class(item.get().getPro_class());
		findById.get().setPro_division(item.get().getPro_division());
		findById.get().setPro_family(item.get().getPro_family());
		findById.get().setPro_sub_class(item.get().getPro_sub_class());
		Optional<Articles> findById2 = articlesRepository.findById(findById.get().getArticleid());
		findById.get().setArticleName(findById2.get().getName());
		return findById.get();
	}
	
	@Override
	public ResponseEntity<String> postArticleStatus(ArticleStatus articleStatus) {
		Optional<Articles> findById = articlesRepository.findById(articleStatus.getArticleid());
		if(!findById.get().getStatus().equals(articleStatus.getStatus())) {
			Articles art = new Articles();
			art = findById.get();
			art.setStatus(articleStatus.getStatus());
			articlesRepository.save(art);
		}
		
		if(!findById.get().getBlockProcurement().equals(articleStatus.getBlockProcurement())) {
			Articles art = new Articles();
			art = findById.get();
			art.setBlockProcurement(articleStatus.getBlockProcurement());
			articlesRepository.save(art);
		}
		
		ArticleStatus save = articleStatusRepository.save(articleStatus);
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getArticleName()+" Artical status changed");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>("Artical Status Change Successfully", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<String> updateArticleStatus(ArticleStatus articleStatus) {
		Optional<Articles> findById = articlesRepository.findById(articleStatus.getArticleid());
		if(!findById.get().getStatus().equals(articleStatus.getStatus())) {
			Articles art = new Articles();
			art = findById.get();
			art.setStatus(articleStatus.getStatus());
			articlesRepository.save(art);
		}
		
		if(!findById.get().getBlockProcurement().equals(articleStatus.getBlockProcurement())) {
			Articles art = new Articles();
			art = findById.get();
			art.setBlockProcurement(articleStatus.getBlockProcurement());
			articlesRepository.save(art);
		}
		ArticleStatus save = articleStatusRepository.save(articleStatus);
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Transactionlog trlog = new Transactionlog();
		trlog.setUsername(name);
		trlog.setTimestamp(LocalDateTime.now());
		trlog.setActivity(save.getArticleName()+" Artical status Updated");
		transactionlogRepository.save(trlog);
		
		return new ResponseEntity<String>("Artical Status Update Successfully", HttpStatus.CREATED);
	}



	// ============================= Article Status End==================






}
