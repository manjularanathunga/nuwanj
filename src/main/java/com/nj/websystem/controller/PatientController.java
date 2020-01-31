package com.nj.websystem.controller;

import com.nj.websystem.enums.Gender;
import com.nj.websystem.enums.Status;
import com.nj.websystem.model.Patient;
import com.nj.websystem.rest.HttpResponse;
import com.nj.websystem.service.PatientService;
import com.nj.websystem.util.CSVUtils;
import com.nj.websystem.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/patient")
public class PatientController {

    static Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService services;

    private static Patient logError(List<String> l, Patient p, String error) {
        StringBuilder _sb = new StringBuilder(l.get(2));
        _sb.append("/n/r");
        //_sb.append("Error in DOB Years :"+ l.get(7) +"/" + l.get(8) +"/" + l.get(9) );
        _sb.append(error);
        p.setRemarks(_sb.toString());
        return p;
    }

/*    @GetMapping
    public ResponseEntity<List<Patient>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Patient> list = services.getAllEmployees(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<Patient>>(list, new HttpHeaders(), HttpStatus.OK);
    }*/

    private static boolean get(String val) {
        if (val != null)
            if (!val.isEmpty() && !val.equals("?")) {
                return true;
            } else {
                return false;
            }
        else
            return false;
    }

    @RequestMapping(value = "/getNextPatientId", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse getNextPatientId() {

        HttpResponse res = new HttpResponse();
        //int yearlyRecordCount = services.findAll().size();
        int yearlyRecordCount = 0;
        String nextPatientId = StringUtility.getCustDateByPatten(StringUtility.YY) + StringUtility.getCustDateByPatten(String.format("%05d", (yearlyRecordCount + 1)));
        logger.info("Patient - NextPatientId : {} " + nextPatientId);
        res.setResponse(nextPatientId);
        res.setSuccess(true);
        res.setRecCount(1);
        return res;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET, headers = "Accept=application/json")
    public Page<Patient> getList() {
        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));
        Page<Patient> list = services.findAll(paging);
        logger.info("Count of UserAdmin : {} " + list.getTotalElements());
        return list;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse getById(@RequestParam(value = "id", required = false) long id) {
        logger.info("Request Patient getById : {} " + id);
        HttpResponse res = new HttpResponse();
        Optional<Patient> patientList = services.findById(id);
        if (patientList != null) {
            res.setResponse(patientList.get());
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid Patient !");
        }
        return res;
    }

    @RequestMapping(value = "/findByPatientId", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findByPatientId(@RequestParam(value = "patientId", required = true) String patientId) {
        logger.info("Request Patient findByPatientId : {} " + patientId);
        HttpResponse res = new HttpResponse();
        List<Patient> patientList = services.findByPatientId(patientId);
        if (patientList != null && !patientList.isEmpty()) {
            res.setResponse(patientList.get(0));
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid Patient !");
        }
        return res;
    }

    @RequestMapping(value = "/findByPatientListById", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findByPatientListById(@RequestParam(value = "patientId", required = false) String patientId) {
        logger.info("Request Patient findByPatientListById : {} " + patientId);

/*
        final PageRequest page1 = new PageRequest(
                0, 20, Sort.Direction.ASC, "lastName", "salary"
        );
*/

        // Pageable paging = PageRequest.of(1, 10000, Sort.Direction.DESC, "patientId");
        HttpResponse res = new HttpResponse();
        List<String> patientList = services.findByPatientIdContaining(patientId);
        if (patientList != null && !patientList.isEmpty()) {
            res.setResponse(patientList);
            res.setSuccess(true);
            res.setRecCount(patientList.size());
        } else {
            res.setSuccess(false);
            res.setException("Invalid Patient ID !");
        }
        return res;
    }

    @RequestMapping(value = "/findByPatientIdContainingPages", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findByPatientIdContainingAndStatus(@RequestParam(value = "patientId", required = false) String patientId) {
        logger.info("Request Patient findByPatientListById : {} " + patientId);
        Pageable paging = PageRequest.of(1, 100, Sort.by("patientId"));
        HttpResponse res = new HttpResponse();
        List<Patient> patientList = services.findByPatientIdContainingAndStatus(patientId, Status.ACTIVE);
        if (patientList != null && !patientList.isEmpty()) {
            res.setResponse(patientList);
            res.setSuccess(true);
            res.setRecCount(patientList.size());
        } else {
            res.setSuccess(false);
            res.setException("Invalid Patient ID !");
        }
        return res;
    }

    @RequestMapping(value = "/findByNicNumber", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponse findByNicNumber(@RequestParam(value = "id", required = false) String nicNumber) {
        logger.info("Request Patient findByNicNumber : {} " + nicNumber);
        HttpResponse res = new HttpResponse();
        Pageable paging = PageRequest.of(1, 12, Sort.by("id"));
        List<Patient> patientList = services.findByNicNumber(nicNumber, paging);
        if (patientList != null && !patientList.isEmpty()) {
            res.setResponse(patientList.get(0));
            res.setSuccess(true);
            res.setRecCount(1);
        } else {
            res.setSuccess(false);
            res.setException("Invalid Patient !");
        }
        return res;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Patient save(@RequestBody Patient obj) {
        logger.info("save Patient Name : {} " + obj.getNicNumber());
        return services.save(obj);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public HttpResponse delete(@RequestParam(value = "id", required = false) Long id) {
        logger.info("Delete Patient id : {} " + id);
        HttpResponse response = new HttpResponse();
        Patient item = services.findAllById(id);
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

    @RequestMapping(value = "/loadPatient", method = RequestMethod.GET, headers = "Accept=application/json")
    public List loadPatient() {
        List list = loadBulk();
        services.saveAll(list);
        logger.info("Count of loadBulk : {} " + list.size());
        return list;
    }

    private List loadBulk() {
        List<List> lineList = null;
        String csvFile = "/Users/sirimewanranathunga/Desktop/PatientData/juwan-2019/Patient-Table 1.csv";
        try {
            lineList = CSVUtils.LoadFile(csvFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Patient> listOfPatients = new ArrayList<>();
        Patient p;
        int count = 0;
        for (List<String> l : lineList) {
            if (l.get(0).equals("Scan Registration No")) {
                continue;
            }
            int j = 0;
            for (String s : l) {
                System.out.print("[" + j + "]=" + s + ", ");
                j++;
            }
            p = new Patient();

            String nextPatientId = StringUtility.getCustDateByPatten(String.format("%05d", (count + 1)));
            Date d = new Date();
            d.setYear(2019);
            p.setPatientId(StringUtility.getCustDateByPatten(StringUtility.YY, d) + nextPatientId);

            try {
                String data = l.get(2);
                if (get(data)) {
                    p.setRemarks(data);
                } else {
                    logError(l, p, "Error in Remarks :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Remarks :" + e.getMessage());
            }

            try {
                String data = l.get(1);
                if (get(data)) {
                    p.setDateCreated(new Date(data));
                } else {
                    logError(l, p, "Error in DateCreated :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in DateCreated :" + e.getMessage());
            }

            try {
                String data = l.get(3);
                if (get(data)) {
                    p.setPatientName(data);
                } else {
                    logError(l, p, "Error in PatientName :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in PatientName :" + e.getMessage());
            }


            try {
                String gender = l.get(10);
                if (get(gender)) {
                    if (gender.toUpperCase().equals("FEMALE")) {
                        p.setGender(Gender.FEMALE);
                    } else {
                        p.setGender(Gender.MALE);
                    }
                } else {
                    logError(l, p, "Error in GENDER :" + gender);
                }
            } catch (Exception e) {
                logError(l, p, "Error in GENDER :" + e.getMessage());
            }

            try {
                String years = l.get(7);
                String months = l.get(8);
                String days = l.get(9);

                if (get(years)) {
                    Date dob = new Date();
                    int year = Integer.parseInt(l.get(7));
                    dob.setYear(dob.getYear() - year);
                    p.setDateOfBirth(dob);
                } else {
                    logError(l, p, "Error in DOB Years :" + years + "/" + months + "/" + days);
                }
            } catch (Exception e) {
                logError(l, p, "Error in GENDER :" + e.getMessage());
            }

            try {
                String data = l.get(33);
                if (get(data)) {
                    p.setNicNumber(data);
                } else {
                    logError(l, p, "Error in NicNumber :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in GENDER :" + e.getMessage());
            }

            try {
                String data = l.get(4);
                if (get(data)) {
                    p.setOther(data);
                } else {
                    logError(l, p, "Error in Other :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Other :" + e.getMessage());
            }

            try {
                String data = l.get(6);
                if (get(data)) {
                    p.setTelNumber(data);
                } else {
                    logError(l, p, "Error in TelNumber :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in TelNumber :" + e.getMessage());
            }

            try {
                String data = l.get(5);
                if (get(data)) {
                    p.setPatientAddress(data);
                } else {
                    logError(l, p, "Error in PatientAddress :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in PatientAddress :" + e.getMessage());
            }

            try {
                String data = l.get(11);
                if (get(data)) {
                    p.setBht(data);
                } else {
                    logError(l, p, "Error in Bht :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Bht :" + e.getMessage());
            }

            //p.setCityName();
            // p.setDistrictName();

            p.setActionBy("admin");
            p.setStatus(Status.ACTIVE);
            System.out.println(p.toString());
            listOfPatients.add(p);
            count++;
            // break;
        }
        return listOfPatients;
    }
}
