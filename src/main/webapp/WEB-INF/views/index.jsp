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
      padding: 20px;
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
      padding: 15px;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      font-size: 14px;
    }

    .panel h2 {
      font-size: 16px;
      margin-bottom: 10px;
      border-bottom: 1px solid #eee;
      padding-bottom: 5px;
    }

    /* 체크인 카드 스타일 */
    .time-circle {
  margin: 10px auto;
  padding: 20px;
  text-align: center;
  border: 1px dashed #ccc;
  border-radius: 50%;
  width: 120px;
  height: 120px;
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
}

.worktime {
  margin-bottom: 10px;
  font-weight: 600;
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

    table td, table th {
      padding: 6px;
      border-bottom: 1px solid #eee;
    }

    /* 어트랙션 상태 강조 */
    .attraction-status table td:last-child {
      color: red;
      font-weight: bold;
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
				<h2>오늘</h2>
				<p>${todayDate}</p>
				<div class="time-info">
				<div class="time-circle">
				<div class="times-row">
					<span>출근: <span>${attendDTO.attendIn eq null ? "--:--:--" : attendDTO.formattedAttendIn}</span></span>
					<span>퇴근: <span>${attendDTO.attendOut eq null ? "--:--:--" : attendDTO.formattedAttendOut}</span></span>
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
	          <h2>어트랙션 운휴 현황</h2>
	          <table>
	            <tr><td>어트랙션</td><td>상태</td></tr>
	            <tr><td>후룸라이드</td><td>운휴</td></tr>
	            <tr><td>롤링 엑스 트레인</td><td>고장</td></tr>
	          </table>
	        </div>
	
	        <!-- 날씨 -->
	        <div class="panel weather">
	          <h2>날씨</h2>
	          <p>지역: 서울</p>
	          <div class="icon">☀️</div>
	          <ul>
	            <li>기온: 32.1℃</li>
	            <li>습도: 60%</li>
	            <li>풍속: 2m/s</li>
	          </ul>
	        </div>
	
	        <!-- 결재 현황 -->
	        <div class="panel approval">
	          <h2>결재 현황</h2>
	          <table>
	            <tr><td>제목</td><td>기안일</td><td>상태</td></tr>
	            <tr><td>지출결의서</td><td>2025-09-11</td><td>진행중</td></tr>
	            <tr><td>휴가신청서</td><td>2025-09-09</td><td>완료</td></tr>
	          </table>
	        </div>
	
	        <!-- 공지사항 -->
	        <div class="panel notices">
	          <h2>공지사항</h2><a href="/notice">더보기</a>
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
      	
      	<button id="webSocket-connection-test">웹소켓 테스트</button>
    
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='대시보드']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
	<script type="text/javascript">
		const wct = document.querySelector('#webSocket-connection-test');
		wct.addEventListener('click', () => {
			stompClient.send("/pub/notify/" + 20250001, {}, "알림 발생");
		});
	</script>
</body>

</html>