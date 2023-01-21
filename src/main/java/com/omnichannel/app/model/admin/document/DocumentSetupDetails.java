package com.omnichannel.app.model.admin.document;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "adm_doc_dtl")
public class DocumentSetupDetails  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer refId;
	
	private String document;
	
	private String file;
	
	private Boolean required;
	
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "doc_hdr_id")
	private DocumentSetupHeader header;
	
	

}
