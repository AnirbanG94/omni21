package com.omnichannel.app.repository.asset;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.asset.AssetRegistration;

@Repository
public interface AssetRegistrationRepository extends JpaRepository<AssetRegistration,Integer> {
    
    @Query(value = "SELECT a.* FROM asset_reg a join mst_outlet b ON a.outlet_id=b.id WHERE b.type=?1 AND a.status=true AND a.visibility_type!='S'", nativeQuery = true)
    public List<AssetRegistration> AssetByOutletType(String type);

}
