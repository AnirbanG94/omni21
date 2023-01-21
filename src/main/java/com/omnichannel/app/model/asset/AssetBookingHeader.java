package com.omnichannel.app.model.asset;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "asset_booking_hdr")
public class AssetBookingHeader extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer assetRegId;

    private Double amount;

    private Double totalAmount;

    private Double discount;

    private String narration;

    @NotNull
    private Integer vendorId;

    @NotNull
    private String strtMth;

    private String endMth;

    @NotNull
    private Integer noMth;

    private String reason;

    private String status;

    private Boolean active;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<AssetBookingDetails> details;

    @Transient
    private String outletType;

    @Transient
    private String outletName;

    @Transient
    private String assetsTypeName;
    @Transient
    private String productFamily;
    @Transient
    private Double cost;

    @Transient
    private Double gst;
    @Transient
    private String vendorName;
    @Transient
    private String assetNo;
    @Transient
    private String visibilityType;

}
