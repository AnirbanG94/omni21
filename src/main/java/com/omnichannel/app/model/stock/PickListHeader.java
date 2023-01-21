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
@Table(name = "pick_list_hdr")
public class PickListHeader extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String picklistNo;

    private Integer fromId;

    private Integer toId;

    private String trDate;

    private String narration;

    private Boolean status;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<PickListDetails> details;

    @Transient
    private String fromName;

    @Transient
    private String toName;

    @Transient
    private String toType;

}
