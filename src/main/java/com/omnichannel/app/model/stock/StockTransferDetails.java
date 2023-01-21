package com.omnichannel.app.model.stock;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "stk_tran_dtl")
public class StockTransferDetails extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer articleId;

    private Integer uomId;

    private Double qty;

    private Double recdQty;

    private Double shortageQty;

    private Double excessQty;

    private Double arrivDamage;

    private Double transitDamage;

    private Double basePrice;

    private Boolean status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "stk_tran_hdr_id")
    private StockTransferHeader header;

    @Transient
    private String articleName;

    @Transient
    private String uomName;

}
