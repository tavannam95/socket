package com.a2m.gen.services.gen;

import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.repository.gen.gen0301.Gen0301Repository;
import com.a2m.gen.utils.TOTPUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Gen0301Service {
    @Autowired
    Gen0301Repository gen0301Repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Map getById(Map param) {
        String id = param.get("USER_INFO_ID").toString();
        return gen0301Repository.getInfoById(Long.parseLong(id));
    }
    
    public Map getPositionName(Map param) {
    	String id = param.get("USER_INFO_ID").toString();
    	Map result = new HashMap<Object, Object>();
        try {
        	result = gen0301Repository.getPositonName(Long.parseLong(id));
		} catch (Exception e) {
			// TODO: handle exception
		}
        return result;
    }
    
    public Map getEmailVerifyKeyById(Map param) {
        String id = param.get("USER_INFO_ID").toString();
         
        Map result = new HashMap<Object, Object>();
        try {
        	result = gen0301Repository.getEmailVerifyKeyById(Long.parseLong(id));
		} catch (Exception e) {
			// TODO: handle exception
		}
        return result;
    }
    public Map get2FaKeyById(Map param) {
        String id = param.get("USER_INFO_ID").toString();
        Map result = new HashMap<Object, Object>();
        try {
        	result = gen0301Repository.getFaKeyById(Long.parseLong(id));
		} catch (Exception e) {
			// TODO: handle exception
		}
        return result;
    }
    public int modify(Map param) {
        int gender = (param.get("gender").toString().equals("true"))? 1 : 0;
        long id = Long.parseLong(param.get("userInfoId").toString().trim());
        String name = param.get("fullName")!=null ? param.get("fullName").toString().trim():"";
        String dob = param.get("dob")!=null ? param.get("dob").toString().trim():"";
        String phone = param.get("cellPhone")!=null ? param.get("cellPhone").toString().trim():"";
        String address = param.get("address")!=null ? param.get("address").toString().trim() : "";
        String organization = param.get("organization")!=null ? param.get("organization").toString().trim():"";
        String introduce = param.get("introduce")!=null ? param.get("introduce").toString().trim():"";
        String imgPath = param.get("imgPath")!=null ? param.get("imgPath").toString().trim() : "";
        int rslt = 0;
        try {
        	String userType = (String) param.get("userType");
        	if(userType.equals("EMP")) {
        		gen0301Repository.modifyInfoEmp(id, name, dob, phone, address, gender,organization,introduce,imgPath);
        	}else if( userType.equals("TEA") ) {
        		gen0301Repository.modifyInfoTea(id, name, dob, phone, address, gender,imgPath,organization,introduce);
    		}else {
    			gen0301Repository.modifyInfoStu(id, name, dob, phone, address, gender,imgPath,organization,introduce);
    		}
            
            rslt = 1;
        } catch (Exception e) {
            System.out.print(e);
        }
        return rslt;

    }
 
    public int modifyEmail(Map param) {
    	int rslt = 0;
    	long id = Long.parseLong(param.get("USER_INFO_ID").toString().trim());
    	String email = param.get("EMAIL").toString().trim();
        try {
            gen0301Repository.modifyEmail(id, email);
            rslt = 1;
        } catch (Exception e) {
            System.out.print(e);
        }
        return rslt;
    }
    
    public int modifyFaEnable(Map param) {
    	int rslt = 0;
    	Map result = get2FaKeyById(param);
        String verifyKey = result.get("FA_KEY").toString().trim();
        String code = param.get("verifyCode").toString().trim();
        if(verify2FAEableCode(code, verifyKey)) {
        	int faEnable = (param.get("FA_ENABLE").toString().trim().equals("true"))? 1 : 0;
        	long id = Long.parseLong(param.get("USER_INFO_ID").toString().trim());
        	try {
        		gen0301Repository.modify2FaEnable(id, faEnable);
        		rslt = 1;
        	} catch (Exception e) {
        		System.out.print(e);
        	}
        }
    	
        return rslt;
    }
    
    public boolean verify2FAEableCode(String code, String verifyKey) {
    	long stepsTime = System.currentTimeMillis() / 30000;
        String encode = TOTPUtils.getOTP(stepsTime, verifyKey);
        if (encode.equals(code.toString())) {
            return true;
        }else {
        	return false;
        }
        
    }
    
    public Map<Object,Object> getPasswordById(Map param){
    	Long id = Long.parseLong(param.get("userInfoId").toString());
    	return gen0301Repository.getPasswordById(id);
    }
    public int changePassword(Map param) {
    	String password = getPasswordById(param).get("PWD").toString();
    	String newPass = passwordEncoder.encode(param.get("newPassword").toString());
    	Long id = Long.parseLong(param.get("userInfoId").toString());
    	if(passwordEncoder.matches(param.get("oldPassword").toString(), password)) {
    		try {
				gen0301Repository.changePassword(id, newPass);
				return 1;
			} catch (Exception e) {
				// TODO: handle exception
				return 0;
			}
    	}else {
    		return 0;
    	}
    	
    }
}
