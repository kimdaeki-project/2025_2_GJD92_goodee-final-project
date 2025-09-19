<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>전자결재</title>
	
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
			    
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/approval/draft">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="기안 작성">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">기안 작성</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/approval">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="내 결재함">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">내 결재함</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/approval/recept">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="내 수신함">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">내 수신함</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/approval/sign">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="서명 등록">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">서명 등록</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
	    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='전자결재']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='내 결재함']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "내 결재함"
	</script>
</body>

</html>