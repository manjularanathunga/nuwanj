package com.nj.websystem.controller.utils;

import com.nj.websystem.enums.Gender;
import com.nj.websystem.enums.Status;
import com.nj.websystem.model.Patient;
import com.nj.websystem.service.PatientService;
import com.nj.websystem.util.CSVUtils;
import com.nj.websystem.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/loader")
public class LoaderController {

    static Logger logger = LoggerFactory.getLogger(LoaderController.class);

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

    @RequestMapping(value = "/loadPatient", method = RequestMethod.GET, headers = "Accept=application/json")
    public List loadPatient() {
        List list = loadBulk();
        services.saveAll(list);
        logger.info("Count of loadBulk : {} " + list.size());
        return list;
    }

    private List loadBulk() {
        List<List> lineList = null;
        String csvFile = "/Users/sirimewanranathunga/Desktop/projects/PatientData/juwan-2019/PatientTable1.csv";
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
