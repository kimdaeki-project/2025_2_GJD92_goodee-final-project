<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>전자결재</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
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
			        <a class="nav-link text-dark" href="/approval/sign">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="서명 등록">fact_check</i>
			          <span class="nav-link-text ms-1 text-sm">서명 등록</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    	
	    	<div class="mt-5 row d-flex justify-content-between" style="width: 95%; margin: 0 auto;">
			    <div class="col-auto" style="width: 180px;">
			    	<div class="rounded text-center w-100" style="border: 1px solid #686868; height: 500px; overflow: hidden; box-shadow: 2px 2px 5px gray; margin: 0 auto;">
			    		<div class="mt-1">
		    				<ul class="list-unstyled">
			    				<c:forEach var="approver" items="${ approval.approverDTOs }">
			    					<c:if test="${ not empty approver.apvrComment }">
			    						
			    						<li>
												<div class="d-flex justify-content-start align-items-center mt-2" style="width: 80%; margin: 0 auto;">
													<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
													<span>${ approver.staffDTO.jobDTO.jobDetail } ${ approver.staffDTO.staffName }</span>
												</div>
				    						<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
													<span> ${ approver.apvrComment }</span>
												</div>
				    					</li>
			    						
			    					</c:if>
			    				</c:forEach>		    				
		    				</ul>
		    			</div>
	    			</div>
	    			
	    			<c:if test="${ approval.aprvState ne 702 and approval.aprvState ne 703 }">
		    			<div class="mt-3">
		    				<div class="text-center mb-1">결재 의견</div>
		    				<form id="apvrContentForm" method="POST" action="/approval/${ approval.aprvCode }/check">
		    					<input type="hidden" id="apvrResult" name="apvrResult" value="" />
			    				<textarea id="apvrComment" name="apvrComment" style="width: 100%; height: 100px; resize: none;"></textarea>
		    				</form>
		    			</div>
	    			
		    			<button type="button" class="btn btn-outline-secondary bg-gradient-dark text-white mt-4 w-100" onclick="checkApproval(true)" <c:if test="${ isMyTurn eq 'N' }">disabled</c:if>>결재</button>
		    			<button type="button" class="btn btn-outline-secondary bg-gradient-dark text-white mt-2 w-100" onclick="checkApproval(false)" <c:if test="${ isMyTurn eq 'N' }">disabled</c:if>>반려</button>
	    			</c:if>
			    </div>
			    
			    <div class="col-auto" style="width: 800px; overflow: hidden auto;">
			    
			    	<c:if test="${ approval.aprvType eq 901 }">
		    			<c:import url="/WEB-INF/views/approval/detail-vacation.jsp"></c:import>
		    		</c:if>
		    		
		    		<c:if test="${ approval.aprvType eq 902 }">
		    			<c:import url="/WEB-INF/views/approval/detail-common.jsp"></c:import>
		    		</c:if>
		    		
		    		<c:if test="${ approval.aprvType eq 903 }">
		    			<c:import url="/WEB-INF/views/approval/detail-common.jsp"></c:import>
		    		</c:if>
		    		
		    		<c:if test="${ approval.aprvType eq 999 }">
		    			<c:import url="/WEB-INF/views/approval/detail-common.jsp"></c:import>
		    		</c:if>

			    </div>
			    
			    <div class="col-auto" style="width: 180px;">
			    	<div class="rounded text-center w-100" style="border: 1px solid #686868; min-height: 500px; box-shadow: 2px 2px 5px gray; margin: 0 auto;">
			    		<div class="mt-1">
		    				<ul class="list-unstyled">
		    					<c:forEach var="approver" items="${ approval.approverDTOs }">
		    						<c:if test="${ approver.apvrSeq ne 0 }">
		    						
		    							<li>
		    								<input type="hidden" class="post-apvr" name="approver" value="${ approver.staffDTO.staffCode }" data-approved="${ not empty approver.apvrResult ? 'Y' : 'N' }">
												<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
													<div class="rounded m-0 px-1 py-0 <c:if test="${ not empty approver.apvrResult }">bg-gradient-dark text-white</c:if>" style="border: 1px solid black; color: black; font-size: 14px;">
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
											<div class="rounded m-0 px-1 py-0 bg-gradient-dark text-white" style="border: 1px solid black; color: black; font-size: 14px;">기안</div>
											<span> ${ approval.staffDTO.deptDTO.deptDetail }</span>
										</div>
										<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
											<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
											<span>${ approval.staffDTO.jobDTO.jobDetail } ${ approval.staffDTO.staffName }</span>
										</div>
		    					</li>
		    				</ul>
			    		</div>
	    				
	    				<div class="mt-5">
		    				<ul id="receiptList" class="list-unstyled">
		    				
		    					<c:forEach var="approver" items="${ approval.approverDTOs }">
		    						<c:if test="${ approver.apvrSeq eq 0 and approver.apvrType eq 710 }">
		    						
		    							<li>
		    								<input type="hidden" class="post-recp" name="receiver" value="${ approver.staffDTO.staffCode }">
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
		    								<input type="hidden" class="post-agre" name="agreer" value="${ approver.staffDTO.staffCode }" data-approved="${ not empty approver.apvrResult ? 'Y' : 'N' }">
				    						<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
													<div class="rounded m-0 px-1 py-0 <c:if test="${ not empty approver.apvrResult }">bg-gradient-dark text-white</c:if>" style="border: 1px solid black; color: black; font-size: 14px;">합의</div>
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
	    			
	    			<c:if test="${ approval.aprvState ne 702 and approval.aprvState ne 703 }">
		    			<button type="button" class="btn btn-primary bg-gradient-dark text-white mt-5 w-100" <c:if test="${ isMyTurn eq 'N' }">disabled</c:if>>결재선 재지정</button>
	    			</c:if>
	    			<button type="button" class="btn btn-outline-secondary mt-5 w-100" <c:if test="${ approval.aprvState ne 702 }">disabled</c:if>>문서 출력</button>
			    </div>
	    	</div>
	    </section>
    </div>
  </main>
  
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='전자결재']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='내 결재함']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "전자 문서 조회"
	</script>
	<script src="/js/approval/detail.js"></script>
</body>

</html>