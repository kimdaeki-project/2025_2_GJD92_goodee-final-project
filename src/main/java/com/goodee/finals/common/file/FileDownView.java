package com.goodee.finals.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.goodee.finals.common.attachment.AttachmentDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FileDownView extends AbstractView {
	
	@Value("${goodee.file.upload.base-directory}")
	private String path;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		AttachmentDTO attachmentDTO = (AttachmentDTO)model.get("file");
		String type = model.get("type").toString();
		String filePath = "";
		
		switch (type) {
			case "staff": filePath = path + FileService.STAFF.toString(); break; 
			case "product": filePath = path + FileService.PRODUCT.toString(); break; 
			case "notice": filePath = path + FileService.NOTICE.toString(); break; 
			case "approval": filePath = path + FileService.APPROVAL.toString(); break; 
			case "ride": filePath = path + FileService.RIDE.toString(); break; 
			case "lost": filePath = path + FileService.LOST.toString(); break; 
			case "document": filePath = path + FileService.DOCUMENT.toString(); break; 
		}

		File file = new File(filePath, attachmentDTO.getSavedName());
		
		response.setContentLengthLong(file.length());
		String fileName = URLEncoder.encode(attachmentDTO.getOriginName(), "UTF-8");
		
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		FileInputStream fis = new FileInputStream(file);
		OutputStream os = response.getOutputStream();
		FileCopyUtils.copy(fis, os);
		
		os.close();
		fis.close();
	}

}
