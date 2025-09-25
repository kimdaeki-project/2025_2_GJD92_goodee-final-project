<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication property="principal" var="staff"/>

<table class="mt-5 text-center" style="width: 100%">
	<tr class="hard-line" style="height: 100px;">
		<td colspan="2" class="hard-line">
			<p class="d-flex justify-content-between" style="width: 200px; margin: 0 auto;">
				<span style="font-size: 48px; font-weight: 700;">기</span>
				<span style="font-size: 48px; font-weight: 700;">안</span>
				<span style="font-size: 48px; font-weight: 700;">서</span>
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
		<td colspan="5" style="text-align: left;">
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