<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Index</title>
	<style type="text/css">
	.dashboard {
      flex: 1;
      display: flex;
      flex-direction: column;
    }
    
    .container {
      display: flex;
      height: 100vh;
    }
    
    .content {
      padding: 15px;
      display: grid;
      grid-template-columns: repeat(3, 1fr); /* 3열 균등 */
      grid-template-rows: auto auto;         /* 2행 */
      gap: 20px;
      overflow-y: auto;
      flex-grow: 1;
    }

    /* 첫 줄 */
    .check-in {
      grid-column: 1 / 2;
      grid-row: 1 / 2;
    }

    .attraction-status {
      grid-column: 2 / 3;
      grid-row: 1 / 2;
    }

    .weather {
      grid-column: 3 / 4;
      grid-row: 1 / 2;
    }

    /* 둘째 줄 */
    .approval {
      grid-column: 1 / 2;
      grid-row: 2 / 3;
    }

    .notices {
      grid-column: 2 / 4; /* 2~3열 합침 */
      grid-row: 2 / 3;
    }

    /* 각 패널 카드 */
    .panel {
      background-color: #fff;
      padding: 10px;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      font-size: 16px;
    }

    .panel h3 {
      /* border-bottom: 1px solid #eee; */
      padding-bottom: 5px;
      padding-left: 15px;
    }

    /* 체크인 카드 스타일 */
    .time-circle {
	  margin: 10px auto;
	  padding: 20px;
	  text-align: center;
	  border: 1px dashed #ccc;
	  border-radius: 50%;
	  width: 160px;
	  height: 160px;
	  display: flex;           /* flex로 변경 */
	  justify-content: center; /* 가운데 정렬 */
	  align-items: center;     /* 수직 중앙 정렬 */
	}
    .time-info {
	  text-align: center;
	  padding: 10px 0;
	}

	.times-row {
	  display: flex;
	  flex-direction: column; /* 세로로 나열 (한 줄에 두개면 row로 놔도됨) */
	  gap: 10px;
	  font-size: 12px;
	  line-height: 1.4;
	  width: 200px;
	}
	
	.worktime {
	  margin-bottom: 15px;
	  font-size: 20px;
	}
	
	.button-row {
	  display: flex;
	  justify-content: center;
	  gap: 10px;
	}
	
	.button-row button {
	  padding: 8px 16px;
	  background-color: #1976d2;
	  border: none;
	  color: white;
	  border-radius: 4px;
	  cursor: pointer;
	}

    .weather .icon {
      font-size: 48px;
      text-align: center;
      margin-bottom: 10px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    table td, table th, .table {
      padding: 8px;
      border-bottom: 1px solid #eee;
    }

	</style>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body>
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden scroll;">
    
	    <section class="content">

			<!-- 왼쪽 (출근 / 근무시간) -->
			<div class="panel check-in">
				<h3 style="border-bottom: 1px solid #eee;">오늘</h3>
				<p style="font-size:22px;">${todayDate}</p>
				<div class="time-info">
				<div class="time-circle">
				
				<div class="times-row"">
					<span style="width:100px;">출근: <span>${attendDTO.attendIn eq null ? "--:--:--" : attendDTO.formattedAttendIn}</span></span>
					<span style="width:100px;">퇴근: <span>${attendDTO.attendOut eq null ? "--:--:--" : attendDTO.formattedAttendOut}</span></span>
				</div>
			</div>
			<div class="worktime">근무시간 : <span>${attendDTO.workTime}</span></div>
				<div class="button-row">
				  
					<c:choose>
					
						<c:when test="${attendDTO.attendIn eq null}">
							<form action="/attend/in" method="post">
								<button style="background-color:#1976d2;">출근</button>
							</form>
							<form action="/attend/out" method="post">
								<button disabled style="background-color:gray;">퇴근</button>
							</form>
						</c:when>
						
						<c:when test="${attendDTO.attendIn ne null and attendDTO.attendOut eq null}">
							<form action="/attend/in" method="post">
							<button disabled style="background-color:gray;">출근</button>
							</form>
							<form action="/attend/out" method="post">
							<button style="background-color:#1976d2;">퇴근</button>
							</form>
						</c:when>
						
						<c:otherwise>
							<form action="/attend/in" method="post">
							<button disabled style="background-color:gray;">출근</button>
							</form>
							<form action="/attend/out" method="post">
							<button disabled style="background-color:gray;">퇴근</button>
							</form>
						</c:otherwise>
					
					</c:choose>
				</div>
			      </div>
	        </div>
	
	        <!-- 어트랙션 운휴 현황 -->
	        <div class="panel attraction-status">
	          <h3 style="border-bottom: 1px solid #eee;">어트랙션 운휴 현황</h3>
	          <table>
	            <tr><td>어트랙션</td><td>상태</td></tr>
	          	<c:forEach items="${rides }" var="ride">
		            <tr><td>${ride.rideName }</td>
		            <td>${ride.rideState eq 300 ? "운휴" : ride.rideState eq 400 ? "고장" : "점검"}</td></tr>
	          	</c:forEach>
	          </table>
	        </div>
	
			<!-- 날씨 -->
			<div class="panel weather-card">
				<h3 style="border-bottom: 1px solid #eee;">날씨</h3>
				<div id="weather-info" style="font-size:20px;">⏳ 불러오는 중...</div>
			</div>

	        <!-- 결재 현황 -->
	        <div class="panel approval">
	        <div class="d-flex justify-content-between" style="border-bottom: 1px solid #eee;">
	          <h3>결재 현황</h3>
	          <a href="/approval" style="margin-top:10px;">더보기</a>
	        </div>
	          <table class="table text-center">
	            <tr>
		            <td class="col-2">문서번호</td>
		            <td class="col-2">제목</td>
		            <td class="col-1">기안일</td>
		            <td class="col-1">결재순서</td>
	            </tr>
	            <c:forEach items="${approvalList }" var="aprv">
		            <tr>
			            <td>${aprv.aprvCode }</td>
			            <td><a href="/approval/${ aprv.aprvCode }" style="color: #737373;">${ aprv.aprvTitle }</a></td>
			            <td>${aprv.aprvDate }</td>
			            <td>${aprv.aprvCrnt } / ${aprv.aprvTotal }</td>
		            </tr>
	            </c:forEach>
	          </table>
	        </div>
	
	        <!-- 공지사항 -->
	        <div class="panel notices">
	        <div class="d-flex justify-content-between" style="border-bottom: 1px solid #eee;">
	          <h3>공지사항</h3><a href="/notice" style="margin-top:10px;">더보기</a>
	        </div>
	          <table>
	            <tr>
		            <td>번호</td>
		            <td>부서</td>
		            <td>제목</td>
		            <td>작성자</td>
		            <td>작성일</td>
	            </tr>
	          <c:forEach items="${noticeList}" var="notice">
	            <tr>
		            <td>${notice.noticeNum }</td>
		            <td>${notice.staffDTO.deptDTO.deptDetail }</td>
		            <td><a href="/notice/${ notice.noticeNum }">${ notice.noticeTitle }</a></td>
		            <td>${notice.staffDTO.staffName }</td>
		            <td>${notice.noticeDate }</td>
	            </tr>
	          
	          </c:forEach>
	          </table>
<!-- 	          <table> -->
<!-- 	            <tr><td>번호</td><td>제목</td><td>작성자</td><td>작성일</td></tr> -->
<!-- 	            <tr><td>7</td><td>신규 입사자 교육 일정 안내</td><td>김은하</td><td>2025-09-11</td></tr> -->
<!-- 	            <tr><td>6</td><td>놀이물 별 점검 개선 안내</td><td>권호찬</td><td>2025-09-07</td></tr> -->
<!-- 	            <tr><td>5</td><td>주말 근무 교대 신청 마감일 안내</td><td>권호찬</td><td>2025-09-07</td></tr> -->
<!-- 	          </table> -->
	        </div>

      	</section>

    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/weather/weather.js"></script>
	<script>
		document.querySelector("i[data-content='대시보드']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
</body>

</html>