package com.omnichannel.app.service.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.omnichannel.app.model.DTO.LoginTranDTO;
import com.omnichannel.app.model.admin.ApplicationSetup;
import com.omnichannel.app.model.admin.ApprovalSetup;
import com.omnichannel.app.model.admin.Company;
import com.omnichannel.app.model.admin.CompanyDocDetails;
import com.omnichannel.app.model.admin.EmailSetup;
import com.omnichannel.app.model.admin.FinancialYear;
import com.omnichannel.app.model.admin.IdentificationSetup;
import com.omnichannel.app.model.admin.LoginHistory;
import com.omnichannel.app.model.admin.Transactionlog;
import com.omnichannel.app.model.admin.document.DocumentSetupDetails;
import com.omnichannel.app.model.admin.document.DocumentSetupHeader;
import com.omnichannel.app.model.master.Country;
import com.omnichannel.app.model.master.Location;
import com.omnichannel.app.model.master.State;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.model.usermanagement.ViewPrivilege;
import com.omnichannel.app.repository.admin.ApplicationSetupRepository;
import com.omnichannel.app.repository.admin.ApprovalSetupRepository;
import com.omnichannel.app.repository.admin.CompanyDocDetailsRepository;
import com.omnichannel.app.repository.admin.CompanyRepository;
import com.omnichannel.app.repository.admin.DocumentSetupDetailsRepository;
import com.omnichannel.app.repository.admin.DocumentSetupHeaderRepository;
import com.omnichannel.app.repository.admin.EmailSetupRepository;
import com.omnichannel.app.repository.admin.FinancialYearRepository;
import com.omnichannel.app.repository.admin.IdentificationSetupRepository;
import com.omnichannel.app.repository.admin.LoginHistoryRepository;
import com.omnichannel.app.repository.admin.TransactionlogRepository;
import com.omnichannel.app.repository.master.CountryRepository;
import com.omnichannel.app.repository.master.LocationRepository;
import com.omnichannel.app.repository.master.OutletRepository;
import com.omnichannel.app.repository.master.StateRepository;
import com.omnichannel.app.repository.usermanagement.UserRepository;
import com.omnichannel.app.repository.usermanagement.ViewPrivilegeRepository;
import com.omnichannel.app.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyDocDetailsRepository companyDocDetailsRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ApplicationSetupRepository applicationSetupRepository;
    @Autowired
    FinancialYearRepository financialYearRepository;
    @Autowired
    IdentificationSetupRepository identificationSetupRepository;
    @Autowired
    OutletRepository outletRepository;
    @Autowired
    ViewPrivilegeRepository viewPrivilegeRepository;
    @Autowired
    EmailSetupRepository emailSetupRepository;
    @Autowired
    ApprovalSetupRepository approvalSetupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginHistoryRepository loginHistoryRepository;
    @Autowired
    TransactionlogRepository transactionlogRepository;
    @Autowired
    DocumentSetupHeaderRepository documentSetupHeaderRepository;
    @Autowired
    DocumentSetupDetailsRepository documentSetupDetailsRepository;

    // ============================= Company Setup Start==================

    @Override
    public List<Company> getCompany() {
        List<Company> collect = companyRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
                .collect(Collectors.toList());
        for (Company cs : collect) {
            Optional<Location> findById = locationRepository.findById(cs.getLocationid());
            cs.setLocationname(findById.get().getName());
            cs.setCountryname(findById.get().getCountryname());
            cs.setStatename(findById.get().getStatename());
        }
        return collect;
    }

    @Override
    public Company getCompanyByid(Integer id) {
        Optional<Company> com = companyRepository.findById(id);
        Optional<Location> findById = locationRepository.findById(com.get().getLocationid());
        com.get().setLocationname(findById.get().getName());
        Optional<State> state = stateRepository.findById(com.get().getStateid());
        com.get().setStatename(state.get().getState());
        Optional<Country> country = countryRepository.findById(com.get().getCountrycode());
        com.get().setCountryname(country.get().getName());

        return com.get();
    }

    @Override
    public ResponseEntity<String> addCompany(@Valid Company company) {
        company.setId(1);
        company.setName(company.getName().toUpperCase());
        company.setLocationname(company.getLocationname().toUpperCase());
        List<CompanyDocDetails> details = new ArrayList<>();
        try {
            details = company.getDetails();
        } catch (Exception e) {
            System.out.println("List is Empty" + e.getMessage());
        }
        Optional<Location> findByName = locationRepository.findByName(company.getLocationname());
        if (findByName.isEmpty()) {
            Location loc = new Location();
            loc.setName(company.getLocationname());
            loc.setStateId(company.getStateid());
            loc.setCountryId(company.getCountrycode());
            loc.setPincode(company.getPincode());
            loc.setStatus(true);
            Location save = locationRepository.save(loc);

            company.setDetails(null);
            company.setLocationid(save.getId());
            company.setStatus(true);
            @Valid
            Company save2 = companyRepository.save(company);
            try {
                for (CompanyDocDetails companyDocDetails : details) {
                    companyDocDetails.setHeader(save2);
                }

                save2.setDetails(details);
                companyRepository.save(save2);
            } catch (Exception e) {
                System.out.println("List is Empty" + e.getMessage());
            }

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Transactionlog trlog = new Transactionlog();
            trlog.setUsername(name);
            trlog.setTimestamp(LocalDateTime.now());
            trlog.setActivity(save2.getName() + " New Company details save Successfully");
            transactionlogRepository.save(trlog);

            return new ResponseEntity<String>(save2.getName() + " Company save Successfully", HttpStatus.CREATED);

        } else {

            company.setDetails(null);
            company.setLocationid(findByName.get().getId());
            company.setStatus(true);
            @Valid
            Company save2 = companyRepository.save(company);
            try {
                for (CompanyDocDetails companyDocDetails : details) {
                    companyDocDetails.setHeader(save2);
                }

                save2.setDetails(details);
                companyRepository.save(save2);
            } catch (Exception e) {
                System.out.println("List is Empty" + e.getMessage());
            }

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Transactionlog trlog = new Transactionlog();
            trlog.setUsername(name);
            trlog.setTimestamp(LocalDateTime.now());
            trlog.setActivity(save2.getName() + " New Company details save Successfully");
            transactionlogRepository.save(trlog);

            return new ResponseEntity<String>(save2.getName() + " Company save Successfully", HttpStatus.CREATED);

        }
    }

    @Override
    public ResponseEntity<String> updateCompany(@Valid Company company) {
        company.setId(1);
        company.setName(company.getName().toUpperCase());
        company.setLocationname(company.getLocationname().toUpperCase());
        List<CompanyDocDetails> details = new ArrayList<>();
        try {
            details = company.getDetails();
        } catch (Exception e) {
            System.out.println("List is Empty" + e.getMessage());
        }
        Optional<Location> findByName = locationRepository.findByName(company.getLocationname());
        if (findByName.isEmpty()) {
            Location loc = new Location();
            loc.setName(company.getLocationname());
            loc.setStateId(company.getStateid());
            loc.setCountryId(company.getCountrycode());
            loc.setPincode(company.getPincode());
            loc.setStatus(true);
            Location save = locationRepository.save(loc);

            company.setDetails(null);
            company.setLocationid(save.getId());
            company.setStatus(true);
            @Valid
            Company save2 = companyRepository.save(company);
            if (!details.isEmpty()) {
                for (CompanyDocDetails companyDocDetails : details) {
                    companyDocDetails.setHeader(save2);
                }

                save2.setDetails(details);
                companyRepository.save(save2);
            }

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Transactionlog trlog = new Transactionlog();
            trlog.setUsername(name);
            trlog.setTimestamp(LocalDateTime.now());
            trlog.setActivity(save2.getName() + " Company details update Successfully");
            transactionlogRepository.save(trlog);

            return new ResponseEntity<String>(save2.getName() + " Company save Successfully", HttpStatus.CREATED);

        } else {

            company.setDetails(null);
            company.setLocationid(findByName.get().getId());
            company.setStatus(true);
            @Valid
            Company save2 = companyRepository.save(company);
            if (!details.isEmpty()) {
                for (CompanyDocDetails companyDocDetails : details) {
                    companyDocDetails.setHeader(save2);
                }

                save2.setDetails(details);
                companyRepository.save(save2);
            }

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Transactionlog trlog = new Transactionlog();
            trlog.setUsername(name);
            trlog.setTimestamp(LocalDateTime.now());
            trlog.setActivity("Company details update Successfully");
            transactionlogRepository.save(trlog);

            return new ResponseEntity<String>(save2.getName() + " Company save Successfully", HttpStatus.CREATED);

        }
    }

    @Override
    public ResponseEntity<String> deleteCompany(Integer id) {
        Optional<Company> com = companyRepository.findById(id);
        com.get().setStatus(false);
        companyRepository.save(com.get());

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(name);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Company details deteted");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Company Delete Successfully", HttpStatus.CREATED);
    }

    @Override
    public String deleteCompanyDocument(Integer id) {
        companyDocDetailsRepository.deleteById(id);
        return "Data Deleted";
    }

    // ============================= Company Setup End==================

    // ============================= Application Setup Start==================

    @Override
    public List<ApplicationSetup> getApplicationSetup() {
        return applicationSetupRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationSetup getApplicationSetupByid(Integer id) {
        return applicationSetupRepository.findById(id).get();
    }

    @Override
    public ResponseEntity<String> addApplicationSetup(@Valid ApplicationSetup applicationSetup) {
        applicationSetup.setKey(applicationSetup.getKey().toUpperCase());
        applicationSetup.setStatus(true);

        if (applicationSetupRepository.existsByKey(applicationSetup.getKey())) {
            return new ResponseEntity<String>(" Already In Database ",
                    HttpStatus.BAD_REQUEST);
        } else {
            applicationSetupRepository.save(applicationSetup);
            return new ResponseEntity<String>(" Saved ",
                    HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<String> updateApplicationSetup(@Valid ApplicationSetup applicationSetup) {
        applicationSetup.setKey(applicationSetup.getKey().toUpperCase());
        applicationSetup.setStatus(true);
        ApplicationSetup applicationSetup2 = applicationSetupRepository.findById(applicationSetup.getId()).get();
        if (!applicationSetup2.getKey().equals(applicationSetup.getKey())) {
            if (applicationSetupRepository.existsByKey(applicationSetup.getKey())) {
                return new ResponseEntity<String>(" Already In Database ",
                        HttpStatus.BAD_REQUEST);
            } else {
                applicationSetupRepository.save(applicationSetup);
                return new ResponseEntity<String>(" Updated ",
                        HttpStatus.ACCEPTED);
            }
        } else {
            applicationSetupRepository.save(applicationSetup);
            return new ResponseEntity<String>(" Updated ",
                    HttpStatus.ACCEPTED);
        }

    }

    @Override
    public ResponseEntity<String> deleteApplicationSetup(Integer id) {
        ApplicationSetup applicationSetup = applicationSetupRepository.findById(id).get();
        applicationSetup.setStatus(false);
        applicationSetupRepository.save(applicationSetup);
        return new ResponseEntity<String>("Data Deleted", HttpStatus.CREATED);
    }

    // ============================= Application Setup Start==================

    // ============================= Financial Year Setup Start==================

    @Override
    public List<FinancialYear> getFinancialYear() {
        return financialYearRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
                .collect(Collectors.toList());
    }

    @Override
    public FinancialYear getFinancialYearByid(Integer id) {
        Optional<FinancialYear> findById = financialYearRepository.findById(id);
        return findById.get();
    }

    @Override
    public ResponseEntity<String> postFinancialYear(FinancialYear financialYear) {

        LocalDate dt1 = LocalDate.parse(financialYear.getStartDate());
        LocalDate dt2 = LocalDate.parse(financialYear.getEndDate());

        if (dt1.isBefore(dt2)) {
            financialYear.setStatus(true);
            @Valid
            FinancialYear save = financialYearRepository.save(financialYear);

            String names = SecurityContextHolder.getContext().getAuthentication().getName();
            Transactionlog trlog = new Transactionlog();
            trlog.setUsername(names);
            trlog.setTimestamp(LocalDateTime.now());
            trlog.setActivity("Year details added");
            transactionlogRepository.save(trlog);

            return new ResponseEntity<String>(" Financial Year Successfully Created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>(" Start Date alwasys less than end date ",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<String> updateFinancialYear(FinancialYear financialYear) {

        LocalDate dt1 = LocalDate.parse(financialYear.getStartDate());
        LocalDate dt2 = LocalDate.parse(financialYear.getEndDate());

        if (dt1.isBefore(dt2)) {
            financialYear.setStatus(true);
            @Valid
            FinancialYear save = financialYearRepository.save(financialYear);

            String names = SecurityContextHolder.getContext().getAuthentication().getName();
            Transactionlog trlog = new Transactionlog();
            trlog.setUsername(names);
            trlog.setTimestamp(LocalDateTime.now());
            trlog.setActivity("year details updated");
            transactionlogRepository.save(trlog);

            return new ResponseEntity<String>(" Financial Year Successfully Updated", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>(" Start Date alwasys less than end date ",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteFinancialYear(Integer id) {
        Optional<FinancialYear> findById = financialYearRepository.findById(id);
        findById.get().setStatus(false);
        financialYearRepository.save(findById.get());

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Application details deleted");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Financial Year Deactive Successfully", HttpStatus.CREATED);
    }

    // ============================= Financial Year Setup Start==================

    // ============================= Identification Setup Start==================
    @Override
    public List<IdentificationSetup> getIdentificationSetup() {
        List<IdentificationSetup> collect = identificationSetupRepository.findAll().stream()
                .filter(t -> t.getStatus().equals(true))
                .collect(Collectors.toList());
        for (IdentificationSetup ds : collect) {
            Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(ds.getMenu());
            ds.setManuname(findById.get().getSubmenu());
        }
        return collect;
    }

    @Override
    public IdentificationSetup getIdentificationSetupByid(Integer id) {
        Optional<IdentificationSetup> doc = identificationSetupRepository.findById(id);
        Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(doc.get().getMenu());
        doc.get().setManuname(findById.get().getSubmenu());
        return doc.get();
    }

    @Override
    public ResponseEntity<String> addIdentificationSetup(@Valid IdentificationSetup identificationSetup) {
        identificationSetup.setStatus(true);
        @Valid
        IdentificationSetup save = identificationSetupRepository.save(identificationSetup);

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Identification Setup added");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Identification Setup Successfully Updated", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateIdentificationSetup(@Valid IdentificationSetup identificationSetup) {
        identificationSetup.setStatus(true);
        @Valid
        IdentificationSetup save = identificationSetupRepository.save(identificationSetup);

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Identification Setup updated");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Identification Setup Successfully Updated", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteIdentificationSetup(Integer id) {
        Optional<IdentificationSetup> doc = identificationSetupRepository.findById(id);
        doc.get().setStatus(false);
        IdentificationSetup save = identificationSetupRepository.save(doc.get());

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Identification Setup deleted");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Identification Setup Delete Successfully", HttpStatus.CREATED);
    }

    // ============================= Identification Setup End==================

    // ============================= Email Setup Start==================

    @Override
    public List<EmailSetup> getEmailSetup() {
        List<EmailSetup> collect = emailSetupRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
                .collect(Collectors.toList());
        for (EmailSetup es : collect) {
            Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(es.getMenuid());
            es.setMenuname(findById.get().getSubmenu());
        }
        return collect;
    }

    @Override
    public EmailSetup getEmailSetupByid(Integer id) {
        Optional<EmailSetup> email = emailSetupRepository.findById(id);
        Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(email.get().getMenuid());
        email.get().setMenuname(findById.get().getSubmenu());
        return email.get();
    }

    @Override
    public ResponseEntity<String> postEmailSetup(@Valid EmailSetup emailSetup) {
        emailSetup.setStatus(true);
        @Valid
        EmailSetup save = emailSetupRepository.save(emailSetup);

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Email setup added");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Email Setup Successfully Created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateEmailSetup(@Valid EmailSetup emailSetup) {
        emailSetup.setStatus(true);
        @Valid
        EmailSetup save = emailSetupRepository.save(emailSetup);

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Email setup updated");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Email Setup Successfully Updated", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteEmailSetup(Integer id) {
        Optional<EmailSetup> email = emailSetupRepository.findById(id);
        email.get().setStatus(false);
        emailSetupRepository.save(email.get());

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("Email setup deleted");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Email Setup Entry Delete Successfully", HttpStatus.CREATED);
    }

    // ============================= Email Setup End==================

    // ============================= Approval Setup Start==================
    @Override
    public List<ApprovalSetup> getApprovalSetup() {
        List<ApprovalSetup> collect = approvalSetupRepository.findAll().stream().filter(t -> t.getStatus().equals(true))
                .collect(Collectors.toList());
        for (ApprovalSetup as : collect) {
            Optional<ViewPrivilege> menu = viewPrivilegeRepository.findById(as.getManuid());
            Optional<User> user = userRepository.findById(as.getUserid());
            as.setMenuname(menu.get().getSubmenu());
            as.setUsername(user.get().getName());
        }
        return collect;
    }

    @Override
    public ApprovalSetup getApprovalSetupByid(Integer id) {
        Optional<ApprovalSetup> approv = approvalSetupRepository.findById(id);
        Optional<ViewPrivilege> menu = viewPrivilegeRepository.findById(approv.get().getManuid());
        Optional<User> user = userRepository.findById(approv.get().getUserid());
        approv.get().setMenuname(menu.get().getSubmenu());
        approv.get().setUsername(user.get().getName());
        return approv.get();
    }

    @Override
    public ResponseEntity<String> postApprovalSetup(@Valid ApprovalSetup approvalSetup) {
        approvalSetup.setStatus(true);
        @Valid
        ApprovalSetup save = approvalSetupRepository.save(approvalSetup);

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("ApprovalSetup setup added");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Approval Setup Successfully Created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateApprovalSetup(@Valid ApprovalSetup approvalSetup) {
        approvalSetup.setStatus(true);
        @Valid
        ApprovalSetup save = approvalSetupRepository.save(approvalSetup);

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("ApprovalSetup setup updated");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Approval Setup Successfully Update", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteApprovalSetup(Integer id) {
        Optional<ApprovalSetup> findById = approvalSetupRepository.findById(id);
        findById.get().setStatus(false);
        approvalSetupRepository.save(findById.get());

        String names = SecurityContextHolder.getContext().getAuthentication().getName();
        Transactionlog trlog = new Transactionlog();
        trlog.setUsername(names);
        trlog.setTimestamp(LocalDateTime.now());
        trlog.setActivity("ApprovalSetup setup deleted");
        transactionlogRepository.save(trlog);

        return new ResponseEntity<String>(" Approval Entry Deleted", HttpStatus.CREATED);
    }
    // ============================= Approval Setup End==================

    // ============================= Login History Start==================
    @Override
    public List<LoginHistory> getLoginHostory() {
        LocalDate date = LocalDate.now();
        List<LoginHistory> findAll = loginHistoryRepository.getTodayLogin(date);
        for (LoginHistory loginHistory : findAll) {
            Optional<User> findByUsername = userRepository.findByUsername(loginHistory.getUsername());
            loginHistory.setName(findByUsername.get().getName());
        }

        return findAll;
    }

    @Override
    public List<LoginHistory> postLoginHostory(LoginTranDTO dto) {
        System.out.println(dto);
        if (dto.getFrom().equals("0") && dto.getFrom().equals("0")) {
            List<LoginHistory> findAll = loginHistoryRepository.getTodayLoginByUser(dto.getUsername());
            for (LoginHistory loginHistory : findAll) {
                Optional<User> findByUsername = userRepository.findByUsername(loginHistory.getUsername());
                loginHistory.setName(findByUsername.get().getName());
            }

            return findAll;
        }

        else if (dto.getUsername().equals("0")) {
            List<LoginHistory> findAll = loginHistoryRepository.getTodayLoginByDate(dto.getFrom(), dto.getTo());
            for (LoginHistory loginHistory : findAll) {
                Optional<User> findByUsername = userRepository.findByUsername(loginHistory.getUsername());
                loginHistory.setName(findByUsername.get().getName());
            }

            return findAll;

        } else {
            List<LoginHistory> findAll = loginHistoryRepository.getTodayLoginByDateAndUser(dto.getFrom(), dto.getTo(),
                    dto.getUsername());
            for (LoginHistory loginHistory : findAll) {
                Optional<User> findByUsername = userRepository.findByUsername(loginHistory.getUsername());
                loginHistory.setName(findByUsername.get().getName());
            }

            return findAll;

        }

    }
    // ============================= Login History End==================

    // ============================= Transaction log End==================
    @Override
    public List<Transactionlog> getTransactionlog(LoginTranDTO dto) {
        List<Transactionlog> findAll = transactionlogRepository.tranLog(dto.getFrom(), dto.getTo(), dto.getUsername());

        for (Transactionlog transactionlog : findAll) {
            Optional<User> findByUsername = userRepository.findByUsername(transactionlog.getUsername());
            transactionlog.setName(findByUsername.get().getName());
        }
        return findAll;
    }
    // ============================= Transaction log End==================

    // ============================= Document Setup Start==================

    @Override
    public List<DocumentSetupHeader> getDocument() {
        return documentSetupHeaderRepository.findAll()
                .stream()
                .filter(t -> t.getStatus().equals(true)).collect(Collectors.toList());
    }

    @Override
    public DocumentSetupHeader getDocumentByid(Integer id) {
        Optional<DocumentSetupHeader> findById = documentSetupHeaderRepository.findById(id);
        return findById.get();
    }

    @Override
    public DocumentSetupHeader getDocumentByCode(String code) {
        List<DocumentSetupHeader> collect = documentSetupHeaderRepository
                .findAll()
                .stream()
                .filter(t -> t.getDocMasterShortName().equals(code)).collect(Collectors.toList());
        return collect.get(0);
    }

    @Override
    public ResponseEntity<String> addDocument(@Valid DocumentSetupHeader document) {
        document.setStatus(true);
        @Valid
        DocumentSetupHeader save = documentSetupHeaderRepository.save(document);

        return new ResponseEntity<String>("New Document Details Added", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateDocument(@Valid DocumentSetupHeader document) {
        document.setStatus(true);
        DocumentSetupHeader heder = new DocumentSetupHeader();
        heder.setId(document.getId());
        heder.setDocMasterName(document.getDocMasterName());
        heder.setDocMasterShortName(document.getDocMasterShortName());
        heder.setStatus(true);

        List<DocumentSetupDetails> details = document.getDetails();
        for (DocumentSetupDetails documentSetupDetails : details) {
            documentSetupDetails.setHeader(heder);
        }
        heder.setDetails(details);
        documentSetupHeaderRepository.save(heder);

        return new ResponseEntity<String>("Document Details Updated", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteDocument(Integer id) {
        Optional<DocumentSetupHeader> findById = documentSetupHeaderRepository.findById(id);
        findById.get().setStatus(false);
        documentSetupHeaderRepository.save(findById.get());
        return new ResponseEntity<String>("Document master deleted", HttpStatus.CREATED);
    }

    @Override
    public String deleteDocumentDetails(Integer id) {
        documentSetupDetailsRepository.deleteById(id);
        return "Data deleted";
    }

    // ============================= Document Setup End==================

    // ============================= View Privilege Setup Start==================

    @Override
    public List<ViewPrivilege> getViewPrivilege() {
        return viewPrivilegeRepository.findAll();
    }

    @Override
    public ViewPrivilege getViewPrivilegeByid(Integer id) {
        Optional<ViewPrivilege> findById = viewPrivilegeRepository.findById(id);
        return findById.get();
    }

    @Override
    public ResponseEntity<String> postViewPrivilege(@Valid ViewPrivilege viewPrivilege) {
        @Valid
        ViewPrivilege save = viewPrivilegeRepository.save(viewPrivilege);
        return new ResponseEntity<String>("Data Saved", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateViewPrivilege(@Valid ViewPrivilege viewPrivilege) {
        @Valid
        ViewPrivilege save = viewPrivilegeRepository.save(viewPrivilege);
        return new ResponseEntity<String>("Data Updated", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteViewPrivilege(Integer id) {
        viewPrivilegeRepository.deleteById(id);
        return new ResponseEntity<String>("Data Deteled", HttpStatus.CREATED);
    }
    // ============================= View Privilege Year Setup End==================

}
