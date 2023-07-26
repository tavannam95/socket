package com.a2m.gen.controllers.gen;

import com.a2m.gen.services.mail.MailService;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.services.gen.Gen0301Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/gen/gen0301")
public class Gen0301Controller {
    @Autowired
    MailService mailService;
    @Autowired
    Gen0301Service gen0301Service;

    @GetMapping("/infomation")
    public Map getById(@RequestParam Map id){
        return gen0301Service.getById(id);
    }
    
    @GetMapping("/position")
    public Map getPositonName(@RequestParam Map id){
        return gen0301Service.getPositionName(id);
    }
    
    @GetMapping("/2fakey")
    public Map getFaKeyById(@RequestParam Map param){
        return gen0301Service.get2FaKeyById(param);
    }
    
    @PostMapping("/modify")
    public int modify(@RequestBody Map param){
        System.out.print(param);
        return gen0301Service.modify(param);
    }
    @GetMapping("/email")
    public void sendMail(@RequestParam Map param) throws IOException {
        String email = param.get("EMAIL").toString();
        Map result = gen0301Service.getEmailVerifyKeyById(param);
        String verifyKey = result.get("EMAIL_VERIFY_KEY").toString();
        mailService.sentVerificationCode(email,verifyKey);
//        passwordEncoder.matches(email, verifyKey)
    }
    @GetMapping("/verifycode")
    public boolean verify(@RequestParam Map param){
        System.out.print(param);
        Map result = gen0301Service.getEmailVerifyKeyById(param);
        String verifyKey = result.get("EMAIL_VERIFY_KEY").toString();
        String code = param.get("code").toString();
        return mailService.verifyEmail(code,verifyKey);
    }
    
    @GetMapping("/modifyEmail")
    public int modifyEmail(@RequestParam Map param){
        return gen0301Service.modifyEmail(param);
    }
    
    @GetMapping("/modify2FaEnable")
    public int modify2FaEnable(@RequestParam Map param){
        System.out.print(param);
        return gen0301Service.modifyFaEnable(param);
    }
    
    @GetMapping("/changePassword")
    public int changePassword(@RequestParam Map param){
        System.out.print(param);
        return gen0301Service.changePassword(param);
    }
}
