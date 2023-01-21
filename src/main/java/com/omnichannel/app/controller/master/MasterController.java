package com.omnichannel.app.controller.master;

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

import com.omnichannel.app.model.DTO.ClusterMapping;
import com.omnichannel.app.model.DTO.POBILL.POHeader;
import com.omnichannel.app.model.admin.ApplicationSetup;
import com.omnichannel.app.model.master.AssetsType;
import com.omnichannel.app.model.master.Bay;
import com.omnichannel.app.model.master.City;
import com.omnichannel.app.model.master.Cluster;
import com.omnichannel.app.model.master.Country;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.master.Paymentmode;
import com.omnichannel.app.model.master.Reason;
import com.omnichannel.app.model.master.Region;
import com.omnichannel.app.model.master.Schedule;
import com.omnichannel.app.model.master.State;
import com.omnichannel.app.model.master.Tot;
import com.omnichannel.app.model.master.TotDetail;
import com.omnichannel.app.model.master.TotHeader;
import com.omnichannel.app.model.master.Transportation;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.model.vendor.VendorInvitation;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.service.MasterService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/master")
public class MasterController {

	@Autowired
	MasterService masterService;

	/**
	 * 
	 * 
	 * @author Rajsekhar Acharya
	 */
	
	//  =========================== Terms Of Trade Creation ===========================//
	
	@Operation(summary = "API Tot-creation Data Save")
	@PostMapping(value = "/addSaveTotCreation", consumes = "application/json")
	public ResponseEntity<String> addSaveTotCreation(@RequestBody TotHeader totHeader) {
		return masterService.addSaveTotCreation(totHeader);
	}
	
	@Operation(summary = "API To Get Particulars Data ")
	@GetMapping(value="/getParticulars")
	public List<Tot>getParticulars(){
		return masterService.getParticulars();
	}
	
	@Operation(summary = "API To Get Article Data For ToT By Vendor Id")
	@GetMapping(value="/getArticlesByVendorForToT")
	public List<VendorProductHeader>getArticlesByVendorForToT(@RequestParam Integer vendorId){
		return masterService.getArticlesByVendorForToT(vendorId);
	}
	
	@GetMapping(value = "/getTotCreationById")
	public TotHeader getTotCreationById(@RequestParam Integer id) {
		return masterService.getTotCreationById(id);
	}
	
	@DeleteMapping(value = "/deleteTotCreation", produces = "application/json")
	public ResponseEntity<String> deleteTotCreation(@RequestParam Integer id) {
		return masterService.deleteTotCreation(id);
	}
	
	   @Operation(summary = "API TO UPDATE TERMS OF TRADE CREATION")
	    @PutMapping(value = "/EditTotCreation", consumes = "application/json")
	    public ResponseEntity<String> EditTotCreation(@Valid @RequestBody TotHeader totHeader) {
	        return masterService.EditTotCreation(totHeader);
	    }
	   
		@Operation( summary = "API To Fetch ToT Creation Data")
		@GetMapping(value = "/getTotCreation")
		public List<TotHeader> getTotCreation() {
			return masterService.getTotCreation();
		}
		
		@Operation(summary = "API To Get ValidFor Data ")
		@GetMapping(value="/getMasterValidFor")
		public List<Tot>getMasterValidFor(){
			return masterService.getMasterValidFor();
		}
	   
	 
	
    //  =========================== Terms Of Trade Master=====================//
	  
	 @Operation(summary = "API TOT Data Save")
	    @PostMapping(value = "/postToT", consumes = "application/json")
	    public ResponseEntity<String> addTot(@RequestBody Tot tot) {
	        return masterService.addTot(tot);
	    }
	
	 
	 

	
	@PutMapping(value = "/updateTot", consumes = "application/json")
	public ResponseEntity<String> updateTot(@RequestBody Tot tot) {
		return masterService.updateTot(tot);
	}
	
	@GetMapping(value = "/getTot")
	public List<Tot> getTot() {
		return masterService.getTot();
	}
	
	@GetMapping(value = "/getTotById")
	public Tot getTotById(@RequestParam Integer id) {
		return masterService.getTotById(id);
	}
	
	@DeleteMapping(value = "/deleteTot", produces = "application/json")
	public ResponseEntity<String> deleteTot(@RequestParam Integer id) {
		return masterService.deleteTot(id);
	}
	
// ==================== Tot Master End ==============================//
	

	
	// <!======================= Location========================>//

	@Operation(summary = "FETCH LIST OF COUNTRY")
	@GetMapping(value = "/getCountry")
	public List<Country> getCountry() {
		return masterService.getCountry();
	}

	@Operation(summary = "FETCH LIST OF STATE")
	@GetMapping(value = "/getState")
	public List<State> getState() {
		return masterService.getState();
	}

	@Operation(summary = "FETCH STATES LIST AS PER COUNTRY CODE")
	@GetMapping(value = "/getStateByCountryCode")
	public List<State> getStateByCountryCode(@RequestParam Integer id) {
		return masterService.getStateByCountryCode(id);
	}

	@GetMapping(value = "/getLocation")
	public List<Location> getLocation() {
		return masterService.getLocation();
	}

	@GetMapping(value = "/getLocationById")
	public Location getLocationById(@RequestParam Integer id) {
		return masterService.getLocationById(id);
	}

	@GetMapping(value = "/getLocationByName")
	public Location getLocationByName(@RequestParam String name) {
		return masterService.getLocationByName(name);
	}

	@PostMapping(value = "/postLocation", consumes = "application/json")
	public ResponseEntity<String> addLocation(@Valid @RequestBody Location location) {
		return masterService.addLocation(location);
	}

	@PutMapping(value = "/updateLocation", consumes = "application/json")
	public ResponseEntity<String> updateLocation(@Valid @RequestBody Location location) {
		return masterService.updateLocation(location);
	}

	@DeleteMapping(value = "/deleteLocation", produces = "application/json")
	public ResponseEntity<String> deleteLocation(@RequestParam Integer id) {
		return masterService.deleteLocation(id);
	}

	// <!============================= End Location==================>

	// <!============================= Start City==================>
	@GetMapping(value = "/getCitybyPincode")
	public  ResponseEntity<?> getCitybyPincode(@RequestParam Integer pin) {
		return masterService.getCitybyPincode(pin);
	}

	@GetMapping(value = "/getCitybyGlobalSearch")
	public  ResponseEntity<?> getCitybyGlobalSearch(@RequestParam String keyword) {
		return masterService.getCitybyGlobalSearch(keyword);
	}

	@GetMapping(value = "/pincodeAvailability")
	public  Boolean pincodeAvailability(@RequestParam Integer pin) {
		return masterService.pincodeAvailability(pin);
	}
	
	@PostMapping(value = "/postCity", consumes = "application/json")
	public ResponseEntity<String> addCity(@Valid @RequestBody City city) {
		return masterService.addCity(city);
	}
	@PutMapping(value = "/updateCity", consumes = "application/json")
	public ResponseEntity<String> updateCity(@Valid @RequestBody City city) {
		return masterService.updateCity(city);
	}

	// <!============================= End City==================>

	// ============================= Paymentmode Start==================

	@Operation(summary = "API Paymentmode Data Fetch")
	@GetMapping(value = "/getPaymentmode")
	public List<Paymentmode> getPaymentmode() {
		return masterService.getPaymentmode();
	}

	@Operation(summary = "API Paymentmode Data Fetch By Id")
	@GetMapping(value = "/getPaymentmodeByid")
	public Paymentmode getPaymentmodeByid(@RequestParam Integer id) {
		return masterService.getPaymentmodeByid(id);
	}

	@Operation(summary = "API Paymentmode Data Save")
	@PostMapping(value = "/postPaymentmode", consumes = "application/json")
	public ResponseEntity<String> addPaymentmode(@Valid @RequestBody Paymentmode paymentmode) {
		return masterService.addPaymentmode(paymentmode);
	}

	@Operation(summary = "API Paymentmode Data Modify")
	@PutMapping(value = "/updatePaymentmode", consumes = "application/json")
	public ResponseEntity<String> updatePaymentmode(@Valid @RequestBody Paymentmode paymentmode) {
		return masterService.updatePaymentmode(paymentmode);
	}

	@Operation(summary = "API Paymentmode Data Deleted")
	@DeleteMapping(value = "/deletePaymentmode")
	public ResponseEntity<String> deletePaymentmode(@RequestParam Integer id) {
		return masterService.deletePaymentmode(id);
	}

	// ============================= Paymentmode End==================

	// ============================= Region Setup Start==================

	@Operation(summary = "API Region Data Fetch")
	@GetMapping(value = "/getRegion")
	public List<Region> getRegion() {
		return masterService.getRegion();
	}

	@Operation(summary = "API Region Data Fetch BY ID")
	@GetMapping(value = "/getRegionByid")
	public Region getRegionByid(@RequestParam Integer id) {
		return masterService.getRegionByid(id);
	}

	@Operation(summary = "API Region Data Save")
	@PostMapping(value = "/postRegion", consumes = "application/json")
	public ResponseEntity<String> addRegion(@Valid @RequestBody Region region) {
		return masterService.addRegion(region);
	}

	@Operation(summary = "API Region Data Modify")
	@PutMapping(value = "/updateRegion", consumes = "application/json")
	public ResponseEntity<String> updateRegion(@Valid @RequestBody Region region) {
		return masterService.updateRegion(region);
	}

	@Operation(summary = "API Region Data Deleted")
	@DeleteMapping(value = "/deleteRegion")
	public ResponseEntity<String> deleteRegion(@RequestParam Integer id) {
		return masterService.deleteRegion(id);
	}

	// ============================= Region Setup End==================

	// ============================= Assets Type Setup Start==================

	@Operation(summary = "API AssetsType Data Fetch")
	@GetMapping(value = "/getAssetsType")
	public List<AssetsType> getAssetsType() {
		return masterService.getAssetsType();
	}

	@Operation(summary = "API AssetsType Data Fetch BY ID")
	@GetMapping(value = "/getAssetsTypeByid")
	public AssetsType getAssetsTypeByid(@RequestParam Integer id) {
		return masterService.getAssetsTypeByid(id);
	}

	@Operation(summary = "API AssetsType Data Save")
	@PostMapping(value = "/addAssetsType", consumes = "application/json")
	public ResponseEntity<String> addAssetsType(@Valid @RequestBody AssetsType assetsType) {
		return masterService.addAssetsType(assetsType);
	}

	@Operation(summary = "API AssetsType Data Modify")
	@PutMapping(value = "/updateAssetsType", consumes = "application/json")
	public ResponseEntity<String> updateAssetsType(@Valid @RequestBody AssetsType assetsType) {
		return masterService.updateAssetsType(assetsType);
	}

	@Operation(summary = "API AssetsType Data Deleted")
	@DeleteMapping(value = "/deleteAssetsType")
	public ResponseEntity<String> deleteAssetsType(@RequestParam Integer id) {
		return masterService.deleteAssetsType(id);
	}

	// ============================= Assets Type Setup End==================

	// ============================= Outlet Setup End==================

	@Operation(summary = "API Outlet Data Fetch")
	@GetMapping(value = "/getOutlet")
	public List<Outlet> getOutlet() {
		return masterService.getOutlet();
	}

	@Operation(summary = "API Outlet Data Fetch BY ID")
	@GetMapping(value = "/getOutletByid")
	public Outlet getOutletByid(@RequestParam Integer id) {
		return masterService.getOutletByid(id);
	}

	@Operation(summary = "API Outlet Data Save")
	@PostMapping(value = "/addOutlet", consumes = "application/json")
	public ResponseEntity<String> addOutlet(@Valid @RequestBody Outlet outlet) {
		return masterService.addOutlet(outlet);
	}

	@Operation(summary = "API Outlet Data Modify")
	@PutMapping(value = "/updateOutlet", consumes = "application/json")
	public ResponseEntity<String> updateOutlet(@Valid @RequestBody Outlet outlet) {
		return masterService.updateOutlet(outlet);
	}

	@Operation(summary = "API Outlet Data Deleted")
	@DeleteMapping(value = "/deleteOutlet")
	public ResponseEntity<String> deleteOutlet(@RequestParam Integer id) {
		return masterService.deleteOutlet(id);
	}

	@Operation(summary = "API FOR GET WAREHOUSE LIST")
	@GetMapping(value = "/getWarehouse")
	public List<Outlet> getWarehouse() {
		return masterService.getWarehouse();
	}

	@Operation(summary = "API FOR GET Store LIST")
	@GetMapping(value = "/getStore")
	public List<Outlet> getStore() {
		return masterService.getStore();
	}

	@Operation(summary = "API FOR GET Office LIST")
	@GetMapping(value = "/getOffice")
	public List<Outlet> getOffice() {
		return masterService.getOffice();
	}

	@Operation(summary = "API Outlet Details Data Deleted")
	@DeleteMapping(value = "/deleteOutletDocument")
	public String deleteOutletDocument(@RequestParam Integer id) {
		return masterService.deleteOutletDocument(id);
	}

	// ============================= Outlet Setup End==================

	// ============================= Transportation Setup Start==================

	@Operation(summary = "API Transportation Data Fetch")
	@GetMapping(value = "/getTransportation")
	public List<Transportation> getTransportation() {
		return masterService.getTransportation();
	}

	@Operation(summary = "API Transportation Data Fetch BY ID")
	@GetMapping(value = "/getTransportationByid")
	public Transportation getTransportationByid(@RequestParam Integer id) {
		return masterService.getTransportationByid(id);
	}

	@Operation(summary = "API Outlet Data Save")
	@PostMapping(value = "/addTransportation", consumes = "application/json")
	public ResponseEntity<String> addTransportation(@Valid @RequestBody Transportation transportation) {
		return masterService.addTransportation(transportation);
	}

	@Operation(summary = "API Transportation Data Modify")
	@PutMapping(value = "/updateTransportation", consumes = "application/json")
	public ResponseEntity<String> updateTransportation(@Valid @RequestBody Transportation transportation) {
		return masterService.updateTransportation(transportation);
	}

	@Operation(summary = "API Transportation Data Deleted")
	@DeleteMapping(value = "/deleteTransportation")
	public ResponseEntity<String> deleteTransportation(@RequestParam Integer id) {
		return masterService.deleteTransportation(id);
	}

	@Operation(summary = "API Transportation Details Data Deleted")
	@DeleteMapping(value = "/deleteTransportationDocument")
	public String deleteTransportationDocument(@RequestParam Integer id) {
		return masterService.deleteTransportationDocument(id);
	}

	// ============================= Transportation Setup End==================

	// ============================= Reason Setup Start==================

	@Operation(summary = "API Reason Data Fetch")
	@GetMapping(value = "/getReason")
	public List<Reason> getReason() {
		return masterService.getReason();
	}

	@Operation(summary = "API Reason Data Fetch BY ID")
	@GetMapping(value = "/getReasonByid")
	public Reason getReasonByid(@RequestParam Integer id) {
		return masterService.getReasonByid(id);
	}

	@Operation(summary = "API Reason Data Save")
	@PostMapping(value = "/postReason", consumes = "application/json")
	public ResponseEntity<String> addReason(@Valid @RequestBody Reason reason) {
		return masterService.addReason(reason);
	}

	@Operation(summary = "API Reason Data Modify")
	@PutMapping(value = "/updateReason", consumes = "application/json")
	public ResponseEntity<String> updateReason(@Valid @RequestBody Reason reason) {
		return masterService.updateReason(reason);
	}

	@Operation(summary = "API Reason Data Deleted")
	@DeleteMapping(value = "/deleteReason")
	public ResponseEntity<String> deleteReason(@RequestParam Integer id) {
		return masterService.deleteReason(id);
	}

	// ============================= Reason Setup End==================

	// ============================= Bay Start==================

	@GetMapping(value = "/getBay")
	public List<Bay> getBay() {
		return masterService.getBay();
	}

	@GetMapping(value = "/getBaybyId")
	public Bay getBaybyId(@RequestParam Integer id) {
		return masterService.getBaybyId(id);
	}

	@PostMapping(value = "/postBay", consumes = "application/json")
	public ResponseEntity<String> addBay(@Valid @RequestBody Bay bay) {
		return masterService.addBay(bay);
	}

	@PutMapping(value = "/updateBay", consumes = "application/json")
	public ResponseEntity<String> updateBay(@Valid @RequestBody Bay bay) {
		return masterService.updateBay(bay);
	}

	@DeleteMapping(value = "/deleteBay")
	public ResponseEntity<String> deleteBay(@RequestParam Integer id) {
		return masterService.deleteBay(id);
	}

	// ============================= Bay End==================

	// ============================= Schedule Start==================

	@Operation(summary = "API Schedule Data Fetch")
	@GetMapping(value = "/getSchedule")
	public List<Schedule> getSchedule() {
		return masterService.getSchedule();
	}

	@Operation(summary = "API Schedule Data Fetch By Id")
	@GetMapping(value = "/getScheduleByid")
	public Schedule getScheduleByid(@RequestParam Integer id) {
		return masterService.getScheduleByid(id);
	}

	@Operation(summary = "API Schedule Data Save")
	@PostMapping(value = "/postSchedule", consumes = "application/json")
	public ResponseEntity<String> postSchedule(@Valid @RequestBody Schedule schedule) {
		return masterService.postSchedule(schedule);
	}

	@Operation(summary = "API Schedule Data Modify")
	@PutMapping(value = "/updateSchedule", consumes = "application/json")
	public ResponseEntity<String> updateSchedule(@Valid @RequestBody Schedule schedule) {
		return masterService.updateSchedule(schedule);
	}

	@Operation(summary = "API Schedule Data Deleted")
	@DeleteMapping(value = "/deleteSchedule")
	public ResponseEntity<String> deleteSchedule(@RequestParam Integer id) {
		return masterService.deleteSchedule(id);
	}

	// ============================= Schedule End==================

	// ============================= Cluster Start==================

	@Operation(summary = "API Cluster Data Fetch")
	@GetMapping(value = "/getCluster")
	public List<Cluster> getCluster() {
		return masterService.getCluster();
	}

	@Operation(summary = "API Cluster Data Fetch By Id")
	@GetMapping(value = "/getClusterByid")
	public Cluster getClusterByid(@RequestParam Integer id) {
		return masterService.getClusterByid(id);
	}

	@Operation(summary = "API Cluster Data Save")
	@PostMapping(value = "/postCluster", consumes = "application/json")
	public ResponseEntity<String> addCluster(@Valid @RequestBody Cluster cluster) {
		return masterService.addCluster(cluster);
	}

	@Operation(summary = "API Cluster Data Modify")
	@PutMapping(value = "/updateCluster", consumes = "application/json")
	public ResponseEntity<String> updateCluster(@Valid @RequestBody Cluster cluster) {
		return masterService.updateCluster(cluster);
	}

	@Operation(summary = "API Cluster Data Deleted")
	@DeleteMapping(value = "/deleteCluster")
	public ResponseEntity<String> deleteCluster(@RequestParam Integer id) {
		return masterService.deleteCluster(id);
	}

	@Operation(summary = "API Cluster Mapping For Store")
	@PutMapping(value = "/clusterMappingForStore", consumes = "application/json")
	public ResponseEntity<String> clusterMappingForStore(@RequestBody ClusterMapping clusterMapping) {
		return masterService.clusterMappingForStore(clusterMapping);
	}

	@Operation(summary = "API Cluster Mapping For Article")
	@PutMapping(value = "/clusterMappingForArticle", consumes = "application/json")
	public ResponseEntity<String> clusterMappingForArticle(@RequestBody ClusterMapping clusterMapping) {
		return masterService.clusterMappingForArticle(clusterMapping);
	}

	@Operation(summary = "API For Fetch Cluster Mapping of Store")
	@GetMapping(value = "/fetchClusterMappingForStore")
	public ClusterMapping fetchClusterMappingForStore(@RequestParam Integer id) {
		return masterService.fetchClusterMappingForStore(id);
	}

	@Operation(summary = "API For Fetch Cluster Mapping of Article")
	@GetMapping(value = "/fetchClusterMappingForArticle")
	public ClusterMapping fetchClusterMappingForArticle(@RequestParam Integer id) {
		return masterService.fetchClusterMappingForArticle(id);
	}

	// ============================= Cluster End==================

}
