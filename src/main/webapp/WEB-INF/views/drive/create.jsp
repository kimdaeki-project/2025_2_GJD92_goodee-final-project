<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>드라이브</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    
    <div class="d-flex my-2 gap-2 ms-2 me-3">
	<!-- 사이드바 시작 -->
	  <aside class="border border-radius-xl bg-white p-3" style="min-width: 200px; min-height: 825px;">
		<div class="w-auto" id="side-sidebar">
		    <ul class="navbar-nav">
		      <li class="nav-item">
		        <div class="nav-link text-dark d-flex justify-content-between">
		          <div class="d-flex align-items-center">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">keyboard_arrow_down</i>
			          <span class="sub-nav-link-text ms-1 text-sm">내드라이브</span>
		          </div>
		          <div>
		          	  <a class="nav-link text-dark d-flex align-items-center" href="/drive/create">
		          	  	<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">add</i>
		          	  </a>
		          </div>	
		        </div>
		        <ul class="navbar-nav" id="my-drive">
		        	<!-- 내 드라이브 반복문 시작 -->
		        	<li class="nav-item">
		        		<a class="nav-link text-dark d-flex align-items-center" href="#">
			        		<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">monitor</i>
			        		개인 드라이브1
		        		</a>
		        	</li>
		        	<li class="nav-item">
		        		<a class="nav-link text-dark d-flex align-items-center" href="#">
			        		<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">monitor</i>
			        		개인 드라이브2
		        		</a>
		        	</li>
		        	<!-- 내 드라이브 반복문 끝 -->
		        </ul>
		      </li>	
		      
		      <li class="nav-item">
		        <div class="nav-link text-dark d-flex align-items-center" >
	 	          <i class="material-symbols-rounded opacity-5 fs-5" data-content="공용드라이브">keyboard_arrow_down</i>
		          <span class="nav-link-text ms-1 text-sm">공용드라이브</span>
		        </div>
		        <ul class="navbar-nav" id="my-drive">
		        	<!-- 공용 드라이브 반복문 시작 -->
		        	<li class="nav-item">
		        		<a class="nav-link text-dark d-flex align-items-center" href="#">
			        		<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">tv_signin</i>
			        		공용 드라이브1
		        		</a>
		        	</li>
		        	<li class="nav-item">
		        		<a class="nav-link text-dark d-flex align-items-center" href="#">
			        		<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">tv_signin</i>
			        		공용 드라이브2
		        		</a>
		        	</li>
		        	<!-- 공용 드라이브 반복문 끝 -->
		        </ul>
		      </li>
		    </ul>
		  </div>
	  </aside>
	  <!-- 사이드바 끝 -->
	  <section class="border border-radius-xl bg-white p-3 flex-grow-1">
	  	<h5 class="m-2">드라이브 추가</h5>
		<div class="p-5">
			<form:form method="post" modelAttribute="driveDTO">
				<div>
					<form:label path="driveName">드라이브 이름 : </form:label>
					<form:input path="driveName"/>
				</div>
			</form:form>
			<div class="mt-5 d-flex align-items-center">
				<span>드라이브 공유 :</span>
				<i class="material-symbols-rounded opacity-5 fs-5 ms-2 border border-secondary p-1 rounded" data-content="내드라이브">group_add</i>
			</div>
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col"></th>
			      <th scope="col"></th>
			      <th scope="col">추가된 사용자</th>
			      <th scope="col">직급</th>
			    </tr>
			  </thead>
			  <tbody class="table-group-divider">
			    <tr>
			      <th scope="row">X</th>
			      <td><i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">contacts_product</i></td>
			      <td>권준하</td>
			      <td>사원</td>
			    </tr>
			    <tr>
			      <th scope="row">X</th>
			      <td><i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">contacts_product</i></td>
			      <td>고은빈</td>
			      <td>과장</td>
			    </tr>
			    <tr>
			      <th scope="row">X</th>
			      <td><i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">contacts_product</i></td>
			      <td>이진우</td>
			      <td>전무</td>
			    </tr>
			  </tbody>
			</table>
		</div>	  	  
	  </section>	
	</div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='드라이버']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>