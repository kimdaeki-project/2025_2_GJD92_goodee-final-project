<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>어트랙션 점검 기록 등록</title>
	
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
			    	<!-- 메뉴 개수만큼 추가 -->
			     	<c:import url="/WEB-INF/views/ride/ride-side-sidebar.jsp"></c:import>
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    
		    <!-- 여기에 코드 작성 -->
			 <div class="row">
        <div class="col-10">
		  <!-- 레일형 / 고속형 어트랙션 -->
          <div class="card my-4 mt-8 m-8">
			  <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
			    <div class="bg-gradient-dark shadow-dark border-radius-lg pt-3 pb-4">
			      <h3 class="text-white text-capitalize ps-5 mt-3"> ${inspection.rideDTO.rideName } </h3>
			    </div>
			  </div>
		  
			  <div class="card-body mt-4">
				  <div class="row inspection-content">
				    
				    <!-- 왼쪽: 어트랙션 점검 기록 이미지 -->
				    <div class="col-md-6 text-center">
				      <img alt="" 
				           src="/file/inspection/${ inspection.inspectionAttachmentDTO.attachmentDTO.savedName }" 
				           style="width:400px; height:400px; object-fit:cover;">
				    </div>
				
				    <!-- 오른쪽: 상세 정보 -->
				    <div class="col-md-6 inspection-info">
				      <table class="table">
				        <tr>
				          <th>어트랙션 코드</th>
				          <td>${inspection.rideDTO.rideCode}</td>
				        </tr>
				        <tr>
				          <th>어트랙션 기종</th>
				          <td>${inspection.rideDTO.rideType}</td>
				        </tr>
				        <tr>
				          <th>점검유형</th>
				          	<c:if test="${ inspection.isptType eq 401 }">
							<td>긴급점검(${inspection.isptType })</td>
							</c:if>
							<c:if test="${ inspection.isptType eq 501 }">
								<td>일일점검(${inspection.isptType })</td>
							</c:if>
							<c:if test="${ inspection.isptType eq 502 }">
								<td>정기점검(${inspection.isptType })</td>
							</c:if>
				        </tr>
				        <tr>
				          <th>점검결과</th>
				          	<c:if test="${ inspection.isptResult eq 201 }">
							<td>정상(${ inspection.isptResult })</td>
							</c:if>
							<c:if test="${ inspection.isptResult eq 202 }">
								<td>특이사항 있음(${ inspection.isptResult })</td>
							</c:if>
							<c:if test="${ inspection.isptResult eq 203 }">
								<td>운영불가(${ inspection.isptResult })</td>
							</c:if>
				        </tr>
				        <tr>
				          <th>담당자</th>
				          <td>${inspection.staffDTO.staffName} (${inspection.staffDTO.staffCode})</td>
				        </tr>
						<tr>
				          <th>점검 시작일</th>
				          <td>${inspection.isptStart}</td>
				        </tr>
				        <tr>
				          <th>점검 종료일</th>
				          <td>${inspection.isptEnd}</td>
				        </tr>
				      </table>
				    </div>
				
				  </div>
			</div>
			
			<!-- 버튼 --><!-- 로그인 사용자 정보 꺼내기 -->
 			<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="staff" />
			
			<!-- 시설부서(deptCode == 1003)일 때만 등록 버튼 보이기 -->
			<c:if test="${staff.deptDTO.deptCode eq 1003}">
			    <div class="form-group row mt-4 text-end">
			      <div class="col-sm-12">
			      	<!-- 수정 버튼 -> add 페이지로가는데 입력된 정보 가지고 감 -->
			      	<form action="${pageContext.request.contextPath }/inspection/${inspection.isptNum}/update"
			      			method="get" style="display: inline;">
				        <button type="submit" 
				        		class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" 
				        		style="width: 100px;">수정</button>
			        </form>
			        <!-- 삭제 버튼 -->
			        <button type="button" 
			                class="btn btn-sm btn-outline-secondary" 
			                style="width: 100px;"
			                onclick="deleteInspection('${ inspection.isptNum }')">삭제</button>
			      </div>
			    </div>
		    </c:if>
		    </sec:authorize>
			
			
		  </div>
        </div>
	  </div>  
	
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/inspection/inspectionDetail.js"></script>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='어트랙션 점검']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "어트랙션 점검"
	</script>
</body>

</html>