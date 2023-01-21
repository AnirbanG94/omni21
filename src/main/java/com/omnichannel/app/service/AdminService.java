package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.omnichannel.app.model.DTO.LoginTranDTO;
import com.omnichannel.app.model.admin.ApplicationSetup;
import com.omnichannel.app.model.admin.ApprovalSetup;
import com.omnichannel.app.model.admin.Company;
import com.omnichannel.app.model.admin.EmailSetup;
import com.omnichannel.app.model.admin.FinancialYear;
import com.omnichannel.app.model.admin.IdentificationSetup;
import com.omnichannel.app.model.admin.LoginHistory;
import com.omnichannel.app.model.admin.Transactionlog;
import com.omnichannel.app.model.admin.document.DocumentSetupHeader;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;

public interface AdminService {

	// ============================= Company Setup Start==================

	public List<Company> getCompany();

	public Company getCompanyByid(Integer id);

	public ResponseEntity<String> addCompany(@Valid Company company);

	public ResponseEntity<String> updateCompany(@Valid Company company);

	public ResponseEntity<String> deleteCompany(Integer id);
	
	 public String deleteCompanyDocument(Integer id);
	// ============================= Company Setup End==================

	// ============================= Application Setup Start==================

	public List<ApplicationSetup> getApplicationSetup();

	public ApplicationSetup getApplicationSetupByid(Integer id);

	public ResponseEntity<String> addApplicationSetup(@Valid ApplicationSetup applicationSetup);

	public ResponseEntity<String> updateApplicationSetup(@Valid ApplicationSetup applicationSetup);

	public ResponseEntity<String> deleteApplicationSetup(Integer id);

	// ============================= Application Setup Start==================

	// ============================= Financial Year Setup Start==================

	public List<FinancialYear> getFinancialYear();

	public FinancialYear getFinancialYearByid(Integer id);

	public ResponseEntity<String> postFinancialYear(@Valid FinancialYear financialYear);

	public ResponseEntity<String> updateFinancialYear(@Valid FinancialYear financialYear);

	public ResponseEntity<String> deleteFinancialYear(Integer id);

	// ============================= Financial Year Setup End==================
	
	// ============================= Identification Setup End==================

	public List<IdentificationSetup> getIdentificationSetup();

	public IdentificationSetup getIdentificationSetupByid(Integer id);

	public ResponseEntity<String> addIdentificationSetup(@Valid IdentificationSetup identificationSetup);

	public ResponseEntity<String> updateIdentificationSetup(@Valid IdentificationSetup identificationSetup);

	public ResponseEntity<String> deleteIdentificationSetup(Integer id);

	// ============================= Identification Setup End==================


	public List<EmailSetup> getEmailSetup();

	public EmailSetup getEmailSetupByid(Integer id);

	public ResponseEntity<String> postEmailSetup(@Valid EmailSetup emailSetup);

	public ResponseEntity<String> updateEmailSetup(@Valid EmailSetup emailSetup);

	public ResponseEntity<String> deleteEmailSetup(Integer id);
	
	// ============================= Email Setup End==================

	// ============================= Approval Setup Start==================

	public List<ApprovalSetup> getApprovalSetup();

	public ApprovalSetup getApprovalSetupByid(Integer id);

	public ResponseEntity<String> postApprovalSetup(@Valid ApprovalSetup approvalSetup);

	public ResponseEntity<String> updateApprovalSetup(@Valid ApprovalSetup approvalSetup);

	public ResponseEntity<String> deleteApprovalSetup(Integer id);

	// ============================= Approval Setup End==================
	

	// ============================= Login History Start==================
	public List<LoginHistory> getLoginHostory();
	public List<LoginHistory> postLoginHostory(LoginTranDTO dto);
	// ============================= Login History End==================

	// ============================= Transaction log End==================
	public List<Transactionlog> getTransactionlog(LoginTranDTO dto);
	// ============================= Transaction log End==================

	// ============================= Document Setup Start==================

    public List<DocumentSetupHeader> getDocument();

	public DocumentSetupHeader getDocumentByid(Integer id);
	
    public DocumentSetupHeader getDocumentByCode(String code);

    public ResponseEntity<String> addDocument(@Valid DocumentSetupHeader document);

    public ResponseEntity<String> updateDocument(@Valid DocumentSetupHeader document);

    public ResponseEntity<String> deleteDocument(Integer id);

    public String deleteDocumentDetails(Integer hdrId);

	// ============================= Document Setup End==================
    
    // ============================= View Privilege Setup Start==================
    
    public List<ViewPrivilege> getViewPrivilege();

    public ViewPrivilege getViewPrivilegeByid(Integer id);

    public ResponseEntity<String> postViewPrivilege(@Valid ViewPrivilege viewPrivilege);

    public ResponseEntity<String> updateViewPrivilege(@Valid ViewPrivilege viewPrivilege);

    public ResponseEntity<String> deleteViewPrivilege(Integer id);

   
    
    // ============================= View Privilege Year Setup End==================

	

	

}
