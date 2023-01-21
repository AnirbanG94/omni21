package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.omnichannel.app.model.DTO.ClusterMapping;
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
import com.omnichannel.app.model.vendor.VendorProductHeader;

public interface MasterService {

	public List<Country> getCountry();

	public List<State> getState();

	public List<State> getStateByCountryCode(Integer id);

	public List<Location> getLocation();

	public ResponseEntity<String> addLocation(Location location);

	public ResponseEntity<String> updateLocation(Location location);

	public ResponseEntity<String> deleteLocation(Integer id);

	public Location getLocationById(Integer id);

	public Location getLocationByName(String name);

	// ============================= Paymentmode Start==================

	public List<Paymentmode> getPaymentmode();

	public ResponseEntity<String> addPaymentmode(Paymentmode paymentmode);

	public ResponseEntity<String> updatePaymentmode(Paymentmode paymentmode);

	public ResponseEntity<String> deletePaymentmode(Integer id);

	public Paymentmode getPaymentmodeByid(Integer id);

	// ============================= Paymentmode End==================

	// ============================= Region Start==================

	public List<Region> getRegion();

	public ResponseEntity<String> addRegion(Region region);

	public ResponseEntity<String> updateRegion(Region region);

	public ResponseEntity<String> deleteRegion(Integer id);

	public Region getRegionByid(Integer id);
	// ============================= Region End==================

	// ============================= AssetsType Start==================

	public List<AssetsType> getAssetsType();

	public AssetsType getAssetsTypeByid(Integer id);

	public ResponseEntity<String> addAssetsType(@Valid AssetsType assetsType);

	public ResponseEntity<String> updateAssetsType(@Valid AssetsType assetsType);

	public ResponseEntity<String> deleteAssetsType(Integer id);

	// ============================= AssetsType End==================

	// ============================= Outlet Setup Start==================

	public List<Outlet> getOutlet();

	public Outlet getOutletByid(Integer id);

	public ResponseEntity<String> addOutlet(@Valid Outlet outlet);

	public ResponseEntity<String> updateOutlet(@Valid Outlet outlet);

	public ResponseEntity<String> deleteOutlet(Integer id);

	public List<Outlet> getWarehouse();

	public List<Outlet> getStore();

	public List<Outlet> getOffice();

	public String deleteOutletDocument(Integer id);

	// ============================= Outlet Setup End==================

	// ============================= Transportation Setup Start==================

	public List<Transportation> getTransportation();

	public Transportation getTransportationByid(Integer id);

	public ResponseEntity<String> addTransportation(@Valid Transportation transportation);

	public ResponseEntity<String> updateTransportation(@Valid Transportation transportation);

	public ResponseEntity<String> deleteTransportation(Integer id);

	public String deleteTransportationDocument(Integer id);

	// ============================= Transportation Setup End==================

	// ============================= Reason Setup Start==================

	public List<Reason> getReason();

	public Reason getReasonByid(Integer id);

	public ResponseEntity<String> addReason(@Valid Reason reason);

	public ResponseEntity<String> updateReason(@Valid Reason reason);

	public ResponseEntity<String> deleteReason(Integer id);

	// ============================= Reason Setup End==================

	// ============================= Bay Start==================

	public List<Bay> getBay();

	public Bay getBaybyId(Integer id);

	public ResponseEntity<String> addBay(Bay bay);

	public ResponseEntity<String> updateBay(Bay bay);

	public ResponseEntity<String> deleteBay(Integer id);

	// ============================= Bay End==================

	// ============================= Schedule Start==================.

	public List<Schedule> getSchedule();

	public Schedule getScheduleByid(Integer id);

	public ResponseEntity<String> postSchedule(@Valid Schedule schedule);

	public ResponseEntity<String> updateSchedule(@Valid Schedule schedule);

	public ResponseEntity<String> deleteSchedule(Integer id);

	// ============================= Schedule End==================

	// ============================= Cluster Start==================

	public List<Cluster> getCluster();

	public ResponseEntity<String> addCluster(Cluster cluster);

	public ResponseEntity<String> updateCluster(Cluster cluster);

	public ResponseEntity<String> deleteCluster(Integer id);

	public Cluster getClusterByid(Integer id);

	public ResponseEntity<String> clusterMappingForStore(ClusterMapping clusterMapping);

	public ResponseEntity<String> clusterMappingForArticle(ClusterMapping clusterMapping);

	public ClusterMapping fetchClusterMappingForStore(Integer id);

	public ClusterMapping fetchClusterMappingForArticle(Integer id);

	// ============================= Cluster Start==================

	// <!============================= Start City==================>

	public  ResponseEntity<?> getCitybyPincode(Integer pin);

    public ResponseEntity<String> addCity(@Valid City city);

    public ResponseEntity<String> updateCity(@Valid City city);

    public ResponseEntity<?> getCitybyGlobalSearch(String keyword);

    public Boolean pincodeAvailability(Integer pin);

	public ResponseEntity<String> addTot(@Valid Tot tot);

	public ResponseEntity<String> updateTot(Tot tot);

	public List<Tot> getTot();

	public Tot getTotById(Integer id);

	public ResponseEntity<String> deleteTot(Integer id);

	public ResponseEntity<String> addSaveTotCreation(@Valid TotHeader totHeader);

	public List<Tot> getParticulars();

	public List<VendorProductHeader> getArticlesByVendorForToT(Integer vendorId);

	public List<TotHeader> getTotCreation();

	public TotHeader getTotCreationById(Integer id);

	public ResponseEntity<String> deleteTotCreation(Integer id);

	public ResponseEntity<String> EditTotCreation(@Valid TotHeader totHeader);

	public List<Tot> getMasterValidFor();

	// <!============================= End City==================>

}
