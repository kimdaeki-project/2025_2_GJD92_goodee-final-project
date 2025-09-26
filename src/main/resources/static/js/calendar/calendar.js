console.log("calendar.js 연결됨")

const allDayCheckBox  = document.getElementById("allDayCheckBox");
const btnAddCalendar  = document.getElementById("btnAddCalendar");
const btnModalWrite   = document.getElementById("btnModalWrite");
const calendarEl      = document.getElementById('calendar');
const selectMinHour   = document.querySelectorAll(".select-min-hour");

const modalAddEvent   = new bootstrap.Modal(document.getElementById('addEventModal'));
const modalDetail     = new bootstrap.Modal(document.getElementById("eventDetailModal"));

document.addEventListener("hidden.bs.modal", (e) => {
	const modalForm = e.target.querySelector("#eventForm");
	if (modalForm) modalForm.reset();
})

const calendar = new FullCalendar.Calendar(calendarEl, {
	 initialView: 'dayGridMonth',
	      locale: 'ko',
	    timezone: 'local',
	dayMaxEvents: true,
        editable: true, // 드래그드롭, 잡아서 늘리기 가능 
	  expandRows: true, // 화면 높이에 맞게
	      height: '95%',
	 slotMinTime: "00:00:00",
	 slotMaxTime: "24:00:00",
	slotDuration: "01:00:00",
	  scrollTime: "00:00:00",
	nowIndicator: true, // 현재시간을 빨간 선으로 표시
      selectable: true, // 날짜 범위를 드래그하여 새로운 일정 구간을 선택 - 사용 할지 미정
	 titleFormat: function(date) {
        const y = date.date.year;
		const m = String(date.date.month + 1).padStart(2, '0');
		return `${y}-${m}`;
	},
	dayCellContent: function(arg) { // 날짜 "2일" 에서 "일"제거
		return arg.dayNumberText.replace('일', '');
	},
	headerToolbar: { // 툴바 위치 변경 스페이스 바로 사이 공간 띄울 수 있음
		  left: 'dayGridMonth,timeGridWeek,listDay',
		center: 'title',
		 right: 'prev,today,next'
	},
	buttonText: { // 툴바 버튼 이름
		   day: '일간',
		  list: '목록',
		  week: '주간',
	 	 month: '월간',
		 today: '오늘'
	},
	eventClick: function(eventInfo) {
		
		calTitle    = eventInfo.event._def.extendedProps.calTitle;
		calStart    = eventInfo.event.startStr;
		calTypeName = eventInfo.event._def.extendedProps.calTypeName;
		calType     = eventInfo.event._def.extendedProps.calType;
		calContent  = eventInfo.event._def.extendedProps.calContent;
		staffName  = eventInfo.event._def.extendedProps.staffName;
		
		document.getElementById("detailModalTitle").textContent = calTitle
		document.getElementById("detailModalDate").textContent = calStart
		document.getElementById("detailModalDept").textContent = calTypeName + " 일정";
		document.getElementById("detailCircle").style.backgroundColor = eventBgColor(calType);
		document.getElementById("detailModalContent").textContent = calContent;
		document.getElementById("detailModalWriter").textContent = staffName;
		
		
		modalDetail.show();
		
		console.log(eventInfo)
	},
	dateClick: function(dateInfo) { // 날짜 빈공간 클릭
		showWriteModal(dateInfo.dateStr);
	},
	events: function(fetchInfo, successCallback, failureCallback) { // 일정 불러오기
		fetch("/calendar/eventList", { method: 'GET' })
		.then(r => r.json())
		.then(r => {
//			console.log("서버 응답:", r);
			const events = r.map(event => addToCalendar(event));
//			console.log(events);
			successCallback(events); // 달력에 이벤트 반영
		})
		.catch(e => {
			console.error("이벤트 로딩 실패:", e);
			failureCallback(e);
		});
	},
})
calendar.render(); // 랜더링

// 종일 체크박스 체크시 시작 시,분 안보임 처리
allDayCheckBox.addEventListener("change", (e) => {
	const check = e.target.checked;
	
	if(check) {
		selectMinHour.forEach(function (select) {
			select.classList.add("d-none");
		})
	} else {
		selectMinHour.forEach(function (select) {
			select.classList.remove("d-none");
		})
	} 
})

btnModalWrite.addEventListener("click", () => {
	const today   = dayjs().format("YYYY-MM-DD");
	showWriteModal(today);
})

function addCalendarEvent() {
	const calStartDate = document.getElementById("calStartDate").value;
	const calStartHour = document.getElementById("calStartHour").value;
	const calStartMin  = document.getElementById("calStartMin").value;
	const calEndDate   = document.getElementById("calEndDate").value;
	const calEndHour   = document.getElementById("calEndHour").value;
	const calEndMin    = document.getElementById("calEndMin").value;
	const calType      = document.getElementById("calType").value;
	const calTitle     = document.getElementById("calTitle").value;
	
	if(!calType) {
		alert("타입을 선택하세요!");
		return;
	}
	if(calStartDate == null || calStartDate == "") {
		alert("시작일을 넣어주세요");
		return;
	}
	if(calEndDate == null || calEndDate == "") {
		alert("종료일을 넣어주세요");
		return;
	}
	if(!calTitle) {
		alert("제목 비어있음!");
		return;
	}
	
	// 시간 포멧팅 해주는 라이브러리 사용
	// <script src="https://cdn.jsdelivr.net/npm/dayjs@1/dayjs.min.js"></script>
	const start = dayjs(`${calStartDate} ${calStartHour}:${calStartMin}`, "YYYY-MM-DD HH:mm")
                  .format("YYYY-MM-DDTHH:mm:ss");
	const end   = dayjs(`${calEndDate} ${calEndHour}:${calEndMin}`, "YYYY-MM-DD HH:mm")
                  .format("YYYY-MM-DDTHH:mm:ss");
	
	const calEvent = {
		calStart:    start,
		calEnd:      end,
		calType:     calType,
		calTitle:    calTitle,
		calIsAllDay: document.getElementById("allDayCheckBox").checked,
		calPlace:    document.getElementById("calPlace").value,
		calContent:  document.getElementById("calContent").value,
	};
	// fetch로 DB에 등록
	fetch("calendar/add", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify(calEvent)
	})
	.then(r => r.json())
	.then(addedEvent => {
		// 캘린더에 추가 랜더링 - 반환 받은 객체를 calendar.addEvent() 사용해서 랜더링
		console.log(addedEvent)
		calendar.addEvent(addToCalendar(addedEvent))
		if(modalAddEvent) modalAddEvent.hide();
	})
}

function addToCalendar(event) {
	return {
		id: event.calNum,              
		title: event.calTitle,         
		start: event.calStart,         
		end: event.calEnd,     
		allDay: event.calIsAllDay,
		backgroundColor: eventBgColor(event.calType),
		borderColor: "transparent",
		classNames: ['my-event'],
		editable : true,
		extendedProps: {
			calType: event.calType,
			calPlace: event.calPlace,
			calTitle: event.calTitle,
			calContent: event.calContent,
			staffCode: event.staffDTO.staffCode,
			staffName: event.staffDTO.staffName,
			deptCode: event.staffDTO.deptDTO.deptCode,
			deptDetail: event.staffDTO.deptDTO.deptDetail,
			calTypeName: event.calTypeName
		}
	}
}

function eventBgColor(calType) {
	switch (calType) {
		case 2000 : return "red";
		case 2001 : return "#F1C40F";
		case 2002 : return "blue";
		case 2003 : return "green";
	}	
}

function showWriteModal(date) {
	modalAddEvent.show();
	document.getElementById("calStartDate").value = date;
	document.getElementById("calEndDate").value = date;
}
