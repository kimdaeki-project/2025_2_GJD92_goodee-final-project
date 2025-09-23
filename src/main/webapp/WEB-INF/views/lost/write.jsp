<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style type="text/css">
		.form-box {
      background: #fff;
      padding: 40px;
      border-radius: 12px;
      box-shadow: 0 2px 6px rgba(0,0,0,0.1);
      width: 400px;
      text-align: center;
    }

    .form-box h2 {
      margin-bottom: 30px;
      font-size: 20px;
      font-weight: bold;
    }

    .form-group {
      margin-bottom: 20px;
      text-align: left;
    }

    .form-group label {
      display: block;
      margin-bottom: 8px;
      font-weight: 500;
      font-size: 14px;
    }

    .form-group input[type="text"],
    .form-group input[type="file"] {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 6px;
      font-size: 14px;
    }

    .form-group input[type="file"] {
      padding: 4px;
    }

    .btn-submit {
      background-color: #333;
      color: #fff;
      padding: 10px 30px;
      border: none;
      border-radius: 6px;
      font-size: 14px;
      cursor: pointer;
    }

    .btn-submit:hover {
      background-color: #555;
    }
	</style>
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
    
    <div class="form-box">
      <h2>${empty lostDTO.lostNum ? "분실물 등록" : "분실물 수정" }</h2>
      <form method="post" enctype="multipart/form-data">
        
        <img id="preview" width="300" height="300" style="object-fit: cover;" <c:if test="${ not empty lostDTO.lostNum }">src="/file/lost/${ lostDTO.lostAttachmentDTO.attachmentDTO.savedName }"</c:if> />
        <div class="form-group">
        <br>
          <label for="itemName">분실물명</label>
          <input type="text" name="lostName" value="${lostDTO.lostName }" required>
        </div>
        
        <div class="form-group">
          <label for="owner">습득자</label>
          <input type="text" name="lostFinder" value="${lostDTO.lostFinder }" required>
        </div>
        
        <div class="form-group">
          <label for="ownerPhone">습득자 전화번호</label>
          <input type="text" name="lostFinderPhone" value="${lostDTO.lostFinderPhone }" required>
        </div>
        
        <div class="form-group">
          <label for="file">사진첨부</label>
          <input type="file" id="attach" name="attach">
        </div>
	        
        <button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">${ empty lostDTO.lostNum ? "등록" : "수정" }</button>
        <button type="button" class="btn btn-sm btn-outline-secondary" onclick="history.back();" style="width: 100px;">취소</button>
      </form>
    </div>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/lost/write.js"></script>
	<script>
		document.querySelector("i[data-content='분실물']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "분실물"
	</script>
</body>

</html>