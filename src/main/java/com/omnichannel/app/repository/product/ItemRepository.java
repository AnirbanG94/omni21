package com.omnichannel.app.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.ItemDTO.AssetDivFamily;
import com.omnichannel.app.model.product.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT u FROM Item u WHERE u.pro_division = ?1 and u.pro_family = ?2 and u.pro_class = ?3")
    List<Item> findUserDivisionFamilyClass(String division, String family, String pclass);

    
    @Query(value = "SELECT DISTINCT u.pro_division ,u.pro_family FROM pro_item u",nativeQuery = true)
    List<AssetDivFamily> distinctDivFamily();

}
