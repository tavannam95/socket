package com.a2m.gen.services.edu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.CandidateDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCandidate;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.edu.CandidateModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.TccoFileService;
import com.a2m.gen.utils.CastUtil;


@Service
public class CandidateService {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private CandidateDao candidateDao;
	 
	@Autowired
	private TccoFileService tccoFileService;
	
    @Value("${path.upload.dir}")
    private String pathUploadDir;
    
    private List < TsstUser > studentList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    
	
	public List<CandidateModel> get(CandidateModel search) throws Exception {
		List<CandidateModel> candidateModels = new ArrayList<CandidateModel>();
		List<AemCandidate> lst = candidateDao.search(search);
		for(AemCandidate db :lst) {
			CandidateModel model = new CandidateModel(db); 
			candidateModels.add(model);
		}
//		exportToPDF(candidateModels);
		return candidateModels;
	}
	
	public Long getCount(CandidateModel search) throws Exception {
		return candidateDao.counCandidate(search);
	}

	public Map<Object, Object> saveCandidate(CandidateModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
	
		
		AemCandidate db = new AemCandidate();
		
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
				db = candidateDao.getCandidate(model);
				model.setUpdDate(new Date());
				AemCandidate saveCandidate = candidateDao.saveCandidate(model, db);
		}else {
				model.setInsDate(new Date());
//				model.setCandidateprogressType("Pending");
				model.setCandidateStatus(true);
				AemCandidate saveCandidate = candidateDao.saveCandidate(model, db);
		}
		return res;
	}
	
	public CandidateModel getCandidateModelById(Long id) throws Exception {
		CandidateModel candidateModel = new CandidateModel();
		candidateModel.setKey(id);
		AemCandidate result	= candidateDao.getCandidate(candidateModel);
		CandidateModel resultModel = new CandidateModel(result);
//		TccoFile file = tccoFileService.findBySequence(resultModel.getCandidateFilePath());
//		resultModel.setInfoFile(file);
//		exportToPDF(resultModel);
		return resultModel;
	}
	
	public Long getCountCandidate(CandidateModel search) throws Exception {
		return candidateDao.counCandidate(search);
	}

	public Boolean candidateForDel(List<CandidateModel> listCandidate) {
		for(CandidateModel candidate : listCandidate) {
			Long id = candidate.getKey();
			delete(id);
		}
		return true;
	}
	
	public Boolean delete(Long id) {
//		CandidateModel candidateModel = new CandidateModel();
//		candidateModel.setKey(id);
//		AemCandidate aemCandidate =  candidateDao.getCandidate(candidateModel);
//		String tccoFileId = aemCandidate.getCandidateFilePath();
//		TccoFile tccoFile = new TccoFile();
//		tccoFile.setAtchFleSeq(tccoFileId);
//		tccoFileService.deleteTccoFile(tccoFile);
		boolean deleteCandidate = candidateDao.deleteCandidate(id);
	return deleteCandidate;
	}
	
	public String getCountCandidateAllType() throws Exception {
		
		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		today = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -30);
		Date today30ago = cal.getTime();
		
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(new Date());
		cal2.set(Calendar.HOUR_OF_DAY,23);
		cal2.set(Calendar.MINUTE,59);
		cal2.set(Calendar.SECOND, 59);
		cal2.set(Calendar.MILLISECOND, 59);
		cal.add(Calendar.DAY_OF_MONTH, +1);
		Date endToday = cal2.getTime();
		
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
 
		
		//Count Today
        CandidateModel todayModel = new CandidateModel();
		todayModel.setStartTime(formatter.format(today));
		todayModel.setEndTime(formatter.format(endToday));
//		todayModel.setRows(10);
//		todayModel.setPage(0);
		todayModel.setProgressType("Chưa giải quyết");
		Long todayCount = candidateDao.counCandidate(todayModel);
		//Count Pending
		CandidateModel pendingModel = new CandidateModel();
		pendingModel.setStartTime( formatter.format(today30ago));
		pendingModel.setEndTime(formatter.format(today));
		pendingModel.setProgressType("Chưa giải quyết");
//		todayModel.setRows(10);
//		todayModel.setPage(0);
		Long pendingCount = candidateDao.counCandidate(pendingModel);
		//Count Inprogress
		CandidateModel inprogressModel = new CandidateModel();
		inprogressModel.setStartTime( formatter.format(today30ago));
		inprogressModel.setEndTime(formatter.format(today));
		inprogressModel.setProgressType("Trong quá trình");
//		todayModel.setRows(10);
//		todayModel.setPage(0);
		Long inprogressCount = candidateDao.counCandidate(inprogressModel);						
		return todayCount+"!#@"+pendingCount+"!#@"+inprogressCount;
	}
	
    public String exportList(CandidateModel searchModel) throws Exception {
    	List<CandidateModel> listModel = new ArrayList<>();
    	if(searchModel.getIsExportAll()==true) {
 		List<AemCandidate> candidates = candidateDao.search(searchModel);
	 		for (AemCandidate aemCandidate : candidates) {
					CandidateModel model = new CandidateModel(aemCandidate);
					listModel.add(model);
				}
    	}else {
       	 listModel = getListModelByListId(searchModel.getListCheckedId());
		}
        String path = exportExcel(listModel);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return path +"!#@"+ currentDateTime;
    }
    
    public List<CandidateModel> getListModelByListId( List<Integer> list) {
    	List<CandidateModel> resultList = new ArrayList<>();
    	for (Integer key : list) {
    		CandidateModel model = new CandidateModel();
    		model.setKey(key.longValue());
			AemCandidate candidate = candidateDao.getCandidate(model);
			CandidateModel resultModel = new CandidateModel(candidate);
			resultList.add(resultModel);
		}
    	return resultList ;
    }
    
   public String exportExcel(List<CandidateModel> list) throws FileNotFoundException, IOException {
       String randomUID = UUID.randomUUID().toString();

       String afterDest = randomUID.replaceAll("[\\-\\+\\.\\!\\@\\#\\$\\%\\&\\*\\(\\)\\`\\~\\;\\/\\|\\?\\^:,]", "");
       // Creating a PdfDocument object
       String dest = pathUploadDir + afterDest + ".xlsx";
       XSSFWorkbook workbook = new XSSFWorkbook();
       writeHeaderLine(workbook);
       writeDataLines(workbook, list);
       try (FileOutputStream out = new FileOutputStream(dest)) {
           workbook.write(out);
       }
       workbook.close();
       return afterDest;
   }
   
   private void writeHeaderLine(XSSFWorkbook workbook) {
       sheet = workbook.createSheet("Users");
        
       Row row = sheet.createRow(0);
       CellStyle style = workbook.createCellStyle();
       XSSFFont font = workbook.createFont();
       font.setBold(true);
       font.setFontHeight(11);
       style.setFont(font);

       createCell(row, 0, "No.", style); 
       createCell(row, 1, "Full Name", style);       
       createCell(row, 2, "E-mail", style);    
       createCell(row, 3, "Phone", style);
       createCell(row, 4, "Progress ", style);
       createCell(row, 5, "Course Name", style);
       createCell(row, 6, "Event", style);
        
   }
   
   private void createCell(Row row, int columnCount, Object value, CellStyle style) {
       sheet.autoSizeColumn(columnCount);
       Cell cell = row.createCell(columnCount);
       if (value instanceof Integer) {
           cell.setCellValue((Integer) value);
       } else if (value instanceof Date) {
           cell.setCellValue((Date) value);
       }else {
           cell.setCellValue((String) value);
       }
       cell.setCellStyle(style);
   }
   
   private void writeDataLines(XSSFWorkbook workbook, List<CandidateModel> list) {
       int rowCount = 1;

       CellStyle style = workbook.createCellStyle();
       XSSFFont font = workbook.createFont();
       font.setFontHeight(14);
       style.setFont(font);
                
       int idx = 1;
       for (CandidateModel can : list) {
    	   String courseNm = "";
    	   String eventNm = "";
    	   Edu0102RequestModel courseModel = can.getCourseModel();
    	   EventModel eventModel = can.getEventModel();
		   courseNm = courseModel.getCourseNm();
		   eventNm = eventModel.getEventTitle();
           Row row = sheet.createRow(rowCount++);
           int columnCount = 0;
           createCell(row, columnCount++, idx, style);
           createCell(row, columnCount++, can.getCandidateName(), style);
           createCell(row, columnCount++, can.getCandidateEmail(), style);
           createCell(row, columnCount++, can.getCandidatePhone(), style);
           createCell(row, columnCount++, can.getCandidateprogressType(), style);
           createCell(row, columnCount++, courseNm, style);
           createCell(row, columnCount++, eventNm, style);
           
            idx++;
       }
   }
   
   public void changProgressByList(ParamSearchModel searchModel ) throws Exception {
	   List<Integer> listCheckedId = searchModel.getListCheckedId();
	   for (Integer key : listCheckedId) {
		CandidateModel model = new CandidateModel();
		model.setKey(key.longValue());
		AemCandidate candidate = candidateDao.getCandidate(model);
		candidate.setCandidateprogressType(searchModel.getProgressType());
		CandidateModel modelPermit = new CandidateModel(candidate);
		candidateDao.saveCandidate(modelPermit, candidate);
	}
   }
		
	
}
