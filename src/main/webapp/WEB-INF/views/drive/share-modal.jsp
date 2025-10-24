<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="modal fade" id="shareModal" data-bs-backdrop="static"
	data-bs-keyboard="true" tabindex="-1">
	<!-- (정중앙) -->
	<div class="modal-dialog modal-xl modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header border-bottom">
				<h5 class="modal-title fw-bold">공유 대상 설정</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<!-- (높이 +100px) -->
			<div class="modal-body bg-color" style="height: 500px;">
				<div class="row g-3 align-items-stretch h-100">

					<!-- 왼쪽: 팀 목록 -->
					<div class="col-12 col-lg-3 h-100">
						<div class="border rounded p-3 h-100 d-flex flex-column">
							<div class="d-flex justify-content-start mb-4 ms-1">
								<span class="fw-bold ms-1">구디월드</span>
							</div>
							<div class="d-grid gap-1">
								<!-- DB에서 가져온 부서 목록-->
								<button class="btn btn-primary bg-gradient-dark text-white text-start deptBtn" data-team="전체">전체</button>
								<c:forEach items="${ deptList }" var="dept">
									<c:choose>
										<c:when test="${ dept.deptDetail eq '임원' }">
											<button class="btn btn-primary bg-gradient-dark text-white text-start deptBtn" data-team="${ dept.deptDetail }">${ dept.deptDetail }</button>
										</c:when>
										<c:otherwise>
											<button class="btn btn-primary bg-gradient-dark text-white text-start deptBtn" data-team="${ dept.deptDetail }">${ dept.deptDetail }팀</button>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</div>
					</div>

					<!-- 검색 -->
					<div class="col-12 col-lg-5 h-100">
						<div class="border rounded p-3 h-100 d-flex flex-column">
							<div>
								<input type="text" id="searchInput" class="form-control mb-2" placeholder="이름, 직책 입력">
							</div>
							<div class="border rounded mb-2 d-flex align-items-center">
								<input type="checkbox" id="checkAllStaff" class="ms-3 me-1">
								<label for="checkAllStaff" class="my-1"><span id="allStaffCheckboxName" style="font-weight: 500;">전체</span></label>
							</div>
							<ul id="staffList" class="list-group flex-grow-1 overflow-auto">
							
							<!-- 사원 리스트 출력 -->
							
							</ul>
						</div>
					</div>
					

					<!-- 가운데: 추가 버튼(정중앙) -->
					<div class="col-12 col-lg-1 h-100 d-flex align-items-center justify-content-center flex-column">
						<button class="btn btn-primary bg-gradient-dark text-white text-start dept-btn" id="btnMoveToRight">추가 →</button>
						<button class="btn btn-primary bg-gradient-dark text-white text-start dept-btn " id="btnClearList">← 제거</button>
					</div>

					<!-- 오른쪽: 선택된 사용자 -->
					<div class="col-12 col-lg-3 h-100">
						<div class="border rounded p-3 h-100 d-flex flex-column">
							<ul id="selectedList" class="list-group flex-grow-1 overflow-auto">
							
							<!-- 추가된 사용자 여기에 출력 -->
							
							</ul>
						</div>
					</div>
				</div>
			</div>

			<!-- 저장 취소버튼 -->
			<div class="modal-footer border-top">
				<button type="button" class="btn btn-outline-secondary bg-gradient-dark" id="saveStaffBtn">저장</button>
				<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>