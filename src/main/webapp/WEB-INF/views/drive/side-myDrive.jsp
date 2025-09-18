<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:forEach items="${ myDriveList }" var="myDrive">
	<li class="nav-item">
		<a class="nav-link text-dark" href="/drive/${ myDrive.driveNum }">
			<i class="material-symbols-rounded opacity-5 fs-5" data-content="${ myDrive.driveName }">folder_open</i>
			<span class="nav-link-text ms-1 text-sm">${ myDrive.driveName }</span>
		</a>
	</li>
</c:forEach>