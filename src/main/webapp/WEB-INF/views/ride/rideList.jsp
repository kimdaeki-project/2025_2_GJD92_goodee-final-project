<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: scroll;">
    
    <!-- 여기에 코드 작성 -->
    <!-- 레일형, 고속 어트랙션 -->
    <div class="rideCategory">레일형 / 고속 어트랙션</div>
    <div class="ride-list">
    	<div class="card">
    		<img alt="롤링엑스트레인" src="/images/ride/롤링 엑스 트레인.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">롤링 엑스 트레인</div>
    	</div>
    	<div class="card">
    		<img alt="아트란티스" src="/images/ride/아트란티스.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">아트란티스</div>
    	</div>
    </div>
    
    <!-- 회전형 어트랙션 -->
    <div class="rideCategory">회전형 어트랙션</div>
    <div class="ride-list">
    	<div class="card">
    		<img alt="회전목마" src="/images/ride/회전목마.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">회전목마</div>
    	</div>
    	<div class="card">
    		<img alt="바이킹" src="/images/ride/바이킹.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">바이킹</div>
    	</div>
    	<div class="card">
    		<img alt="자이로드롭" src="/images/ride/자이로드롭.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">자이로드롭</div>
    	</div>
    </div>    
    
    <!-- 수상 어트랙션 -->
    <div class="rideCategory">수상 어트랙션</div>
    <div class="ride-list">
    	<div class="card">
    		<img alt="후룸라이드" src="/images/ride/후룸라이드.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">후룸라이드</div>
    	</div>
    	<div class="card">
    		<img alt="아마존 익스프레스" src="/images/ride/아마존 익스프레스.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">아마존 익스프레스</div>
    	</div>
    </div>
    
    <!-- 관람형 어트랙션 -->
    <div class="rideCategory">관람형 어트랙션</div>
    <div class="ride-list">
    	<div class="card">
    		<img alt="관람차" src="/images/ride/관람차.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">관람차</div>
    	</div>
    </div>
    
    <!-- 어린이 놀이기구 -->
    <div class="rideCategory">어린이 놀이기구</div>
    <div class="ride-list">
    	<div class="card">
    		<img alt="범퍼카" src="/images/ride/범퍼카.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">범퍼카</div>
    	</div>
    	<div class="card">
    		<img alt="미니 트레인" src="/images/ride/미니 트레인.jpg" style="width:300px; height:300px;">
    		<div class="ride-name">미니 트레인</div>
    	</div>
    </div>
    
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>