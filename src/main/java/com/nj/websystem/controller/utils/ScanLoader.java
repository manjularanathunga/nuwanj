package com.nj.websystem.controller.utils;

import com.nj.websystem.enums.LabType;
import com.nj.websystem.enums.Status;
import com.nj.websystem.enums.TestType;
import com.nj.websystem.model.MedicalTest;
import com.nj.websystem.model.Patient;
import com.nj.websystem.model.PatientMedicalTest;
import com.nj.websystem.service.MedicalTestService;
import com.nj.websystem.service.PatientMedicalTestService;
import com.nj.websystem.service.PatientService;
import com.nj.websystem.util.CSVUtils;
import com.nj.websystem.util.StringUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScanLoader {

    static Logger logger = LoggerFactory.getLogger(ScanLoader.class);

    @Autowired
    private static PatientMedicalTestService myservices;

    public static final String SAMPLE_XLSX_FILE_PATH = "/Users/sirimewanranathunga/Desktop/projects/PatientData/xls/";

    public void executeLoader(PatientMedicalTestService services, PatientService servicesp, MedicalTestService medicalTestService) {
        List<String> list = new ArrayList<>();list.add("2019.xlsx");list.add("2018.xlsx");list.add("2017.xlsx");
        myservices = services;
        Workbook workbook = null;
        for(String year : list){
            try {
                workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH+year));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            System.out.println("Retrieving Sheets using Iterator");
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                if("Scan Report".equalsIgnoreCase(sheet.getSheetName().trim())){
                    run(sheet, servicesp, medicalTestService);
                }
            }
        }


        //run(lineList, servicesp, medicalTestService);
    }

    private void run(Sheet sheet , PatientService servicesp, MedicalTestService medicalTestService) {
        logger.info("Calling Scan Report");
        List<PatientMedicalTest> listOfPatients = new ArrayList<>();
        PatientMedicalTest p;
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            String no = dataFormatter.formatCellValue(cellIterator.next());
            if ("No".equalsIgnoreCase(no)) {
                continue;
            }

            String idpatient = dataFormatter.formatCellValue(cellIterator.next());
            //String bhtclinicno = dataFormatter.formatCellValue(cellIterator.next());
            String dose = dataFormatter.formatCellValue(cellIterator.next());
            String procedure = dataFormatter.formatCellValue(cellIterator.next());
            String indication = dataFormatter.formatCellValue(cellIterator.next());
            String finding = dataFormatter.formatCellValue(cellIterator.next());
            String impression = dataFormatter.formatCellValue(cellIterator.next());
            String scanid = dataFormatter.formatCellValue(cellIterator.next());
            String submitDate = dataFormatter.formatCellValue(cellIterator.next());

            p = new PatientMedicalTest();
            p.setLabType(LabType.Scan);
            p.setDose(dose);
            p.setPatientId(idpatient);
            //p.setBhtClinicNo(bhtclinicno);
            p.setProcedure(procedure);
            p.setIndication(indication);
            p.setFinding(finding);
            p.setImpression(impression);
            p.setTestType(TestType.H);
            p.setScanNumber(scanid);

            try {
                if (StringUtility.get(scanid)) {
                    //TH0007/19 -> 19TH00007
                    StringTokenizer _st = new StringTokenizer(scanid,"/");
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
                    }else{
                        p.setName(scanid);
                        p.setTestNumber(scanid);
                    }

                } else {
                    logError(p, "Error in scanid :" + scanid);
                }
            } catch (Exception e) {
                logError(p, "Error in Impression :" + e.getMessage());
            }

            if(p.getScanNumber() == null){
                p.setScanNumber(scanid);
                p.setName(scanid);
                p.setTestNumber(scanid);
            }

            try {
                if (StringUtility.get(submitDate)) { // [9]=08-Jan-19
                    StringTokenizer _st = new StringTokenizer(submitDate, "-");
                    String date = _st.nextToken();
                    String month = _st.nextToken();
                    String year = _st.nextToken();
                    String appDate = year + "-" + month + "-" + date;
                    SimpleDateFormat formatter = new SimpleDateFormat("yy-MMM-dd");
                    p.setDateCreated(formatter.parse(appDate));
                } else {
                    logError(p, "Error in DateCreated :" + submitDate);
                }
            } catch (Exception e) {
                logError(p, "Error in DateCreated :" + e.getMessage());
            }
            p.setActionBy("admin");
            p.setStatus(Status.ACTIVE);
            //System.out.println(p.toString());
            listOfPatients.add(p);

            try {
                myservices.save(p);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
            int j = 0;
            for (String s : l) {
                System.out.print("[" + j + "]=" + s + ", ");
                j++;
            }
            System.out.println("-------------------------------------------------------------------");
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

            // R0=,R1=1/1/19,R2=19H00001,R3=NIKINI DESHANI,R4=,R5=?,R6=,R7=9,R8=,R9=,R10=Female,R11=SBSCH,R12=Hospital,R13=Dr.Faizal,R14="TSH - FT4,R15="Dr.D.Nanayakkara- MBBS- M Phil. (U.K)PhD-FANMB,R16=11-Nov-19,R17=TRUE,R18=TRUE,R19=FALSE,R20=FALSE,R21=FALSE,R22=FALSE,R23=FALSE,R24=FALSE,R25=FALSE,R26=FALSE,R27=FALSE,R28=FALSE,R29=FALSE,R30=FALSE,R31=,R32=0,R33=000000000V,R34=FALSE,R35=,R36=DUPLICATE

            //System.out.println("patVals -> " + patVals);
            //            PatientMedicalTest{id=null, testNumber='null', patientId='1906909', testType=null, billingNumber='19S02493', name='Bone Mineral Density Test (DXA)', price=null, reference='null', units='mU/L', results='20', seenBy='"Dr.D.Nanayakkara- MBBS- M Phil. (U.K)PhD-FANMB', labType=null, actionBy='admin', dateCreated=Tue Jan 12 00:00:00 SGT 2021, lastModified=null, status=ACTIVE, remarks='null'}
            //[0]=9897, [1]=20, [2]=19S02493, [3]=SERUM FREE THYROXIN:, [4]=12/25/19, [5]=mU/L,
            // patVals -> {R21=FALSE, R20=FALSE, R23=FALSE, R22=FALSE, R25=FALSE, R24=FALSE, R27=FALSE, R26=FALSE, R29=FALSE, R28=FALSE, R0=, R1=12/23/19, R2=19S02493, R3=A.L.S.MADEENA, R4=A.L.S.MADEENA, R5=A.L.S.MADEENA, R6=A.L.S.MADEENA, R7=39, R8=39, R9=39, R30=FALSE, R10=Female, R32=0, R31=FALSE, R12=NMU Clinic, R34=FALSE, R11=GHK, R33=000000000V, R14=Bone Mineral Density Test (DXA), R36=FALSE, R13=Dr.A.J.Hilmi(MD), R35=FALSE, R16=17-Dec-19, R15="Dr.D.Nanayakkara- MBBS- M Phil. (U.K)PhD-FANMB, R18=TRUE, R17=FALSE, R19=FALSE}
            //PatientMedicalTest{id=null, testNumber='null', patientId='1906909', testType=null, billingNumber='19S02493', name='Bone Mineral Density Test (DXA)', price=null, reference='null', units='mU/L', results='20', seenBy='"Dr.D.Nanayakkara- MBBS- M Phil. (U.K)PhD-FANMB', labType=null, actionBy='admin', dateCreated=Tue Jan 12 00:00:00 SGT 2021, lastModified=null, status=ACTIVE, remarks='null'}

            p.setReferBy(patVals.get("R13"));
            //p.setBhtClinicNo(patVals.get("R11"));
            p.setSeenBy(patVals.get("R15"));
            // p.setName(patVals.get("R14"));
/*            List<MedicalTest> lst = medicalTestService.findAllByName(p.getName());
            if (lst.size() > 0) {
                p.setTestNumber(lst.get(0).getTestNumber());
            }
            p.setTestNumber(patVals.get("R14"));*/

            p.setPatientId(patient.getPatientId());
        }
        return p;
    }

    private static PatientMedicalTest logError(List<String> l, PatientMedicalTest p, String error) {
        StringBuilder _sb = new StringBuilder(p.getRemarks());
        _sb.append(error);
        p.setRemarks(_sb.toString());
        return p;
    }

    private static PatientMedicalTest logError(PatientMedicalTest p, String error) {
        StringBuilder _sb = new StringBuilder();
        if(p.getRemarks() != null){
            _sb.append(p.getRemarks());
        }
        _sb.append(File.separatorChar);
        _sb.append(error);
        p.setRemarks(_sb.toString());
        return p;
    }
}
