<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 상세보기 모달 -->
<div class="modal fade" id="calendarDetailModal" tabindex="-1"
	aria-labelledby="viewEventTitle" aria-hidden="true">
	<!-- 가운데 정렬 + 내부 스크롤 -->
	<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
		<div class="modal-content rounded-4">
			<div class="modal-header border-0">
				<!-- 제목 -->
				<h5 class="modal-title fw-semibold" id="detailModalTitle"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
			</div>

			<div class="modal-body pt-0">
				<!-- 날짜 -->
				<div class="mb-2">
					<div class="text-secondary" id="detailModalDate"></div>
				</div>

				<!-- 타입 -->
				<div class="d-flex align-items-center gap-2 mb-2">
					<span class="d-inline-block rounded-circle" id="detailDeptCircle" style="width: 12px; height: 12px;" aria-hidden="true"></span>
					<span class="text-secondary" id="detailModalDept"></span>
				</div>

				<!-- 장소 -->
				<div class="d-flex align-items-center gap-2 mb-2">
					<span class="text-secondary" id="detailModalPlace"></span>
				</div>

				<!-- 내용 -->
				<div class="mb-4">
					<div class="text-secondary mb-2">내용</div>
					<!-- 회색 느낌: Bootstrap 배경 유틸 + 비활성 폼 -->
					<textarea id="detailModalContent" class="form-control bg-body-tertiary border-0 text-body" 
					rows="4" style="resize: none;" readonly></textarea>
				</div>

				<!-- 등록자/일시 -->
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center gap-2">
						<span class="text-secondary">등록자</span> 
						<span class="material-symbols-rounded align-middle">person</span> 
						<span id="detailModalWriter">박요한</span>
					</div>
					<div class="text-secondary">
						<span id="detailModalReg"></span>
					</div>
				</div>

				<!-- 수정일 -->
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center gap-2">
						<span class="text-secondary">수정일</span>
					</div>
					<div class="text-secondary">
						<span id="detailModalMod"></span>
					</div>
				</div>
			</div>

			<!-- 푸터 -->
			<div class="modal-footer border-0 justify-content-end">
				<button type="button" class="btn btn-outline-secondary btn-sm d-flex align-items-center btn-update-delete" id="btnOpenUpdateModal">
					<span class="material-symbols-rounded fs-6">edit</span>
				</button>
				<button type="button" class="btn btn-outline-secondary btn-sm d-flex align-items-center btn-update-delete" id="btnDeleteCalendar">
					<span class="material-symbols-rounded fs-6">delete</span>
				</button>
			</div>
		</div>
	</div>
</div>
