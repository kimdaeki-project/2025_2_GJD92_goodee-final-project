<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden scroll;">
    
    <!-- 여기에 코드 작성 -->
	<h1 class="text-center mb-5">어트랙션 등록</h1>
	<form:form method="post" modelAttribute="rideDTO" enctype="multipart/form-data" class="container col-8">
	
	  <!-- 어트랙션 이름 -->
	  <div class="form-group row mb-3">
	    <form:label path="rideName" class="col-sm-4 col-form-label text-start">어트랙션 이름</form:label>
	    <div class="col-sm-5">
	      <form:input path="rideName" cssClass="form-control" placeholder="어트랙션 이름을 입력하세요."/>
	    </div>
	  </div>
	
	  <!-- 어트랙션 코드 -->
	  <div class="form-group row mb-3">
	    <form:label path="rideCode" class="col-sm-4 col-form-label text-start">어트랙션 코드</form:label>
	    <div class="col-sm-5">
	      <form:input path="rideCode" cssClass="form-control" placeholder="어트랙션 코드를 입력하세요."/>
	    </div>
	  </div>
	
	  <!-- 담당자 -->
	  <div class="form-group row mb-3">
	    <form:label path="staffDTO.staffCode" class="col-sm-4 col-form-label text-start">담당자</form:label>
	    <div class="col-sm-5">
	      <form:input path="staffDTO.staffCode" cssClass="form-control" placeholder="담당자를 입력하세요."/>
	    </div>
	  </div>
	
	  <!-- 어트랙션 기종 -->
	  <div class="form-group row mb-3">
	    <form:label path="rideType" class="col-sm-4 col-form-label text-start">어트랙션 기종</form:label>
	    <div class="col-sm-5">
	      <form:select path="rideType" cssClass="form-select">
	        <form:option value="">어트랙션 기종을 선택하세요</form:option>
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
	      <form:input path="rideCapacity" cssClass="form-control" placeholder="탑승인원을 입력하세요."/>
	    </div>
	  </div>
	
	  <!-- 운행시간 -->
	  <div class="form-group row mb-3">
	    <form:label path="rideDuration" class="col-sm-4 col-form-label text-start">운행시간</form:label>
	    <div class="col-sm-5">
	      <form:input path="rideDuration" cssClass="form-control" placeholder="운행시간을 입력하세요."/>
	    </div>
	  </div>
	
	  <!-- 이용정보 -->
	  <div class="form-group row mb-3">
	    <form:label path="rideRule" class="col-sm-4 col-form-label text-start">이용정보</form:label>
	    <div class="col-sm-5">
	      <form:input path="rideRule" cssClass="form-control" placeholder="이용정보를 입력하세요."/>
	    </div>
	  </div>
	
	  <!-- 어트랙션 설명 -->
	  <div class="form-group row mb-3">
	    <form:label path="rideInfo" class="col-sm-4 col-form-label text-start">어트랙션 설명</form:label>
	    <div class="col-sm-5">
	      <form:input path="rideInfo" cssClass="form-control" placeholder="어트랙션 설명을 입력하세요."/>
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
	        <form:option value="">운행 상태를 선택하세요</form:option>
	        <form:option value="200">운영</form:option>
	        <form:option value="300">운휴</form:option>
	        <form:option value="400">고장</form:option>
	        <form:option value="500">점검</form:option>
	      </form:select>
	    </div>
	  </div>
	
	  <!-- 사진첨부 -->
	  <%-- <div class="form-group row mb-3">
	    <label class="col-sm-4 col-form-label text-start">사진첨부</label>
	    <div class="col-sm-5">
	      <input type="file" name="attach" class="form-control">
	      <c:if test="${rideDTO.attachmentDTO != null }">
	      	<p>현재 파일 : ${rideDTO.attachmentDTO.originName }</p>
	      </c:if>
	    </div>
	  </div> --%>
	  
	  <!-- 사진첨부 -->
	  <div class="form-group row mb-3">
	    <label class="col-sm-4 col-form-label text-start">사진첨부</label>
	    <div class="col-sm-5">
	      <input type="file" name="attach" class="form-control">
	
	      <!-- 수정 모드일 때 기존 파일 출력 -->
	      <c:if test="${mode eq 'edit' && attachmentDTO != null}">
	        <p>
	          현재 파일 :
	          <a href="${pageContext.request.contextPath}/file/${attachmentDTO.savedName}" target="_blank">
	            ${attachmentDTO.originName}
	          </a>
	        </p>
	      </c:if>
	    </div>
	  </div>
	  
	
	  <!-- 버튼 -->
	  <div class="form-group row mt-4 text-center">
	    <div class="col-sm-12">
	      <button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">등록</button>
	      <a href="/ride" class="btn btn-sm btn-outline-secondary" style="width: 100px;">취소</a>
	    </div>
	  </div>
	
	</form:form>

    
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "어트랙션"
	</script>
</body>

</html>