<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start"
	style="height: 92vh; min-width: 200px">
	<div class="w-100">
		<!-- 일정 목록 ,쓰기 버튼 시작 -->
		<ul class="navbar-nav">
			<li class="nav-item">
				<a href="/calendar/list" class="btn btn-dark w-100 mt-2 mb-3">일정 목록</a>
			</li>
			<li class="nav-item">
				<button type="button" class="btn btn-dark w-100 mb-2">일정 쓰기</button>
			</li>
		</ul>
		<!-- 일정 목록 ,쓰기 버튼 끝-->

		<hr class="my-1 bg-dark">

		<!-- 일정 분류 체크 박스 -->
		<!-- TODO 반복문으로 바꿀예정 -->
		<ul class="navbar-nav">
			<li class="nav-item">
				<input type="checkbox">
				<span>공휴일</span>
			</li>
			<li class="nav-item">
				<input type="checkbox"> 
				<span>점검 일정</span>
			</li>
			<li class="nav-item">
				<input type="checkbox"> 
				<span>사내 일정</span>
			</li>
			<li class="nav-item">
				<input type="checkbox"> 
				<span>우리 팀 일정</span>
			</li>
		</ul>
		<!-- 일정 분류 체크 박스 -->

	</div>
</aside>
