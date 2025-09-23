<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
<!-- 구글 머티리얼 아이콘 -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
        height: 100vh;
        display: flex;
    }
    /* 좌측 사이드바 */
    .sidebar {
        width: 60px;
        border-right: 1px solid #eee;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px 0;
        box-sizing: border-box;
    }
    .sidebar .material-icons {
        font-size: 28px;
        margin: 20px 0;
        cursor: pointer;
        color: #444;
        transition: color 0.2s;
    }
    .sidebar .material-icons.active {
        color: #000;
    }
    .sidebar .material-icons:hover {
        color: #000;
    }

    /* 메인 컨텐츠 */
    .main {
        flex: 1;
        padding: 20px;
        box-sizing: border-box;
        overflow-y: auto;
    }

    /* 헤더 */
    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 15px;
    }
    .header h4 {
        margin: 0;
        font-size: 18px;
        font-weight: bold;
    }
    .header .material-icons {
        cursor: pointer;
        font-size: 24px;
        color: #444;
    }

    /* 탭 메뉴 */
    .tabs {
        display: flex;
        gap: 20px;
        font-size: 14px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 20px;
    }
    .tab {
        padding: 8px 0;
        cursor: pointer;
        color: #555;
        position: relative;
    }
    .tab.active {
        font-weight: bold;
        color: #000;
    }
    .tab.active::after {
        content: "";
        position: absolute;
        left: 0;
        bottom: -1px;
        width: 100%;
        height: 2px;
        background: #000;
    }

    /* 채팅방 리스트 */
	.chat-room {
	    display: flex;
	    align-items: center;
	    justify-content: space-between;
	    padding: 12px 0;
	    border-bottom: 1px solid #f0f0f0;
	    cursor: pointer;          /* 마우스 올리면 포인터 표시 */
	    transition: background 0.2s;
	}
	.chat-room:hover {
	    background: #f9f9f9;       /* 살짝 회색 배경 */
	}
    .chat-room-info {
        display: flex;
        flex-direction: column;
    }
    .chat-room-name {
        font-size: 15px;
        font-weight: bold;
        margin-bottom: 4px;
    }
    .chat-room-last {
        font-size: 13px;
        color: #777;
    }
    .chat-room-right {
        display: flex;
        align-items: center;
        gap: 8px;
        font-size: 12px;
        color: #666;
    }
</style>
</head>
<body>
    <!-- 좌측 사이드바 -->
    <div class="sidebar">
        <a href="/msg"><span class="material-icons">person_outline</span></a>
        <span class="material-icons active">chat_bubble_outline</span>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="main">
        <!-- 헤더 -->
        <div class="header">
            <h3>채팅</h3>
            <a href="/msg/create"><span class="material-icons">add</span></a>
        </div>

        <!-- 탭 메뉴 -->
        <div class="tabs">
            <div class="tab active">전체</div>
            <div class="tab">1:1</div>
            <div class="tab">그룹</div>
        </div>

        <!-- 채팅방 리스트 -->
        <c:forEach items="${ room }" var="r">
        	<c:if test="${ r.chatRoomGroup eq false }">
	            <form action="/msg/chat" method="post" class="chat-room">
	                <input type="hidden" name="chatRoomNum" value="${ r.chatRoomNum }">
	                <div class="chat-room-info">
	                	<sec:authorize access="isAuthenticated()">
							<sec:authentication property="principal" var="staff" />
		                	<c:forEach items="${ r.chatUserDTOs }" var="s">
		                		<c:if test="${ s.staffDTO.staffCode ne staff.staffCode }">
			                    	<div class="chat-room-name">${ s.staffDTO.staffName }</div>
			                    </c:if>               	
		                	</c:forEach>
	                	</sec:authorize>
	                    <div id="chat-room-last-${ r.chatRoomNum }" class="chat-room-last"></div>
	                </div>
	                <div class="chat-room-right">
	                    <span class="time" id="time-${ r.chatRoomNum }">어제</span>
	                    <span id="unread-count-${ r.chatRoomNum }" class="badge"></span>
	                </div>
	            </form>
            </c:if>
            <c:if test="${ r.chatRoomGroup eq true }">
	            <form action="/msg/chat" method="post" class="chat-room">
	                <input type="hidden" name="chatRoomNum" value="${ r.chatRoomNum }">
	                <div class="chat-room-info">
	                    <div class="chat-room-name">${ r.chatRoomName }</div>
	                    <div id="chat-room-last-${ r.chatRoomNum }" class="chat-room-last"></div>
	                </div>
	                <div class="chat-room-right">
	                    <span class="time" id="time-${ r.chatRoomNum }">어제</span>
	                    <span id="unread-count-${ r.chatRoomNum }" class="badge"></span>
	                </div>
	            </form>
            
            </c:if>
        </c:forEach>
    </div>

    <script>	
        const rooms = [
            <c:forEach var="room" items="${ room }" varStatus="status">
                ${ room.chatRoomNum }<c:if test="${ !status.last }">,</c:if>
            </c:forEach>
        ];
        // 이니셜라이즈
        fetch("/msg/unread/count", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(rooms)
        })
        .then(res => res.json())
        .then(data => {
        	let unread = data.unread;
        	let latest = data.latest;
        	let today = new Date();
            if (unread != null && latest != null) {
                for (const chatRoomNum in unread) {
                    let badge = document.querySelector('#unread-count-' + chatRoomNum);
                    badge.innerText = unread[chatRoomNum] > 0 ? unread[chatRoomNum] : "";
                    
                    let latestMessage = document.querySelector('#chat-room-last-' + chatRoomNum);
                    latestMessage.innerText = latest[chatRoomNum].chatBodyContent;
                    
            		let time = document.querySelector('#time-' + chatRoomNum);
            		let timeFromJava = latest[chatRoomNum].chatBodyDtm;
            	    const timeToJs = new Date(timeFromJava);
            	    if (latest[chatRoomNum].chatBodyContent == '메시지 없음') {
            	    	time.innerText = '';
            	    } else {
            	    	if (timeToJs.getDate() == today.getDate()) {
            	    		time.innerText = timeToJs.getHours() + '시 ' + timeToJs.getMinutes() + '분';
            	    	} else if (timeToJs.getDate() == today.getDate() - 1) {
            	    		time.innerText = '어제';
            	    	} else {
            	    		time.innerText = timeToJs.getMonth() + 1 + '월 ' + timeToJs.getDate() + '일';
            	    	}
            	    }
                }
            }
        });
        // 지속화
        setInterval(() => {
            fetch("/msg/unread/count", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(rooms)
            })
            .then(res => res.json())
            .then(data => {
            	let unread = data.unread;
            	let latest = data.latest;
            	let today = new Date();
                if (unread != null && latest != null) {
                    for (const chatRoomNum in unread) {
                        let badge = document.querySelector('#unread-count-' + chatRoomNum);
                        badge.innerText = unread[chatRoomNum] > 0 ? unread[chatRoomNum] : "";
                        
                        let latestMessage = document.querySelector('#chat-room-last-' + chatRoomNum);
                        latestMessage.innerText = latest[chatRoomNum].chatBodyContent;
                        
                		let time = document.querySelector('#time-' + chatRoomNum);
                		let timeFromJava = latest[chatRoomNum].chatBodyDtm;
                	    const timeToJs = new Date(timeFromJava);
                	    if (latest[chatRoomNum].chatBodyContent == '메시지 없음') {
                	    	time.innerText = '';
                	    } else {
                	    	if (timeToJs.getDate() == today.getDate()) {
                	    		time.innerText = timeToJs.getHours() + '시 ' + timeToJs.getMinutes() + '분';
                	    	} else if (timeToJs.getDate() == today.getDate() - 1) {
                	    		time.innerText = '어제';
                	    	} else {
                	    		time.innerText = timeToJs.getMonth() + 1 + '월 ' + timeToJs.getDate() + '일';
                	    	}
                	    }
                    }
                }
            });
        }, 500);
        // 채팅방 입장
        const forms = document.querySelectorAll('.chat-room');
        forms.forEach(form => {
        	form.addEventListener('click', () => {
        		form.submit();
        	});
        });
    </script>
</body>
</html>
