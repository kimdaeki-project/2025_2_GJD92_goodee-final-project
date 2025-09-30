<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- 결재선 지정 모달 -->
	<div class="modal fade" id="receiveModal" data-bs-backdrop="static" data-bs-keyboard="true" tabindex="-1">
	<!-- (정중앙) -->
	<div class="modal-dialog modal-xl modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header border-bottom">
				<h5 class="modal-title fw-bold">수신자 추가</h5>
				<button type="button" class="btn-close btn-close-white bg-black" data-bs-dismiss="modal"></button>
			</div>

			<div class="modal-body bg-color" style="height: 500px;">
				<div class="row g-3 align-items-stretch h-100">

					<div class="col-12 col-lg-3 h-100">
						<div class="border rounded p-3 h-100 d-flex flex-column">
							<div class="d-grid gap-1">
								<button class="btn btn-primary bg-gradient-dark text-white text-start dept-btn-receive" data-team="전체">전체</button>
								<c:forEach items="${ deptList }" var="dept">
									<c:if test="${ dept.deptDetail eq '임원' }">
										<button class="btn btn-primary bg-gradient-dark text-white text-start dept-btn-receive" data-team="${ dept.deptDetail }">${ dept.deptDetail }</button>
									</c:if>
									
									<c:if test="${ dept.deptDetail ne '임원' }">
										<button class="btn btn-primary bg-gradient-dark text-white text-start dept-btn-receive" data-team="${ dept.deptDetail }">${ dept.deptDetail }팀</button>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>

					<div class="col-12 col-lg-5 h-100">
						<div class="border rounded p-3 h-100 d-flex flex-column">
							<input type="text" id="receiveSearchInput" class="form-control mb-2" placeholder="이름, 직책 입력">
							<ul id="receiveStaffList" class="list-group flex-grow-1 overflow-auto">
							
							
							</ul>
						</div>
					</div>

					<div class="col-12 col-lg-1 h-100 d-flex flex-column align-items-center justify-content-center">
						<button id="receiveBtn" class="btn btn-secondary bg-gradient-dark text-white mt-5">추가 →</button>
					</div>

					<div class="col-12 col-lg-3 h-100">
						<div class="border rounded p-3 d-flex flex-column" style="height: 100%;">
							<ul id="receiveList" class="list-group flex-grow-1 overflow-auto">
							
							
							</ul>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-footer border-top">
				<button type="button" class="btn btn-outline-secondary bg-gradient-dark" id="saveReceiveBtn">저장</button>
				<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>