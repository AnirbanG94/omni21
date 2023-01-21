package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.omnichannel.app.model.master.Cluster;
import com.omnichannel.app.model.master.Outlet;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Integer> {

	Boolean existsByDes(String Des);

	Cluster findByDes(String Des);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM article_cluster_mapping WHERE cluster_id = ?1", nativeQuery = true)
	void deleteArticle(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM outlet_cluster_mapping WHERE cluster_id = ?1", nativeQuery = true)
	void deleteOutlet(Integer id);

}
