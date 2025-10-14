<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>${empty productDTO.productCode ? "품목 등록" : "품목 수정" }</title>
	<style type="text/css">

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
      width: 80%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 6px;
      font-size: 14px;
    }

    .form-group input[type="file"] {
      padding: 4px;
    }

    aside.sidenav {
	width: 200px !important;   /* 원하는 값 (180~220px 정도 추천) */
	min-width: 200px !important;
	max-width: 200px !important;
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
    
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; width: 100%; overflow: hidden;">

		<div class="col-6 offset-3">

      <h4 class="text-center mt-5 mb-5">${empty productDTO.productCode ? "물품 등록" : "물품 수정" }</h4>
      <form method="post" enctype="multipart/form-data" class="d-flex flex-column mt-6" style="gap: 40px;">
      	<div class="d-flex justify-content-between" >
      	
      	<div style="flex: 1;">
			<div class="form-group">
	        	<img id="preview" width="300" height="300" style="object-fit: clip;" <c:if test="${ not empty productDTO.productCode }">src="/file/product/${ productDTO.productAttachmentDTO.attachmentDTO.savedName }"</c:if> />
	        </div>
	        
	        <div class="form-group">
<!-- 	          <label for="attach">사진첨부</label> -->
	          <input type="file" id="attach" name="attach">
	        </div>
        </div>
        
        <div style="flex: 1; display: flex; flex-direction: column; justify-content: center; height: 300px;">
        <div class="form-group">
        	<label for="productTypeCode">물품 타입</label>
        	<select class="form-select" id="productTypeCode" name="productTypeDTO.productTypeCode" style="width:80%; height:44px;">
				<option value="">--선택--</option>
        		<c:forEach items="${productTypeList }" var = "productType">
				<option value="${productType.productTypeCode }"
					<c:if test="${productType.productTypeCode == productDTO.productTypeDTO.productTypeCode}">
		                selected
		            </c:if>>
				${productType.productTypeName }</option>
				</c:forEach>
			</select>
        </div>
			
		<div class="form-group">
          <label for="productName">물품명</label>
          <input type="text" id="productName" name="productName" value="${productDTO.productName }" required>
		</div>
		
		<div class="form-group">
          <label for="productSpec">규격</label>
          <input type="text" id="productSpec" name="productSpec" value="${productDTO.productSpec }" required>
		</div>

      </div>
      </div>

		<div class="mt-4 d-flex justify-content-center gap-3">
	        <button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" style="width: 100px;">${ empty productDTO.productCode ? "등록" : "수정" }</button>
	        <button type="button" class="btn btn-sm btn-outline-secondary" onclick="history.back();" style="width: 100px;">취소</button>
	    </div>
      </form>
      </div>

    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/product/write.js"></script>
	<script>
		document.querySelector("i[data-content='재고']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='물품 리스트']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "물품 리스트"
	</script>
</body>