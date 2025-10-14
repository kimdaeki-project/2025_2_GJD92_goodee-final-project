package com.goodee.finals.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.goodee.finals.common.attachment.AttachmentDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ZipDownView extends AbstractView {

	@Value("${goodee.file.upload.base-directory}")
	private String path;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
        List<AttachmentDTO> attachList = (List<AttachmentDTO>) model.get("files");
		String driveNum = model.get("driveNum").toString();
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		String zipName = "docs_" + timestamp + ".zip";
		
		// 응답을 ZIP 파일로 지정, 브라우저가 files.zip이름으로 다운로드 하도록 설정
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);

        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
        	byte[] buffer = new byte[1024];
        	
        	// ✅ 중복 파일명 방지를 위한 이름 저장용 Set
        	Set<String> usedNames = new HashSet<>();

            for (AttachmentDTO attach : attachList) {
                String filePath = path + FileService.DRIVE + "/" + driveNum;

                File file = new File(filePath, attach.getSavedName());
                if (!file.exists()) continue;

                String originName = attach.getOriginName();
                String uniqueName = originName;
                int count = 1;

                // 동일한 파일명이 이미 존재할 경우 (1), (2), (3) 형태로 이름 변경
                while (usedNames.contains(uniqueName)) {
                    int dotIndex = originName.lastIndexOf('.');
                    if (dotIndex > 0) {
                        String base = originName.substring(0, dotIndex);
                        String ext = originName.substring(dotIndex);
                        uniqueName = base + "(" + count + ")" + ext;
                    } else {
                        uniqueName = originName + "(" + count + ")";
                    }
                    count++;
                }
                usedNames.add(uniqueName);

                // ZIP 안에 들어갈 새로운 파일 엔트리(=압축 항목)를 생성 (파일 이름 지정)
                zos.putNextEntry(new ZipEntry(uniqueName));

                // 실제 파일 내용을 읽어서(buffer 단위) ZIP 스트림에 써 넣음
                try (FileInputStream fis = new FileInputStream(file)) {
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                
                // 현재 ZIP 엔트리(파일 하나) 기록을 끝내고 닫음
                zos.closeEntry();
            }
        }
	}
}
