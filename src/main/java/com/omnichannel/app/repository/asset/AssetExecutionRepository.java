package com.omnichannel.app.repository.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.asset.AssetExecution;

@Repository
public interface AssetExecutionRepository extends JpaRepository<AssetExecution, Integer> {

}
