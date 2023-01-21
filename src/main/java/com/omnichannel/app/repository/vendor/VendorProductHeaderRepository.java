package com.omnichannel.app.repository.vendor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.vendor.VendorProductHeader;

@Repository
public interface VendorProductHeaderRepository extends JpaRepository<VendorProductHeader, Integer> {

	List<VendorProductHeader> findByVendorId(Integer vendorId);

	Optional<VendorProductHeader> findByArticleId(Integer articleId);

	Optional<VendorProductHeader> findByArticleIdAndVendorId(Integer articleId, Integer vendorId);

	Optional<VendorProductHeader> findByArticleIdAndVendorIdAndEanCD(Integer articleId, Integer vendorId, String eanCD);
	
	@Query(value="select v.* from ven_product v where v.vendor_id=?1",nativeQuery = true)
	public List<VendorProductHeader> getArticleByVendorIdForToT(Integer vendorId);

}
