package com.omnichannel.app.model.stock;

import java.util.List;

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
import com.omnichannel.app.model.DTO.OpenStockDTO;

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
@Table(name = "opening_stock")
public class OpeningStock extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer outletId;
    @NotNull
    private String yearCd;
    @NotNull
    @Column(unique = true)
    private Integer articleId;

    private Integer uomId;

    private Double qty;

    private Double val;

    private Boolean active;

    @Transient
    private List<OpenStockDTO> detail;

    @Transient
	private String outletName;

    @Transient
    private String articleName;

    @Transient
    private String uomName;
    
}
