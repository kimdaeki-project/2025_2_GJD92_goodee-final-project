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
			        <a class="nav-link text-dark" href="/approval/recept?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="내 수신함">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">내 수신함</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" onclick="openApprovalSign()">
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
			    				<a href="/approval/draft" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">기안서</a>
		    				</div>
		    				
		    				<div class="mt-3">
			    				<a href="/approval/draft/vacation" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">휴가 신청서</a>
		    				</div>
		    				
		    				<div class="mt-3">
			    				<a href="/approval/draft/overtime" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">연장 근무 신청서</a>
		    				</div>
		    				
		    				<div class="mt-3">
			    				<a href="/approval/draft/early" class="text-start text-decoration-underline" style="color: #686868; font-weight: 500; ">조기 퇴근 신청서</a>
		    				</div>
		    			</div>
		    			
		    			<button type="button" onclick="sendApproval()" class="btn btn-primary bg-gradient-dark text-white mt-5 w-100">기안</button>
		    			<button type="button" class="btn btn-outline-secondary mt-2 w-100">불러오기</button>
		    			<button type="button" class="btn btn-outline-secondary mt-2 w-100">임시저장</button>
				    </div>
				    
				    <div class="col-auto" style="width: 800px; overflow: hidden auto;">
				    	<sec:authentication property="principal" var="staff"/>
			    		
			    		<c:if test="${ draftForm eq 'vacation' }">
			    			<c:import url="/WEB-INF/views/approval/draft-vacation.jsp"></c:import>
			    		</c:if>
			    		
			    		<c:if test="${ draftForm eq 'overtime' }">
			    			<c:import url="/WEB-INF/views/approval/draft-overtime.jsp"></c:import>
			    		</c:if>
			    		
			    		<c:if test="${ draftForm eq 'early' }">
			    			<c:import url="/WEB-INF/views/approval/draft-early.jsp"></c:import>
			    		</c:if>
			    		
			    		<c:if test="${ draftForm eq 'common' }">
			    			<c:import url="/WEB-INF/views/approval/draft-common.jsp"></c:import>
			    		</c:if>
	
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