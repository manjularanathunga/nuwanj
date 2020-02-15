package com.nj.websystem.controller.utils;

import com.nj.websystem.enums.LabType;
import com.nj.websystem.enums.Status;
import com.nj.websystem.enums.TestType;
import com.nj.websystem.model.MedicalTest;
import com.nj.websystem.service.MedicalTestService;
import com.nj.websystem.util.CSVUtils;
import com.nj.websystem.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class TestLoader {

    static Logger logger = LoggerFactory.getLogger(TestLoader.class);

    @Autowired
    private MedicalTestService services;

    private static Map<String, String> stringMap = new HashMap<>();
/*    static  {
        stringMap.put("VQ","VQ Scintigraphy");
        stringMap.put("SERUM TSH:", "TSH");
        stringMap.put("BO","Three Phase Bone Scintigraphy");
        stringMap.put("SERUM TRIIODOTHYRONINE:", "TG");
        stringMap.put("SERUM TRIIODOTHYRONINE:", "Testosterone");
        stringMap.put("TH", "Technetium Thyroid Scintigraphy");
        stringMap.put("RBC","Technetium Red Blood Cell Tagging");
        stringMap.put("MI","Technetium Mikels Scintigraphy");
        stringMap.put("LY","Technetium LYMPHO Scintigraphy");
        stringMap.put("HI","Technetium HIDA Scintigraphy");
        stringMap.put("DT","Technetium DTPA Scintigraphy");
        stringMap.put("DM","Technetium DMSA Scintigraphy");
        stringMap.put("BO","Technetium Bone Scintigraphy");
        stringMap.put("SERUM TRIIODOTHYRONINE:", "T3");
        stringMap.put("SERUM PROLACTIN :", "Prolactin");
        stringMap.put("SERUM PROGESTERON:", "Progesterone");
        stringMap.put("SERUM OESTRADIOL:", "Oesterdiol");
        stringMap.put("MY","Myocardial Perfusion");
        stringMap.put("SERUM LH:", "LH");
        stringMap.put("WB","Iodine 131 Whole body Scintigraphy");//-3mCi
        stringMap.put("Iodine 131 Thyroid", "TH");
        stringMap.put("Thyroid Scintigraphy-10mCi ","Iodine 131 Therapy For Thyrotoxicosis");
        stringMap.put("WB","Iodine 131 Ablation & Whole body Scintigraphy");//-30mCi
        stringMap.put("SERUM FREE THYROXIN:", "FT4");
        stringMap.put("SERUM FSH:", "FSH");
        stringMap.put("SERUM CORTISOL:", "Cortisol");
        stringMap.put("DX", "Bone Mineral Density Test (DXA)");
    }*/

    static  {
        stringMap.put("VQ Scintigraphy","VQ");
        stringMap.put("TSH","SERUM TSH:");
        stringMap.put("Three Phase Bone Scintigraphy","BO");
        stringMap.put("TG","SERUM TRIIODOTHYRONINE:");
        stringMap.put("Testosterone","SERUM TRIIODOTHYRONINE:");
        stringMap.put("Technetium Thyroid Scintigraphy","TH");
        stringMap.put("Technetium Red Blood Cell Tagging","RBC");
        stringMap.put("Technetium Mikels Scintigraphy","MI");
        stringMap.put("Technetium LYMPHO Scintigraphy","LY");
        stringMap.put("Technetium HIDA Scintigraphy","HI");
        stringMap.put("Technetium DTPA Scintigraphy","DT");
        stringMap.put("Technetium DMSA Scintigraphy","DM");
        stringMap.put("Technetium Bone Scintigraphy","BO");
        stringMap.put("T3","SERUM TRIIODOTHYRONINE:");
        stringMap.put("Prolactin","SERUM PROLACTIN :");
        stringMap.put("Progesterone","SERUM PROGESTERON:");
        stringMap.put("Oesterdiol","SERUM OESTRADIOL:");
        stringMap.put("Myocardial Perfusion","MY");
        stringMap.put("LH","SERUM LH:");
        stringMap.put("Iodine 131 Whole body Scintigraphy","WB");//-3mCi
        stringMap.put("TH","Iodine 131 Thyroid");
        stringMap.put("Iodine 131 Therapy For Thyrotoxicosis","Thyroid Scintigraphy-10mCi ");
        stringMap.put("Iodine 131 Ablation & Whole body Scintigraphy","WB");//-30mCi
        stringMap.put("Iodine 131 Thyroid Scintigraphy","WB"); //-10mCi
        stringMap.put("FT4","SERUM FREE THYROXIN:");
        stringMap.put("FSH","SERUM FSH:");
        stringMap.put("Cortisol","SERUM CORTISOL:");
        stringMap.put("Bone Mineral Density Test (DXA)","DX");
    }

    private static MedicalTest logError(List<String> l, MedicalTest p, String error) {
        StringBuilder _sb = new StringBuilder(l.get(2));
        _sb.append("/n/r");
        //_sb.append("Error in DOB Years :"+ l.get(7) +"/" + l.get(8) +"/" + l.get(9) );
        _sb.append(error);
        p.setRemarks(_sb.toString());
        return p;
    }

    public void executeLoader(MedicalTestService medicalTestService) {

        services = medicalTestService;

        List<List> lineList = null;
        String csvFile = "/Users/sirimewanranathunga/Desktop/projects/PatientData/juwan-2019/cvs/nuwan_test_2019.csv";
        try {
            lineList = CSVUtils.LoadFile(csvFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        run(lineList);
    }

    private void run(List<List> lineList) {
        List<MedicalTest> listOfPatients = new ArrayList<>();
        MedicalTest p;
        int count = 0;
        for (List<String> l : lineList) {
            if (l.get(0).equals("Group")) {
                continue;
            }
            int j = 0;
            for (String s : l) {
                System.out.print("[" + j + "]=" + s + ", ");
                j++;
            }
            p = new MedicalTest();


            try {
                String data = l.get(0);
                if (StringUtility.get(data)) {
                    p.setTestNumber(data);
                } else {
                    logError(l, p, "Error in setTestNumber :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in setTestNumber :" + e.getMessage());
            }

            try {
                String data = l.get(1);
                if (StringUtility.get(data)) {
                    p.setTestType(TestType.valueOf(data.trim()));
                } else {
                    logError(l, p, "Error in setTestType :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in setTestType :" + e.getMessage());
            }

            try {
                String data = l.get(2);
                if (StringUtility.get(data)) {
                    p.setLabType(LabType.valueOf(data.trim()));
                } else {
                    logError(l, p, "Error in setLabType :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in setLabType :" + e.getMessage());
            }

            try {
                String data = l.get(3);
                if (StringUtility.get(data)) {
                    p.setName(data);
                    p.setOldTestName(stringMap.get(data.trim()));
                } else {
                    logError(l, p, "Error in setName :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in setName :" + e.getMessage());
            }

            try {
                String data = l.get(4);
                if (StringUtility.get(data)) {
                    p.setPrice(new Double(data));
                } else {
                    logError(l, p, "Error in setPrice :" + data);
                }
            } catch (Exception e) {
                logError(l, p, "Error in setPrice :" + e.getMessage());
            }


            //p.setCityName();
            // p.setDistrictName();

            p.setActionBy("admin");
            p.setStatus(Status.ACTIVE);
            p.setDateCreated(new Date());
            System.out.println(p.toString());
            listOfPatients.add(p);
            count++;
            // break;
        }

        services.saveAll(listOfPatients);
        logger.info("Count of loadBulk : {} " + listOfPatients.size());
    }
}
