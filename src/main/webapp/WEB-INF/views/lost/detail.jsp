<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>분실물 상세</title>
	<style type="text/css">

	</style>
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden;">
    
    <div class="col-6 offset-3">
    
      <h4 class="text-center mt-5 mb-5">분실물 상세</h4>
      
      <div class="d-flex flex-column mt-6" style="gap: 80px;">
      <div class="d-flex justify-content-between" >
      <div style="flex: 1;">
      
	      <div>
	      <div class="mb-2">관리번호: ${lostDTO.lostNum }</div>
	      	<img width="400" height="400" style="object-fit: cover;" src="/file/lost/${ lostDTO.lostAttachmentDTO.attachmentDTO.savedName }"/>
	      </div>
      
      </div>
      
      <div style="flex: 1; display: flex; flex-direction: column; justify-content: space-evenly; height: 450px; font-size: 25px;">
      <table>
      	<tbody>
<!--       		<tr> -->
<!--       			<td>분실물번호</td> -->
<%--       			<td>${lostDTO.lostNum }</td> --%>
<!--       		</tr> -->
      		<tr>
      			<td>분실물명</td>
      			<td>${lostDTO.lostName }</td>
      		</tr>
      		<tr>
      			<td>등록일자</td>
      			<td>${lostDTO.lostDate }</td>
      		</tr>
      		<tr>
      			<td>습득자</td>
      			<td>${lostDTO.lostFinder }</td>
      		</tr>
      		<tr>
      			<td>습득자 연락처</td>
      			<td>${lostDTO.lostFinderPhone }</td>
      		</tr>
      		<tr>
      			<td>특이사항</td>
      			<td>${lostDTO.lostNote }</td>
      		</tr>
      		
      		<tr>
      		<td> </td>
      		</tr>
      		
      		<tr>
      			<td>담당자</td>
      			<td>${lostDTO.staffDTO.staffName }</td>
      		</tr>
      		<tr>
      			<td>담당자 연락처</td>
      			<td>${lostDTO.staffDTO.staffPhone }</td>
      		</tr>
      		
      	</tbody>
      </table>  
      </div>
	  </div>
	  
      <div class="mt-4 d-flex justify-content-center gap-2">
	      <button onclick="location.href='/lost/${lostNum}/update'" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">수정</button>
	      <form action="/lost/${lostNum }/delete" method="post">
		      <button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">삭제</button>
		  </form>
	  </div>
	       
	      <div class="d-flex justify-content-end">
		      <button onclick="location.href='/lost'" class="btn btn-sm btn-outline-secondary">목록</button>
	      </div> 
	  </div>
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