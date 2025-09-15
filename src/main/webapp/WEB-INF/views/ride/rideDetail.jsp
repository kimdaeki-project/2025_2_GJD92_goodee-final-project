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
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden scroll;">
    
    <!-- 여기에 코드 작성 -->
    <h1></h1>
   
    <div class="container">
        <!-- 어트랙션 이미지 -->
	    <div class="ride-image">
	    	<img class="img-ride" src="/images/ride/">
	    </div>
	    <!-- 어트랙션 정보 -->
	    <div class="ride-info">
	    	<div></div>
	    </div>
    </div>

    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>