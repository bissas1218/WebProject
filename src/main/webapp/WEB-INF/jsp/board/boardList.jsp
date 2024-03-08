<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

</head>
<body>

<jsp:include page="/header"></jsp:include>

<div id="main">

	<div id="content_all">
		
		<p>게시글 목록</p>
		
		<hr/>
		
		<div id="boardList">
			<table>
				<thead>
					<tr>
						<th width="10%">번호</th>
						<th width="50%">제목</th>
						<th width="20%">작성자</th>
						<th width="20%">작성일</th>
					</tr>
				</thead>
				<tbody>
				
				<c:forEach items="${boardList}" var="boardList">
					<tr>
						<td align="center"><c:out value="${boardList.seq}" /></td>
						<td><c:out value="${boardList.title}" /></td>
						<td align="center"><c:out value="${boardList.regId}" /></td>
						<td align="center"><c:out value="${boardList.regDate}" /></td>
					</tr>
				</c:forEach>
					
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
						<c:forEach begin="1" end="${pageCnt}" step="1" var="pageNum" varStatus="status">
							<c:set var="pageLimit" value="${pageNum-1}" />
							<a href="/BoardList?pageNum=${pageLimit * pageListSize}">${pageNum}</a>
						</c:forEach>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		
		<input type="button" value="작성하기" onclick="location.href='/BoardInsert'" class="button" />

	</div>
	
</div>

<footer></footer>

</body>
</html>