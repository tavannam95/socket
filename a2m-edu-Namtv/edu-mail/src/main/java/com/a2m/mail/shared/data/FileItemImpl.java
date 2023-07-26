package com.a2m.mail.shared.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.springframework.web.multipart.MultipartFile;

public class FileItemImpl implements FileItem {
	
	private MultipartFile multipart;
	
	public FileItemImpl(MultipartFile multipartFile) {
		this.multipart = multipartFile;
	}

	@Override
	public FileItemHeaders getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHeaders(FileItemHeaders headers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return this.multipart.getInputStream();
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return this.multipart.getContentType();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.multipart.getOriginalFilename();
	}

	@Override
	public boolean isInMemory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return this.multipart.getSize();
	}

	@Override
	public byte[] get() {
		// TODO Auto-generated method stub
		try {
			return this.multipart.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getString(String encoding) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(File file) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return this.multipart.getName();
	}

	@Override
	public void setFieldName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFormField() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFormField(boolean state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
