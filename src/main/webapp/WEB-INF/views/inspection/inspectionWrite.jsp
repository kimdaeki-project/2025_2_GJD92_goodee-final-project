<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>어트랙션 점검 기록 등록</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <div class="d-flex">
    	<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="height: 92vh;">
    		<div class="w-100">
			    <ul class="navbar-nav">
			      <!-- 메뉴 개수만큼 추가 -->
		    	  <c:import url="/WEB-INF/views/ride/ride-side-sidebar.jsp"></c:import>
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    
		    <!-- 여기에 코드 작성 -->
		    
			<form:form method="post" 
			           modelAttribute="inspectionDTO" 
			           enctype="multipart/form-data" 
			           class="container col-8"
			           style="margin-top:100px"
			           data-mode="${mode}">
			           
			<!-- 어트랙션 코드 -->
			<div class="form-group row mb-3">
			  <form:label path="rideDTO.rideCode" class="col-sm-4 col-form-label text-start">어트랙션 코드</form:label>
			  <div class="col-sm-5">
			    <form:input path="rideDTO.rideCode" cssClass="form-control" placeholder="어트랙션 코드를 입력하세요."/>
			  </div>
			</div>
			  
			<!-- 어트랙션 점검유형 -->
			<div class="form-group row mb-3">
			  <form:label path="isptType" class="col-sm-4 col-form-label text-start">점검유형</form:label>
			  <div class="col-sm-5">
			    <form:select path="isptType" cssClass="form-select">
			      <form:option value="">-- 점검유형을 선택하세요 --</form:option>
			      <form:option value="401">긴급점검</form:option>
			      <form:option value="501">일일점검</form:option>
			      <form:option value="502">정기점검</form:option>
			    </form:select>
			  </div>
			</div>
			  
			<!-- 어트랙션 점검결과 -->
			<div class="form-group row mb-3">
			  <form:label path="isptResult" class="col-sm-4 col-form-label text-start">점검결과</form:label>
			  <div class="col-sm-5">
			    <form:select path="isptResult" cssClass="form-select">
			      <form:option value="">-- 점검결과를 선택하세요 --</form:option>
			      <form:option value="201">정상</form:option>
			      <form:option value="202">특이사항 있음</form:option>
			      <form:option value="203">운영불가</form:option>
			    </form:select>
			  </div>
			</div>
			  
			<!-- 담당자 -->
			<div class="form-group row mb-3">
			  <form:label path="staffDTO.staffCode" class="col-sm-4 col-form-label text-start">담당자</form:label>
			  <div class="col-sm-5">
			    <form:input path="staffDTO.staffCode" cssClass="form-control" placeholder="담당자를 입력하세요."/>
			  </div>
			</div>
			  
			<!-- 점검 시작일 -->
			<div class="form-group row mb-3">
			  <form:label path="isptStart" class="col-sm-4 col-form-label text-start">점검 시작일</form:label>
			  <div class="col-sm-5">
			    <form:input path="isptStart" type="date" cssClass="form-control"/>
			  </div>
			</div>
			  
			<!-- 점검 종료일 -->
			<div class="form-group row mb-3">
			  <form:label path="isptEnd" class="col-sm-4 col-form-label text-start">점검 종료일</form:label>
			  <div class="col-sm-5">
			    <form:input path="isptEnd" type="date" cssClass="form-control"/>
			  </div>
			</div>
			           
			<!-- 사진첨부 -->
			<div class="form-group row mb-3">
			  <form:label path="" class="col-sm-4 col-form-label text-start">첨부파일</form:label>
			  <div class="col-sm-5">
			    <c:if test="${not empty inspectionDTO.inspectionAttachmentDTO}">
			      <!-- 기존 파일 존재 여부 -->
			      <input type="hidden" id="hasExistingFile" value="true"/>
			    </c:if>
			    <c:if test="${empty inspectionDTO.inspectionAttachmentDTO}">
			      <input type="hidden" id="hasExistingFile" value="false"/>
			    </c:if>
			
			    <img id="preview"
			         style="object-fit: cover; width:200px; height:200px; !important;"
			         <c:if test="${ not empty inspectionDTO.isptNum }">
			           src="/file/inspection/${ inspectionDTO.inspectionAttachmentDTO.attachmentDTO.savedName }"
			         </c:if>
			         class="border border-1 border-dark p-1"/>
			    <label for="attach">
			      <div class="btn btn-outline-secondary px-2 py-0 m-auto">어트랙션 점검 기록 사진 등록</div>
			    </label>
			  </div>
			  <input type="file" class="d-none" id="attach" name="attach"/>
			</div>
		
			<!-- 버튼 -->
			 <div class="form-group row mt-4 text-center">
			   <div class="col-sm-12">
			     <c:choose>
			       <c:when test="${mode eq 'edit'}">
			         <button type="submit"
			                 class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3"
			                 style="width: 100px;">수정</button>
			       </c:when>
			       <c:otherwise>
			         <button type="submit"
			                 class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3"
			                 style="width: 100px;">등록</button>
			       </c:otherwise>
			     </c:choose>
			     <a href="/inspection" class="btn btn-sm btn-outline-secondary" style="width: 100px;">취소</a>
			   </div>
			 </div>	           
			           
			</form:form>
	
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/inspection/inspectionWrite.js"></script>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='어트랙션 점검']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "어트랙션 점검"
	</script>
</body>

</html>