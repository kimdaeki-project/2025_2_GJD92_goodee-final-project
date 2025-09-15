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
    
    	<form:form method="POST" modelAttribute="staffDTO" enctype="multipart/form-data">
    		<div class="col-6 offset-3">
    			<div class="form-group row mt-5">
    				<div class="col-4">
    					<input type="file" id="attach" name="attach" class="form-control" />
    				</div>
    				
    				<div class="col-4">
    					<div class="mb-5">
	    					<form:label path="staffName">성명</form:label>
	    					<form:input path="staffName" cssClass="form-control" />
    					</div>
    					
    					<div>
    						<form:label path="inputDeptCode">부서</form:label>
    						<form:select path="inputDeptCode">
    							<form:option value="">-- 선택 --</form:option>
    							<form:option value="1000">인사</form:option>
    							<form:option value="1001">운영</form:option>
    							<form:option value="1002">시설</form:option>
    						</form:select>
    					</div>
    				</div>
    				
    				<div class="col-4">
    					<div class="mb-5">
    						<form:label path="staffGender">성별</form:label>
	    					<form:select path="staffGender">
	    						<form:option value="">-- 선택 --</form:option>
	    						<form:option value="1">남</form:option>
	    						<form:option value="2">여</form:option>
	    					</form:select>
    					</div>
    					
    					<div>
    						<form:label path="inputJobCode">직책</form:label>
    						<form:select path="inputJobCode">
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
    			
    			<div class="form-group row mt-5">
    				<div class="col-6">
    					<form:label path="staffEmail">이메일</form:label>
    					<form:input path="staffEmail" cssClass="form-control" />
    				</div>
    				
    				<div class="col-6">
    					<form:label path="staffPhone">연락처</form:label>
    					<form:input path="staffPhone" cssClass="form-control" />
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-6">
    					<form:label path="staffPostcode">우편번호</form:label>
    					<form:input path="staffPostcode" cssClass="form-control" readonly="true" />
    				</div>
    				
    				<div class="col-6">
    					<button type="button" onclick="DaumPostcode()">우편번호 검색</button>
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-6">
    					<form:label path="staffAddress">주소</form:label>
    					<form:input path="staffAddress" cssClass="form-control" readonly="true" />
    				</div>
    				
    				<div class="col-6">
    					<form:label path="staffAddressDetail">상세주소</form:label>
    					<form:input path="staffAddressDetail" cssClass="form-control" />
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<div class="col-6">
    					<form:label path="staffHireDate">입사일</form:label>
    					<form:input path="staffHireDate" type="date" cssClass="form-control" />
    				</div>
    				
    				<div class="col-6">
    					<form:label path="staffFireDate">퇴사일</form:label>
    					<form:input path="staffFireDate" type="date" cssClass="form-control" disabled="true" />
    				</div>
    			</div>
    			
    			<div class="form-group row mt-5">
    				<button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">등록</button>
    				<button type="button" class="btn btn-sm btn-outline-secondary" style="width: 100px;">취소</button>
    			</div> 
    		</div>
    	</form:form>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/js/staff/regist.js"></script>
	<script>
		document.querySelector("i[data-content='']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>