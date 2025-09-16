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
    <div class="d-flex">
    	<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="height: 92vh;">
    		<div class="w-100">
			    <ul class="navbar-nav">
			    
			    	<li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/regist">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="사원 등록">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">사원 등록</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="사원 조회">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">사원 조회</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="연차 현황">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">연차 현황</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="퇴사자 조회">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">퇴사자 조회</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="휴가 사용 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">휴가 사용 내역</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="연장근무 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">연장근무 내역</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="조기 퇴근 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">조기 퇴근 내역</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    
    		<div class="col-6 offset-3">
    			<div class="form-group row mt-5">
    				<div class="col-4 text-center">
	    				<img width="150" height="150" style="object-fit: cover;" src="/file/staff/${ staff.staffAttachmentDTO.attachmentDTO.savedName }" class="border border-1 border-dark p-1" />
    				</div>
    				
    				<div class="col-4">
    					<div class="col-10">
    						<div class="mb-5">
		    					<h5>이름</h5>
		    					<p class="ms-4">${ staff.staffName }</p>
	    					</div>
	    					
	    					<div>
	    						<h5>부서</h5>
		    					<p class="ms-4">${ staff.deptDTO.deptDetail }</p>
	    					</div>
    					</div>
    				</div>
    				
    				<div class="col-4">
    					<div class="col-10">
    						<div class="mb-5">
	    						<h5>성별</h5>
		    					<p class="ms-4">${ staff.staffGender eq 1 ? '남성' : '여성' }</p>
	    					</div>
	    					
	    					<div>
	    						<h5>직책</h5>
		    					<p class="ms-4">${ staff.jobDTO.jobDetail }</p>
	    					</div>
    					</div>
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1">
    					<h5>이메일</h5>
		    			<p class="ms-4">${ staff.staffEmail }</p>
    				</div>
    				
    				<div class="col-5">
    					<h5>연락처</h5>
		    			<p class="ms-4">${ staff.staffPhone }</p>
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1">
    					<h5>우편번호</h5>
		    			<p class="ms-4">${ staff.staffPostcode }</p>
    				</div>
    				
    				<div class="col-6 d-flex align-items-end">
    					
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1">
    					<h5>주소</h5>
		    			<p class="ms-4">${ staff.staffAddress }</p>
    				</div>
    				
    				<div class="col-5">
    					<h5>상세주소</h5>
		    			<p class="ms-4">${ staff.staffAddressDetail }</p>
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1">
    					<h5>입사일</h5>
		    			<p class="ms-4">${ staff.staffHireDate }</p>
    				</div>
    				
    				<div class="col-5">
    					<h5>퇴사일</h5>
		    			<p class="ms-4">${ not empty staff.staffFireDate ? staff.staffFireDate : '재직중' }</p>
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5 d-flex justify-content-center align-items-center">
    				<button type="button" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">수정</button>
    				<button type="button" class="btn btn-sm btn-outline-secondary" onclick="history.back();" style="width: 100px;">목록</button>
    			</div> 
    		</div>
	    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='사원']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='사원 조회']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "사원 정보"
	</script>
</body>

</html>