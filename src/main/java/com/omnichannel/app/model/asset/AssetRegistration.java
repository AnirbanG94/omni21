package com.omnichannel.app.model.asset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "asset_reg",uniqueConstraints = { @UniqueConstraint(name = "assetRegUnq", columnNames = { "year","outletId", "assetsTypeId","assetNo" }) })

public class AssetRegistration extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer outletId;

    @NotNull
    private Integer assetsTypeId;

    @NotNull
    private String assetNo;
    
    @NotNull
	private  String year;

    @NotNull
    private String productFamily;

    @NotNull
    private Integer maxsf;

    @NotNull
    private String visibilityType;

    private Double cost;
    
    private Double gst;
    
    private Double totalAmount;

    private String assetFile;
    
    private String narration;

    private Boolean status;

    @Transient
    private String outletName;
    
    @Transient
    private String assetsTypeName;
    
    @Transient
    private String outletType;

}
