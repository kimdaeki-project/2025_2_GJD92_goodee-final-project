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
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden scroll;">
    
    <!-- 여기에 코드 작성 -->
    <div class="row">
        <div class="col-10">
		  <!-- 레일형 / 고속형 어트랙션 -->
          <div class="card my-4 mt-8 m-8">
			  <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
			    <div class="bg-gradient-dark shadow-dark border-radius-lg pt-3 pb-4">
			      <h3 class="text-white text-capitalize ps-5 mt-3"> ${ride.rideName } </h3>
			    </div>
			  </div>
		  
			  <div class="card-body mt-4">
				  <div class="row ride-content">
				    
				    <!-- 왼쪽: 어트랙션 이미지 -->
				    <div class="col-md-6 text-center">
				      <img alt="" 
				           src="/file/ride/${ ride.rideAttachmentDTO.attachmentDTO.savedName }" 
				           style="width:400px; height:400px; object-fit:cover;">
				    </div>
				
				    <!-- 오른쪽: 상세 정보 -->
				    <div class="col-md-6 ride-info">
				      <table class="table">
				        <tr>
				          <th>어트랙션 코드</th>
				          <td>${ride.rideCode}</td>
				        </tr>
				        <tr>
				          <th>담당자</th>
				          <td>${ride.staffDTO.staffName} (${ride.staffDTO.staffCode})</td>
				        </tr>
				        <tr>
				          <th>어트랙션 기종</th>
				          <td>${ride.rideType}</td>
				        </tr>
				        <tr>
				          <th>탑승인원</th>
				          <td>${ride.rideCapacity}</td>
				        </tr>
				        <tr>
				          <th>운행시간</th>
				          <td>${ride.rideDuration}</td>
				        </tr>
				        <tr>
				          <th>이용정보</th>
				          <td>${ride.rideRule}</td>
				        </tr>
				        <tr>
				          <th>어트랙션 설명</th>
				          <td>${ride.rideInfo}</td>
				        </tr>
				        <tr>
				          <th>개장일</th>
				          <td>${ride.rideDate}</td>
				        </tr>
				        <tr>
				          <th>운행상태</th>
				          <td>${ride.rideState}</td>
				        </tr>
				      </table>
				    </div>
				
				  </div>
			</div>
			
			<!-- 버튼 -->
		    <div class="form-group row mt-4 text-end">
		      <div class="col-sm-12">
		      	<!-- 수정 버튼 -> add 페이지로가는데 입력된 정보 가지고 감 -->
		      	<form action="${pageContext.request.contextPath }/ride/${ride.rideCode}/update"
		      			method="get" style="display: inline;">
			        <button type="submit" 
			        		class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" 
			        		style="width: 100px;">수정</button>
		        </form>
		        
		        <!-- 삭제 버튼 -->
		        <form action="${pageContext.request.contextPath}/ride/${ride.rideCode}/delete" 
			          method="post" style="display:inline;">
			        <button type="submit" 
			                class="btn btn-sm btn-outline-secondary" 
			                style="width: 100px;"
			                onclick="return confirm('정말 삭제하시겠습니까?');">삭제</button>
			    </form>
		      </div>
		    </div>
			
			
		  </div>
        </div>
	  </div> 

    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>