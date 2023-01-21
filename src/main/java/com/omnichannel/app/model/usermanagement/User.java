package com.omnichannel.app.model.usermanagement;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "usm_user")
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "username")
	private String username;
	
	@NotNull
	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "email")
	private String email;
	
	@NotNull
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "parentuser")
	private Integer parentUserId;
	
	@Column(name = "superparentuser")
	private String superParentUser;
	
	@Column(name = "employeeid")
	private String employeeId;
	
	private String profilePicLink;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "coid")
	private Integer coId;
	
	@Column(name = "locationid")
	private Integer locationId;
	
	@Column(name = "ishod")
	private boolean hodstatus;
	
	@Column(name = "accountNotExpired")
	private boolean accountNotExpired;
	
	private Integer vendorId;
	
	@ManyToMany 
    @JoinTable( 
        name = "usm_users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
	private Set<AdhocPrivilege> adhocPrivilege;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
	private Set<AdhocViewPrivilege> adhocViewPrivilege;
	

}
