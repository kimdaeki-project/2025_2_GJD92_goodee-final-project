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
    <h1>어트랙션 등록</h1>
    <form:form method="post" modelAttribute="rideDTO" enctype="multipart/form-data">
    	<div class="mb-3 ride-add">
		    <form:label path="rideName">어트랙션 이름</form:label>
		    <form:input path="rideName" cssClass="form-control" placeholder="어트랙션 이름을 입력하세요."/>
	    </div>
		<div class="mb-3 ride-add">
		    <form:label path="rideCode">어트랙션 코드</form:label>
		    <form:input path="rideCode" cssClass="form-control" placeholder="어트랙션 코드를 입력하세요."/>
	    </div>
	    <div class="mb-3 ride-add">
		    <form:label path="rideName">담당자</form:label>
		    <form:input path="rideName" cssClass="form-control" placeholder="담당자를 입력하세요."/>
	    </div>
	    <div class="mb-3 ride-add">
		    <form:label path="rideType">어트랙션 기종</form:label>
		    <form:input path="rideType" cssClass="form-control" placeholder="어트랙션 기종을 입력하세요."/>
	    </div>
	    <div class="mb-3 ride-add">
		    <form:label path="rideCapacity">탑승인원</form:label>
		    <form:input path="rideCapacity" cssClass="form-control" placeholder="탑승인원을 입력하세요."/>
	    </div>
	    <div class="mb-3 ride-add">
		    <form:label path="rideDuration">운행시간</form:label>
		    <form:input path="rideDuration" cssClass="form-control" placeholder="운행시간을 입력하세요."/>
	    </div>
	    <div class="mb-3 ride-add">
		    <form:label path="rideRule">이용정보</form:label>
		    <form:input path="rideRule" cssClass="form-control" placeholder="이용정보를 입력하세요."/>
	    </div>
	    <div class="mb-3 ride-add">
		    <form:label path="rideInfo">어트랙션 설명</form:label>
		    <form:input path="rideInfo" cssClass="form-control" placeholder="어트랙션 이름을 입력하세요."/>
	    </div>
	    <div class="mb-3 ride-add">
		    <form:label path="rideDate">개장일</form:label>
		    <form:input  path="rideDate" type="date" cssClass="form-control" placeholder="어트랙션 이름을 입력하세요."/>
	    </div>
	    <div>
			<form:label path="rideState">운행상태</form:label>
			<form:select path="rideState">
				<form:option value="">운행 상태를 선택하세요</form:option>
				<form:option value="200">운영</form:option>
				<form:option value="300">운휴</form:option>
				<form:option value="400">고장</form:option>
				<form:option value="500">점검</form:option>
			</form:select>
		</div>
	    <div class="mb-3 ride-add">
		    <label for="attach_num" class="form-label">사진첨부</label>
		    <input type="file" value="${ride }" class="" name="attach_num">
	    </div>
	    
    <div class="form-group row mt-5">
		<button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">등록</button>
		<button type="button" class="btn btn-sm btn-outline-secondary" style="width: 100px;">취소</button>
	</div> 
		
	</form:form>
    
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>