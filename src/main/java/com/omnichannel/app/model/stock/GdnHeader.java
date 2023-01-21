package com.omnichannel.app.model.stock;

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
@Table(name = "gdn_hdr")
public class GdnHeader extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer outletId;
    private Integer vendorId;
    private String timestamp;
    private Integer poId;
    private String invoiceNo;
    private String invoiceDate;
    private String Note;
    private Double totBasicAmt;
    private Double totSgstAmt;
    private Double totCgstAmt;
    private Double totIgstAmt;
    private Double totNetAmt;
    private Boolean active;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<GdnDetails> details;

    @Transient
    private String outletName;

    @Transient
    private String vendorName;

}
