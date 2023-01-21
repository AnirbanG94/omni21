package com.omnichannel.app.model.admin;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.omnichannel.app.model.admin.document.DocumentSetupDetails;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "adm_company")
public class Company extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer id;
    @NotNull
    @Column(name = "company_name")
    private String name;
    @NotNull
    private String addr1;

    private String addr2;

    private String addr3;

    private Integer locationid;

    private Integer stateid;

    private Integer countrycode;

    @NotNull
    @Column(name = "pin_code")
    private Integer pincode;

    private String phone;

    @NotNull
    private String mobile;

    private String fax;

    @NotNull
    private String email;

    private String website;

    private String gst;

    private String fsa_no;

    private String cin_no;

    private String tin_no;

    private String pan_no;

    private String cst_no;

    private String hd1;

    private String hd2;

    private Boolean status;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<CompanyDocDetails> details;

    @Transient
    private String locationname;

    @Transient
    private String statename;

    @Transient
    private String countryname;

}
