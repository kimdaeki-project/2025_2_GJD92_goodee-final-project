<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    	<sec:authentication property="principal" var="staff" />
    
    	<div class="col-6 offset-3 mt-5 d-flex justify-content-center align-items-center">
    		<div class="col-4">
    			<img width="150" height="150" style="object-fit: cover; border: 1px solid #686868; padding: 5px;" alt="Staff Profile" src="/file/staff/${ staff.staffAttachmentDTO.attachmentDTO.savedName }">
    		</div>
    	</div>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
</body>

</html>