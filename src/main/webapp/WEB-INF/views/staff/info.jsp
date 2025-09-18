<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <section class="px-2 px-md-4">
    	<sec:authentication property="principal" var="staff" />
    	
      <div class="page-header min-height-300 border-radius-xl mt-4" style="background-image: url('https://images.unsplash.com/photo-1531512073830-ba890ca4eba2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80');">
        <span class="mask  bg-gradient-dark  opacity-6"></span>
      </div>
      <div class="card card-body mx-2 mx-md-2 mt-n6" style="height: 64vh; overflow: hidden;">
        <div class="row gx-4 mb-2">
          <div class="col-auto">
            <div class="position-relative">
              <img width="100" height="100" src="/file/staff/${ staff.staffAttachmentDTO.attachmentDTO.savedName }" alt="profile_image" class="border-radius-lg shadow-sm">
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
        		<ul class="list-group" style="height: 30vh">
        			<li class="list-group-item border-0 ps-0 pt-0 d-flex align-items-center"><i class="material-symbols-rounded me-1 text-dark">man</i><strong class="text-dark">성별:</strong> &nbsp; ${ staff.staffGender eq 1 ? '남성' : '여성' }</li>
        			<li class="list-group-item border-0 mt-2 ps-0 pt-0 d-flex align-items-center"><i class="material-symbols-rounded me-1 text-dark">mail</i><strong class="text-dark">이메일:</strong> &nbsp; ${ staff.staffEmail }</li>
        			<li class="list-group-item border-0 mt-2 ps-0 pt-0 d-flex align-items-center"><i class="material-symbols-rounded me-1 text-dark">call</i><strong class="text-dark">연락처:</strong> &nbsp; ${ staff.staffPhone }</li>
        			<li class="list-group-item border-0 mt-2 ps-0 pt-0 d-flex align-items-center"><i class="material-symbols-rounded me-1 text-dark">post</i><strong class="text-dark">우편번호:</strong> &nbsp; ${ staff.staffPostcode }</li>
        			<li class="list-group-item border-0 mt-2 ps-0 pt-0 d-flex align-items-center"><i class="material-symbols-rounded me-1 text-dark">home</i><strong class="text-dark">주소:</strong> &nbsp; ${ staff.staffAddress }</li>
        			<li class="list-group-item border-0 mt-2 ps-0 pt-0 d-flex align-items-center"><i class="material-symbols-rounded me-1 text-dark">home</i><strong class="text-dark">상세주소:</strong> &nbsp; ${ staff.staffAddressDetail }</li>
        			<li class="list-group-item border-0 mt-2 ps-0 pt-0 d-flex align-items-center"><i class="material-symbols-rounded me-1 text-dark">apartment</i><strong class="text-dark">입사일:</strong> &nbsp; ${ staff.staffHireDate }</li>
        		</ul>
        		
        		<div class="col-4 mt-5 ms-5 d-flex justify-content-center">
        			<button type="button" onclick="location.href = '/staff/info/update'" class="btn btn-outline-secondary bg-gradient-dark text-white">수정</button>
        		</div>
        	</div>
        	
        	<div class="col-3">
        		<div style="height: 30vh">
	        		<form id="passwordUpdate">
	        			<input type="hidden" id="staffCode" name="staffCode" value="${ staff.staffCode }" />
	        		
	        			<label for="oldPw" class="form-label"><strong class="text-dark">현재 비밀번호</strong></label>
	        			<input type="password" id="oldPw" name="oldPw" class="form-control w-50" />
	        			
	        			<label for="newPw" class="form-label mt-3"><strong class="text-dark">새 비밀번호</strong></label>
	        			<input type="password" id="newPw" name="newPw" class="form-control w-50" />
	        			
	        			<label for="newPwChk" class="form-label mt-3"><strong class="text-dark">비밀번호 확인</strong></label>
	        			<input type="password" id="newPwChk" name="newPwChk" class="form-control w-50" />
	        		</form>
        		</div>
        		
        		<div class="col-4 mt-5 ms-5 d-flex justify-content-center">
        			<button type="button" onclick="changePw()" class="btn btn-outline-secondary bg-gradient-dark text-white">비밀번호 변경</button>
        		</div>
        	</div>
        	
        	<div class="col-5">
        		<img width="800" height="800" src="/images/info.png/" class="opacity-3" />	
        	</div>
        </div>
      </div>
      
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/staff/info.js"></script>
</body>

</html>