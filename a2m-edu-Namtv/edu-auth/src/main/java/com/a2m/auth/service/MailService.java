package com.a2m.auth.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.a2m.auth.util.TOTPUtils;

@Service
public class MailService {
	
	private static long stepsTime = System.currentTimeMillis() / 300000;
	
	@Autowired
	private Environment env;
	
	public boolean sentVerificationCode(String toEmail, String verifyKey) throws FileNotFoundException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.atwom.com.vn");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(env.getProperty("spring.mail.username"), env.getProperty("spring.mail.password"));
			}
		});
		Message msg = new MimeMessage(session);

		try {
			String html = readFile(new File(env.getProperty("email.html.verify").toString() + "email.html"));
			String encode = TOTPUtils.getOTP(stepsTime, verifyKey);
			html = html.replace("_VERIFY_CODE_", encode);
			msg.setFrom(new InternetAddress(env.getProperty("spring.mail.username"), false));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			msg.setSubject("AtwoM - Email verification code");
			msg.setContent(html, "text/html");
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (AddressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}
	
	public boolean verifyEmail(String code, String verifyKey) {
		String encode = TOTPUtils.getOTP(stepsTime, verifyKey);
		if (encode.equals(code)) {
			return true;
		}
		return false;
	}
	
	public String readFile(File file) throws FileNotFoundException {
		String result = "";
		BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                result += line;
            }
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        } catch (IOException ex) {
        	ex.printStackTrace();
        } finally {
            try {
                reader.close();
                // file.close();
            } catch (IOException ex) {
            }
        }
        return result;
	}
}
