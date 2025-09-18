<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>비밀번호 변경</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body>
	<main class="main-content  mt-0">
    <div class="page-header align-items-start min-vh-100" style="background-image: url('https://images.unsplash.com/photo-1497294815431-9365093b7331?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80');">
      <span class="mask bg-gradient-dark opacity-6"></span>
      <div class="container my-auto">
        <div class="row">
          <div class="col-lg-4 col-md-8 col-12 mx-auto">
            <div class="card z-index-0 fadeIn3 fadeInBottom">
              <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-dark shadow-dark border-radius-lg py-3 pe-1">
                  <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">Change Password</h4>
                </div>
              </div>
              <div class="card-body">
              	<sec:authentication property="principal" var="staff" />
              	
              	<div class="text-center">
              		<p>${ staff.staffName } 님은 기본 비밀번호를 사용하고 계십니다.<br>비밀번호를 변경해주세요.</p>
              	</div>
              
                <form role="form" class="text-start" action="/staff/password/update" method="POST">
                	<input type="hidden" name="staffCode" value="${ staff.staffCode }" />
                  <div class="input-group input-group-outline my-3">
                    <input type="password" name="oldPw" class="form-control" placeholder="현재 비밀번호" />
                  </div>
                  <div class="input-group input-group-outline my-3">
                    <input type="password" name="newPw" class="form-control" placeholder="새 비밀번호" />
                  </div>
                  <div class="input-group input-group-outline mb-3">
                    <input type="password" name="newPwChk" class="form-control" placeholder="비밀번호 확인">
                  </div>
                  <div class="text-center">
                    <button type="submit" class="btn bg-gradient-dark w-100 my-4 mb-2">비밀번호 변경</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>

	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
</body>

</html>