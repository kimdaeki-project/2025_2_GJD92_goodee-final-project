package com.goodee.finals.common.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {
	public static final String STAFF = "staff"; // 사원 프로필
	public static final String PRODUCT = "product"; // 물품 사진
	public static final String NOTICE = "notice"; // 공지 첨부파일
	public static final String APPROVAL = "approval"; // 결재 서류
	public static final String RIDE = "ride"; // 어트랙션 사진
	public static final String LOST = "lost"; // 분실물 사진
	public static final String DOCUMENT = "document"; // 문서
	public static final String INSPECTION = "inspection"; // 점검 기록 파일
	public static final String FAULT = "fault"; // 어트랙션 고장 첨부파일
	public static final String DRIVE = "drive"; // 문서
	public static final String SIGN = "sign"; // 서명

	@Value("${goodee.file.upload.base-directory}")
	private String baseDir;
	
	public String saveFile(String attachType, MultipartFile attach) throws IOException {
		File file = new File(baseDir + attachType);
		if (!file.exists()) file.mkdirs();
		
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + attach.getOriginalFilename();
		file = new File(file, fileName);
		
		FileCopyUtils.copy(attach.getBytes(), file);
		return fileName;
	}
	
	public boolean fileDelete(String attachType, String fileName) {
		File file = new File(baseDir + attachType, fileName);
		return file.delete();
	}
}
