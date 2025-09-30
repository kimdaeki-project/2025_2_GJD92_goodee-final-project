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
    aside.sidenav {
	width: 200px !important;   /* 원하는 값 (180~220px 정도 추천) */
	min-width: 200px !important;
	max-width: 200px !important;
	}
	.updateDeletebtns {
		display: flex;
	}
	</style>
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    
    <div class="d-flex">
    <aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="width: 200px; height: 92vh;">
    		<div class="w-100">
			    <ul class="navbar-nav">
			    
			    	<li class="nav-item">
			        <a class="nav-link text-dark" href="/product">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="물품 리스트">remove_shopping_cart</i>
			          <span class="nav-link-text ms-1 text-sm">물품 리스트</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/productManage">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="물품관리대장">remove_shopping_cart</i>
			          <span class="nav-link-text ms-1 text-sm">물품관리대장</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
    
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
    
    <div class="form-box">
      <h2>분실물 상세</h2>
      
      <img width="300" height="300" style="object-fit: cover;" src="/file/product/${ productDTO.productAttachmentDTO.attachmentDTO.savedName }"/>
      
      <table>
      	<tbody>
      		<tr>
      			<td>분실물번호</td>
      			<td>${productDTO.productCode }</td>
      		</tr>
      		<tr>
      			<td>등록일자</td>
      			<td>${productDTO.productDate }</td>
      		</tr>
      		<tr>
      			<td>분실물명</td>
      			<td>${productDTO.productName }</td>
      		</tr>
      		<tr>
      			<td>수량</td>
      			<td>${productDTO.productAmount }</td>
      		</tr>
      	</tbody>
      </table>  
      
      <div class="updateDeletebtns">
	      <button onclick="location.href='/product/${productCode}/update'" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">수정</button>
	      <form action="/product/${productCode }/delete" method="post">
		      <button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">삭제</button>
		  </form>
	 </div>
        
      <button onclick="location.href='/product'" class="btn btn-sm btn-outline-secondary">목록</button>
    </div>
    
    </section>
   </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='재고']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='물품 리스트']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "재고"
	</script>
</body>

</html>