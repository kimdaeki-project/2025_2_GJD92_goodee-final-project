<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>어트랙션 고장 신고</title>
	
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
						modelAttribute="faultDTO"
						enctype="multipart/form-data"
						action="${pageContext.request.contextPath }/fault/write">
						
						
			<!-- 어트랙션 코드 -->
			<div class="form-group row mb-3">
			  <form:label path="rideDTO.rideCode" class="col-sm-4 col-form-label text-start">어트랙션 코드</form:label>
			  <div class="col-sm-5">
			    <form:select path="rideDTO.rideCode" cssClass="form-select">
			      <form:option value="">-- 어트랙션 코드를 선택하세요 --</form:option>
			      <c:forEach var="ride" items="${rideList}">
			        <form:option value="${ride.rideCode}">${ride.rideName} (${ride.rideCode})</form:option>
			      </c:forEach>
			    </form:select>
			  </div>
			</div>
			
			
			<!-- 신고 제목 -->
			<div class="form-group row mb-3">
				<form:label path="faultTitle" class="col-sm-4 col-form-label text-start">신고 제목</form:label>
				<div class="col-sm-5">
					<form:input path="faultTitle" cssClass="form-control" placeholder="신고 제목을 입력하세요."/>
				</div>
			</div>
			
			<!-- 신고 내용 -->
			<div class="form-group row mb-3">
				<form:label path="faultContent" class="col-sm-4 col-form-label text-start">신고 내용</form:label>
				<div class="col-sm-5">
					<form:textarea path="faultContent" cssClass="form-control" placeholder="신고 내용을 입력하세요."/>
				</div>
			</div>
			
			<!-- 신고 날짜 -->
			<div class="form-group row mb-3">
			  <form:label path="faultDate" class="col-sm-4 col-form-label text-start">신고 날짜</form:label>
			  <div class="col-sm-5">
			    <form:input path="faultDate" type="date" cssClass="form-control"/>
			  </div>
			</div>
			
			<!-- 등록 버튼 -->
			<button type="submit"
			                 class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3"
			                 style="width: 100px;">등록</button>
			                 
			                 
			
			</form:form>

	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/fault/faultWrite.js"></script>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='고장 신고']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "고장 신고"
	</script>
</body>

</html>