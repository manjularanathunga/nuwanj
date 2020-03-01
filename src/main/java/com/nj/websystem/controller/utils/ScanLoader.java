package com.nj.websystem.controller.utils;

import com.nj.websystem.enums.LabType;
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

public class ScanLoader {

    static Logger logger = LoggerFactory.getLogger(ScanLoader.class);

    @Autowired
    private static PatientMedicalTestService myservices;

    public void executeLoader(PatientMedicalTestService services, PatientService servicesp, MedicalTestService medicalTestService) {

        myservices = services;

        List<List> lineList = null;
        String csvFile = "/Users/sirimewanranathunga/Desktop/projects/PatientData/juwan-2019/cvs/2019Scan/Scan_Report_Table_1.csv";
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
        //No,ID Patient,BHT/Clinic No,Dose,Procedure,Indication:,Finding:,Impression,Scan ID,Date
        //[0]=, [1]=19H00001, [2]=, [3]=3mci, [4]=Whole body bone mineral density study was done, [5]=1y old baby giral with development delay and multiple fractures??? Osteogentic imperfecta, [6]="Global BMC 118.35 with BMD  0.218. regional analysis also done. Scan showed normal BMC , previous fractures not seen, [7]=Normal BMD, [8]=xx0000/19, [9]=08-Jan-19, 2020-03-01 11:11:29,280
        for (List<String> l : lineList) {
            if (l.get(0).equals("No")) {
                continue;
            }
            p = new PatientMedicalTest();


            p.setLabType(LabType.Scan);
            Patient patient = null;
            try {
                String data = l.get(1);
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
                String data = l.get(2);
                if (StringUtility.get(data)) {
                    p.setBhtClinicNo(data);
                } else {
                    logError(l, p, "Error in BhtClinicNo :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in BhtClinicNo :" + e.getMessage());
            }


            try {
                String data = l.get(3);
                if (StringUtility.get(data)) {
                    p.setDose(data);
                } else {
                    logError(l, p, "Error in Dose :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Dose :" + e.getMessage());
            }

            try {
                String data = l.get(4);
                if (StringUtility.get(data)) {
                    p.setProcedure(data);
                } else {
                    logError(l, p, "Error in Procedure :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Procedure :" + e.getMessage());
            }

            try {
                String data = l.get(5);
                if (StringUtility.get(data)) {
                    p.setIndication(data);
                } else {
                    logError(l, p, "Error in Indication :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Indication :" + e.getMessage());
            }

            try {
                String data = l.get(6);
                if (StringUtility.get(data)) {
                    p.setFinding(data);
                } else {
                    logError(l, p, "Error in Finding :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Finding :" + e.getMessage());
            }


            try {
                String data = l.get(7);
                if (StringUtility.get(data)) {
                    p.setImpression(data);
                } else {
                    logError(l, p, "Error in Impression :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Impression :" + e.getMessage());
            }

            try {
                String data = l.get(8);
                if (StringUtility.get(data)) {
                    //TH0007/19 -> 19TH00007
                    StringTokenizer _st = new StringTokenizer(data,"/");
                    String st1 = "";
                    String st2 = "";
                    while (_st.hasMoreTokens()){
                        st1 = _st.nextToken();
                        st2 = _st.nextToken();
                    }
                    String scanType = st1.substring(0,2).toUpperCase();
                    StringBuilder _sb= new StringBuilder(st2);
                    _sb.append(scanType);
                    _sb.append(st1.substring(2,st1.length()));
                    p.setScanNumber(_sb.toString());

                    List<MedicalTest> test = medicalTestService.findAllByOldTestName(scanType);
                    if(test.size() > 0){
                        MedicalTest selectedTest = test.get(0);
                        p.setName(selectedTest.getName());
                        p.setTestNumber(selectedTest.getTestNumber());
                    }

                } else {
                    logError(l, p, "Error in Impression :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in Impression :" + e.getMessage());
            }

            try {
                String data = l.get(9);
                if (StringUtility.get(data)) { // [9]=08-Jan-19
                    StringTokenizer _st = new StringTokenizer(data, "-");
                    String date = _st.nextToken();
                    String month = _st.nextToken();
                    String year = _st.nextToken();
                    String appDate = year + "-" + month + "-" + date;
                    SimpleDateFormat formatter = new SimpleDateFormat("yy-MMM-dd");
                    p.setDateCreated(formatter.parse(appDate));
                } else {
                    logError(l, p, "Error in DateCreated :" + data);
                }
            } catch (Exception e) {
                //e.printStackTrace();
                logError(l, p, "Error in DateCreated :" + e.getMessage());
            }

            p.setActionBy("admin");
            p.setStatus(Status.ACTIVE);
            //System.out.println(p.toString());
            listOfPatients.add(p);

            int j = 0;
            for (String s : l) {
                System.out.print("[" + j + "]=" + s + ", ");
                j++;
            }
            try {
                myservices.save(p);
            }catch (Exception e){
                e.printStackTrace();
            }
/*            if(count == 2){
                break;
            }*/

            count++;
        }

        //myservices.saveAll(listOfPatients);
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

    private static PatientMedicalTest logError(List<String> l, PatientMedicalTest p, String error) {
        StringBuilder _sb = new StringBuilder();
        _sb.append("/n/r");
        //_sb.append("Error in DOB Years :"+ l.get(7) +"/" + l.get(8) +"/" + l.get(9) );
        _sb.append(error);
        p.setRemarks(_sb.toString());
        return p;
    }
}
