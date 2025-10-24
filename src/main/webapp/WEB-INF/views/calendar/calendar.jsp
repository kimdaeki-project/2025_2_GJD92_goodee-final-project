<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>캘린더</title>
	
	<link rel="stylesheet" href="/css/calendar/calendar.css"/>
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <div class="d-flex">
		<c:import url="/WEB-INF/views/calendar/calendar-sidebar.jsp"></c:import>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden;">
	    	<input type="hidden" id="calNum" value="">
<!-- 	    	<div class="d-flex justify-content-end mt-3 me-3"> -->
<!-- 	    		<div>이쪽은 검색란</div>	    	 -->
<!-- 	    	</div> -->
	    	<div id='calendar' class="mt-3"></div>
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
		    <!-- 여기에 코드 작성 -->
	    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<c:import url="/WEB-INF/views/calendar/calendar-modal-add.jsp"></c:import>
	<c:import url="/WEB-INF/views/calendar/calendar-modal-detail.jsp"></c:import>
	<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.19/index.global.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/dayjs@1/dayjs.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@fullcalendar/google-calendar@6.1.11/index.global.min.js"></script>
	<script>
		document.querySelector("i[data-content='일정']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "일정";
		const loginStaffCode  = ${ staffDTO.staffCode }		
	</script>
	<script src="/js/calendar/calendar.js"></script>
	
</body>

</html>