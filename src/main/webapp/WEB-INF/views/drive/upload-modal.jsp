<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- 문서 업로드 모달 -->
<div class="modal fade" id="uploadModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<form action="/drive/${ driveDTO.driveNum }/upload" method="post" id="uploadFrm" enctype="multipart/form-data" class="w-100">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title">문서 업로드</h5>
					<button type="button" class="btn-close btn-close-white bg-black" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>

				<div class="modal-body">
					<!-- Drag & Drop 업로드 영역 -->
					<div id="dropZone" class="border border-2 border-dashed rounded d-flex flex-column justify-content-center align-items-center p-4 text-secondary"
						style="cursor: pointer; min-height: 150px;">
						<span class="material-symbols-rounded fs-1 mb-2">upload_file</span>
						<p class="mb-0">파일을 이 영역에 드래그하거나 클릭하여 업로드</p>
						<input type="file" id="fileInput" name="attach" class="d-none">
						<div id="fileName" class="mt-2 small text-muted"></div>
					</div>

					<div class="mt-4">
						<label for="jobCode" class="form-label">다운로드 권한</label>
						<select name="jobCode" id="jobCode" class="form-select">
							<option value="1202">전체</option>
							<c:if test="${ not empty jobList }">
								<c:forEach items="${ jobList }" var="job">
									<option value="${ job.jobCode }">${ job.jobDetail }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>

				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">업로드</button>
				</div>
			</div>
		</form>
	</div>
</div>