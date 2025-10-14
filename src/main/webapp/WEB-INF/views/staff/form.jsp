<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>사원</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
	<style>
		.inputDiv span {
			font-size: 12px;
			color: red;
		}
		
		.error {
			border-color: red !important;
		}
	</style>
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
			        <a class="nav-link text-dark" href="/staff/leave?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="연차 현황">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">연차 현황</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/quit?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="퇴사자 조회">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">퇴사자 조회</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/vacation?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="휴가 사용 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">휴가 사용 내역</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/overtime?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="연장근무 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">연장근무 내역</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/early?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="조기 퇴근 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">조기 퇴근 내역</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    
		    <form:form method="POST" modelAttribute="staffDTO" enctype="multipart/form-data">
    		<div class="col-6 offset-3">
    			<div class="form-group row mt-5">
    				<div class="col-4 text-center">
	    				<img id="preview" width="150" height="150" style="object-fit: cover;" <c:if test="${ not empty staffDTO.staffCode }">src="/file/staff/${ staffDTO.staffAttachmentDTO.attachmentDTO.savedName }"</c:if> class="border border-1 border-dark p-1" />
							<label for="attach">
								<div class="btn btn-outline-secondary px-2 py-0 m-auto">프로필 사진 등록</div>
							</label>
							<input type="file" class="d-none" id="attach" name="attach" />
    				</div>
    				
    				<div class="col-4">
    					<div class="col-10">
    						<div class="mb-5 inputDiv">
		    					<form:label path="staffName">성명</form:label>
		    					<form:errors path="staffName"></form:errors>
		    					<form:input path="staffName" cssClass="form-control" cssErrorClass="form-control error" style="height: 34px;" />
	    					</div>
	    					
	    					<div>
	    						<form:label path="inputDeptCode">부서</form:label>
	    						<form:select path="inputDeptCode" cssClass="form-select py-1">
	    							<form:option value="">-- 선택 --</form:option>
	    							<form:option value="1000">임원</form:option>
	    							<form:option value="1001">인사</form:option>
	    							<form:option value="1002">운영</form:option>
	    							<form:option value="1003">시설</form:option>
	    						</form:select>
	    					</div>
    					</div>
    				</div>
    				
    				<div class="col-4">
    					<div class="col-10">
    						<div class="mb-5 inputDiv">
	    						<form:label path="staffGender">성별</form:label>
		    					<form:errors path="staffGender"></form:errors>
		    					<form:select path="staffGender" cssClass="form-select py-1" cssErrorClass="form-select error py-1">
		    						<form:option value="">-- 선택 --</form:option>
		    						<form:option value="1">남</form:option>
		    						<form:option value="2">여</form:option>
		    					</form:select>
	    					</div>
	    					
	    					<div>
	    						<form:label path="inputJobCode">직책</form:label>
	    						<form:select path="inputJobCode" cssClass="form-select py-1">
	    							<form:option value="">-- 선택 --</form:option>
	    							<form:option value="1100">사장</form:option>
	    							<form:option value="1101">전무</form:option>
	    							<form:option value="1102">상무</form:option>
	    							<form:option value="1200">부장</form:option>
	    							<form:option value="1201">과장</form:option>
	    							<form:option value="1202">사원</form:option>
	    						</form:select>
	    					</div>
    					</div>
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1 inputDiv">
    					<form:label path="staffEmail">이메일</form:label>
    					<form:errors path="staffEmail"></form:errors>
    					<form:input path="staffEmail" cssClass="form-control" cssErrorClass="form-control error" />
    				</div>
    				
    				<div class="col-5 inputDiv">
    					<form:label path="staffPhone">연락처</form:label>
    					<form:errors path="staffPhone"></form:errors>
    					<form:input path="staffPhone" cssClass="form-control" cssErrorClass="form-control error" />
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1">
    					<form:label path="staffPostcode">
    						우편번호
    						<button type="button" class="btn btn-outline-secondary py-0 ms-4 mb-1" onclick="DaumPostcode()">우편번호 검색</button>	
    					</form:label>
    					<form:input path="staffPostcode" cssClass="form-control" readonly="true" />
    				</div>
    				
    				<div class="col-6 d-flex align-items-end">
    					
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1">
    					<form:label path="staffAddress">주소</form:label>
    					<form:input path="staffAddress" cssClass="form-control" readonly="true" />
    				</div>
    				
    				<div class="col-5">
    					<form:label path="staffAddressDetail">상세주소</form:label>
    					<form:input path="staffAddressDetail" cssClass="form-control" />
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-5 offset-1 inputDiv">
    					<form:label path="staffHireDate">입사일</form:label>
    					<form:errors path="staffHireDate"></form:errors>
    					<form:input path="staffHireDate" type="date" cssClass="form-control py-1" cssErrorClass="form-control py-1 error" />
    				</div>
    				
    				<div class="col-5">
    					<form:label path="staffFireDate">퇴사일</form:label>
    					<form:input path="staffFireDate" type="date" cssClass="form-control py-1" disabled="true" />
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5 d-flex justify-content-center align-items-center">
    				<button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">${ empty staffDTO.staffCode ? "등록" : "수정" }</button>
    				<button type="button" class="btn btn-sm btn-outline-secondary" onclick="history.back();" style="width: 100px;">취소</button>
    			</div> 
    		</div>
    	</form:form>
	    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/js/staff/regist.js"></script>
	<script>
		document.querySelector("i[data-content='사원']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='${ empty staffDTO.staffCode ? "사원 등록" : "사원 조회" }']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "${ empty staffDTO.staffCode ? '사원 등록' : '사원 정보 수정' }"
	</script>
</body>

</html>