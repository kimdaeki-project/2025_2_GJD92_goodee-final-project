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
      <h2>분실물 등록</h2>
      <form action="/lost/write" method="post" enctype="multipart/form-data">
        
        <div class="form-group">
          <label for="itemName">분실물명</label>
          <input type="text" id="itemName" name="lostName" required>
        </div>
        
        <div class="form-group">
          <label for="owner">습득자</label>
          <input type="text" id="owner" name="lostFinder" required>
        </div>
        
        <div class="form-group">
          <label for="ownerPhone">습득자 전화번호</label>
          <input type="text" id="ownerPhone" name="lostFinderPhone" required>
        </div>
        
        <div class="form-group">
        <img id="preview" width="150" height="150" style="object-fit: cover;" class="border border-1 border-dark p-1" />
          <label for="file">사진첨부</label>
          <input type="file" id="file" name="attach">
        </div>
        
        <button type="submit" class="btn-submit">등록</button>
      </form>
    </div>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='분실물']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "분실물"
	</script>
</body>

</html>