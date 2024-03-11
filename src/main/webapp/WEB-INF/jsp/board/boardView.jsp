<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물조회하기</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/ckeditor.js"></script>
</head>
<body>

<jsp:include page="/header"></jsp:include>

<div id="main">

	<div id="content_all">
	
		<p class="viewTitle"><c:out value="${boardVO.title }" /></p>
		<p class="reginfo">
			<c:out value="${boardVO.regDate }" /> <c:out value="${boardVO.regId }" /> 오후 11:05
		</p>
		
		<div class="viewContent">
			<c:out value="${boardVO.content }" escapeXml="false" />
		</div>
		
		<input type="button" value="목록보기" onclick="location.href='/BoardList'" class="button" />
		<input type="button" value="수정하기" onclick="location.href='/BoardUpdate?seq=<c:out value="${boardVO.seq}" />'" class="button" />
		<input type="button" value="삭제하기" onclick="location.href='/BoardList'" class="button" />
		
	</div>
	
</div>

<footer></footer>

</body>
</html>