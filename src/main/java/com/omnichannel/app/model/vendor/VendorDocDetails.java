package com.omnichannel.app.model.vendor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omnichannel.app.model.master.Outlet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ven_reg_doc")
public class VendorDocDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer refId;
    private String document;
    private String link;
    private String validity;
    private Boolean required;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "vendor_id")
    private VendorRegistreation header;

}
