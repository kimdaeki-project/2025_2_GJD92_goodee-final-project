<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Log In</title>
	
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
                  <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">Goodee World Groupware</h4>
                </div>
              </div>
              <div class="card-body">
                <form role="form" class="text-start" method="POST">
                  <div class="input-group input-group-outline my-3">
                    <input type="text" name="staffCode" class="form-control" placeholder="사원번호" value="${ cookie.rememberId.value }">
                  </div>
                  <div class="input-group input-group-outline mb-3">
                    <input type="password" name="staffPw" class="form-control" placeholder="비밀번호 (로그인 5회 실패 시 계정이 차단됩니다)">
                  </div>
                  <div class="form-check form-switch d-flex align-items-center mb-3">
                    <input class="form-check-input" type="checkbox" id="rememberId" name="rememberId" value="true" <c:if test="${ not empty cookie.rememberId.value }">checked</c:if>>
                    <label class="form-check-label mb-0 ms-3" for="rememberId">사원번호 기억하기</label>
                  </div>
                  <div class="text-center">
                    <button type="submit" class="btn bg-gradient-dark w-100 my-4 mb-2">로그인</button>
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