package com.a2m.gen.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CommonFileUtils {
	@Value("${path.upload.dir}")
	private static String pathUploadDir;

	public static String getPathDefaultUploaddir() {
		return pathUploadDir;
	}

	public static byte[] convertToBytes(File file) {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] getFileByPath(String path) {
		if (StringUtils.isEmpty(path))
			return new byte[0];

		File file = FileUtils.getFile(path);
		return convertToBytes(file);
	}

	public static File save(String path, MultipartFile multipartFile) throws IOException {
		File file = new File(path);
		multipartFile.transferTo(file);
		return file;
	}

	public static String getExt(String nameOrPath) {
		int dotIndex = nameOrPath.lastIndexOf(".");
		if (dotIndex < 0)
			return null;
		return nameOrPath.substring(dotIndex);
	}

	public static String getFileName(String path) {
		int seperatorIndex = path.lastIndexOf("/");
		if (seperatorIndex < 0)
			return null;
		return path.substring(++seperatorIndex);
	}

	public static String getDir(String path) {
		int seperatorIndex = path.lastIndexOf("/");
		if (seperatorIndex < 0)
			return null;
		return path.substring(0, seperatorIndex);
	}

	public static String replaceFileName(String newPrefixFileName, String originFileName) {
		return newPrefixFileName + getExt(originFileName);
	}

	public static void deleteFile(String fileName) {
		File file = new File(pathUploadDir + fileName);
		if (file.exists()) {
			file.delete();
		}
	}
}
