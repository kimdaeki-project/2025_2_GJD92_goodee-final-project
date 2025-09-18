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
    
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3 p-4 shadow-sm" style="min-height: 90vh; overflow-y: auto;">

		<!-- 제목 -->
		<h4 class="fw-bold mb-3">${ notice.noticeTitle }</h4>
    	
    	<!-- 작성자 정보 -->
    	<div class="d-flex align-items-center mb-4">
    		<img width="60" height="60" class="rounded-circle border me-3" 
                 src="/file/staff/${ notice.staffDTO.staffAttachmentDTO.attachmentDTO.savedName }" alt="프로필">
    		<div>
    			<h5 class="mb-1 fw-bold">${ notice.staffDTO.staffName }</h5>
    			<small class="text-muted">작성일: ${ notice.noticeDate } | 조회수: ${ notice.noticeHits }</small>
    		</div>
    	</div>


		<!-- 내용 -->
		<div class="mb-4 p-3 border rounded bg-white" style="white-space: pre-line;">
			${ notice.noticeContent }
		</div>

		<!-- 첨부파일 -->
		<div class="mb-4">
			<h6 class="fw-bold">첨부파일</h6>
			<c:if test="${ notice.noticeAttachmentDTOs.size() gt 0 }">
				<ul class="list-group">
					<c:forEach items="${ notice.noticeAttachmentDTOs }" var="file">
						<li class="list-group-item d-flex justify-content-between align-items-center">
							<span>${ file.attachmentDTO.originName }</span>
							<a href="/notice/${ file.attachmentDTO.attachNum }/download" 
							   class="btn btn-sm btn-outline-primary">다운로드</a>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${ notice.noticeAttachmentDTOs eq null or notice.noticeAttachmentDTOs.size() eq 0 }">
				<div class="text-muted">첨부파일 없음</div>
			</c:if>
		</div>

		<!-- 버튼 영역 -->
		<div class="d-flex gap-2">
			<a href="/notice" class="btn btn-secondary">목록</a>
			<a href="/notice/${ notice.noticeNum }/edit" class="btn btn-warning">수정</a>
			<form action="/notice/${ notice.noticeNum }/delete" method="post" id="form"></form>
			<button id="btn-delete" class="btn btn-danger">삭제</button>
		</div>

		<!-- 스크립트 -->
		<script type="text/javascript" src="/js/notice/detail.js"></script>

    </section>
  </main>

	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='공지사항']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "공지사항"
	</script>
</body>

</html>