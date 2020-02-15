package com.nj.websystem.controller.utils;


import com.nj.websystem.service.MedicalTestService;
import com.nj.websystem.service.PatientMedicalTestService;
import com.nj.websystem.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/loader")
public class LoaderController {

    static Logger logger = LoggerFactory.getLogger(LoaderController.class);

    @Autowired
    private PatientService services;

    @Autowired
    private MedicalTestService medicalTestService;

    @Autowired
    private PatientMedicalTestService patientMedicalTestService;

    @RequestMapping(value = "/loadPatient", method = RequestMethod.GET, headers = "Accept=application/json")
    public void loadPatient() throws Exception {
        logger.info("loadPatient Start");
        PatientLoader loader = new PatientLoader();
        loader.executeLoader(services);
        logger.info("loadPatient Done");
    }

    @RequestMapping(value = "/testLoader", method = RequestMethod.GET, headers = "Accept=application/json")
    public void testLoader() throws Exception {
        logger.info("testLoader Start");
        TestLoader loader = new TestLoader();
        loader.executeLoader(medicalTestService);
        logger.info("testLoader Done");
    }

    @RequestMapping(value = "/resultsLoader", method = RequestMethod.GET, headers = "Accept=application/json")
    public void resultsLoader() throws Exception {
        logger.info("resultsLoader Start");
        PatientResultsLoader loader = new PatientResultsLoader();
        loader.executeLoader(patientMedicalTestService, services, medicalTestService);
        logger.info("resultsLoader Done");
    }

    @RequestMapping(value = "/scanLoader", method = RequestMethod.GET, headers = "Accept=application/json")
    public void scanLoader() throws Exception {
        logger.info("scanLoader Start");
        //loader = new PatientLoader();
        //loader.executeLoader();
        logger.info("scanLoader Done");
    }
}
