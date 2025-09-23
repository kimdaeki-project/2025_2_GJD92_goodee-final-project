<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>전자결재</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
	
	<!-- Summernote -->
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-lite.min.css" rel="stylesheet">
	<style>
		table {
			border: 2px solid #686868;
			border-collapse: collapse;
		}
	
		tr, td {
			border: 1px solid #686868;
			border-collapse: collapse;
		}
		
		.hard-line {
			border: 2px solid #686868;
			border-collapse: collapse;
		}
		
		.hard-side-line {
			border-left: 2px solid #686868;
			border-right: 2px solid #686868;
			border-collapse: collapse;
		}
		
		.note-toolbar {
		  height: 50px;
		  min-height: 50px;
		  padding: 2px 5px;
		}
		
		.note-btn {
		  padding: 6px 10px;
		  font-size: 12px;
		}
		
		.note-btn .note-icon-caret {
		  font-size: 12px;
		}
		
		.note-resizebar {
			display: none;
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
			        <a class="nav-link text-dark" href="/approval/draft">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="기안 작성">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">기안 작성</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/approval">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="내 결재함">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">내 결재함</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/approval/recept">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="내 수신함">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">내 수신함</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/approval/sign">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="서명 등록">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">서명 등록</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    	<form id="approvalForm" method="POST" enctype="multipart/form-data">
		    	<div class="mt-5 row d-flex justify-content-between" style="width: 95%; margin: 0 auto;">
				    <div class="col-auto" style="width: 180px;">
				    	<div class="rounded text-center w-100" style="border: 1px solid #686868; height: 500px; overflow: hidden; box-shadow: 2px 2px 5px gray; margin: 0 auto;">
		    				<div class="mt-4">
			    				<a href="#" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">기안서</a>
		    				</div>
		    				
		    				<div class="mt-3">
			    				<a href="#" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">휴가 신청서</a>
		    				</div>
		    				
		    				<div class="mt-3">
			    				<a href="#" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">연장 근무 신청서</a>
		    				</div>
		    				
		    				<div class="mt-3">
			    				<a href="#" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">조기 퇴근 신청서</a>
		    				</div>
		    			</div>
		    			
		    			<button type="button" onclick="sendApproval()" class="btn btn-primary bg-gradient-dark text-white mt-5 w-100">결재</button>
		    			<button type="button" class="btn btn-outline-secondary mt-2 w-100">불러오기</button>
		    			<button type="button" class="btn btn-outline-secondary mt-2 w-100">임시저장</button>
				    </div>
				    
				    <div class="col-auto" style="width: 800px; overflow: hidden auto;">
			    		<sec:authentication property="principal" var="staff"/>
			    		
			    		<table class="mt-5 text-center" style="width: 100%">
				    		<tr class="hard-line" style="height: 100px;">
				    			<td colspan="2" class="hard-line"><p class="d-flex justify-content-between" style="width: 200px; margin: 0 auto;"><span style="font-size: 48px; font-weight: 700;">기</span><span style="font-size: 48px; font-weight: 700;">안</span><span style="font-size: 48px; font-weight: 700;">서</span></p></td>
				    			<td colspan="3">
				    				<!-- 결재선 -->
				    			</td>
				    		</tr>
				    		
				    		<tr class="hard-line">
				    			<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>문</span><span>서</span><span>번</span><span>호</span></p></td>
				    			<td colspan="4"><input type="hidden" name="aprvCode" value="${ aprvCode }">${ aprvCode }</td>
				    		</tr>
				    		
				    		<tr class="hard-line">
				    			<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>기</span><span>안</span><span>일</span><span>자</span></p></td>
				    			<td colspan="4">${ nowDate }</td>
				    		</tr>
				    		
				    		<tr class="hard-line">
				    			<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>시</span><span>행</span><span>일</span></p></td>
				    			<td colspan="4">
				    				<input type="date" name="aprvExe" />
				    			</td>
				    		</tr>
				    		
				    		<tr>
				    			<td rowspan="2" class="hard-line" style="width: 200px;"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>기</span><span>안</span><span>자</span></p></td>
				    			<td class="hard-side-line" style="width: 100px;"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>사</span><span>원</span><span>번</span><span>호</span></p></td>
				    			<td style="width: 200px;">${ staff.staffCode }</td>
				    			<td class="hard-side-line" style="width: 100px;"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>부</span><span>서</span></p></td>
				    			<td style="width: 200px;">${ staff.deptDTO.deptDetail }</td>
				    		</tr>
				    		
				    		<tr>
				    			<td class="hard-side-line"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>직</span><span>급</span></p></td>
				    			<td>${ staff.jobDTO.jobDetail }</td>
				    			<td class="hard-side-line"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>성</span><span>명</span></p></td>
				    			<td>${ staff.staffName }</td>
				    		</tr>
				    		
				    		<tr class="hard-line">
				    			<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>제</span><span>목</span></p></td>
				    			<td colspan="4">
				    				<input type="text" class="form-control" name="aprvTitle" />
				    			</td>
				    		</tr>
				    		
				    		<tr style="border: 2px solid #686868;">
				    			<td colspan="5">
				    				<textarea id="summernote" name="aprvContent"></textarea>
				    			</td>
				    		</tr>
				    		
				    		<tr>
				    			<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>첨</span><span>부</span><span>파</span><span>일</span></p></td>
				    			<td colspan="4">
				    				<input type="file" class="form-control" name="attach" />
				    			</td>
				    		</tr>
				    	</table>
	
				    </div>
				    
				    <div class="col-auto" style="width: 180px;">
				    	<div class="rounded text-center w-100" style="border: 1px solid #686868; min-height: 500px; box-shadow: 2px 2px 5px gray; margin: 0 auto;">
				    		<div class="mt-1">
			    				<ul id="approverList" class="list-unstyled">
			    				
			    				</ul>
				    		</div>
		    				
		    				<div class="mt-5">
			    				<ul id="receiptList" class="list-unstyled">
			    				
			    				</ul>
		    				</div>
		    				
		    				<div class="mt-5">
			    				<ul id="agreeList" class="list-unstyled">
			    				
			    				</ul>
		    				</div>
		    			</div>
		    			
		    			<button type="button" class="btn btn-primary bg-gradient-dark text-white mt-5 w-100" data-bs-toggle="modal" data-bs-target="#shareModal">결재선 지정</button>
				    </div>
		    	</div>
				</form>
	    </section>
    </div>
  </main>
  
  <c:import url="/WEB-INF/views/approval/draft-modal.jsp"></c:import>
  
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	
	<!-- jQuery 3.7.1 -->
	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	
	<!-- Summernote -->
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-lite.min.js"></script>
	
	
	<script>
		document.querySelector("i[data-content='전자결재']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='기안 작성']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "기안 작성"
		
		$(document).ready(function() {
		  $('#summernote').summernote({
			  height: 350,
			  minHeight: 350,
			  maxHeight: 350
		  })
		})
		
		const loginStaffCode = ${ staff.staffCode }
	</script>
	<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js"></script>
	<script src="/js/approval/draft.js"></script>
</body>

</html>