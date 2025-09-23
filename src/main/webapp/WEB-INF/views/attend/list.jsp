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
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
    
    <div class="container-fluid">
    <div class="row">
      <!-- 왼쪽 카드 영역 -->
      <div class="col-md-4">
        <!-- 이달 근태 현황 -->
        <div class="card mb-3">
          <div class="card-header">이상근태 현황 <span class="float-end"><small>${month }월</small></span></div>
          
          <div class="card-body">
            <div class="row text-center">
              <div class="col">지각<br><strong>${lateCount }</strong></div>
              <div class="col">조퇴<br><strong>${earlyLeaveCount }</strong></div>
              <div class="col">결근<br><strong>1</strong></div>
            </div>
          </div>
        </div>

        <!-- 연차 현황 -->
        <div class="card mb-3">
          <div class="card-header">연차 현황</div>
          <div class="card-body text-center">
            <div class="row">
              <div class="col">잔여<br><strong>${staffDTO.staffRemainLeave - staffDTO.staffUsedLeave }</strong></div>
              <div class="col">사용<br><strong>${staffDTO.staffUsedLeave }</strong></div>
              <div class="col">총 연차<br><strong>${staffDTO.staffRemainLeave }</strong></div>
            </div>
          </div>
        </div>

        <!-- 근로시간 현황 -->
        <div class="card">
          <div class="card-header">근로시간 현황 <span class="float-end"><i class="material-symbols-rounded me-2 text-lg">date_range</i>
                  <small>9/22 - 9/28</small>
                </span></div>
          <div class="card-body">
            <p>주 근로시간 : 40h 00m</p>
            <p>누적 근로시간 : 00h 00m</p>
            <p>잔여 근로시간 : 00h 00m</p>
            <p>연장 근로시간 : 00h 00m</p>
          </div>
        </div>
      </div>

      <!-- 오른쪽 출퇴근 내역 -->
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">출퇴근 내역
          <form action="${pageContext.request.contextPath}/attend/monthly" method="get">
    <input type="hidden" name="staffCode" value="${staffDTO.staffCode}" />

    <label for="year">년도</label>
    <select name="year" id="year">
        <c:forEach var="y" begin="2020" end="2030">
            <option value="${y}" <c:if test="${y == year}">selected</c:if>>${y}</option>
        </c:forEach>
    </select>

    <label for="month">월</label>
    <select name="month" id="month">
        <c:forEach var="m" begin="1" end="12">
            <option value="${m}" <c:if test="${m == month}">selected</c:if>>${m}월</option>
        </c:forEach>
    </select>

    <button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">조회</button>
</form>
          
          <div class="card-body">
            <table class="table text-center align-middle">
              <thead class="table-light">
                <tr>
                  <th>날짜</th>
                  <th>출근시간</th>
                  <th>퇴근시간</th>
                  <th>실제근로시간</th>
                  <th>총근로시간</th>
                  <th>출퇴근상태</th>
                  <th>근무상태</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${attendances.content }" var="attend">
	                <tr>
	                  <td>${attend.attendDate }</td>
	                  <td>${attend.attendIn eq null ? "--:--:--" : attend.formattedAttendIn}</td>
	                  <td>${attend.attendOut eq null ? "--:--:--" : attend.formattedAttendOut}</td>
	                  <td>${attend.workTime }</td>
	                  <td>${attend.totalWorkTime }</td>
	                  <td>${attend.attendStatus }</td>
	                  <td>${attend.workStatus }</td>
	                </tr>
                </c:forEach>
              </tbody>
            </table>
            
             <!-- 페이징 -->
            <nav>
  <ul class="pagination justify-content-center">
    <!-- 이전 페이지 -->
    <li class="page-item ${attendances.hasPrevious() ? '' : 'disabled'}">
      <a class="page-link" href="?page=${attendances.number - 1}&year=${year}&month=${month}">&lt;</a>
    </li>
    
    <!-- 페이지 번호 -->
    <c:forEach var="i" begin="0" end="${attendances.totalPages - 1}">
      <li class="page-item ${i == attendances.number ? 'active' : ''}">
        <a class="page-link" href="?page=${i}&year=${year}&month=${month}">${i + 1}</a>
      </li>
    </c:forEach>
    
    <!-- 다음 페이지 -->
    <li class="page-item ${attendances.hasNext() ? '' : 'disabled'}">
      <a class="page-link" href="?page=${attendances.number + 1}&year=${year}&month=${month}">&gt;</a>
    </li>
  </ul>
</nav>

          </div>
        </div>
      </div>
    </div>
  </div>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='근태']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "근태"
	</script>
</body>

</html>