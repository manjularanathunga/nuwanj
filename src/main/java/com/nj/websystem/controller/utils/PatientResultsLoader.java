package com.nj.websystem.controller.utils;

import com.nj.websystem.enums.Status;
import com.nj.websystem.model.MedicalTest;
import com.nj.websystem.model.Patient;
import com.nj.websystem.model.PatientMedicalTest;
import com.nj.websystem.service.MedicalTestService;
import com.nj.websystem.service.PatientMedicalTestService;
import com.nj.websystem.service.PatientService;
import com.nj.websystem.util.CSVUtils;
import com.nj.websystem.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class PatientResultsLoader {

    private static Logger logger = LoggerFactory.getLogger(PatientResultsLoader.class);

    @Autowired
    private static PatientMedicalTestService myservices;



    private static PatientMedicalTest logError(List<String> l, PatientMedicalTest p, String error) {
        StringBuilder _sb = new StringBuilder(l.get(2));
        _sb.append("/n/r");
        //_sb.append("Error in DOB Years :"+ l.get(7) +"/" + l.get(8) +"/" + l.get(9) );
        _sb.append(error);
        p.setRemarks(_sb.toString());
        return p;
    }

    public void executeLoader(PatientMedicalTestService services, PatientService servicesp, MedicalTestService medicalTestService) {

        myservices = services;

        List<List> lineList = null;
        String csvFile = "/Users/sirimewanranathunga/Desktop/projects/PatientData/juwan-2019/cvs/PatientResults.csv";
        try {
            lineList = CSVUtils.LoadFile(csvFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        run(lineList, servicesp, medicalTestService);
    }

    private void run(List<List> lineList, PatientService servicesp, MedicalTestService medicalTestService) {
        List<PatientMedicalTest> listOfPatients = new ArrayList<>();
        PatientMedicalTest p;
        int count = 0;
        for (List<String> l : lineList) {
            if (l.get(0).equals("ID")) {
                continue;
            }
            int j = 0;
            for (String s : l) {
                //System.out.print("[" + j + "]=" + s + ", ");
                j++;
            }
            p = new PatientMedicalTest();


            try {
                String data = l.get(1);
                if (StringUtility.get(data)) {
                    p.setResults(data);
                } else {
                    logError(l, p, "Error in Remarks :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Remarks :" + e.getMessage());
            }
            Patient patient = null;
            try {
                String data = l.get(2);
                if (StringUtility.get(data)) {
                    p.setBillingNumber(data);
                    List<Patient> lst = servicesp.findAllByBillingNumber(data);
                    if (lst.size() > 0) {
                        patient = lst.get(0);
                    }
                } else {
                    logError(l, p, "Error in DateCreated :" + data);
                }

                p = updateFromPatient(p, patient, medicalTestService);


            } catch (Exception e) {
                logError(l, p, "Error in DateCreated :" + e.getMessage());
            }

            try {
                String data = l.get(3);
                if (StringUtility.get(data)) {
                    //String strNamer = stringMap.get(data);
/*                    if(strNamer.contains("-")){

                    }*/
                    //p.setName(strNamer);
                    //System.out.println("strNamer >" + data);
                    List<MedicalTest> lst = medicalTestService.findAllByOldTestName(data);

                    //System.out.println("isPresent >" + lst.size());

                    if(lst.size() > 0){
                        p.setTestNumber(lst.get(0).getTestNumber());
                        p.setName(lst.get(0).getName());
                    }

                } else {
                    logError(l, p, "Error in PatientName :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in PatientName :" + e.getMessage());
            }

            try {
                String data = l.get(4);
                if (StringUtility.get(data)) {
                    StringTokenizer _st = new StringTokenizer(data, "/");
                    String date = _st.nextToken();
                    String month = _st.nextToken();
                    String year = _st.nextToken();
                    String appDate = year + "-" + month + "-" + date;
                    SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
                    p.setDateCreated(formatter.parse(appDate));
                } else {
                    logError(l, p, "Error in DateCreated :" + data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logError(l, p, "Error in DateCreated :" + e.getMessage());
            }

            try {
                String data = l.get(5);
                if (StringUtility.get(data)) {
                    p.setUnits(data);
                } else {
                    logError(l, p, "Error in DateCreated :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in DateCreated :" + e.getMessage());
            }

            //p.setCityName();
            // p.setDistrictName();

            p.setActionBy("admin");
            p.setStatus(Status.ACTIVE);
            //System.out.println(p.toString());
            listOfPatients.add(p);
            count++;
            //break;
        }

        myservices.saveAll(listOfPatients);
        logger.info("Count of loadBulk : {} " + listOfPatients.size());
    }

/*    private PatientMedicalTest updateTestData(PatientMedicalTest p, Patient patient, MedicalTestService medicalTestService) {


        p.setName(patVals.get("R14"));
        p.setTestNumber(lst.get(0).getTestNumber());

        return p;
    }*/

    private PatientMedicalTest updateFromPatient(PatientMedicalTest p, Patient patient, MedicalTestService medicalTestService) {
        if (patient != null) {
            Map<String, String> patVals = new HashMap<>();
            p.setPatientId(patient.getPatientId());
            StringTokenizer _st = new StringTokenizer(patient.getOldValues(), ",");
            String key = "";
            String value = "";
            while (_st.hasMoreTokens()) {
                StringTokenizer _st1 = new StringTokenizer(_st.nextToken(), "=");
                try {
                    key = _st1.nextToken();
                } catch (Exception e) {
                }
                try {
                    value = _st1.nextToken();
                } catch (Exception e) {
                }
                patVals.put(key, value);

            }

            //System.out.println("patVals -> " + patVals);
            //            PatientMedicalTest{id=null, testNumber='null', patientId='1906909', testType=null, billingNumber='19S02493', name='Bone Mineral Density Test (DXA)', price=null, reference='null', units='mU/L', results='20', seenBy='"Dr.D.Nanayakkara- MBBS- M Phil. (U.K)PhD-FANMB', labType=null, actionBy='admin', dateCreated=Tue Jan 12 00:00:00 SGT 2021, lastModified=null, status=ACTIVE, remarks='null'}
            //[0]=9897, [1]=20, [2]=19S02493, [3]=SERUM FREE THYROXIN:, [4]=12/25/19, [5]=mU/L,
            // patVals -> {R21=FALSE, R20=FALSE, R23=FALSE, R22=FALSE, R25=FALSE, R24=FALSE, R27=FALSE, R26=FALSE, R29=FALSE, R28=FALSE, R0=, R1=12/23/19, R2=19S02493, R3=A.L.S.MADEENA, R4=A.L.S.MADEENA, R5=A.L.S.MADEENA, R6=A.L.S.MADEENA, R7=39, R8=39, R9=39, R30=FALSE, R10=Female, R32=0, R31=FALSE, R12=NMU Clinic, R34=FALSE, R11=GHK, R33=000000000V, R14=Bone Mineral Density Test (DXA), R36=FALSE, R13=Dr.A.J.Hilmi(MD), R35=FALSE, R16=17-Dec-19, R15="Dr.D.Nanayakkara- MBBS- M Phil. (U.K)PhD-FANMB, R18=TRUE, R17=FALSE, R19=FALSE}
            //PatientMedicalTest{id=null, testNumber='null', patientId='1906909', testType=null, billingNumber='19S02493', name='Bone Mineral Density Test (DXA)', price=null, reference='null', units='mU/L', results='20', seenBy='"Dr.D.Nanayakkara- MBBS- M Phil. (U.K)PhD-FANMB', labType=null, actionBy='admin', dateCreated=Tue Jan 12 00:00:00 SGT 2021, lastModified=null, status=ACTIVE, remarks='null'}


            p.setSeenBy(patVals.get("R15"));
            // p.setName(patVals.get("R14"));
/*            List<MedicalTest> lst = medicalTestService.findAllByName(p.getName());
            if (lst.size() > 0) {
                p.setTestNumber(lst.get(0).getTestNumber());
            }
            p.setTestNumber(patVals.get("R14"));*/
        }
        return p;
    }



}
