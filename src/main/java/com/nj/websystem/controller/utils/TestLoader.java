package com.nj.websystem.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nj.websystem.enums.LabType;
import com.nj.websystem.enums.Status;
import com.nj.websystem.enums.TestType;
import com.nj.websystem.model.Finding;
import com.nj.websystem.model.Indication;
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
    private static Map<String, List<Indication>> listMap = new HashMap<>();

    static  {
        stringMap.put("VQ Scintigraphy","VQ");
        stringMap.put("TSH","SERUM TSH:");
        stringMap.put("Three Phase Bone Scintigraphy","BO");
        stringMap.put("TG","SERUM TRIIODOTHYRONINE:");
        stringMap.put("Testosterone","SERUM TRIIODOTHYRONINE:");
        stringMap.put("Technetium Thyroid Scintigraphy","TH");
        stringMap.put("Technetium Red Blood Cell Tagging","RB");
        stringMap.put("Technetium Mikels Scintigraphy","MI");
        stringMap.put("Technetium LYMPHO Scintigraphy","LP");
        stringMap.put("Technetium HIDA Scintigraphy","HI");
        stringMap.put("Technetium DTPA Scintigraphy","KI");
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
        stringMap.put("Technetium Mikels Scintigraphy","ME");

        List<Indication> fiindingList = new ArrayList();
        fiindingList.add(new Indication(1L,"No bone metastases",Status.ACTIVE));
        fiindingList.add(new Indication(2L,"Papillary carcinoma",Status.ACTIVE));
        fiindingList.add(new Indication(3L,"post thyroidectomy",Status.ACTIVE));
        fiindingList.add(new Indication(4L,"opst ablation",Status.ACTIVE));
        listMap.put("8",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(5L,"No bone metastases",Status.ACTIVE));
        fiindingList.add(new Indication(6L,"Papillary carcinoma",Status.ACTIVE));
        fiindingList.add(new Indication(7L,"post thyroidectomy",Status.ACTIVE));
        fiindingList.add(new Indication(8L,"opst ablation",Status.ACTIVE));
        listMap.put("7",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(9L,"No bone metastases",Status.ACTIVE));
        fiindingList.add(new Indication(10L,"Papillary carcinoma",Status.ACTIVE));
        fiindingList.add(new Indication(11L,"post thyroidectomy",Status.ACTIVE));
        fiindingList.add(new Indication(12L,"opst ablation",Status.ACTIVE));
        listMap.put("5",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(13L,"No bone metastases",Status.ACTIVE));
        fiindingList.add(new Indication(14L,"Papillary carcinoma",Status.ACTIVE));
        fiindingList.add(new Indication(15L,"post thyroidectomy",Status.ACTIVE));
        fiindingList.add(new Indication(16L,"opst ablation",Status.ACTIVE));
        listMap.put("22",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(17L,"carcinoma of the left Breast",Status.ACTIVE));
        fiindingList.add(new Indication(18L,"carcinoma of the right Breast",Status.ACTIVE));
        fiindingList.add(new Indication(19L,"carcinoma of the prostate",Status.ACTIVE));
        fiindingList.add(new Indication(20L,"Renal cell carcinoma",Status.ACTIVE));
        fiindingList.add(new Indication(21L,"facial asymmetry",Status.ACTIVE));
        listMap.put("15",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(22L,"Both kidneys show normal perfusion and function",Status.ACTIVE));
        fiindingList.add(new Indication(23L,"Right PUJ obstruction",Status.ACTIVE));
        fiindingList.add(new Indication(24L,"Left side partial PUJO",Status.ACTIVE));
        fiindingList.add(new Indication(25L,"Prospective donor",Status.ACTIVE));
        fiindingList.add(new Indication(26L,"Post renal transplant",Status.ACTIVE));
        fiindingList.add(new Indication(27L,"Non functonal right kidney",Status.ACTIVE));
        fiindingList.add(new Indication(28L,"Non functonal Leftt kidney",Status.ACTIVE));
        fiindingList.add(new Indication(29L,"PROSPECTIVE RENAL DONOR",Status.ACTIVE));
        fiindingList.add(new Indication(30L,"left kiddddney gross hydronephrosis",Status.ACTIVE));
        fiindingList.add(new Indication(31L,"Right kiddddney gross hydronephrosis",Status.ACTIVE));
        listMap.put("17",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(32L,"Normal DMSA scan",Status.ACTIVE));
        fiindingList.add(new Indication(33L,"UTI",Status.ACTIVE));
        fiindingList.add(new Indication(34L,"Absent left kidney",Status.ACTIVE));
        fiindingList.add(new Indication(35L,"Absent Right kidney",Status.ACTIVE));
        fiindingList.add(new Indication(36L,"Right PUJ obstruction",Status.ACTIVE));
        fiindingList.add(new Indication(37L,"Left side partial PUJO",Status.ACTIVE));
        fiindingList.add(new Indication(38L,"Ectopic kidney",Status.ACTIVE));
        fiindingList.add(new Indication(39L,"Duplex kidney",Status.ACTIVE));
        listMap.put("16",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(40L,"Normal HIDA scan",Status.ACTIVE));
        fiindingList.add(new Indication(41L,"Biliary atresia",Status.ACTIVE));
        fiindingList.add(new Indication(42L,"Direct hyperbilirubinemi",Status.ACTIVE));
        fiindingList.add(new Indication(43L,"Symptamatic cholicystitis",Status.ACTIVE));
        fiindingList.add(new Indication(44L,"obstructive jaundice",Status.ACTIVE));
        fiindingList.add(new Indication(45L,"Renal stone.",Status.ACTIVE));
        fiindingList.add(new Indication(46L,"",Status.ACTIVE));
        listMap.put("18",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(47L,"Normal Milk study",Status.ACTIVE));
        fiindingList.add(new Indication(48L,"Gastrooesopahgial reflux",Status.ACTIVE));
        listMap.put("20",fiindingList);


        fiindingList = new ArrayList();
        fiindingList.add(new Indication(49L,"Normal lymphoscintigraphy",Status.ACTIVE));
        fiindingList.add(new Indication(50L,"Left lower limb oedema",Status.ACTIVE));
        fiindingList.add(new Indication(51L,"Right lower limb oedema",Status.ACTIVE));
        fiindingList.add(new Indication(52L,"OEDEMA OF THE LEFT ARM",Status.ACTIVE));
        fiindingList.add(new Indication(52L,"OEDEMA OF THE Right ARM",Status.ACTIVE));
        fiindingList.add(new Indication(53L,"Bilateral significant lympahtic",Status.ACTIVE));
        listMap.put("19",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(54L,"carcinoma of the left Breast",Status.ACTIVE));
        fiindingList.add(new Indication(55L,"carcinoma of the right Breast",Status.ACTIVE));
        fiindingList.add(new Indication(56L,"carcinoma of the prostate",Status.ACTIVE));
        fiindingList.add(new Indication(57L,"Renal cell carcinoma",Status.ACTIVE));
        fiindingList.add(new Indication(58L,"facial asymmetry",Status.ACTIVE));
        listMap.put("25",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(59L,"Normal RBC scan",Status.ACTIVE));
        fiindingList.add(new Indication(60L,"PR bleeding",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"GI bleeding",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"Angiodysplasia",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"Hyperspeism",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"Malena",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"sclerotherapy",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"hemarroidectomy",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"occult blood",Status.ACTIVE));
        listMap.put("21",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(1L,"Normal VQ  scan",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"prednisolone",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"pulmonary embolism",Status.ACTIVE));
        listMap.put("27",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(1L,"Normal Myocardial Perfusion  scan",Status.ACTIVE));
        listMap.put("10",fiindingList);

        fiindingList = new ArrayList();
        fiindingList.add(new Indication(1L,"Normal BMI",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"Arthritis",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"patient is on HRT",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"menopaused",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"long term prednisolone",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"postmenopausal",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"carcinoma",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"joint disease",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"hypothyroidism",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"chronic back pain.",Status.ACTIVE));
        fiindingList.add(new Indication(1L,"multiple fracture",Status.ACTIVE));
        listMap.put("1",fiindingList);

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
            p = loadScanList(p);

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

    private MedicalTest loadScanList(MedicalTest p){
        ObjectMapper Obj = new ObjectMapper();
        List<Indication> indicationList= listMap.get(p.getTestNumber());
        if(indicationList != null){
            try {
                String jsonStr = Obj.writeValueAsString(indicationList);
                p.setScanOpsionProps(jsonStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return p;
    }


}


