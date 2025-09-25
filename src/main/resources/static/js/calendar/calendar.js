console.log("calendar.js 연결됨")

const allDayCheckBox = document.getElementById("allDayCheckBox");
const selectMinHour = document.querySelectorAll(".select-min-hour");
const btnAddCalendar = document.getElementById("btnAddCalendar");
const calendarEl = document.getElementById('calendar')

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
	dayCellContent: function(arg) {
		return arg.dayNumberText.replace('일', '');
	},
	headerToolbar: {
		left: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth',
		center: 'title',
		right: 'prev,today,next'
	},
	buttonText: {
		today: '오늘',
		month: '월간',
		week: '주간',
		day: '일간',
		list: '목록'
	},
	events: function(fetchInfo, successCallback, failureCallback) {
		fetch("/calendar/eventList", { method: 'GET' })
		.then(r => r.json())
		.then(r => {
			console.log("서버 응답:", r);

			// FullCalendar가 요구하는 형태로 매핑
			const events = r.map(event => ({
				id: event.calNum,              // 고유 ID
				title: event.calTitle,         // 제목
				start: event.calStart,         // 시작일
				end: event.calEnd,     
				allDay: event.calIsAllDay,
				backgroundColor: eventBgColor(event.calType),
				borderColor: "transparent",
				classNames: ['my-event'],
				editable : true,
				extendedProps: {
					content: event.calContent,
					place: event.calPlace,
					type: event.calType,
					dept: event.deptCode
				}
			} 
		));

			successCallback(events); // ✅ 달력에 이벤트 반영
		})
		.catch(e => {
			console.error("이벤트 로딩 실패:", e);
			failureCallback(e);
		});
	},
})
calendar.render()

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

function addCalendarEvent() {
	calStartDate = document.getElementById("calStartDate").value,
	calStartHour = document.getElementById("calStartHour").value
	calStartMin  = document.getElementById("calStartMin").value
	calEndDate   = document.getElementById("calEndDate").value,
	calEndHour   = document.getElementById("calEndHour").value
	calEndMin    = document.getElementById("calEndMin").value
	
	const title = document.getElementById("calTitle").value
	
	if(calStartDate == null || calStartDate == "") {
		alert("시작값을 넣어주세요");
		return;
	}
	if(calEndDate == null || calEndDate == "") {
		alert("종료값을 넣어주세요");
		return;
	}
	if(!title) {
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
		calType:     document.getElementById("calType").value,
		calStart:    start,
		calEnd:      end,
		calIsAllDay: document.getElementById("allDayCheckBox").checked,
		calPlace:    document.getElementById("calPlace").value,
		calTitle:    title,
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
		// 캘린더에 추가 랜더링 - 반환 받은 객체를 calendar.addEvent({}) 사용해서 랜더링
		console.log(addedEvent)
		calendar.addEvent({
			id: addedEvent.calNum,              
			title: addedEvent.calTitle,         
			start: addedEvent.calStart,         
			end: addedEvent.calEnd,     
			allDay: addedEvent.calIsAllDay,
			backgroundColor: eventBgColor(addedEvent.calType),
			borderColor: "transparent",
			classNames: ['my-event'],
			editable : true,
			extendedProps: {
				content: addedEvent.calContent,
				place: addedEvent.calPlace,
				type: addedEvent.calType,
				dept: addedEvent.deptCode
			}
		})
		const modalEl = document.getElementById('addEventModal');
		const modal = bootstrap.Modal.getInstance(modalEl);
		if(modal) modal.hide();
	})
}

function eventBgColor(calType) {
	switch (calType) {
		case 2000 : return "red";
		case 2001 : return "#F1C40F";
		case 2002 : return "blue";
		case 2003 : return "green";
	}	
}

document.addEventListener("hidden.bs.modal", () => {
	const modalForm = document.getElementById("addEventModal");
	modalForm.reset();
})

