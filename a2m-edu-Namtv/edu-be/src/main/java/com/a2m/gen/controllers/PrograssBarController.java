//package com.a2m.gen.controllers;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.a2m.gen.services.ProgressBarService;
//
//@RestController
//@RequestMapping(value = "/api/common/common0001")
//public class PrograssBarController {
//    
//    @Autowired
//    private ProgressBarService progressBarService;
//    
//    @GetMapping(value = "/getNameProgress")
//    public ResponseEntity<?> getNameProgress(@RequestParam Map<String, Object> search) throws Exception {
//        Map<String, Object> response = new HashMap<>();
//        Map<String, Object> progress = progressBarService.getNameProgress(search);
//
//        //response.put("list", progress.);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//}
