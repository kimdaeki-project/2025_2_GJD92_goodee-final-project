<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<nav class="navbar navbar-main navbar-expand-lg px-0 mx-3 shadow-none border-radius-xl bg-white ms-2 mt-2" style="height: 50px;" id="navbarBlur" data-scroll="true">
  <div class="container-fluid px-3 d-flex justify-content-between">
  	<div>
			<h5 class="mb-0 ms-3" id="navTitle"></h5>	
  	</div>

    <div class="collapse navbar-collapse d-flex align-items-center justify-content-end mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
      <ul class="navbar-nav me-3 d-flex align-items-center justify-content-end">
      	<sec:authorize access="!isAuthenticated()">
	        <li class="nav-item d-flex align-items-center">
	          <a class="btn btn-sm mb-0 me-3 bg-gradient-dark text-white" style="width: 100px;" href="/staff/login">로그인</a>
	        </li>      	
      	</sec:authorize>
        
        <sec:authorize access="isAuthenticated()">
        	<sec:authentication property="principal" var="staff" />
        	<input type="hidden" value="${ staff.staffCode }" id="loggedStaffCodeForWebSocket">
        	
        	<li class="nav-item d-flex align-items-center me-3">
        		<img width="35" height="35" class="rounded-circle" style="padding: 1px; border: 1px solid #686868" alt="" src="/file/staff/${ staff.staffAttachmentDTO.attachmentDTO.savedName }">
        		<span class="ms-2 align-middle" style="height: 25px; line-height: 25px;">${ staff.deptDTO.deptDetail }부서 ${ staff.jobDTO.jobDetail } ${ staff.staffName } 님</span>
	        </li> 
	        
			<li class="nav-item dropdown pe-3 d-flex align-items-center">
			    <a href="javascript:;" class="nav-link text-body p-0 position-relative" style="height: 25px; line-height: 25px;" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
			        <i class="material-symbols-rounded">notifications</i>
			        <span class="badge bg-danger rounded-pill" style="position: absolute; top: -5px; right: -5px; font-size: 10px; padding: 2px 6px;">0</span>
			    </a>
			    <ul class="dropdown-menu dropdown-menu-end px-2 py-3 me-sm-n4" aria-labelledby="dropdownMenuButton" style="width: 400px; max-height: 205px; overflow-y: auto;"></ul>
			</li>
	        
	        <li class="nav-item d-flex align-items-center">
	          <a href="/staff/info" class="nav-link text-body font-weight-bold px-0" style="padding: 0; height: 25px; line-height: 25px;">
	            <i class="material-symbols-rounded">account_circle</i>
	          </a>
	        </li>
	        
	        <li class="nav-item d-flex align-items-center">
	          <a class="btn btn-sm mb-0 mx-3 bg-gradient-dark text-white" style="width: 100px;" href="/staff/logout">로그아웃</a>
	        </li>
        </sec:authorize>
        
      </ul>
    </div>
  </div>
</nav>
<c:import url="/WEB-INF/views/common/heartBeat.jsp"/>