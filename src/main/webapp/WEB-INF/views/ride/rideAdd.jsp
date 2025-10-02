<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>어트랙션</title>
	
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
		           modelAttribute="rideDTO" 
		           enctype="multipart/form-data" 
		           class="container col-8"
		           style="margin-top:100px"
		           data-mode="${mode}">
		
		  <!-- 어트랙션 이름 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideName" class="col-sm-4 col-form-label text-start">어트랙션 이름</form:label>
		    <div class="col-sm-5">
		      <form:input path="rideName" cssClass="form-control" placeholder="ex) 롤링썬더"/>
		    </div>
		  </div>
		
		  <!-- 어트랙션 코드 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideCode" class="col-sm-4 col-form-label text-start">어트랙션 코드</form:label>
		    <div class="col-sm-5">
		      <form:input path="rideCode" cssClass="form-control" placeholder="ex) A001"/>
		    </div>
		  </div>
		
		  <!-- 담당자 -->
	      <div class="form-group row mb-3">
		    <form:label path="staffDTO.staffCode" class="col-sm-4 col-form-label text-start">담당자</form:label>
		    <div class="col-sm-5">
		      <form:select path="staffDTO.staffCode" cssClass="form-select">
		        <form:option value="">-- 담당자를 선택하세요 --</form:option>
		        <c:forEach var="staff" items="${staffList}">
		          <form:option value="${staff.staffCode}">
		            ${staff.staffName} (${staff.staffCode})
		          </form:option>
		        </c:forEach>
		      </form:select>
		    </div>
		  </div>
		
		  <!-- 어트랙션 기종 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideType" class="col-sm-4 col-form-label text-start">어트랙션 기종</form:label>
		    <div class="col-sm-5">
		      <form:select path="rideType" cssClass="form-select">
		        <form:option value="">-- 어트랙션 기종을 선택하세요 --</form:option>
		        <form:option value="A11">레일형 어트랙션</form:option>
		        <form:option value="B11">회전형 어트랙션</form:option>
		        <form:option value="C11">수상형 어트랙션</form:option>
		        <form:option value="D11">관람형 어트랙션</form:option>
		        <form:option value="E11">어린이 어트랙션</form:option>
		      </form:select>
		    </div>
		  </div>
		
		  <!-- 탑승인원 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideCapacity" class="col-sm-4 col-form-label text-start">탑승인원</form:label>
		    <div class="col-sm-5">
		      <form:input path="rideCapacity" cssClass="form-control" placeholder="ex) 5명"/>
		    </div>
		  </div>
		
		  <!-- 운행시간 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideDuration" class="col-sm-4 col-form-label text-start">운행시간</form:label>
		    <div class="col-sm-5">
		      <form:input path="rideDuration" cssClass="form-control" placeholder="ex) 약 1분 30초"/>
		    </div>
		  </div>
		
		  <!-- 이용정보 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideRule" class="col-sm-4 col-form-label text-start">이용정보</form:label>
		    <div class="col-sm-5">
		      <form:input path="rideRule" cssClass="form-control" placeholder="ex) 140cm 이상"/>
		    </div>
		  </div>
		
		  <!-- 어트랙션 설명 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideInfo" class="col-sm-4 col-form-label text-start">어트랙션 설명</form:label>
		    <div class="col-sm-5">
		      <form:input path="rideInfo" cssClass="form-control" placeholder="ex) 어린이만 탑승 가능"/>
		    </div>
		  </div>
		
		  <!-- 개장일 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideDate" class="col-sm-4 col-form-label text-start">개장일</form:label>
		    <div class="col-sm-5">
		      <form:input path="rideDate" type="date" cssClass="form-control"/>
		    </div>
		  </div>
		
		  <!-- 운행상태 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideState" class="col-sm-4 col-form-label text-start">운행상태</form:label>
		    <div class="col-sm-5">
		      <form:select path="rideState" cssClass="form-select">
		        <form:option value="">-- 운행 상태를 선택하세요 --</form:option>
		        <form:option value="200">운영</form:option>
		        <form:option value="300">운휴</form:option>
		        <form:option value="400">고장</form:option>
		        <form:option value="500">점검</form:option>
		      </form:select>
		    </div>
		  </div>
		  
		  <!-- 사진첨부 -->
		  <div class="form-group row mb-3">
		    <form:label path="rideState" class="col-sm-4 col-form-label text-start">첨부파일</form:label>
		    <div class="col-sm-5">
		      <c:if test="${not empty rideDTO.rideAttachmentDTO}">
		      <!-- 기존 파일 존재 여부 -->
		      <input type="hidden" id="hasExistingFile" value="true"/>
		      </c:if>
		      <c:if test="${empty rideDTO.rideAttachmentDTO}">
		        <input type="hidden" id="hasExistingFile" value="false"/>
		      </c:if>
			  <!-- 이미지 미리보기 -->
		      <img id="preview"
		           style="object-fit: cover; width:200px; height:200px; !important;"
		           <c:if test="${ not empty rideDTO.rideName }">
		             src="/file/ride/${ rideDTO.rideAttachmentDTO.attachmentDTO.savedName }"
		           </c:if>
		           class="border border-1 border-dark p-1"/>
		      <label for="attach">
		        <div class="btn btn-outline-secondary px-2 py-0 m-auto">어트랙션 사진 등록</div>
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
		      <a href="/ride" class="btn btn-sm btn-outline-secondary" style="width: 100px;">취소</a>
		    </div>
		  </div>
		
		</form:form>
			
	    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/ride/rideAdd.js"></script>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='어트랙션 목록']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "어트랙션 목록"
	</script>
</body>

</html>