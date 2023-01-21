package com.omnichannel.app.model.asset;

import java.util.List;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "asset_execution")
public class AssetExecution extends Auditable<String> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Integer vendorId;
    @NotNull
    private Integer assetbookingId;
    
    private String executionFile;
    private String narration;
    
    private Boolean status;
    
    @Transient
    private String vendorName;
    @Transient
    private String assetNo;
    @Transient
    private String assetTypeName;
    @Transient
    private String productFamily;
    @Transient
    private String bookingUpto;

}
