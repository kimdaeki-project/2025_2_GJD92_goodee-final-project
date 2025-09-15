<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden scroll;">
    
		<form method="post" id="form">
			<div>
				<label for="noticeTitle">제목</label>
				<input type="text" id="noticeTitle" name="noticeTitle" value="${ notice.noticeTitle }">
			</div>
			<div>
				<label for="noticeContent">내용</label>
				<textarea rows="10" cols="20" id="noticeContent" name="noticeContent">${ notice.noticeContent }</textarea>
			</div>
			<div>
				<label for="noticePinned">상단고정</label>
				<input type="checkbox" id="noticePinned" name="noticePinned">
			</div>
			<c:if test="${ notice ne null }">
				<input type="hidden" name="noticeNum" value="${ notice.noticeNum }">
			</c:if>
		</form>
		<c:if test="${ notice eq null }">
			<button id="btn-write" data-kind="write">등록</button>
		</c:if>
		<c:if test="${ notice ne null }">
			<button id="btn-write" data-kind="edit">수정</button>
		</c:if>
		<script type="text/javascript" src="/js/notice/write.js"></script>	
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='공지사항']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>