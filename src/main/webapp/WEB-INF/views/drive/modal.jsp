<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="modal fade" id="shareModal" data-bs-backdrop="static"
	data-bs-keyboard="false" tabindex="-1">
	<!-- (정중앙) -->
	<div class="modal-dialog modal-xl modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header border-bottom">
				<h5 class="modal-title fw-bold">공유 대상 설정</h5>
				<button type="button" class="btn-close btn-close-white bg-black" data-bs-dismiss="modal" aria-label="Close"></button>
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
								<button class="btn btn-light text-start dept-btn" data-team="전체">전체</button>
								<button class="btn btn-light text-start dept-btn" data-team="인사팀">인사팀</button>
								<button class="btn btn-light text-start dept-btn" data-team="운영팀">운영팀</button>
								<button class="btn btn-light text-start dept-btn" data-team="시설팀">시설팀</button>
							</div>
						</div>
					</div>

					<!-- 검색 -->
					<div class="col-12 col-lg-5 h-100">
						<div class="border rounded p-3 h-100 d-flex flex-column">
							<input type="text" id="searchInput" class="form-control mb-2" placeholder="이름, 직책, 소속 입력">
							<ul id="staffList" class="list-group flex-grow-1 overflow-auto">
							
							<!-- 사원 리스트 출력 -->
							<!-- 사원 리스트 출력 -->
							<!-- 사원 리스트 출력 -->
							
							</ul>
						</div>
					</div>

					<!-- 가운데: 추가 버튼(정중앙) -->
					<div class="col-12 col-lg-1 h-100 d-flex align-items-center justify-content-center">
						<button id="addBtn" class="btn btn-secondary">추가 →</button>
					</div>

					<!-- 오른쪽: 선택된 사용자 -->
					<div class="col-12 col-lg-3 h-100">
						<div class="border rounded p-3 h-100 d-flex flex-column">
							<ul id="selectedList" class="list-group flex-grow-1 overflow-auto">
							
							<!-- 추가된 사용자 여기에 출력 -->
							<!-- 추가된 사용자 여기에 출력 -->
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