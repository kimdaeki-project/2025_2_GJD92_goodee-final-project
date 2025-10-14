<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>내 정보</title>
	
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
    <section class="px-2 px-md-4">
    	<sec:authentication property="principal" var="staff" />
    	
      <div class="page-header min-height-300 border-radius-xl mt-4" style="background-image: url('https://images.unsplash.com/photo-1531512073830-ba890ca4eba2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80');">
        <span class="mask  bg-gradient-dark  opacity-6"></span>
      </div>
      <div class="card card-body mx-2 mx-md-2 mt-n6" style="height: 64vh; overflow: hidden;">
      	<form:form method="POST" modelAttribute="staffDTO" enctype="multipart/form-data">
      		<form:hidden path="staffCode" />
	        <div class="row gx-4 mb-2">
	          <div class="col-auto">
	            <div class="position-relative">
								<label for="attach">
		            	<img id="preview" width="100" height="100" style="object-fit: cover;" src="/file/staff/${ staffDTO.staffAttachmentDTO.attachmentDTO.savedName }" class="border-radius-lg shadow-sm" />
								</label>
								<input type="file" class="d-none" id="attach" name="attach" />
	            </div>
	          </div>
	          <div class="col-auto my-auto">
	            <div class="h-100">
	              <h5 class="mb-1">
	                ${ staff.staffName }
	              </h5>
	              <p class="mb-0 font-weight-normal text-sm">
	                ${ staff.jobDTO.jobDetail } / ${ staff.deptDTO.deptDetail }
	              </p>
	            </div>
	          </div>
	        </div>
	        
	        <div class="d-flex justify-content-between aligh-items-center mt-5 ms-5">
	        
	        	<div class="col-4">
	        		<div class="col-6">
	    					<form:label path="staffPostcode">
	    						우편번호
	    						<button type="button" class="btn btn-outline-secondary py-0 ms-4 mb-1" onclick="DaumPostcode()">우편번호 검색</button>	
	    					</form:label>
	    					<form:input path="staffPostcode" cssClass="form-control" readonly="true" />
	    				</div>
	    				
	    				<div class="col-6 mt-3">
	    					<form:label path="staffAddress">주소</form:label>
	    					<form:input path="staffAddress" cssClass="form-control" readonly="true" />
	    				</div>
	    				
	    				<div class="col-6 mt-3">
	    					<form:label path="staffAddressDetail">상세주소</form:label>
	    					<form:input path="staffAddressDetail" cssClass="form-control" />
	    				</div>
	        	</div>
	        	
	        	<div class="col-4">
	        		<div class="col-6 inputDiv">
	    					<form:label path="staffEmail">이메일</form:label>
	    					<form:errors path="staffEmail"></form:errors>
	    					<form:input path="staffEmail" cssClass="form-control" cssErrorClass="form-control error" />
	    				</div>
	    				
	    				<div class="col-6 mt-3 inputDiv">
	    					<form:label path="staffPhone">연락처</form:label>
	    					<form:errors path="staffPhone"></form:errors>
	    					<form:input path="staffPhone" cssClass="form-control" cssErrorClass="form-control error" />
	    				</div>
	    				
	    				<div class="col-6 mt-5 d-flex justify-content-center align-items-center">
	    					<button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">수정</button>
    						<button type="button" class="btn btn-sm btn-outline-secondary" onclick="history.back();" style="width: 100px;">취소</button>
	    				</div>
	        	</div>

	        	<div class="col-5">
	        		<img width="800" height="800" src="/images/info.png/" class="opacity-3" />	
	        	</div>
	        </div>
        </form:form>
      </div>
      
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/js/staff/info-update.js"></script>
</body>

</html>