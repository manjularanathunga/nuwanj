package com.nj.websystem.controller;

import com.nj.websystem.model.PatientMedicalTest;
import com.nj.websystem.model.PatientScan;
import com.nj.websystem.model.PatientScan;
import com.nj.websystem.model.PatientScan;
import com.nj.websystem.rest.HttpResponse;
import com.nj.websystem.service.PatientMedicalTestService;
import com.nj.websystem.service.PatientScanServise;
import com.nj.websystem.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/scan")
public class PatientScanController {

    static Logger logger = LoggerFactory.getLogger(PatientScanController.class);

    @Autowired
    private PatientScanServise services;

    @Autowired
    private PatientMedicalTestService patientMedicalTestService;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/getList", method = RequestMethod.GET, headers = "Accept=application/json")
    public Page<PatientScan> getList() {
        Pageable paging = PageRequest.of(1, 25, Sort.by("id"));
        Page<PatientScan> list = services.findAll(paging);
        logger.info("Count of PatientScan : {} " + list.getTotalElements());
        return list;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse getById(@RequestParam(value = "id", required = false) String id) {
        logger.info("Request PatientScan Id : {} " + id);
        HttpResponse res = new HttpResponse();
        PatientScan patientScan = services.findAllById(Long.parseLong(id));
        if (patientScan != null) {
            res.setResponse(patientScan);
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid User !");
        }
        return res;
    }

    @RequestMapping(value = "/getPatientByBilling", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse billingNumber(@RequestParam(value = "billingNumber", required = false) String billingNumber) {
        logger.info("Request PatientScan billingNumber : {} " + billingNumber);
        HttpResponse res = new HttpResponse();
        List<PatientMedicalTest> patientMedicalTestList = patientMedicalTestService.findAllByBillingNumber(billingNumber);
        if(!patientMedicalTestList.isEmpty()){
            List uiItemMap = new ArrayList();
            PatientMedicalTest item = patientMedicalTestList.get(0);
            uiItemMap.add(item);
            uiItemMap.add(patientService.findByPatientId(item.getPatientId()).get(0));
            uiItemMap.add(patientMedicalTestService.getAllByPatientId(item.getPatientId()));
            res.setResponse(uiItemMap);
            res.setSuccess(true);
            res.setRecCount(1);
        }else{
            res.setSuccess(false);
            res.setException("Invalid PatientScan Id !");
        }
        return res;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public PatientScan save(@RequestBody PatientScan obj) {
        logger.info("Save / PatientScan Name : {} " + obj.getBillingNumber());
        HttpResponse res = new HttpResponse();
        PatientScan result = services.save(obj);
        if (result == null) {
            res.setResponse(result);
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid User !");
        }
        return services.save(obj);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public HttpResponse delete(@RequestParam(value = "id", required = false) Long id) {
        logger.info("Delete PatientScan Name : {} " + id);
        HttpResponse response = new HttpResponse();
        PatientScan patientScan = services.findAllById(id);
        if (patientScan != null) {
            services.delete(patientScan);
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            logger.info("Record has been already deleted : {} " + id);
            response.setException("Record has been already deleted");
        }
        return response;
    }

    @RequestMapping(value = "/bulkInsert", method = RequestMethod.POST, headers = "Accept=application/json")
    public void bulkInsert(@RequestBody List<PatientScan> items) {
        logger.info("PatientScan countt : {} " + items.size());
        services.saveAll(items);
    }
}