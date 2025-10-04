console.log("calendar.js 연결됨")

// 메인
const calendarEl      = document.getElementById("calendar");
const inputCalNum     = document.getElementById("calNum");

// 사이드바
const calTypeCheckboxs = document.querySelectorAll(".cal-type-checkbox");
let selectedCalType    = getSelectedTypes();

// 모달
const modalAddCalendar     = new bootstrap.Modal(document.getElementById('addCalendarModal'));
const modalCalendarDetail  = new bootstrap.Modal(document.getElementById("calendarDetailModal"));

// 등록,수정 모달
const btnModalWrite     = document.getElementById("btnModalWrite");
const btnAddCalendar    = document.getElementById("btnAddCalendar");

const inputCalType      = document.getElementById("calType");
const inputCalTitle     = document.getElementById("calTitle");
const inputCalPlace     = document.getElementById("calPlace")
const inputCalContent   = document.getElementById("calContent")
const inputCalEndMin    = document.getElementById("calEndMin");
const inputCalEndHour   = document.getElementById("calEndHour");
const inputCalEndDate   = document.getElementById("calEndDate");
const inputCalStartMin  = document.getElementById("calStartMin");
const inputCalStartHour = document.getElementById("calStartHour");
const inputCalStartDate = document.getElementById("calStartDate");
const inputCalIsAllDay  = document.getElementById("allDayCheckBox")
const selectMinHour     = document.querySelectorAll(".select-min-hour"); // 종일 체크시 숨김처리 용도

// 상세모달
const btnOpenUpdateModal = document.getElementById("btnOpenUpdateModal");
const btnDeleteCalendar = document.getElementById("btnDeleteCalendar");
const btnUpAndDel       = document.querySelectorAll(".btn-update-delete");

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
//      selectable: true, // 날짜 범위를 드래그하여 새로운 일정 구간을 선택 - 사용 할지 미정
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
	buttonText: {    // 툴바 버튼 이름
		   day: '일간',
		  list: '목록',
		  week: '주간',
	 	 month: '월간',
		 today: '오늘'
	},
	views: {
		timeGridWeek: {
			titleFormat: { year: 'numeric', month: '2-digit', day: '2-digit' }
		},
		listDay: {
			titleFormat: function(arg) {
				const y = arg.date.year;
				const m = String(arg.date.month + 1).padStart(2, '0');
				const d = String(arg.date.day).padStart(2, '0');
				const weekday = arg.date.marker.toLocaleDateString('ko-KR', { weekday: 'long' });
				return `${y}-${m}-${d} ${weekday}`;
			}
		}
	},
	eventClick: function(eventInfo) {
		calDetail(eventInfo);
		modalCalendarDetail.show();
	},
	dateClick: function(dateInfo) { 
		showWriteModal(dateInfo.dateStr); 
	},
	eventDrop: function(eventInfo) { 
		dragResizeUpdate(eventInfo); 
	},
	eventResize: function(eventInfo) {
		dragResizeUpdate(eventInfo)
	},
	eventMouseEnter: function(info) { 
		info.el.style.cursor = "pointer"; 
	}, // 마우스 포인터
	eventMouseLeave: function(info) { 
		info.el.style.cursor = "default";	
	},
	events: function(fetchInfo, successCallback, failureCallback) { // 일정 불러오기
		fetch(`/calendar/calList?calTypes=${ selectedCalType }`, { method: 'GET' })
		.then(r => r.json())
		.then(cals => {
//			console.log(fetchInfo)
			if(!cals) return;
			const events = cals.map(cal => addInCalendar(cal)).filter(event => event !== null);
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
inputCalIsAllDay.addEventListener("change", (e) => {
	const check = e.target.checked;
	showHideInput(check);
})

btnModalWrite.addEventListener("click", () => {
	const today = dayjs().format("YYYY-MM-DD");
	showWriteModal(today);
})

calTypeCheckboxs.forEach(function (calTypeCheckbox) {
	calTypeCheckbox.addEventListener("change", () => {
		selectedCalType = getSelectedTypes();
		calendar.refetchEvents();
	})
})

btnOpenUpdateModal.addEventListener("click", () => {
	btnAddCalendar.dataset.request = "update";
	const calNum = inputCalNum.value;
	
	fetch(`/calendar/${calNum}`, { method: "GET" })
	.then(r => r.json())
	.then(cal => {
		if(cal != null) {
			inputCalEndMin.value     = dayjs(cal.calEnd).format("mm");
			inputCalEndHour.value    = dayjs(cal.calEnd).format("HH");
			inputCalEndDate.value    = dayjs(cal.calEnd).format("YYYY-MM-DD");
			inputCalStartMin.value   = dayjs(cal.calStart).format("mm");
			inputCalStartHour.value  = dayjs(cal.calStart).format("HH");
			inputCalStartDate.value  = dayjs(cal.calStart).format("YYYY-MM-DD");

			inputCalType.value       = cal.calType;
			inputCalPlace.value      = cal.calPlace;
			inputCalTitle.value      = cal.calTitle;
			inputCalContent.value    = cal.calContent;
			inputCalIsAllDay.checked = cal.calIsAllDay;
			
			showHideInput(cal.calIsAllDay);
			
			if(modalCalendarDetail) modalCalendarDetail.hide();
			modalAddCalendar.show();
		}
	})
	.catch(e => {
		console.log("캘린더 Detail 불러오기 실패", e)
	});
})

btnDeleteCalendar.addEventListener("click", () => {
	Swal.fire({
	   title: "일정 삭제",
	   text: '일정을 삭제하시겠습니까?',
	   icon: "error",
	   showCancelButton: true,
	   confirmButtonColor: "#191919",
	   cancelButtonColor: "#FFFFFF",
	   confirmButtonText: "삭제",
	   cancelButtonText: "취소",
	   customClass: {
	       cancelButton: 'my-cancel-btn'
	     }
	}).then(result => {
		if(!result.isConfirmed) {
			return
		}
		const calNum = inputCalNum.value;		
		const param  = new URLSearchParams();
		param.append("calNum", calNum);
		
		fetch("/calendar/delete", {
			method: "POST",
			body: param
		})
		.then(r => r.json())
		.then(r => {
			if (r) {
				const event = calendar.getEventById(calNum)
				if (event) {
					event.remove();
					selectMinHour.forEach(function (select) {
						select.classList.remove("d-none");
					})
				}
			} else {
				Swal.fire({
			        text: "작성자 본인만 삭제 가능",
			        icon: "error",
			        confirmButtonColor: "#191919",
			        confirmButtonText: "확인"
		  	    });
			}
			if (modalCalendarDetail) modalCalendarDetail.hide();
		})
		.catch(e => {
			console.log("삭제하는데 에러 발생", e)	
		})
	})
})

function addCalendar() {
	const calType      = inputCalType.value;
	const calTitle     = inputCalTitle.value;
	const calEndMin    = inputCalEndMin.value;
	const calEndHour   = inputCalEndHour.value;
	const calEndDate   = inputCalEndDate.value;
	const calStartMin  = inputCalStartMin.value;
	const calStartHour = inputCalStartHour.value;
	const calStartDate = inputCalStartDate.value;
	
	if(!calType) {
		Swal.fire({
	        text: "타입을 선택하세요!",
	        icon: "error",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
  	    });
		return;
	}
	if(calStartDate == null || calStartDate == "") {
		Swal.fire({
	        text: "시작날짜 설정 필수!",
	        icon: "error",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
  	    });
		return;
	}
	if(calEndDate == null || calEndDate == "") {
		Swal.fire({
	        text: "종료날짜 설정 필수!",
	        icon: "error",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
  	    });
		return;
	}
	if(!calTitle) {
		Swal.fire({
	        text: "제목 작성 필수!",
	        icon: "error",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
  	    });
		return;
	}
	
	// 시간 포멧팅 해주는 라이브러리 사용
	// <script src="https://cdn.jsdelivr.net/npm/dayjs@1/dayjs.min.js"></script>
	const start = dayjs(`${calStartDate} ${calStartHour}:${calStartMin}`, "YYYY-MM-DD HH:mm")
	const end   = dayjs(`${calEndDate} ${calEndHour}:${calEndMin}`, "YYYY-MM-DD HH:mm")
	
	if(end.isBefore(start)) {
		Swal.fire({
	        text: "종료일을 시작일보다 빠르게 설정할 수 없습니다.",
	        icon: "error",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
  	    });
		return;
	}
	
	const calEvent = {
		calStart:    start.format("YYYY-MM-DDTHH:mm:ss"),
		calEnd:      end.format("YYYY-MM-DDTHH:mm:ss"),
		calType:     calType,
		calTitle:    calTitle,
		calPlace:    inputCalPlace.value,
		calContent:  inputCalContent.value,
		calIsAllDay: inputCalIsAllDay.checked
	};
	// fetch로 DB에 등록
	// 1. 일정 쓰기
	if(btnAddCalendar.dataset.request == "add") {
		fetch("calendar/add", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(calEvent)
		})
		.then(r => r.json())
		.then(addedCalendar => {
			console.log(addedCalendar)
			calendar.refetchEvents();
		})
	// 2. 일정 수정
	} else if(btnAddCalendar.dataset.request == "update") {
		calEvent.calNum = inputCalNum.value;
		
		fetch("calendar/update", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(calEvent)
		})
		.then(r => r.json())
		.then(updatedCal => {
			console.log(updatedCal)
			calendar.refetchEvents();
		})
	}
	if(modalAddCalendar) modalAddCalendar.hide();
	showHideInput(false)
}

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
//		editable : (cal.staffDTO.staffCode === loginStaffCode) && (cal.calType != 2000),
		editable : true,
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

function calDetail(eventInfo) {
	let calStart      = eventInfo.event.startStr;
	let calReg        = eventInfo.event._def.extendedProps.calReg;
	calReg   = dayjs(calReg).format("YYYY-MM-DD HH:mm");		
	calStart = eventInfo.event.allDay ? dayjs(calStart).format("YYYY-MM-DD") : dayjs(calStart).format("YYYY-MM-DD HH:mm");				

	const calNum      = eventInfo.event._def.extendedProps.calNum;
	const calMod      = eventInfo.event._def.extendedProps.calMod;
	const calType     = eventInfo.event._def.extendedProps.calType;
	const calTitle    = eventInfo.event._def.extendedProps.calTitle;
	const calPlace    = eventInfo.event._def.extendedProps.calPlace;
	const staffName   = eventInfo.event._def.extendedProps.staffName;
	const staffCode   = eventInfo.event._def.extendedProps.staffCode;
	const calContent  = eventInfo.event._def.extendedProps.calContent;
	const calTypeName = eventInfo.event._def.extendedProps.calTypeName;
	
	document.getElementById("detailModalReg").textContent = calReg;
	document.getElementById("detailModalDate").textContent = calStart;
	document.getElementById("detailModalTitle").textContent = calTitle
	document.getElementById("detailModalPlace").textContent = calPlace ? "장소 - " + calPlace : "";
	document.getElementById("detailModalWriter").textContent = staffName;
	document.getElementById("detailModalContent").textContent = calContent;
	document.getElementById("detailModalDept").textContent = calTypeName + " 일정";
	document.getElementById("detailDeptCircle").style.backgroundColor = eventBgColor(calType);
	document.getElementById("detailModalMod").textContent = dayjs(calMod).format("YYYY-MM-DD HH:mm");
	
	inputCalNum.value = calNum; // hiddenInput에 calNum을 설정해줌
	
	// 공휴일, 작성자 본인이 아닌경우 수정,삭제 버튼 숨김처리
	if(calType == 2000 || loginStaffCode != staffCode) {
		btnUpAndDel.forEach(function(el) {
			el.classList.add("d-none");
		})
	} else {
		btnUpAndDel.forEach(function(el) {
			el.classList.remove("d-none");
		})
	}
}

// 드래그드롭시 날짜 업데이트
function dragResizeUpdate(eventInfo) {
	const calStart = dayjs(eventInfo.event.startStr).format("YYYY-MM-DDTHH:mm:ss");
	let calEnd = dayjs(eventInfo.event.endStr).format("YYYY-MM-DDTHH:mm:ss");
	
	if(eventInfo.event.allDay) {
		calEnd = dayjs(eventInfo.event.endStr).add(-1, "day").format("YYYY-MM-DDTHH:mm:ss");
	};
	
	const calEvent = {
		calNum   : eventInfo.event._def.extendedProps.calNum,
		calStart : calStart,
		calEnd   : calEnd
	}
	
	fetch("/calendar/updateDate", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify(calEvent)
	})
	.then(r => r.json())
	.then(r => {
		if(r) console.log("날짜변경 성공")
		else console.log("날짜변경 실패")
	})
	.catch(error => {
		console.log("DB수정 에러발생" ,error)
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

function showWriteModal(date) {
	btnAddCalendar.dataset.request = "add";
	
	document.getElementById("calStartDate").value = date;
	document.getElementById("calEndDate").value = date;
	
	modalAddCalendar.show();
}

function showHideInput(checked) {
	if(checked) {
		selectMinHour.forEach(function (select) {
			select.classList.add("d-none");
			inputCalStartMin.value  = "00";
			inputCalStartHour.value = "00";
			inputCalEndMin.value    = "00";
			inputCalEndHour.value   = "00";
		})
	} else {
		selectMinHour.forEach(function (select) {
			select.classList.remove("d-none");
		})
	} 
}

function getSelectedTypes() {
	return Array.from(calTypeCheckboxs).filter(cb => cb.checked).map(cb => cb.dataset.calType);
}

function plusOneDay(cal) {
	let modEndDate = cal.calEnd;
	
	if(cal.calIsAllDay) {
		modEndDate = dayjs(modEndDate).add(1, "day").toDate();
	}
	
	return modEndDate;
}


