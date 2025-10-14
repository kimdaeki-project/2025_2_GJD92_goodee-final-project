<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>어트랙션 고장 신고 목록</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
	
	<style>
		/* 테이블 마지막 줄 경계선 보정 */
		.table tbody tr:last-child td,
		.table tbody tr:last-child th {
			border-bottom: 1px solid #dee2e6;
		}
		/* 높이 통일 */
		.input-group .form-control,
		.input-group .btn {
		    height: 40px !important;
		    line-height: 1.5 !important;
		}
		
		/* 왼쪽 input → 오른쪽 모서리 각지게 */
		.input-group .form-control {
		    border-top-right-radius: 0 !important;
		    border-bottom-right-radius: 0 !important;
		}
		
		/* 오른쪽 버튼 → 왼쪽 모서리 각지게 */
		.input-group .btn {
		    border-top-left-radius: 0 !important;
		    border-bottom-left-radius: 0 !important;
		}
		
		.input-group:not(.has-validation)> :not(:last-child):not(.dropdown-toggle):not(.dropdown-menu), .input-group:not(.has-validation)>.dropdown-toggle:nth-last-child(n+3) {
		    border-top-right-radius: 0 !important;
		    border-bottom-right-radius: 0 !important;
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
			   		 <!-- 메뉴 개수만큼 추가 -->
			    	<c:import url="/WEB-INF/views/ride/ride-side-sidebar.jsp"></c:import>
			    </ul>
			  </div>
    	</aside>
    	
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    <!-- 여기에 코드 작성 -->
    	<!-- 검색창 -->
		<form action="/fault" class="mb-4">
		    <div class="d-flex justify-content-end" style="margin-top:30px;">
		        <div class="input-group w-25">
		
		            <!-- 검색 조건 선택 -->
		            <!-- 선택했던 옵션이 검색 후에도 유지되도록 selected 속성 -->
		            <select class="form-select" name="searchType" id="searchType" style="max-width:120px; height:40px;">
		                <option value="ride" ${searchType == 'ride' ? 'selected' : '' }>어트랙션</option>
		                <option value="title" ${searchType == 'title' ? 'selected' : '' }>신고 제목</option>
		                <option value="staff" ${searchType == 'staff' ? 'selected' : '' }>담당자</option>
		                <option value="state" ${searchType == 'state' ? 'selected' : '' }>신고 상태</option>
		            </select>
		
		            <!-- 검색어 입력 -->
		            <input type="text" class="form-control" placeholder="검색어를 입력해주세요." name="keyword" id="keywordInput" value="${pager.keyword}">
		            
		            <!-- 신고 상태 옵션 -->
		            <select class="form-select d-none" name="keywordState" id="stateSelect" style="height:40px;">
	            		<option value="">-- 점검결과 --</option>
		            	<option value="410" ${pager.keyword == '410' ? 'selected' : '' }>신고접수</option>
		            	<option value="411" ${pager.keyword == '411' ? 'selected' : '' }>담당자 배정</option>
		            	<option value="412" ${pager.keyword == '412' ? 'selected' : '' }>수리중</option>
		            	<option value="420" ${pager.keyword == '420' ? 'selected' : '' }>수리완료</option>
					 </select>
		            <!-- 검색 버튼 -->
		            <button class="btn btn-dark" type="submit">검색</button>
		        </div>
		    </div>
		</form>
	    
		<table class="table table-hover align-middle text-center">
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">어트랙션</th>
					<th scope="col">신고 제목</th>
					<th scope="col">신고 날짜</th>
					<th scope="col">담당자</th>
					<th scope="col">신고 상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ fault.content }" var="f">
					<tr>
						<td scope="row">${ f.faultNum }</td>
						<td scope="row">${ f.rideDTO.rideName }</td>
						<td><a href="${pageContext.request.contextPath }/fault/${ f.faultNum }">${ f.faultTitle }</a></td>
						<td scope="row">${ f.faultDate }</td>
						<td scope="row">${ f.staffDTO.staffName }</td>
						
						<!-- 신고 상태 -->		
						<c:choose>
						    <c:when test="${ f.faultState eq 410 }"><td>신고접수</td></c:when>
						    <c:when test="${ f.faultState eq 411 }"><td>담당자 배정</td></c:when>
						    <c:when test="${ f.faultState eq 412 }"><td>수리중</td></c:when>
						    <c:when test="${ f.faultState eq 420 }"><td>수리완료</td></c:when>
						</c:choose>


					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 페이지네이션 -->
		<c:if test="${ fault.content.size() gt 0 }">
		<nav>
			<ul class="pagination justify-content-center">
				<c:if test="${ fault.hasPrevious() and pager.startPage gt 1 }">
					<li class="page-item">
						<a class="page-link" href="?page=${ pager.startPage - 1 }&keyword=${ pager.keyword }">&lt;</a>
					</li>
				</c:if>
				
				<c:forEach var="i" begin="${ pager.startPage }" end="${ pager.endPage }">
					<li class="page-item ${ i == fault.number ? 'active' : '' }">
						<a class="page-link" href="?page=${i}&keyword=${ pager.keyword }">${i + 1}</a>
					</li>
				</c:forEach>
				
				<c:if test="${ fault.hasNext() and pager.endPage + 1 ne fault.totalPages }">
					<li class="page-item">
						<a class="page-link" href="?page=${ pager.endPage + 1 }&keyword=${ pager.keyword }">&gt;</a>
					</li>
				</c:if>
			</ul>
		</nav>
    	</c:if>
    	
	<!-- 검색 결과 없음 -->
	<c:if test="${ totalFault eq 0 }">
		<div class="alert alert-secondary text-center" style="color:white;">검색된 결과가 없습니다.</div>
	</c:if>

	    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/fault/faultList.js"></script>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='고장 신고 목록']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "고장 신고 목록"
	</script>
</body>

</html>