package com.a2m.gen.controllers.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.models.common.AjaxResponse;
import com.a2m.gen.models.common.TccoFileModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.TccoFileService;
import com.a2m.gen.utils.CommonFileUtils;

@RestController
@RequestMapping(value = "api/tcco-files")
public class CommonFileController {

	@Autowired
	private TccoFileService tccoFileService;
	@Value("${path.upload.dir}")
	private String pathUploadDir;
	@Autowired
	private CommonService commonService;

	@PostMapping(value = "/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public TccoFile saveTccoFile(@RequestParam String userUid, @RequestParam String atchFleSeq,
			@RequestParam MultipartFile multipartFile) throws Exception {
		if (multipartFile == null)
			return null;

		TccoFile tccoFile = commonService.setDefaultValues(userUid, atchFleSeq, multipartFile);

        String dir = pathUploadDir.concat(tccoFile.getNewFleNm());
		CommonFileUtils.save(pathUploadDir.concat(tccoFile.getNewFleNm()), multipartFile);
		

		return tccoFileService.saveTccoFile(tccoFile);
	}

	@PostMapping(value = "/singleFileUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public TccoFile singleFileUpload(@RequestParam MultipartFile file) throws Exception {
		if (file == null)
			return null;
		System.out.println(file);

		String userUid = commonService.getUserUid();
		String seq = UUID.randomUUID().toString();
		TccoFile tccoFile = commonService.setDefaultValues(userUid, seq, file);
		CommonFileUtils.save(pathUploadDir.concat(tccoFile.getNewFleNm()), file);
		tccoFile = tccoFileService.saveTccoFile(tccoFile);
		return tccoFile;
	}

	@PostMapping(value = "/multiFileUpload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public List<TccoFile> multiFileUpload(@RequestParam List<MultipartFile> files) throws Exception {
		if (files == null)
			return null;
		System.out.println(files);
		List<TccoFile> listTccoFile = new ArrayList<TccoFile>();
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			String userUid = commonService.getUserUid();
			String seq = UUID.randomUUID().toString();
			TccoFile tccoFile = commonService.setDefaultValues(userUid, seq, file);
			CommonFileUtils.save(pathUploadDir.concat(tccoFile.getNewFleNm()), file);
			tccoFile = tccoFileService.saveTccoFile(tccoFile);
			listTccoFile.add(tccoFile);
		}

		return listTccoFile;
	}

	@PostMapping("/uploadFileCkeditor")
	public ResponseEntity<?> UploadFile(MultipartHttpServletRequest request, @RequestParam Optional<String> dir,
			@RequestParam Optional<String> type, @RequestParam Optional<Long> appId, HttpServletRequest servletRequest)
			throws IOException {
		Iterator<String> itr = request.getFileNames();
		MultipartFile multipartFile = request.getFile(itr.next());
		if (appId.isPresent() && !dir.isPresent()) {
			// todo code to fetch any application sub-directory should go here
		}
		AjaxResponse response = new AjaxResponse();

		String seq = UUID.randomUUID().toString();
		try {
			String userUid = commonService.getUserUid();
			TccoFile tccoFile = commonService.setDefaultValues(userUid, seq, multipartFile);

			CommonFileUtils.save(pathUploadDir.concat(tccoFile.getNewFleNm()), multipartFile);

			tccoFile = tccoFileService.saveTccoFile(tccoFile);

			if (tccoFile != null) {
				response.setFileName(tccoFile.getFleNm());
				response.setUploaded(true);
				response.setMessage("File uploaded successfully!");
				// String urlAppend =
				// urlAppend.concat(serviceFactory.getFileSeparator().concat(file.getOriginalFilename()));

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("api/tcco-files/getFile/").path(tccoFile.getFlePath()).toUriString();

				fileDownloadUri += "?filePath=" + tccoFile.getFlePath();

				response.setUrl(fileDownloadUri);
			} else {
				String message = "a file by that name already exists";
				response.setMessage(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String message = "unable to upload file::" + multipartFile.getName() + " at this time.";
			response.setMessage(message);
		}

		return ResponseEntity.ok(response);

	}

	// get file for ckediter// view ảnh
	@GetMapping("/getFile/{fileName}")
	public void getFile(@PathVariable String fileName, @RequestParam String filePath, HttpServletResponse response) {
		File file = new File(pathUploadDir + filePath);
		if (file.exists()) {
			try (InputStream in = FileUtils.openInputStream(file)) {
				response.setContentType(Files.probeContentType(file.toPath()));
				response.getOutputStream().write(IOUtils.toByteArray(in));
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@PutMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public TccoFile updateTccoFile(@RequestParam String userUid, @RequestParam String atchFleSeq,
			@RequestParam MultipartFile multipartFile) throws Exception {
		if (multipartFile == null)
			return null;

		TccoFile tccoFile = commonService.setDefaultValues(userUid, atchFleSeq, multipartFile);

		CommonFileUtils.save(pathUploadDir.concat(tccoFile.getNewFleNm()), multipartFile);

		return tccoFileService.saveTccoFile(tccoFile);
	}

	@GetMapping(value = "/getFileDetails")
	public ResponseEntity<?> getFileDetails(@RequestParam String atchFleSeq) {
		return ResponseEntity.ok(tccoFileService.findBySequence(atchFleSeq));
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse response, @RequestParam String flePath, @RequestParam String fleNm)
			throws IOException {
		try {
			File file = ResourceUtils.getFile(pathUploadDir + flePath);
			if (file.exists()) {
				byte[] data = FileUtils.readFileToByteArray(file);
				// Thiết lập thông tin trả về
				response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition", "attachment; filename=" + fleNm);
				response.setContentLength(data.length);
				InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//	@RequestMapping(value = "/downloadv2", method = RequestMethod.POST ,consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PostMapping(value = "/downloadv2")
	public ResponseEntity<InputStreamResource> downloadv2(@RequestBody TccoFileModel req ) throws IOException {
		HttpHeaders responseHeader = new HttpHeaders();
		String aaa = "";
		String flePath = req.getFlePath();
		String fleNm = req.getFleNm();
		try {
			File file = ResourceUtils.getFile(pathUploadDir + flePath);
			byte[] data = FileUtils.readFileToByteArray(file);
			// Set mimeType trả về
			responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			// Thiết lập thông tin trả về
			responseHeader.set("Content-disposition", "attachment; filename=" + fleNm);
			responseHeader.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
			return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
		}
//		return null;
	}

	// pdf view
	@RequestMapping(value = "/get/pdf/{filePath}", method = RequestMethod.GET)
	public Object getPdf(@PathVariable("filePath") String filePath) {
		File file = new File(pathUploadDir + filePath);
		;
		try {
			if (!file.exists())
				return null;
			FileInputStream fileInputStream = new FileInputStream(file);
			return IOUtils.toByteArray(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@DeleteMapping(value = "/deleteFile/{filePath}")
	public ResponseEntity<?> deletePDF(@PathVariable("filePath") String filePath) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
			commonService.deleteFile(filePath);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}
}