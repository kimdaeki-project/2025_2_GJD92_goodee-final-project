<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<style type="text/css">
		.sidenav {
			z-index: 1040 !important;
		}
	</style>
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
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="메뉴이름">dashboard</i>
			          <span class="nav-link-text ms-1 text-sm">메뉴이름</span>
			        </a>
			      </li>
			      <!-- 메뉴 개수만큼 추가 -->
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
		<button type="button" data-bs-toggle="modal" data-bs-target="#myModal">
		  모달 열기
		</button>
	    
	    </section>
    </div>
    
    
  </main>
  	<div class="modal" tabindex="-1" id="myModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Modal title</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">XXXX</button>
	      </div>
	      <div class="modal-body">
	        <p>Modal body text goes here.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
  
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script type="text/javascript">
		var myModal = document.getElementById('myModal')
		var myInput = document.getElementById('myInput')
	
		myModal.addEventListener('shown.bs.modal', function () {
		  myInput.focus()
	})
	</script>
	<script>
		document.querySelector("i[data-content='드라이브']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "드라이브"
	</script>
</body>

</html>