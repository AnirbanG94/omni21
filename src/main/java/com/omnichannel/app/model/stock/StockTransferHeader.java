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
@Table(name = "stk_tran_hdr")
public class StockTransferHeader extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String transferNo;

    private String transferOutDate;

    private String transferInDate;

    private Integer transferFromId;

    private Integer transferToId;

    private String narration;

    private Integer pickListId;

    private Double totAmount;

    private Boolean status;

    private Boolean transferFlag;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<StockTransferDetails> details;

    @Transient
    private String transferFromName;

    @Transient
    private String transferToName;

}
