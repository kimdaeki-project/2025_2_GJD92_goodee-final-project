<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication property="principal" var="staff"/>

<table class="mt-5 text-center" style="width: 100%">
	<tr class="hard-line" style="height: 100px;">
		<td colspan="2" class="hard-line">
			<p class="d-flex justify-content-between" style="width: 200px; margin: 0 auto;">
				<span style="font-size: 28px; font-weight: 700;">연</span>
				<span style="font-size: 28px; font-weight: 700;">장</span>
				<span style="font-size: 28px; font-weight: 700;">근</span>
				<span style="font-size: 28px; font-weight: 700;">무</span>
				<span style="font-size: 28px; font-weight: 700;">신</span>
				<span style="font-size: 28px; font-weight: 700;">청</span>
				<span style="font-size: 28px; font-weight: 700;">서</span>
			</p>
		</td>
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
		<td colspan="4"><input type="hidden" class="form-control" name="aprvTitle" value="연장 근무 신청서" />연장 근무 신청서</td>
	</tr>

	<tr class="hard-line">
		<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>근</span><span>무</span><span>시</span><span>간</span></p></td>
		<td class="hard-side-line" style="width: 100px;"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>시</span><span>작</span><span>시</span><span>간</span></p></td>
		<td style="width: 200px;"><input type="datetime-local" name="overStart" value="${ approval.overtimeDTO.overStart }"></td>
		<td class="hard-side-line" style="width: 100px;"><p class="d-flex justify-content-between" style="width: 70px; margin: 0 auto;"><span>종</span><span>료</span><span>시</span><span>간</span></p></td>
		<td style="width: 200px;"><input type="datetime-local" name="overEnd" value="${ approval.overtimeDTO.overEnd }"></td>
	</tr>
	
	<tr class="hard-line">
		<td class="hard-line"><p class="d-flex justify-content-between" style="width: 100px; margin: 0 auto;"><span>근</span><span>무</span><span>내</span><span>용</span></p></td>
		<td colspan="4">
			<input type="text" class="form-control" name="overReason" value="${ approval.overtimeDTO.overReason }" />
		</td>
	</tr>
	
	<tr style="border: 2px solid #686868;">
		<td colspan="5" style="height: 150px; text-align: center; vertical-align: middle;">
			<p>상기의 사유로 연장 근무를 신청합니다.</p>
		</td>
	</tr>
	
</table>