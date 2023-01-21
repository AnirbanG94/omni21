package com.omnichannel.app.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.omnichannel.app.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "mst_location")
@EntityListeners(AuditingEntityListener.class)
public class Location extends Auditable<String>{
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "countryid")
    private Integer countryId;

    @NotNull
    @Column(name = "stateid")
    private Integer stateId;
    
    @NotNull
    private Integer pincode;
    
    @Transient
    private String countryname;
    
    @Transient
    private String statename;
    
    private Boolean status;

}
