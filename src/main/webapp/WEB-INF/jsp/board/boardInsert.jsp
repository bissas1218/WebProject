<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물등록하기</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<style>
  .ck-editor__editable { height: 400px; }
  .ck-content { font-size: 12px; }
</style>

</head>
<body>

<jsp:include page="/header"></jsp:include>

<div id="main">

	<div id="content_all">
	<form name="" id="" method="post" action="/BoardInsert">
	제목:<input type="text" name="title" id="title"></input>
	<textarea id="editor" name="content"></textarea>
	<input type="submit" value="등록하기"/>
	</form>
	</div>
	
	<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/ckeditor.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>
	
    <script>
    ClassicEditor.create( document.querySelector( '#editor' ), {
        language: "ko"
      } );
    </script>
</div>

<footer></footer>

</body>
</html>