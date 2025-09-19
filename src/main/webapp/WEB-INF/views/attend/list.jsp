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
          <div class="card-header">이상근태 현황 <span class="float-end">9월</span></div>
          <div class="card-body">
            <div class="row text-center">
              <div class="col">지각<br><strong>1</strong></div>
              <div class="col">조퇴<br><strong>2</strong></div>
              <div class="col">결근<br><strong>1</strong></div>
            </div>
          </div>
        </div>

        <!-- 연차 현황 -->
        <div class="card mb-3">
          <div class="card-header">연차 현황</div>
          <div class="card-body text-center">
            <div class="row">
              <div class="col">잔여<br><strong>9</strong></div>
              <div class="col">사용<br><strong>3</strong></div>
              <div class="col">총 연차<br><strong>12</strong></div>
            </div>
          </div>
        </div>

        <!-- 근로시간 현황 -->
        <div class="card">
          <div class="card-header">근로시간 현황 <span class="float-end">09-15 ~ 09-21</span></div>
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
    <input type="hidden" name="staffCode" value="${staffCode}" />

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

    <button type="submit">조회</button>
</form>
          
          <div class="card-body">
            <table class="table table-bordered text-center align-middle">
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
              <c:forEach items="attendances" var="attend">
	                <tr>
	                  <td>${attend.attendNum }</td>
	                  <td>08:57:22</td>
	                  <td>18:02:00</td>
	                  <td>09h 04m</td>
	                  <td>09h 04m</td>
	                  <td>지각</td>
	                  <td>-</td>
	                </tr>
                </c:forEach>
              </tbody>
            </table>

            <!-- 페이징 -->
            <nav>
              <ul class="pagination justify-content-center">
                <li class="page-item disabled"><a class="page-link">&lt;</a></li>
                <li class="page-item active"><a class="page-link">1</a></li>
                <li class="page-item"><a class="page-link">&gt;</a></li>
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