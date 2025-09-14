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
    
    <div class="d-flex my-2 gap-2 ms-2 me-3">
	<!-- 사이드바 시작 -->
	  <aside class="border border-radius-xl bg-white p-3" style="min-width: 200px; min-height: 825px;">
		<div class="w-auto " id="side-sidebar">
		    <ul class="navbar-nav">
		      <li class="nav-item">
		        <a class="nav-link text-dark" href="#">
		          <i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">campaign</i>
		          <span class="sub-nav-link-text ms-1 text-sm">내드라이브</span>
		        </a>
		      </li>
		      
		      <li class="nav-item">
		        <a class="nav-link text-dark" href="#">
		          <i class="material-symbols-rounded opacity-5 fs-5" data-content="공용드라이브">dashboard</i>
		          <span class="nav-link-text ms-1 text-sm">공용드라이브</span>
		        </a>
		      </li>
		    </ul>
		  </div>
	  </aside>
	  <!-- 사이드바 끝 -->
	  <section class="border border-radius-xl bg-white p-3 flex-grow-1">
	  
	  <!-- 메인 컨텐츠 -->
	  <!-- 메인 컨텐츠 -->
	  <!-- 메인 컨텐츠 -->
	  <!-- 메인 컨텐츠 -->
	  
	  </section>	
	</div>

  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>