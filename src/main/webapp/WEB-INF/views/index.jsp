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
      height: 410px;
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
      grid-row: 2 / 2;
      height: 350px;
    }

    .notices {
      grid-column: 2 / 3; /* 2~3열 합침 */
      grid-row: 2 / 2;
    }

	.cal {
		grid-row: 3 / 4;
        grid-row: 2 / 2;
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

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden;">
    
	    <section class="content">

			<!-- 왼쪽 (출근 / 근무시간) -->
			<div class="panel check-in">
				<div class="d-flex justify-content-between" style="border-bottom: 1px solid #eee;">
					<h3>오늘</h3>
					<small style="font-size:20px;">${todayDate}</small>
				</div>
				<div class="time-info">
				<div class="time-circle">
					<div class="times-row" style="text-align:center;">
						<span style="width:100px;">출근: ${attendDTO.attendIn eq null ? "--:--:--" : attendDTO.formattedAttendIn}</span>
						<span style="width:100px;">퇴근: ${attendDTO.attendOut eq null ? "--:--:--" : attendDTO.formattedAttendOut}</span>
					</div>
				</div>
			<div class="worktime">근무시간 : 
				<c:if test="${not empty attendDTO.workTime}">
					<span>${attendDTO.workTime}</span>
				</c:if>
				<span>--  --</span>
					</div>
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
	          <div class="d-flex justify-content-between" style="border-bottom: 1px solid #eee;">
	          	<h3>어트랙션 운휴 현황</h3>
	          </div>
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
				<div class="d-flex justify-content-between" style="border-bottom: 1px solid #eee;">
					<h3>날씨</h3>
				</div>
					<div id="weather-info" style="font-size:20px;">⏳ 불러오는 중...</div>
			</div>

	        <!-- 결재 현황 -->
	        <div class="panel approval">
	        <div class="d-flex justify-content-between" style="border-bottom: 1px solid #eee;">
	          <h3>결재 현황</h3><a href="/approval" style="margin-top:10px;">더보기</a>
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
	          <table style="table-layout: fixed; width: 100%;">
	            <tr>
		            <td style="width: 50px;">번호</td>
		            <td style="width: 240px;">제목</td>
		            <td>작성자</td>
		            <td>작성일</td>
	            </tr>
	          <c:forEach items="${noticeList}" var="notice">
	            <tr>
		            <td class="text-center">${notice.noticeNum }</td>
		            <td><div class="text-truncate"><a href="/notice/${ notice.noticeNum }">${ notice.noticeTitle }</a></div></td>
		            <td>${notice.staffDTO.staffName }</td>
		            <td>${notice.noticeDate }</td>
	            </tr>
	          
	          </c:forEach>
	          </table>
	        </div>
	        
	        <div class="panel cal">
	        	<div class="d-flex justify-content-between" style="border-bottom: 1px solid #eee;">
		          <h3>일정</h3>
		          
		        </div>
		        <div id='calendar' class="mt-3"></div>
	        </div>

      	</section>

    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/weather/weather.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.19/index.global.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/dayjs@1/dayjs.min.js"></script>
	<script>
		document.querySelector("i[data-content='대시보드']").parentElement.classList.add("bg-gradient-dark", "text-white")
	</script>
	<script>
	const calendarEl = document.getElementById("calendar");
	const calendarType = [2001, 2002, 2003]
	console.log(calendarEl);
	const calendar = new FullCalendar.Calendar(calendarEl, {
		 initialView: 'listDay',
		      locale: 'ko',
		    timezone: 'local',
		dayMaxEvents: true,
	        editable: false, // 드래그드롭, 잡아서 늘리기 가능 
		  expandRows: false, // 화면 높이에 맞게
		      height: '75%',
		 slotMinTime: "00:00:00",
		 slotMaxTime: "24:00:00",
		slotDuration: "01:00:00",
		  scrollTime: "00:00:00",
		  allDayText: '종일',
		nowIndicator: true, // 현재시간을 빨간 선으로 표시
//	      selectable: true, // 날짜 범위를 드래그하여 새로운 일정 구간을 선택 - 사용 할지 미정
		googleCalendarApiKey: "AIzaSyAriTdVIXQDpo48t7KVpxkw2H6sXMOuJt4", // API 키
		 titleFormat: function(date) {
	        const y = date.date.year;
			const m = String(date.date.month + 1).padStart(2, '0');
			return `${y}-${m}`;
		},
		headerToolbar: false,
		eventClick: function(eventInfo) {
			if (eventInfo.event.classNames.includes('holiday-event')) { // 휴일은 클릭시 아무런 동작하지 않음
			}
			/* calDetail(eventInfo);
			modalCalendarDetail.show(); */
		},
		events: function(fetchInfo, successCallback, failureCallback) { // 일정 불러오기
			fetch(`/calendar/calList?calTypes=2001&calTypes=2002&calTypes=2003`, { method: 'GET' })
			.then(r => r.json())
			.then(cals => {
//				console.log(fetchInfo)
				if(!cals) return;
				const events = cals.map(cal => addInCalendar(cal))       // .filter(event => event !== null);
				console.log(events);
				successCallback(events); // 달력에 이벤트 반영
			})
			.catch(e => {
				console.error("이벤트 로딩 실패:", e);
				failureCallback(e);
			});
		},
		eventDidMount: function(info) { // 공휴일 API사용시 부여한 className이 실제로 DOM에는 반영되지 않는 현상 발생, 실제 이벤트가 마운트됐을때 클래스를 추가
		  if (info.event.title &&  // 공휴일(holiday-event) 판별: 구글 캘린더 소스인지 확인
		      info.event.source && 
		      info.event.source.googleCalendarId) {
		    info.el.classList.add('holiday-event'); // 실제 DOM 요소에 class 직접 부여
		  }
		}
	})
	calendar.render(); // 랜더링
	
	function addInCalendar(cal) {
		return {
			id: cal.calNum,              
			title: cal.calTitle,         
			allDay: cal.calIsAllDay,
			start: cal.calStart,         
			end: plusOneDay(cal),     
			backgroundColor: eventBgColor(cal.calType),
			borderColor: "transparent",
			classNames: ['my-event'],
			editable : false,
			extendedProps: {
				calNum      : cal.calNum,
				calReg      : cal.calReg,
				calMod      : cal.calMod,
				calType     : cal.calType,
				calPlace    : cal.calPlace,
				calTitle    : cal.calTitle,
				calContent  : cal.calContent,
				calTypeName : cal.calTypeName,
				staffCode   : cal.staffDTO.staffCode,
				staffName   : cal.staffDTO.staffName,
				deptCode    : cal.staffDTO.deptDTO.deptCode,
				deptDetail  : cal.staffDTO.deptDTO.deptDetail
			}
		}
	}
	function plusOneDay(cal) {
		let modEndDate = cal.calEnd;
		
		if(cal.calIsAllDay) {
			modEndDate = dayjs(modEndDate).add(1, "day").toDate();
		}
		
		return modEndDate;
	}
	function eventBgColor(calType) {
		switch (calType) {
			case 2000 : return "red";
			case 2001 : return "#E67E22";
			case 2002 : return "#2E86DE";
			case 2003 : return "#16A085";
		}	
	}
	</script>
</body>

</html>