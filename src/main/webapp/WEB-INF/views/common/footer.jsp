<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<style>
.fixed-plugin-button {
  position: relative;
  display: inline-block;
}

.fixed-plugin-button .badge {
  position: absolute;
  top: 0;
  right: 0;
  background: red;
  color: white;
  font-size: 12px;
  border-radius: 50%;
  padding: 2px 6px;
  line-height: normal;
}
</style>

<!-- Messenger -->
<sec:authorize access="isAuthenticated()">
	<div class="fixed-plugin" onclick="openMessenger()">
	  <a class="fixed-plugin-button text-dark position-fixed px-3 py-2">
	    <i class="material-symbols-rounded py-2">sms</i>
	    <span class="badge badge-footer-display">0</span>
	  </a>
	</div>
</sec:authorize>
<!-- Popper.js -->
<script src="https://unpkg.com/@popperjs/core@2"></script>

<!-- Bootstrap 5.3.8 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>

<!-- Perfect Scrollbar -->
<script src="/js/perfect-scrollbar.min.js"></script>

<!-- SweetAlert 2 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.23.0/dist/sweetalert2.all.min.js"></script>

<!-- Template JS -->
<script src="/js/material-dashboard.js"></script>

<script src="/js/messenger/footer.js"></script>
<script>
	<sec:authorize access="isAuthenticated()">
		fetch('/msg/footer')
	    .then(response => response.json())
	    .then(response => {
	        let result = response.map(room => room.chatRoomNum);
	        fetch("/msg/unread/count", {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify(result)
	        })
	        .then(res => res.json())
	        .then(res => {
	        	let unreadTotal = 0;
	        	result.forEach(room => {
	        		unreadTotal += res.unread[room];
	        	});
	        	let bdg = document.querySelector('.badge-footer-display');
	        	let count = unreadTotal;
	        	if (count > 9) {
	        		bdg.innerText = '9+';
	        	} else {
	        		bdg.innerText = count;
	        	}
	        })
	    });
	</sec:authorize>
</script>