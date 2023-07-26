package com.a2m.mail.controller;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.mail.model.EmployeeSalaryMail;
import com.a2m.mail.server.service.ExcelSalaryService;
import com.a2m.mail.server.service.SendMessageService;
import com.a2m.mail.shared.data.GenericResultImpl;
import com.a2m.mail.shared.data.SendMessageActionImpl;
import com.a2m.mail.shared.data.SendReplyMessageActionImpl;
import com.a2m.mail.shared.domain.GenericResult;
import com.a2m.mail.shared.domain.SendMessageAction;
import com.a2m.mail.shared.domain.SendReplyMessageAction;

@RestController
@RequestMapping("/api/mail")
public class MessageController {

	@Autowired
	private SendMessageService sendMessageService;

	@Autowired
	private ExcelSalaryService excelSalaryService;

	@PostMapping("/sendMessage")
	public boolean sendMessage(@RequestBody SendMessageActionImpl sendMessageActionImpl) {
		SendMessageAction sendMessageAction = sendMessageActionImpl;
		GenericResult result = new GenericResultImpl();
		try {
			result = sendMessageService.send(sendMessageAction, true, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.isSuccess();
	}

	@PostMapping("/forwardMessage")
	public boolean repplyMessage(@RequestBody SendReplyMessageActionImpl sendRepplyMessageActionImpl) {
		SendReplyMessageAction sendRepplyMessageAction = sendRepplyMessageActionImpl;
		GenericResult result = new GenericResultImpl();
		try {
//			result = sendRepplyMessageService.send(sendRepplyMessageAction);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.isSuccess();
	}

	@PostMapping("/sendMailSalary")
	public ResponseEntity<?> sendMailSalary(@RequestBody List<EmployeeSalaryMail> salaryMails) throws Exception {

		if (salaryMails == null || salaryMails.isEmpty()) {
			return ResponseEntity.badRequest().body("List mail cann't blank or empty");
		}
		for (EmployeeSalaryMail employeeSalaryMail : salaryMails) {
			GenericResult result = new GenericResultImpl();
			String emailCheck = employeeSalaryMail.getTo().get(0);
			if (!this.validateEmail(emailCheck)) {
				result.setSuccess(false);
				result.setMessage("Địa chỉ email người nhận không hợp lệ : " + emailCheck);
				return ResponseEntity.ok().body(result);
			}
		}
		return ResponseEntity.ok().body(excelSalaryService.sendGmailSalary(salaryMails));
	}

	@PostMapping("/genExcelToGmail")
	public ResponseEntity<?> genExcelToGmail(@RequestParam("file") MultipartFile file,
			@RequestParam("gmailColumn") String gmailColumn, @RequestParam("attachmentColumn") String attachmentColumn,
			@RequestParam("startRow") Integer startRow, @RequestParam("finishRow") Integer finishRow,
			@RequestParam("subject") String subject, @RequestParam("content") String content,
			@RequestParam("fromUser") String fromUser) throws Exception {

		if (file == null) {
			return new ResponseEntity<>("File upload null", HttpStatus.BAD_REQUEST);
		} else if (gmailColumn == null || "".equals(gmailColumn)) {
			return new ResponseEntity<>("Gmail column cann't blank or empty", HttpStatus.BAD_REQUEST);
		} else if (startRow == null) {
			return new ResponseEntity<>("Start row cann't blank or empty", HttpStatus.BAD_REQUEST);
		} else if (finishRow == null) {
			return new ResponseEntity<>("Finish row cann't blank or empty", HttpStatus.BAD_REQUEST);
		} else if (subject == null || "".equals(subject)) {
			return new ResponseEntity<>("Gmail subject cann't blank or empty", HttpStatus.BAD_REQUEST);
		} else if (fromUser == null || "".equals(fromUser)) {
			return new ResponseEntity<>("From user cann't blank or empty", HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok().body(excelSalaryService.genExcelToGmail(file, gmailColumn, attachmentColumn,
				startRow, finishRow, subject, content, fromUser));
	}

	private boolean validateEmail(String email) {
		boolean isValid = false;
		try {
			// Create InternetAddress object and validated the supplied
			// address which is this case is an email address.
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			isValid = true;
		} catch (AddressException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	@PostMapping("/sendDraftMessage")
	public boolean sendDraftMessage(@RequestBody SendMessageActionImpl sendMessageActionImpl) {
		SendMessageAction sendMessageAction = sendMessageActionImpl;
		GenericResult result = new GenericResultImpl();
		try {
			result = sendMessageService.send(sendMessageAction, true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.isSuccess();
	}
}
