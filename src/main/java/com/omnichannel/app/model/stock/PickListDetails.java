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
@Table(name = "pick_list_dtl")
public class PickListDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer articleId;

    private String assetsNo;

    private Integer uomId;

    private Double qty;

    private Double basePrice;

    private Boolean status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pick_list_hdr_id")
    private PickListHeader header;

    @Transient
    private String articleName;
    @Transient
    private String uomName;
}
