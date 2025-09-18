<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:forEach items="${ shareDriveList }" var="shareDrive">
	<li class="nav-item">
		<a class="nav-link text-dark" href="/drive/${ shareDrive.driveDTO.driveNum }">
			<i class="material-symbols-rounded opacity-5 fs-5" data-content="${ shareDrive.driveDTO.driveName }">folder_shared</i>
			<span class="nav-link-text ms-1 text-sm">${ shareDrive.driveDTO.driveName }</span>
		</a>
	</li>
</c:forEach>