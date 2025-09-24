<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title></title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<c:import url="/WEB-INF/views/common/heartBeat.jsp"/>
	<script>
		Swal.fire({
		  text: "${ resultMsg }",
		  icon: "${ resultIcon }",
		  showCancelButton: false,
		  confirmButtonColor: "#3085d6",
		  confirmButtonText: "확인"
		}).then((result) => {
			const url = "${ resultUrl }"
			
		  if (url != null && url != "") {
			  let beat = "${ beat }"
			  if (beat != null & beat != "") {
				  let wsMsg = "${ wsMsg }"
				  stompClient.send("/pub/notify/" + beat, {}, wsMsg);
			  }
			  location.href = url
		  } else {
			  history.back()
		  }
		});
	</script>
</body>

</html>