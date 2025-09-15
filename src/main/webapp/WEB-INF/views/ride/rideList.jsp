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
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: scroll;">
    
    <!-- 여기에 코드 작성 -->
    <div class="row">
        <div class="col-12">
          <div class="card my-4">
	          <!-- 레일형 / 고속형 어트랙션 -->
	          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
	            <div class="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
	              <h6 class="text-white text-capitalize ps-3">레일형 / 고속형 어트랙션</h6>
	            </div>
	          </div>
	          <div class="card-body px-0 pb-2">
	              <table class="table align-items-center mb-0">
	                <thead>
	                  <tr>
	                   	<img alt="롤링엑스트레인" src="/images/ride/롤링 엑스 트레인.jpg" style="width:300px; height:300px;">
	                  </tr>
	                  <tr>
	                   	<img alt="아트란티스" src="/images/ride/아트란티스.jpg" style="width:300px; height:300px;">
	                  </tr>
	                </thead>
	                <tbody>
	                  <tr>
	                    <td>
					<div class="ride-name">롤링 엑스 트레인</div>
	                    </td>
	                    <td>
					<div class="ride-name">아트란티스</div>
	                    </td>
	                  </tr>
	                </tbody>
	              </table>
	          </div>
	      </div>   
	      <div class="card my-4">
	          <!-- 회전형 어트랙션 -->            
	          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
	            <div class="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
	              <h6 class="text-white text-capitalize ps-3">회전형 어트랙션</h6>
	            </div>
	          </div>
	          <div class="card-body px-0 pb-2">
	              <table class="table align-items-center mb-0">
	                <thead>
	                  <tr>
	                   	<img alt="회전목마" src="/images/ride/회전목마.jpg" style="width:300px; height:300px;">
	                  </tr>
	                  <tr>
	                   	<img alt="바이킹" src="/images/ride/바이킹.jpg" style="width:300px; height:300px;">
	                  </tr>
	                  <tr>
	                   	<img alt="자이로드롭" src="/images/ride/자이로드롭.jpg" style="width:300px; height:300px;">
	                  </tr>
	                </thead>
	                <tbody>
	                  <tr>
	                    <td>
					<div class="ride-name">회전목마</div>
	                    </td>
	                    <td>
					<div class="ride-name">바이킹</div>
	                    </td>
	                    <td>
					<div class="ride-name">자이로드롭</div>
	                    </td>
	                  </tr>
	                </tbody>
	              </table>
	          </div>
		  </div> 
	      <div class="card my-4">
          <!-- 수상 어트랙션 -->            
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
              <h6 class="text-white text-capitalize ps-3">수상 어트랙션</h6>
            </div>
          </div>
          <div class="card-body px-0 pb-2">
              <table class="table align-items-center mb-0">
                <thead>
                  <tr>
                   	<img alt="후룸라이드" src="/images/ride/후룸라이드.jpg" style="width:300px; height:300px;">
                  </tr>
                  <tr>
                   	<img alt="아마존 익스프레스" src="/images/ride/아마존 익스프레스.jpg" style="width:300px; height:300px;">
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>
						<div class="ride-name">후룸라이드</div>
                    </td>
                    <td>
						<div class="ride-name">아마존 익스프레스</div>
                    </td>

                  </tr>
                </tbody>
              </table>
          </div>
          </div>
          <div class="card my-4">
          <!-- 관람형 어트랙션 -->            
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
              <h6 class="text-white text-capitalize ps-3">관람형 어트랙션</h6>
            </div>
          </div>
          <div class="card-body px-0 pb-2">
              <table class="table align-items-center mb-0">
                <thead>
                  <tr>
                  	<img alt="관람차" src="/images/ride/관람차.jpg" style="width:300px; height:300px;">
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>
						<div class="ride-name">관람차</div>
                    </td>
                  </tr>
                </tbody>
              </table>
          </div>
          </div>
          <div class="card my-4">
          <!-- 어린이 놀이기구 -->            
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
              <h6 class="text-white text-capitalize ps-3">어린이 놀이기구</h6>
            </div>
          </div>
          <div class="card-body px-0 pb-2">
              <table class="table align-items-center mb-0">
                <thead>
                  <tr>
                   	<img alt="후룸라이드" src="/images/ride/후룸라이드.jpg" style="width:300px; height:300px;">
                  </tr>
                  <tr>
               		<img alt="미니 트레인" src="/images/ride/미니 트레인.jpg" style="width:300px; height:300px;">
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>
    					<div class="ride-name">범퍼카</div>
                    </td>
                    <td>
    					<div class="ride-name">미니 트레인</div>
                    </td>
                  </tr>
                </tbody>
              </table>
          </div>
          </div>
      	</div>
      </div>
      
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>