package com.a2m.mail.server.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.mail.model.EmployeeSalaryMail;
import com.a2m.mail.server.constant.MailConstant;
import com.a2m.mail.shared.data.GenericResultImpl;
import com.a2m.mail.shared.data.SendMessageActionImpl;
import com.a2m.mail.shared.data.SmtpMessageImpl;
import com.a2m.mail.shared.domain.GenericResult;
import com.a2m.mail.shared.domain.SendMessageAction;
import com.a2m.mail.shared.domain.SmtpMessage;
import com.google.common.collect.Iterators;

@Service
public class ExcelSalaryService {

	@Autowired
	private SendMessageService sendMessageService;

	public static final int COLUMN_INDEX_STT = 0;
	public static final int COLUMN_INDEX_FULL_NAME = 1;
	public static final int COLUMN_INDEX_INSURANCE_SALARY = 2;
	public static final int COLUMN_INDEX_CONTRACT_SALARY = 3;
	public static final int COLUMN_INDEX_TOTAL_WORK_DAY = 4;
	public static final int COLUMN_INDEX_COUNT_WORK_DAY = 5;
	public static final int COLUMN_INDEX_DAYOFF_NOT_SAL = 6;
	public static final int COLUMN_INDEX_DAYOFF_HAVE_SAL = 7;
	public static final int COLUMN_INDEX_VACATION_DAY_REMAIN = 8;
	public static final int COLUMN_INDEX_REAL_SALARY = 9;
	public static final int COLUMN_INDEX_TEL_ALLOWANCE = 10;
	public static final int COLUMN_INDEX_VEHICLE_ALLOWANCE = 11;
	public static final int COLUMN_INDEX_LUNCH_ALLOWANCE = 12;
	public static final int COLUMN_INDEX_COSTUME_ALLOWANCE = 13;
	public static final int COLUMN_INDEX_BONUS = 14;
	public static final int COLUMN_INDEX_MONETARY_FINE = 15;
	public static final int COLUMN_INDEX_TOTAL_SALARY = 16;
	public static final int COLUMN_INDEX_INSURANCE = 17;
	public static final int COLUMN_INDEX_TAX = 18;
	public static final int COLUMN_INDEX_RECEIVE_MONEY = 19;

	public Object genExcelToGmail(MultipartFile file, String gmailColumn, String attachmentColumn, Integer startRow,
			Integer finishRow, String subject, String content, String fromUser) throws Exception {

		List<EmployeeSalaryMail> salaryMails = new ArrayList<EmployeeSalaryMail>();

		// Get file
		String excelName = file.getOriginalFilename();
		InputStream inputStream = new BufferedInputStream(file.getInputStream());

		// Get workbook
		Workbook workbook = getWorkbook(inputStream, excelName);

		// Get sheet
		Sheet sheet;
		try {
			sheet = workbook.getSheetAt(0);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			return "File Excel không có trang tính";
		}

		// Get all rows
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			EmployeeSalaryMail employeeSalaryMail = new EmployeeSalaryMail();
			employeeSalaryMail.setFrom(fromUser);
			employeeSalaryMail.setSubject(subject);

			String contentSend = content;
			Row nextRow = iterator.next();

			if (nextRow == null || nextRow.getRowNum() >= finishRow) {
				break;
			} else if (nextRow.getRowNum() < (startRow - 1)) {
				continue;
			}

			// Get all cells
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			// Read cells and set value for book object
			while (cellIterator.hasNext()) {
				// Read cell
				Cell cell = cellIterator.next();
				Object cellValue = getCellValue(cell);
				if (cellValue == null) {
					continue;
				}
//				int columnIndex = cell.getColumnIndex();
				String columnName = CellReference.convertNumToColString(cell.getColumnIndex());
				Object cellValueConvert = getCellValue(cell);
				if (gmailColumn.toUpperCase().equals(columnName.toUpperCase())) {
					List<String> gmailTos = new ArrayList<String>();
					gmailTos.add(cellValueConvert.toString());
					employeeSalaryMail.setTo(gmailTos);
				} else if (attachmentColumn.toUpperCase().equals(columnName.toUpperCase())) {
					employeeSalaryMail.setPrivateFileListName(cellValueConvert.toString());
				}
				contentSend = contentSend.replace("{{" + columnName.toLowerCase() + "}}",
						"{{" + columnName.toUpperCase() + "}}");
				contentSend = contentSend.replace("{{" + columnName.toUpperCase() + "}}", cellValueConvert.toString());
			}

			employeeSalaryMail.setContent(contentSend);
			if (employeeSalaryMail.getTo() != null && !employeeSalaryMail.getTo().isEmpty()
					&& !"".equals(employeeSalaryMail.getTo().get(0))) {
				salaryMails.add(employeeSalaryMail);
			} else {
				System.out.println("Row " + (nextRow.getRowNum() + 1) + " không có địa chỉ Gmail");
			}
		}
		workbook.close();
		inputStream.close();
		return salaryMails;
	}

	public GenericResult sendGmailSalary(List<EmployeeSalaryMail> salaryMails) {
		for (int i = 0; i < salaryMails.size(); i++) {
			EmployeeSalaryMail employeeSalaryMail = salaryMails.get(i);
			String from = employeeSalaryMail.getFrom();
			SendMessageAction sendMessageAction = new SendMessageActionImpl();
			sendMessageAction.setUsername(from);
			SmtpMessageImpl smtpMessage = new SmtpMessageImpl();
			smtpMessage.setFrom(from);
			smtpMessage.setSubject(employeeSalaryMail.getSubject());
			smtpMessage.setTo(employeeSalaryMail.getTo());
			smtpMessage.setMessageAttachments(employeeSalaryMail.getAttachments());

			if (smtpMessage.getTo() != null && !smtpMessage.getTo().isEmpty()) {
				smtpMessage.setText(employeeSalaryMail.getContent());
				sendMessageAction.setMessage(smtpMessage);
				sendMessageService.send(sendMessageAction, i == (salaryMails.size() - 1), false);
			}
		}
		GenericResult resultSend = new GenericResultImpl();
		resultSend.setSuccess(true);
		resultSend.setMessage(MailConstant.SEND_SUCCESS);
		return resultSend;
	}

	private static Workbook getWorkbook(InputStream inputStream, String excelName) throws IOException {
		Workbook workbook = new HSSFWorkbook();

		if (excelName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelName.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
	}

	// Get cell value
	private static String getCellValue(Cell cell) {
		String pattern = "###,###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);

		CellType cellType = cell.getCellTypeEnum();
		String cellValue = null;
		switch (cellType) {
		case BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case FORMULA:
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			cellValue = decimalFormat.format(evaluator.evaluate(cell).getNumberValue());
			break;
		case NUMERIC:

			if (HSSFDateUtil.isCellDateFormatted(cell) || HSSFDateUtil.isCellInternalDateFormatted(cell)) {
				cellValue = decimalFormat.format(cell.getDateCellValue());
			} else {
				cellValue = decimalFormat.format(cell.getNumericCellValue());
			}

			break;
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case _NONE:
			cellValue = "";
			break;
		case BLANK:
			cellValue = "";
			break;
		case ERROR:
			cellValue = "";
			break;
		default:
			break;
		}

		return cellValue;
	}

}
