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
				
			</table>
			
			<div class="paging_normal">
					<c:if test="${currentPageBlock ne '1'}">
					<a href="/BoardList?pageNum=<c:out value="${startPageNum - 1}"/>">Prev</a>
					</c:if>
					<c:forEach begin="${startPageNum}" end="${endPageNum}" step="1" var="pageNum" varStatus="status">
						<a href="/BoardList?pageNum=${pageNum}" <c:if test="${currentPageNum eq pageNum}">class="active"</c:if>>
							${pageNum}
						</a>
					</c:forEach>
					<c:if test="${currentPageBlock ne totalPageBlock}">
					<a href="/BoardList?pageNum=${endPageNum + 1}">Next</a>
					</c:if>
			</div>
			<div class="paging_mini">
					<c:if test="${currentPageNum ne '1'}">
					<a href="/BoardList?pageNum=<c:out value="${currentPageNum - 1}"/>">Prev</a>
					</c:if>
					&nbsp;&nbsp;&nbsp;
					<c:if test="${currentPageNum ne totalPageCnt}">
					<a href="/BoardList?pageNum=${currentPageNum + 1}">Next</a>
					</c:if>
			</div>
				
		</div>
		
		<input type="button" value="작성하기" onclick="location.href='/BoardInsert'" class="button" />

	</div>
	
</div>

<footer></footer>

</body>
</html>