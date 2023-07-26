package com.a2m.mail.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.a2m.mail.server.FileItemRegistry;
import com.a2m.mail.server.utils.SessionUtils;
import com.a2m.mail.shared.SConsts;
import com.a2m.mail.shared.data.FileItemImpl;

@RestController
@RequestMapping("/api/file")
public class FileController {
	
	@Autowired
	private SessionUtils sessionUtils;
	
	@Autowired
    private Environment env;
	
	@PostMapping("/uploadFile")
	public int uploadFile(HttpServletRequest request) {
		File uploadRootDir = null;
		final String rootPath = env.getProperty("dir.upload.temp");
		uploadRootDir = new File(rootPath);
		if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = null;
		
		for(Iterator it = multiRequest.getFileNames(); it.hasNext();){
			file = multiRequest.getFile((String)it.next());
			String fileName = file.getOriginalFilename();
			int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
			fileName = fileName.substring(startIndex + 1);
			uploadRootDir = new File(rootPath+fileName);
			try {
				file.transferTo(uploadRootDir);
				System.out.println("File path when upload: " + uploadRootDir.getAbsolutePath());
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			sessionUtils.getSessionRegistry().add(uploadRootDir);
		}
		
		return 1;
	}
	
	public static Map getParameterMap(HttpServletRequest request) {
		Map map = new HashMap();
		try {
			Map paramerterMap = request.getParameterMap();
			Iterator iter = paramerterMap.keySet().iterator();
			String key = null;
			String[] value = null;
			while (iter.hasNext()) {
				key = (String) iter.next();
				value = (String[]) paramerterMap.get(key);
				if (value.length > 1) {
					map.put(key, value);
				} else {
					map.put(key, value[0]);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return map;
	}

}
