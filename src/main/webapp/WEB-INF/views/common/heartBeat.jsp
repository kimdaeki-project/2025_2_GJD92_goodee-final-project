<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
<script src="/js/messenger/heartBeat.js"></script>
<script>
	const staffCodeForWebSocketConnection = '<sec:authentication property="principal.username"/>';
	connectWebSocket(staffCodeForWebSocketConnection);
</script>