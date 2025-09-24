console.log("calendar.js 연결됨")

document.addEventListener('DOMContentLoaded', function() {
	const calendarEl = document.getElementById('calendar')
	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		locale: 'ko',
		timezone: 'local',
		dayMaxEvents: true,
		editable: true,
		dayCellContent: function(arg) {
			return arg.dayNumberText.replace('일', '');
		},
		buttonText: {
			today: '오늘',
			month: '월간',
			week: '주간',
			day: '일간',
			list: '목록'
		},
		headerToolbar: {
			left: 'dayGridMonth,timeGridWeek,timeGridDay',
			center: 'title',
			right: 'prev,next today'
		},
		events: function(fetchInfo, successCallback, failureCallback) {
			fetch("/calendar/eventList", { method: 'GET' })
			.then(r => r.json())
			.then(r => {
				console.log("서버 응답:", r);

				// FullCalendar가 요구하는 형태로 매핑
				const events = r.map(item => ({
					id: item.calNum,              // 고유 ID
					title: item.calTitle,         // 제목
					start: item.calStart,         // 시작일
					end: item.calEnd || null,     // 종료일 (없으면 null)
					extendedProps: {
						content: item.calContent,
						place: item.calPlace,
						type: item.calType,
						dept: item.deptCode
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
		height: '85%',
		slotMinTime: "00:00:00",
		slotMaxTime: "24:00:00",
		slotDuration: "01:00:00",
		scrollTime: "00:00:00",
		titleFormat: function(date) {
			const y = date.date.year;
			const m = String(date.date.month + 1).padStart(2, '0');
			return `${y}-${m}`;
		}
	})
	calendar.render()
})
