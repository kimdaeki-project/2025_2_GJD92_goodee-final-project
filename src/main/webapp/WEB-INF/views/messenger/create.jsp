<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
</head>
<body>
	<h4>방 생성</h4>
	<form action="/msg/create" method="post" id="form">
		<label for="chatRoomName">채팅방 이름</label>
		<input type="text" name="chatRoomName" id="chatRoomName">
		<input type="hidden" id="addedStaff" name="addedStaff">
	</form>
	<c:forEach items="${ staff }" var="s">
		<div>
			${ s.staffName }
			<button value="${ s.staffCode }" class="add-user">추가</button>
		</div>
	</c:forEach>
	<hr>
	<h4>추가됨</h4>
	<div id="addedUsers"></div>
	<div>
		<button id="createRoom">생성</button>
	</div>
	
	<script type="text/javascript">
		const addBtns = document.querySelectorAll('.add-user');
		const addedUsers = document.querySelector('#addedUsers');
		let room = [];
		addBtns.forEach(el => {
			el.addEventListener('click', () => {				
				const nameDiv = document.createElement('div');
				
				const nameSpan = document.createElement('span');
				nameSpan.innerText = el.value;
				
				const axe = document.createElement('button');
				axe.setAttribute('type', 'button');
				axe.innerText = 'X';
				
				nameDiv.appendChild(nameSpan);
				nameDiv.appendChild(axe);
				
				addedUsers.appendChild(nameDiv);
				room.push(el.value);
			});
		});
		
		// ...
		
		const createRoomBtn = document.querySelector('#createRoom');
		const form = document.querySelector('#form');
		createRoomBtn.addEventListener('click', () => {
			if (room.length > 0) {
				let addedStaff = document.querySelector('#addedStaff');
				addedStaff.value = room.join(",");
				console.log(addedStaff.value)
				form.submit();
			}
		});
	</script>
</body>
</html>