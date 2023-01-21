package com.omnichannel.app.model.usermanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Getter
@Setter
@ToString
@Entity
@Table(name = "usm_viewprivileges")
@EntityListeners(AuditingEntityListener.class)
public class ViewPrivilege extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "menu")
    private String menu;

    @NotNull
    @Column(name = "submenu")
    private String submenu;

    @NotNull
    @Column(name = "privilage")
    private String privilage;

    @NotNull
    private Boolean isIdentify;

    @NotNull
    private Boolean isEmail;

    @NotNull
    private Boolean isApproval;

}
