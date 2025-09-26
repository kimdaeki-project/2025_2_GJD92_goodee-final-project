/**
 * connection
 */
let stompClient = null;
const staffCode = '<sec:authentication property="principal.username"/>'

function connectWebSocket(staffCode) {
	const socket = new SockJS('http://192.168.1.35/ws-stomp');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log("Connected: " + frame);
		stompClient.subscribe("/sub/notify/" + staffCode, (msg) => {
			showNotification(msg.body);
		})
	}, (error) => {
		console.error("Disconnected. Reconnecting in 5s...");
		setTimeout(() => connectWebSocket(staffCode), 5000);
	});
}

function showNotification(msg) {
	window.alert(msg);
}