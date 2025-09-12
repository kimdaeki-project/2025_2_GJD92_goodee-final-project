<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재고관리 물품리스트 작성 페이지</title>
</head>
<body>
	<h1>재고관리 물품리스트 작성 페이지</h1>
	<form action="/product/write" method="post">
		<p>물품타입 : </p>
		<select name="productType">
			<option selected>선택</option>
			<option value="1">기념품</option>
			<option value="2">수리소모품</option>
		</select>
		
		<p>물품명 : </p>
		<input type="text" name="productName">
		
		<button type="submit">등록</button>
	</form>
<!-- 		파일첨부기능 -->
	</div>
</body>
</html>