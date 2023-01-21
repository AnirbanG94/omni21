package com.omnichannel.app.model.stock;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@ToString
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stock", uniqueConstraints = {
        @UniqueConstraint(name = "stock", columnNames = { "outletId", "yearCd", "articleId" }) })
public class Stock extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer outletId;
    @NotNull
    private String yearCd;
    @NotNull
    private Integer articleId;
    @NotNull
    private Integer uomId;

    private Double opnQty;
    private Double opnVal;

    private Double grnQty;
    private Double grnVal;

    private Double tranInQty;
    private Double tranInVal;

    private Double tranOutQty;
    private Double tranOutVal;

    private Double salesQty;
    private Double salesVal;

    private Double salesRcvQty;
    private Double salesRcvVal;

    private Double dmgQty;
    private Double dmgVal;

    private Double dumpQty;
    private Double dumpVal;

    private Double stckQty;
    private Double stckVal;

}
