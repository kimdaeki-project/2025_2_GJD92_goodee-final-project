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
    	<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="width: 200px !important; height: 92vh;">
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
		    	<div class="mt-5 row d-flex justify-content-between" style="width: 90%; margin: 0 auto;">
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
		    			<button type="button" class="btn btn-outline-secondary mt-2 w-100" data-bs-toggle="modal" data-bs-target="#saveModal">불러오기</button>
		    			<button type="button" onclick="saveApproval()" class="btn btn-outline-secondary mt-2 w-100">임시저장</button>
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
			    					<c:if test="${ hasApprover }">
			    						<c:forEach var="approver" items="${ approval.approverDTOs }">
				    						<c:if test="${ approver.apvrSeq ne 0 }">
				    						
				    							<li>
				    								<input type="hidden" name="approver" value="${ approver.staffDTO.staffCode }" />
														<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
															<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">
																<c:if test="${ approver.apvrType eq 712 }">승인</c:if>
						    								<c:if test="${ approver.apvrType eq 711 }">검토</c:if>
															</div>
															<span> ${ approver.staffDTO.deptDTO.deptDetail }</span>
														</div>
														<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
															<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
															<span>${ approver.staffDTO.jobDTO.jobDetail } ${ approver.staffDTO.staffName }</span>
														</div>
														<div class="text-center mt-2" style="color: black;">│</div>
				    							</li>
													
				    						</c:if>
				    					</c:forEach>
				    					
				    					<li>
				    						<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
													<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">기안</div>
													<span> ${ staff.deptDTO.deptDetail }</span>
												</div>
												<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
													<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
													<span>${ staff.jobDTO.jobDetail } ${ staff.staffName }</span>
												</div>
				    					</li>
			    					</c:if>
			    				</ul>
				    		</div>
		    				
		    				<div class="mt-5">
			    				<ul id="receiptList" class="list-unstyled">
			    					<c:forEach var="approver" items="${ approval.approverDTOs }">
			    						<c:if test="${ approver.apvrSeq eq 0 and approver.apvrType eq 710 }">
			    						
			    							<li>
			    								<input type="hidden" name="receiver" value="${ approver.staffDTO.staffCode }">
					    						<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
														<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">수신</div>
														<span> ${ approver.staffDTO.deptDTO.deptDetail }</span>
													</div>
													<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
														<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
														<span>${ approver.staffDTO.jobDTO.jobDetail } ${ approver.staffDTO.staffName }</span>
													</div>
					    					</li>
			    						
			    						</c:if>
			    					</c:forEach>
			    				</ul>
		    				</div>
		    				
		    				<div class="mt-5">
			    				<ul id="agreeList" class="list-unstyled">
			    					<c:forEach var="approver" items="${ approval.approverDTOs }">
			    						<c:if test="${ approver.apvrSeq eq 0 and approver.apvrType eq 713 }">
			    						
			    							<li>
			    								<input type="hidden" name="agreer" value="${ approver.staffDTO.staffCode }" />
					    						<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
														<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">합의</div>
														<span> ${ approver.staffDTO.deptDTO.deptDetail }</span>
													</div>
													<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
														<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
														<span>${ approver.staffDTO.jobDTO.jobDetail } ${ approver.staffDTO.staffName }</span>
													</div>
					    					</li>
			    						
			    						</c:if>
			    					</c:forEach>
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
  
  <div class="modal fade" id="saveModal" tabindex="-1">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="saveModalLabel">임시저장한 기안</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	      <div class="modal-body">
	       	<form id="savedApprovalForm">
	       		<select id="savedApproval" name="savedCode" class="form-select">
	       			<option value="" selected>-- 선택 --</option>
	       		</select>
	       	</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" onclick="loadApproval()" class="btn btn-primary bg-gradient-dark text-white">확인</button>
	        <button type="button" onclick="deleteApproval()" class="btn btn-primary">삭제</button>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	      </div>
	    </div>
	  </div>
	</div>
  
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
		const hasSign = ${ not empty staff.staffSignDTO ? true : false }
	</script>
	<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js"></script>
	<script src="/js/approval/draft.js"></script>
</body>

</html>