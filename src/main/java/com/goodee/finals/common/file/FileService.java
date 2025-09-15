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
	public static final String DOCUMENT = "document"; // 분실물 사진

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
}
