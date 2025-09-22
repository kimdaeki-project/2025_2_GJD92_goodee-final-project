<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style type="text/css">
		.sidenav .nav-link {
  white-space: nowrap; /* 줄바꿈 방지 */
}
aside.sidenav {
  width: 200px !important;   /* 원하는 값 (180~220px 정도 추천) */
  min-width: 200px !important;
  max-width: 200px !important;
}
	
	</style>
	<!-- 부트스트랩 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- 부트스트랩 JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
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
			        <a class="nav-link text-dark" href="/product/manage">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="물품관리대장">remove_shopping_cart</i>
			          <span class="nav-link-text ms-1 text-sm">물품관리대장</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
    	
	    <section class="flex-grow-1 border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh ; overflow: auto;">
	    
	    <div class="d-flex justify-content-end align-items-end">
			<div class="input-group">
				<input type="text" class="form-control" id="searchText" value="${ requestScope.search }" style="width: 200px; height: 30px; border-radius: 0.375rem 0 0 0.375rem !important;" >
				<button class="btn btn-outline-secondary p-0 m-0" type="button" onclick="movePage()" style="width: 50px; height: 30px;" >검색</button>
			</div>
		</div>
		
	    <div class="mt-3" style="min-height: 500px;">
			    	<div class="col-10 offset-1">
			    		<table class="table text-center">
			    			<thead>
			    				<tr>
			    					<th class="col-1">물품번호</th>
			    					<th class="col-2">물품타입</th>
			    					<th class="col-3">물품명</th>
			    					<th class="col-2">수량</th>
			    				</tr>
			    			</thead>
			    			<tbody>
			    				
			    				<c:forEach var="product" items="${ productList.content }">
			    					<tr>
				    					<td>${ product.productCode }</td>
				    					<td>${ product.productTypeDTO.productTypeName}</td>
				    					<td><a href="/product/${ product.productCode }" style="color: #737373;">${ product.productName }</a></td>
				    					<td>${ product.productAmount }</td>
			    					</tr>
			    				</c:forEach>
			    				
			    			</tbody>
			    		</table>
			    	</div>
			    </div>
			    
			    <c:if test="${ totalProduct eq 0 }">
					<div>검색된 결과가 없습니다.</div>
				</c:if>
			    
	    <div class="d-flex justify-content-center aling-items-center">
			    	<nav aria-label="Page navigation example">
						  <ul class="pagination">
						    <c:if test="${ productList.number - 2 ge 0 }">
							    <li class="page-item">
							      <a class="page-link" onclick="movePage('${ productList.number - 2 }')" aria-label="Previous">
							        <span aria-hidden="true">&laquo;</span>
							      </a>
							    </li>
						    </c:if>
						    <c:if test="${ productList.hasPrevious() }">
							    <li class="page-item"><a class="page-link" onclick="movePage('${ productList.number - 1 }')">${ productList.number }</a></li>
						    </c:if>
						    <li class="page-item"><a class="page-link active" onclick="movePage('${ productList.number }')">${ productList.number + 1 }</a></li>
						    <c:if test="${ productList.hasNext() }">
							    <li class="page-item"><a class="page-link" onclick="movePage('${ productList.number + 1 }')">${ productList.number + 2 }</a></li>
						    </c:if>
						    <c:if test="${ productList.number + 2 le productList.totalPages - 1 }">
							    <li class="page-item">
							      <a class="page-link" onclick="movePage('${ productList.number + 2 }')" aria-label="Next">
							        <span aria-hidden="true">&raquo;</span>
							      </a>
							    </li>
						    </c:if>
						  </ul>
						</nav>
			    </div>
				
<!-- 			    <button class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" onclick="location.href='/product/write'">등록</button> -->

					<!-- 모달 버튼 -->
<button type="button" class="btn btn-sm btn-primary bg-gradient-dark text-white me-3" data-toggle="modal" data-target="#exampleModal">
  물품 등록
</button>

<!-- 모달 -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2>${empty productDTO.productCode ? "물품 등록" : "물품 수정" }</h2>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       <form method="post"
      action="${empty productDTO.productCode ? '/product/write' : '/product/update'}"
      enctype="multipart/form-data">
       <img id="preview" width="300" height="300" style="object-fit: cover;" <c:if test="${ not empty productDTO.productCode }">src="/file/product/${ productDTO.productAttachmentDTO.attachmentDTO.savedName }"</c:if> />
        
          <div class="form-group">
            <label for="productName">물품명</label>
          <input type="text" class="form-control" id="productName" name="productName" value="${productDTO.productName }" required>
          </div>
          <div class="form-group">
            <label for="productType">물품타입</label>
            <select name="productTypeDTO.productTypeCode">
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
<!--           <div class="form-group"> -->
<!--             <label for="productImage">사진 첨부</label> -->
<!--             <input type="file" class="form-control" id="productImage" name="productImage"> -->
<!--           </div> -->
          
          <div class="form-group">
          <label for="file">사진첨부</label>
          <input type="file" class="form-control" id="file" name="attach">
        </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-sm btn-primary bg-gradient-dark text-white me-3" style="width: 100px;">${ empty productDTO.productCode ? "등록" : "수정" }</button>
        <button type="button" class="btn btn-sm btn-outline-secondary" data-dismiss="modal";" style="width: 100px;">취소</button>
      </div>
      </form>
      </div>
    </div>
  </div>
</div>
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/product/list.js"></script>
	<script>
		document.querySelector("i[data-content='재고']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='물품 리스트']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "재고"
	</script>
</body>

</html>