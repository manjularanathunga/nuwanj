package com.nj.websystem.controller;

import com.nj.websystem.enums.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/analysis")
public class DataAnalysisController {

    static Logger logger = LoggerFactory.getLogger(MedicalTestController.class);

    @RequestMapping(value = "/genderStaticsByTest", method = RequestMethod.GET, headers = "Accept=application/json")
    public List getNextPatientId(@RequestParam(value = "testid", required = false) long testid) {
        List list = new ArrayList();
        List male = new ArrayList();
        male.add(Gender.MALE.name());
        male.add(60);
        List female = new ArrayList();
        female.add(Gender.FEMALE.name());
        female.add(45);
        list.add(male);
        list.add(female);
        return list;
    }

}
