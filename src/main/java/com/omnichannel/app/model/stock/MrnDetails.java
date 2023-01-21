package com.omnichannel.app.model.stock;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "mrn_dtl")
public class MrnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer articleId;

    private Integer uomId;

    private Integer qty;

    private Double rate;

    private Double amount;

    private Boolean active;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "mrn_hdr_id")
    private MrnHeader header;

    @Transient
    private String articleName;

    @Transient
    private String uomName;

}
