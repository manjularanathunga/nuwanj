package com.nj.websystem.controller;

import com.nj.websystem.enums.Status;
import com.nj.websystem.enums.TestType;
import com.nj.websystem.model.*;
import com.nj.websystem.rest.HttpResponse;
import com.nj.websystem.rest.Rest;
import com.nj.websystem.rest.lab.LabBaen;
import com.nj.websystem.service.*;
import com.nj.websystem.util.DateUtility;
import com.nj.websystem.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/patientmedicaltest")
public class PatientMedicalTestController {

    static Logger logger = LoggerFactory.getLogger(PatientMedicalTestController.class);

    @Autowired
    private PatientMedicalTestService services;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private MedicalTestService medicalTestService;

    @Autowired
    private PatientTestService patientTestService;

    @RequestMapping(value = "/getList", method = RequestMethod.GET, headers = "Accept=application/json")
    public List getList() {
        List list = services.findFirst25ByOrderByDateCreatedDesc();
        logger.info("Count of UserAdmin : " + list.size());
        return list;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse getById(@RequestParam(value = "id", required = false) long id) {
        logger.info("Request MedicalTest Id : {} " + id);
        HttpResponse res = new HttpResponse();
        List<PatientMedicalTest> patientList = services.findAll();
        if (patientList != null && !patientList.isEmpty()) {
            res.setResponse(patientList);
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid MedicalTest !");
        }
        return res;
    }

    @RequestMapping(value = "/findAllByPatientId", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findAllByPatientId(@RequestParam(value = "patientId", required = false) String patientId) {
        logger.info("Request MedicalTest Patient Id : {} " + patientId);
        HttpResponse res = new HttpResponse();
        List<PatientMedicalTest> patientList = services.findAllByPatientId(patientId);
        if (patientList != null && !patientList.isEmpty()) {
            res.setResponse(patientList);
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid Patient Medical Test !");
        }
        return res;
    }

    @RequestMapping(value = "/findAllByPatientIdAndType", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findAllByPatientIdAndType(@RequestParam(value = "patientid", required = false) String patientid, @RequestParam(value = "type", required = false) TestType type) {
        logger.info("Request findAllByPatientIdAndType Id : {patientId, type} " + patientid + " | " + type);
        HttpResponse res = new HttpResponse();
        List<PatientMedicalTest> patientList = services.findAllByPatientIdAndTestType(patientid, type);
        if (patientList != null && !patientList.isEmpty()) {
            res.setResponse(patientList);
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid Patient !");
        }
        return res;
    }

    @RequestMapping(value = "/getAllByTestTypeOrderByIdDesc", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse getAllByTestTypeOrderByIdDesc(@RequestParam(value = "type", required = false) TestType type) {
        logger.info("Request findAllByPatientIdAndType Id : {type} " + " | " + type);
        HttpResponse res = new HttpResponse();
        List<PatientMedicalTest> patientList = services.getAllByTestTypeOrderByIdDesc(type);
        PatientMedicalTest medicalTest = null;

        if (patientList.size() > 0) {
            medicalTest = patientList.get(0);
        }

        String nextNumber = "";
        if (medicalTest != null) {
            String exitsingNumber = medicalTest.getBillingNumber().substring(3, medicalTest.getBillingNumber().length());
            logger.info("Patient - NextPatientId : {} " + exitsingNumber);
            nextNumber = StringUtility.getCustDateByPatten(StringUtility.YY) + type + StringUtility.getFilledNumber((Integer.parseInt(exitsingNumber) + 1), 4L);
        } else {
            nextNumber = StringUtility.getCustDateByPatten(StringUtility.YY) + type + StringUtility.getFilledNumber((1), 4L);
        }

        if (!StringUtility.isEmpty(nextNumber)) {
            res.setResponse(nextNumber);
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Fail to read next sequence !");
        }

        return res;
    }

    @RequestMapping(value = "/getAllByTestNumberOrderByPriority", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse getAllByTestNumberOrderByPriority(@RequestParam(value = "testNumber", required = false) String testNumber) {
        logger.info("Request getAllByTestNumberOrderByPriority testNumber : {} " ,  testNumber);
        HttpResponse res = new HttpResponse();
        List<PatientMedicalTest> result = new ArrayList<>();
        List<PatientMedicalTest> patientMedicalTest = services.getAllByTestNumberOrderByPriority(testNumber);
        patientMedicalTest.forEach(i ->{
            if(i.getDateCreated().after(DateUtility.getCuttentYear())){
                result.add(i);
            }
        });
        logger.info("result.size()  > " + result.size());
        if (patientMedicalTest.size() > 0) {
            res.setResponse(result);
            res.setSuccess(true);
            res.setRecCount(result.size());
        } else {
            res.setSuccess(false);
            res.setException("Records not found !");
        }
        return res;
    }



    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponse save(@RequestBody PatientMedicalTest obj) {
        HttpResponse res = new HttpResponse();
        logger.info("Saving TestName : " + obj.getName());
        List<PatientMedicalTest> testsList = services.getAllByTestType(obj.getTestType());
        String testNumber = StringUtility.getCustDateByPatten(StringUtility.YY) + obj.getTestType() + String.format("%05d", (testsList.size() + 1));
        obj.setTestNumber(testNumber.toUpperCase());
        PatientMedicalTest savedMedicalTest = services.save(obj);

        if (savedMedicalTest != null) {
            //res.setResponse();
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Fail to save Medical Test : " + obj.getTestNumber());
        }
        return res;
    }

    @RequestMapping(value = "/SaveScan", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponse SaveScan(@RequestBody PatientMedicalTest obj) {
        HttpResponse res = new HttpResponse();
        logger.info("Saving TestName : " + obj.getName());

        logger.info("toString " + obj.toString());

        PatientMedicalTest savedMedicalTest = services.save(obj);

        if (savedMedicalTest != null) {
            res.setResponse(savedMedicalTest);
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Fail to save Medical Test : " + obj.getTestNumber());
        }
        return res;
    }


    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public HttpResponse delete(@RequestParam(value = "id", required = false) Long id) {
        logger.info("Delete OfficeRoom Name : {} " + id);
        HttpResponse response = new HttpResponse();
        PatientMedicalTest item = services.getOne(id);
        if (item != null) {
            services.delete(item);
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            logger.info("Record has been already deleted : {} " + id);
            response.setException("Record has been already deleted");
        }
        return response;
    }

    @RequestMapping(value = "/findAllByPatientIdAndBillingNumber", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findAllByPatientIdAndBillingNumber(@RequestParam(value = "patientid", required = false) String patientid, @RequestParam(value = "billingNumber", required = false) String billingNumber) {
        logger.info("Delete OfficeRoom Name : {} " + billingNumber);
        HttpResponse response = new HttpResponse();
        List<PatientMedicalTest> itemList = services.findAllByPatientIdAndBillingNumber(patientid, billingNumber);
        if (itemList != null && itemList.size() > 0) {
            response.setResponse(itemList);
            response.setRecCount(itemList.size());
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            logger.info("Record not found  : {} " + billingNumber);
            response.setException("Record not found  : {} " + billingNumber);
        }
        return response;
    }

    @RequestMapping(value = "/findAllByBillingNumber", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findAllByBillingNumber(@RequestParam(value = "billingNumber", required = false) String billingNumber) {
        logger.info("findAllByBillingNumber : {} " + billingNumber);
        HttpResponse response = new HttpResponse();
        Optional<Billing> blOps = billingService.findByBillingNumber(billingNumber);
        if(blOps.isPresent()){
            Billing bl = blOps.get();
            List<PatientTest> listOfPatientTst = bl.getListOfPatientTst();
            if (listOfPatientTst != null && listOfPatientTst.size() > 0) {
                LabBaen labBaen = new LabBaen();
                List neanLst = new ArrayList();
                if (listOfPatientTst.size() > 0) {
                    listOfPatientTst.forEach(r ->{
                        neanLst.add(initLabBaen(r,bl));
                    });
                    response.setResponse(neanLst);
                    response.setSuccess(true);
                    response.setRecCount(neanLst.size());
                    return response;
                }
            }
        }

        response.setSuccess(false);
        logger.info("Record not found  : {} " + billingNumber);
        response.setException("Record not found  : {} " + billingNumber);
        return response;
    }

    @RequestMapping(value = "/findAllActiveByBillingNumber", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findAllActiveByBillingNumber(@RequestParam(value = "billingNumber", required = false) String billingNumber) {
        logger.info("findAllActiveByBillingNumber : {} " + billingNumber);
        HttpResponse response = new HttpResponse();
        List<PatientMedicalTest> itemList = services.findAllByBillingNumber(billingNumber);
        if (itemList != null && itemList.size() > 0) {
            if (itemList.size() > 0) {
                Patient patient = patientService.findByPatientId(itemList.get(0).getPatientId()).get(0);
                List test = new ArrayList();
                test.add(patient);
                test.add(itemList);
                response.setResponse(test);
            }
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            logger.info("Record not found  : {} " + billingNumber);
            response.setException("Record not found  : {} " + billingNumber);
        }
        return response;
    }

    @RequestMapping(value = "/saveList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponse bulkInsert(@RequestBody List<PatientMedicalTest> items) {
        logger.info("PatientMedicalTest count : {} " + items.size());
        HttpResponse response = new HttpResponse();
        items.forEach(i ->
                i.setId(null)
        );
        List<PatientMedicalTest> resultLst = services.saveAll(items);

        Billing bln = initPatientMedicalTest(resultLst.get(0));
        billingService.save(bln);

        resultLst.forEach(r ->{
            PatientTest pt = initPatientTest(r);
            pt.setBilling(bln);
            // pt.setRemarks(); // set by doctor
            //pt.setPrescription();// set by doctor
            //pt.setReferBy();// set by doctor
            patientTestService.save(pt);

        });
        //saving bill number


        if (resultLst != null && resultLst.size() > 0) {
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.setException("Record not saved");
        }
        return response;

    }

    private Billing initPatientMedicalTest(PatientMedicalTest pmt){
        Billing blg = new Billing();
        blg.setBillingNumber(pmt.getBillingNumber());
        blg.setPriority(pmt.getPriority());
        Patient p = patientService.findByPatientId(pmt.getPatientId()).get(0);
        blg.setPatient(p);
        blg.setActionBy(pmt.getActionBy());
        blg.setDateCreated(new Date());
        blg.setStatus(Status.OPEN);
        //blg.setLastModified(); // set by printer or doc
        return blg;
    }

    private PatientTest initPatientTest(PatientMedicalTest pmt){
        MedicalTest mt = medicalTestService.findAllByTestNumber(pmt.getTestNumber()).get(0);
        PatientTest pt = new PatientTest();
        pt.setLabType(mt.getLabType());
        pt.setMedicalTest(mt);
        pt.setStatus(Status.OPEN);
        pt.setSeenBy(pmt.getSeenBy());
        pt.setPriority(pmt.getPriority());
        return pt;
    }

    private LabBaen initLabBaen(PatientTest rec, Billing b){
        LabBaen labBaen = new LabBaen();
        MedicalTest mt = rec.getMedicalTest();
        labBaen.setStatus(Status.OPEN);
        labBaen.setTestNumber(mt.getTestNumber());
        labBaen.setPatientName(b.getPatient().getPatientName());
        labBaen.setPatientDOB(b.getPatient().getDateOfBirth());
        labBaen.setBillingDate(b.getDateCreated());
        labBaen.setName(rec.getMedicalTest().getName());
        labBaen.setDistrictName(b.getPatient().getDistrictName());
        //labBaen.setReference();
        // labBaen.setResults(); adding in the screeen
      return labBaen;
    }
}