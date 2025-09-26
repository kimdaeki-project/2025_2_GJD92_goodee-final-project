<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<table class="mt-5 mb-5 text-center" style="width: 100%">
	<tr class="hard-line" style="height: 100px;">
		<td colspan="2" class="hard-line"><p class="d-flex justify-content-between" style="width: 200px; margin: 0 auto;"><span style="font-size: 48px; font-weight: 700;">기</span><span style="font-size: 48px; font-weight: 700;">안</span><span style="font-size: 48px; font-weight: 700;">서</span></p></td>
		<td colspan="3" class="p-0">
			<div class="d-flex justify-content-start flex-row-reverse" style="height: 100px;">
				
				<c:forEach var="approver" items="${ approval.approverDTOs }">
					<c:if test="${ approver.apvrSeq ne 0 }">
						
 					<div style="width: 100px; height: 100px; border-left: 2px solid #686868;">
 						<div style="height: 30px; border-bottom: 2px solid #686868;">
 							<p class="d-flex justify-content-between" style="width: 50px; margin: 0 auto; padding: 0;">
 								<c:if test="${ approver.apvrType eq 712 }">
  								<span>승</span><span>인</span>
 								</c:if>
 								
 								<c:if test="${ approver.apvrType eq 711 }">
  								<span>검</span><span>토</span>
 								</c:if>
 							</p>
 						</div>
 						
 						<div class="d-flex justify-content-center align-items-center" style="height: 70px;">
 							<c:choose>
 								<c:when test="${ approver.apvrState eq 722 and approver.apvrResult }">
 									<img width="70" height="70" src="/file/sign/${ approver.staffDTO.staffSignDTO.attachmentDTO.savedName }" />
 								</c:when>
 								
 								<c:otherwise>
		 							${ approver.staffDTO.jobDTO.jobDetail } ${ approver.staffDTO.staffName }							
 								</c:otherwise>
 							</c:choose>
 						</div>
 					</div>
						
					</c:if>
				</c:forEach>
			
				<div style="width: 100px; height: 100px; border-left: 2px solid #686868;">
					<div style="height: 30px; border-bottom: 2px solid #686868;">
						<p class="d-flex justify-content-between" style="width: 50px; margin: 0 auto; padding: 0;"><span>기</span><span>안</span></p>
					</div>
					
					<div class="d-flex justify-content-center align-items-center" style="height: 70px;">
						<img width="70" height="70" src="/file/sign/${ approval.staffDTO.staffSignDTO.attachmentDTO.savedName }" />
					</div>
				</div>
				
			</div>
		</td>
	</tr>
	
	<tr class="hard-line">
		<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>문</span><span>서</span><span>번</span><span>호</span></p></td>
		<td colspan="4">${ approval.aprvCode }</td>
	</tr>
	
	<tr class="hard-line">
		<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>기</span><span>안</span><span>일</span><span>자</span></p></td>
		<td colspan="4">${ approval.aprvDate }</td>
	</tr>
	
	<tr class="hard-line">
		<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>시</span><span>행</span><span>일</span></p></td>
		<td colspan="4">${ approval.aprvExe }</td>
	</tr>
	
	<tr>
		<td rowspan="2" class="hard-line" style="width: 200px;"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>기</span><span>안</span><span>자</span></p></td>
		<td class="hard-side-line" style="width: 100px;"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>사</span><span>원</span><span>번</span><span>호</span></p></td>
		<td style="width: 200px;">${ approval.staffDTO.staffCode }</td>
		<td class="hard-side-line" style="width: 100px;"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>부</span><span>서</span></p></td>
		<td style="width: 200px;">${ approval.staffDTO.deptDTO.deptDetail }</td>
	</tr>
	
	<tr>
		<td class="hard-side-line"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>직</span><span>급</span></p></td>
		<td>${ approval.staffDTO.jobDTO.jobDetail }</td>
		<td class="hard-side-line"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>성</span><span>명</span></p></td>
		<td>${ approval.staffDTO.staffName }</td>
	</tr>
	
	<tr class="hard-line">
		<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>제</span><span>목</span></p></td>
		<td colspan="4">${ approval.aprvTitle }</td>
	</tr>
	
	<tr style="border: 2px solid #686868;">
		<td colspan="5" style="height: 600px; text-align: left; vertical-align: top; padding: 5px;">${ approval.aprvContent }</td>
	</tr>
	
	<tr>
		<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>첨</span><span>부</span><span>파</span><span>일</span></p></td>
		<td colspan="4" style="text-align: left;">
			<ul class="list-unstyled">
				
				<c:forEach var="attach" items="${ approval.approvalAttachmentDTOs }">
					<li><a href="/approval/${ attach.attachmentDTO.attachNum }/download">${ attach.attachmentDTO.originName }</a></li>
				</c:forEach>
				
			</ul>
		</td>
	</tr>
</table>