<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>공지사항</title>
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3 p-4" style="height: 90vh; overflow: hidden scroll;">
    
		<form method="post" id="form" enctype="multipart/form-data" class="container" style="max-width: 1000px;">

			<!-- 제목 + 상단 고정 (라벨 왼쪽, 체크박스 오른쪽 끝) -->
			<div class="mb-3">
			    <div class="d-flex justify-content-between align-items-center mb-2">
			        <label for="noticeTitle" class="fw-bold">제목</label>
			        <div class="form-check">
			            <input class="form-check-input" type="checkbox" id="noticePinned" name="noticePinned"
			                ${ notice.noticePinned ? "checked" : "" }>
			            <label class="form-check-label fw-bold" for="noticePinned">상단 고정</label>
			        </div>
			    </div>
			    <input type="text" class="form-control" id="noticeTitle" name="noticeTitle"
			           value="${ notice.noticeTitle }">
			</div>

			<!-- 첨부파일 -->
			<div class="mb-4">
			    <label class="fw-bold d-block mb-2">첨부파일</label>
			
			    <!-- 내 PC 버튼 왼쪽 정렬 -->
			    <div class="mb-2">
			        <button id="pcBtn" type="button" class="btn btn-dark btn-sm">내 PC</button>
			        <input id="fileInput" type="file" multiple hidden name="files" />
			    </div>
			
			    <!-- 파일 리스트 박스 (form-control 대신 커스텀 박스) -->
			    <div id="fileList" class="border rounded p-3 text-center text-muted bg-white" 
			         style="min-height: 70px;">
			        선택된 파일이 없습니다.
			    </div>
			</div>

			<!-- 본문 -->
			<div class="mb-4">
				<label for="noticeContent" class="fw-bold d-block mb-2">본문</label>
				<textarea rows="10" class="form-control" id="noticeContent" 
						  name="noticeContent" style="resize: none;">${ notice.noticeContent }</textarea>
			</div>

			<c:if test="${ notice ne null }">
				<input type="hidden" name="noticeNum" value="${ notice.noticeNum }">
				<input type="hidden" name="deleteFiles" id="deleteFiles">
			</c:if>

		</form>
		<!-- 버튼 -->
		<div class="d-flex justify-content-end gap-2 mt-4" style="max-width:1000px; margin:0 auto;">
			<c:if test="${ notice eq null }">
				<button id="btn-write" data-kind="write" class="btn btn-dark px-4">등록</button>
			</c:if>
			<c:if test="${ notice ne null }">
				<button id="btn-write" data-kind="edit" class="btn btn-dark px-4">수정</button>
			</c:if>
			<button type="reset" class="btn btn-outline-secondary px-4" id="btn-cancel">취소</button>
		</div>

		<script>
		    window.existingFiles = window.existingFiles || [];
		    <c:forEach items="${ notice.noticeAttachmentDTOs }" var="file">
		        window.existingFiles.push({
		            name: "${ file.attachmentDTO.originName }",
		            size: ${ file.attachmentDTO.attachSize },
		            attachNum: ${ file.attachmentDTO.attachNum },
		            url: '/file/notice/${ file.attachmentDTO.savedName }'
		        });
		    </c:forEach>
		</script>
		<script type="text/javascript" src="/js/notice/write.js"></script>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<c:import url="/WEB-INF/views/common/heartBeat.jsp"/>
	<script>
		document.querySelector("i[data-content='공지사항']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "공지사항"
	</script>
</body>

</html>