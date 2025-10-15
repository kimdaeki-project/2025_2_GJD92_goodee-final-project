<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>ERROR!</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
    
    	<div class="d-flex flex-column justify-content-center align-items-center mt-5">
    		<div class="m-auto">
    			<img width="200" height="200" src="/images/error.png" />
    		</div>
    		
    		<div class="mt-5">
    			<h4>처리 중 오류가 발생했습니다.</h4>
    		</div>
    		
    		<div class="mt-2">
    			<a href="/">홈으로</a>
    		</div>
    	</div>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
</body>

</html>