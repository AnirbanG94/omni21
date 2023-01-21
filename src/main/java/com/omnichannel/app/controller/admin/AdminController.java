package com.omnichannel.app.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.omnichannel.app.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    // ============================= Company Setup Start==================

    @Operation(summary = "API Company Data Fetch")
    @GetMapping(value = "/getCompany")
    public List<Company> getCompany() {
        return adminService.getCompany();
    }

    @Operation(summary = "API Company Data Fetch By Id")
    @GetMapping(value = "/getCompanyByid")
    public Company getCompanyByid(@RequestParam Integer id) {
        return adminService.getCompanyByid(id);
    }

    @Operation(summary = "API Company Data Save")
    @PostMapping(value = "/postCompany", consumes = "application/json")
    public ResponseEntity<String> addCompany(@Valid @RequestBody Company company) {
        return adminService.addCompany(company);
    }

    @Operation(summary = "API Company Data Modify")
    @PutMapping(value = "/updateCompany", consumes = "application/json")
    public ResponseEntity<String> updateCompany(@Valid @RequestBody Company company) {
        return adminService.updateCompany(company);
    }

    @Operation(summary = "API Company Data Deleted")
    @DeleteMapping(value = "/deleteCompany")
    public ResponseEntity<String> deleteCompany(@RequestParam Integer id) {
        return adminService.deleteCompany(id);
    }
    
    @Operation(summary = "API Company Details Data Deleted")
    @DeleteMapping(value = "/deleteCompanyDocument")
    public String deleteCompanyDocument(@RequestParam Integer id) {
        return adminService.deleteCompanyDocument(id);
    }

    // ============================= Company Setup End==================

    // ============================= Application Setup Start==================

    @Operation(summary = "API Application Setup Data Fetch")
    @GetMapping(value = "/getApplicationSetup")
    public List<ApplicationSetup> getApplicationSetup() {
        return adminService.getApplicationSetup();
    }

    @Operation(summary = "API Application Setup Data Fetch By Id")
    @GetMapping(value = "/getApplicationSetupByid")
    public ApplicationSetup getApplicationSetupByid(@RequestParam Integer id) {
        return adminService.getApplicationSetupByid(id);
    }

    @Operation(summary = "API Application Setup Data Save")
    @PostMapping(value = "/postApplicationSetup", consumes = "application/json")
    public ResponseEntity<String> addApplicationSetup(@Valid @RequestBody ApplicationSetup applicationSetup) {
        return adminService.addApplicationSetup(applicationSetup);
    }

    @Operation(summary = "API Application Setup Data Modify")
    @PutMapping(value = "/updateApplicationSetup", consumes = "application/json")
    public ResponseEntity<String> updateApplicationSetup(@Valid @RequestBody ApplicationSetup applicationSetup) {
        return adminService.updateApplicationSetup(applicationSetup);
    }

    @Operation(summary = "API Application Setup Data Deleted")
    @DeleteMapping(value = "/deleteApplicationSetup")
    public ResponseEntity<String> deleteApplicationSetup(@RequestParam Integer id) {
        return adminService.deleteApplicationSetup(id);
    }

    // ============================= Application Setup End==================

    // ============================= Financial Year Setup Start==================

    @Operation(summary = "API Financial Year Setup Data Fetch")
    @GetMapping(value = "/getFinancialYear")
    public List<FinancialYear> getFinancialYear() {
        return adminService.getFinancialYear();
    }

    @Operation(summary = "API Financial Year Setup Data Fetch By Id")
    @GetMapping(value = "/getFinancialYearByid")
    public FinancialYear getFinancialYearByid(@RequestParam Integer id) {
        return adminService.getFinancialYearByid(id);
    }

    @Operation(summary = "API Financial Year Setup Data Save")
    @PostMapping(value = "/postFinancialYear", consumes = "application/json")
    public ResponseEntity<String> postFinancialYear(@Valid @RequestBody FinancialYear financialYear) {
        return adminService.postFinancialYear(financialYear);
    }

    @Operation(summary = "API Financial Year Setup Data Modify")
    @PutMapping(value = "/updateFinancialYear", consumes = "application/json")
    public ResponseEntity<String> updateFinancialYear(@Valid @RequestBody FinancialYear financialYear) {
        return adminService.updateFinancialYear(financialYear);
    }

    @Operation(summary = "API Financial Year Setup Data Deleted")
    @DeleteMapping(value = "/deleteFinancialYear")
    public ResponseEntity<String> deleteFinancialYear(@RequestParam Integer id) {
        return adminService.deleteFinancialYear(id);
    }

    // ============================= Financial Year Setup End==================

//============================= Identification Setup Start==================

    @Operation(summary = "API Identification Setup Data Fetch")
    @GetMapping(value = "/getIdentificationSetup")
    public List<IdentificationSetup> getIdentificationSetup() {
        return adminService.getIdentificationSetup();
    }

    @Operation(summary = "API Identification Setup Data Fetch BY ID")
    @GetMapping(value = "/getIdentificationSetupByid")
    public IdentificationSetup getIdentificationSetupByid(@RequestParam Integer id) {
        return adminService.getIdentificationSetupByid(id);
    }

    @Operation(summary = "API Identification Setup Data Save")
    @PostMapping(value = "/postIdentificationSetup", consumes = "application/json")
    public ResponseEntity<String> addIdentificationSetup(@Valid @RequestBody IdentificationSetup identificationSetup) {
        return adminService.addIdentificationSetup(identificationSetup);
    }

    @Operation(summary = "API Identification Setup Data Modify")
    @PutMapping(value = "/updateIdentificationSetup", consumes = "application/json")
    public ResponseEntity<String> updateIdentificationSetup(
            @Valid @RequestBody IdentificationSetup identificationSetup) {
        return adminService.updateIdentificationSetup(identificationSetup);
    }

    @Operation(summary = "API Identification Setup Data Deleted")
    @DeleteMapping(value = "/deleteIdentificationSetup")
    public ResponseEntity<String> deleteIdentificationSetup(@RequestParam Integer id) {
        return adminService.deleteIdentificationSetup(id);
    }

    // ============================= Identification Setup End==================

    // ============================= Email Setup Start==================

    @Operation(summary = "API Email Setup Data Fetch")
    @GetMapping(value = "/getEmailSetup")
    public List<EmailSetup> getEmailSetup() {
        return adminService.getEmailSetup();
    }

    @Operation(summary = "API Email Setup Data Fetch BY ID")
    @GetMapping(value = "/getEmailSetupByid")
    public EmailSetup getEmailSetupByid(@RequestParam Integer id) {
        return adminService.getEmailSetupByid(id);
    }

    @Operation(summary = "API Email Setup Data Save")
    @PostMapping(value = "/postEmailSetup", consumes = "application/json")
    public ResponseEntity<String> postEmailSetup(@Valid @RequestBody EmailSetup emailSetup) {
        return adminService.postEmailSetup(emailSetup);
    }

    @Operation(summary = "API Email Setup Data Modify")
    @PutMapping(value = "/updateEmailSetup", consumes = "application/json")
    public ResponseEntity<String> updateEmailSetup(@Valid @RequestBody EmailSetup emailSetup) {
        return adminService.updateEmailSetup(emailSetup);
    }

    @Operation(summary = "API Email Setup Data Deleted")
    @DeleteMapping(value = "/deleteEmailSetup")
    public ResponseEntity<String> deleteEmailSetup(@RequestParam Integer id) {
        return adminService.deleteEmailSetup(id);
    }

    // ============================= Email Setup End==================

    // ============================= Approval Setup Start==================

    @Operation(summary = "API Approval Setup Data Fetch")
    @GetMapping(value = "/getApprovalSetup")
    public List<ApprovalSetup> getApprovalSetup() {
        return adminService.getApprovalSetup();
    }

    @Operation(summary = "API Approval Setup Data Fetch BY ID")
    @GetMapping(value = "/getApprovalSetupByid")
    public ApprovalSetup getApprovalSetupByid(@RequestParam Integer id) {
        return adminService.getApprovalSetupByid(id);
    }

    @Operation(summary = "API Approval Setup Data Save")
    @PostMapping(value = "/postApprovalSetup", consumes = "application/json")
    public ResponseEntity<String> postApprovalSetup(@Valid @RequestBody ApprovalSetup approvalSetup) {
        return adminService.postApprovalSetup(approvalSetup);
    }

    @Operation(summary = "API Approval Setup Data Modify")
    @PutMapping(value = "/updateApprovalSetup", consumes = "application/json")
    public ResponseEntity<String> updateApprovalSetup(@Valid @RequestBody ApprovalSetup approvalSetup) {
        return adminService.updateApprovalSetup(approvalSetup);
    }

    @Operation(summary = "API Approval Setup Data Deleted")
    @DeleteMapping(value = "/deleteApprovalSetup")
    public ResponseEntity<String> deleteApprovalSetup(@RequestParam Integer id) {
        return adminService.deleteApprovalSetup(id);
    }

    // ============================= Approval Setup End==================

    // ============================= Document Setup Start==================

    @Operation(summary = "API Document Data Fetch")
    @GetMapping(value = "/getDocument")
    public List<DocumentSetupHeader> getDocument() {
        return adminService.getDocument();
    }

    @Operation(summary = "API Document Data Fetch By Id")
    @GetMapping(value = "/getDocumentByid")
    public DocumentSetupHeader getDocumentByid(@RequestParam Integer id) {
        return adminService.getDocumentByid(id);
    }
    
    @Operation(summary = "API Document Data Fetch By Code")
    @GetMapping(value = "/getDocumentByCode")
    public DocumentSetupHeader getDocumentByCode(@RequestParam String code) {
        return adminService.getDocumentByCode(code);
    }

    @Operation(summary = "API Document Data Save")
    @PostMapping(value = "/postDocument", consumes = "application/json")
    public ResponseEntity<String> addDocument(@Valid @RequestBody DocumentSetupHeader document) {
        return adminService.addDocument(document);
    }

    @Operation(summary = "API Document Data Modify")
    @PutMapping(value = "/updateDocument", consumes = "application/json")
    public ResponseEntity<String> updateDocument(@Valid @RequestBody DocumentSetupHeader document) {
        return adminService.updateDocument(document);
    }

    @Operation(summary = "API Document Data Deleted")
    @DeleteMapping(value = "/deleteDocument")
    public ResponseEntity<String> deleteDocument(@RequestParam Integer id) {
        return adminService.deleteDocument(id);
    }

    @Operation(summary = "API Document Details Data Deleted")
    @DeleteMapping(value = "/deleteDocumentDetails")
    public String deleteDocumentDetails(@RequestParam Integer id) {
        return adminService.deleteDocumentDetails(id);
    }

    // ============================= Document Setup End==================

    // ============================= View Privilege Setup Start==================

    @Operation(summary = "API Financial Year Setup Data Fetch")
    @GetMapping(value = "/getViewPrivilege")
    public List<ViewPrivilege> getViewPrivilege() {
        return adminService.getViewPrivilege();
    }

    @Operation(summary = "API Financial Year Setup Data Fetch By Id")
    @GetMapping(value = "/getViewPrivilegeByid")
    public ViewPrivilege getViewPrivilegeByid(@RequestParam Integer id) {
        return adminService.getViewPrivilegeByid(id);
    }

    @Operation(summary = "API Financial Year Setup Data Save")
    @PostMapping(value = "/postViewPrivilege", consumes = "application/json")
    public ResponseEntity<String> postViewPrivilege(@Valid @RequestBody ViewPrivilege viewPrivilege) {
        return adminService.postViewPrivilege(viewPrivilege);
    }

    @Operation(summary = "API Financial Year Setup Data Modify")
    @PutMapping(value = "/updateViewPrivilege", consumes = "application/json")
    public ResponseEntity<String> updateViewPrivilege(@Valid @RequestBody ViewPrivilege viewPrivilege) {
        return adminService.updateViewPrivilege(viewPrivilege);
    }

    @Operation(summary = "API Financial Year Setup Data Deleted")
    @DeleteMapping(value = "/deleteViewPrivilege")
    public ResponseEntity<String> deleteViewPrivilege(@RequestParam Integer id) {
        return adminService.deleteViewPrivilege(id);
    }

    // ============================= View Privilege Year Setup End==================

    // ============================= Login History Start==================

    @Operation(summary = "API FOR LOGIN HISTORY FETCH")
    @GetMapping(value = "/getLoginHostory")
    public List<LoginHistory> getLoginHostory() {
        return adminService.getLoginHostory();
    }

    @Operation(summary = "API FOR LOGIN HISTORY FETCH by SPECIFIC DATE AND USER")
    @PostMapping(value = "/postLoginHostory", consumes = "application/json")
    public List<LoginHistory> postLoginHostory(@RequestBody LoginTranDTO dto) {
        return adminService.postLoginHostory(dto);
    }

    // ============================= Login History End==================

    // ============================= Transaction log End==================

    @Operation(summary = "API FOR TRANSACTION HISTORY FETCH")
    @PostMapping(value = "/getTransactionlog", consumes = "application/json")
    public List<Transactionlog> getTransactionlog(@RequestBody LoginTranDTO dto) {
        return adminService.getTransactionlog(dto);
    }

    // ============================= Transaction log End==================

}
