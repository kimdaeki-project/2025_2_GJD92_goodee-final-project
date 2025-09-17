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
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden scroll;">
    
		<div>
			제목: ${ notice.noticeTitle }
		</div>
		<div>
			작성일: ${ notice.noticeDate }
		</div>
		<div>
			내용: ${ notice.noticeContent }
		</div>
		<div>
			작성자: ${ notice.staffDTO.staffName }
		</div>
		<br>
		<div>
			<c:forEach items="${ notice.noticeAttachmentDTOs }" var="file">
				<div>${ file.attachmentDTO.originName }<a href="/notice/${ file.attachmentDTO.attachNum }/download">다운로드</a></div>
			</c:forEach>
		</div>
		<br>
		<div>
			<a href="/notice">목록</a>
		</div>
		<div>
			<a href="/notice/${ notice.noticeNum }/edit">수정</a>
		</div>
		<div>
			<form action="/notice/${ notice.noticeNum }/delete" method="post" id="form"></form>
			<button id="btn-delete">삭제</button>
		</div>
		<%-- 서버에서 가져온 파일 정보로 JS 배열 생성 --%>
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