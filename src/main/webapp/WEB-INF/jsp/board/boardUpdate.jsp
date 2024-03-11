<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물수정하기</title>
<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1, user-scalable=yes,initial-scale=1.0" />

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/media.css" />

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="/js/uploadAdpater.js"></script>

<style>
  .ck-editor__editable { height: 500px; }
  .ck-content { font-size: 12px; }
</style>

<script type="text/javascript">

	function boardUpdateChk(){
		
		if($("#title").val() == ''){
			alert('게시물 제목을 입력해주세요.');
			$("#title").focus();
			return false;
		}
		
		if(editor.getData() == ''){
			alert('게시물 내용을 입력해주세요.');
			editor.focus();
			return false;
		}
		
		return true;
	}
</script>

</head>
<body>

<jsp:include page="/header"></jsp:include>

<div id="main">

	<div id="content_all">
	<form name="" id="" method="post" action="/BoardUpdate" onsubmit="return boardUpdateChk();">
		<input type="hidden" name="seq" id="seq" value="<c:out value="${boardVO.seq}" />" />
		
		<p class="boardTitle">게시글 수정하기</p>
		
		<hr/>
		
		<div class="fields">
			<label>제목</label>
			<input type="text" name="title" id="title" value="<c:out value="${boardVO.title}" />"></input>
		</div>
	
		<div class="fields">
			<label>내용</label>
		</div>
		
		<div class="fields">
			<textarea id="editor" name="content"><c:out value="${boardVO.content}" /></textarea>
		</div>
		
		<input type="submit" value="수정하기" class="button" />
		<input type="button" value="취소하기" class="button" onclick="location.href='/BoardList'"/>
		
	</form>
	</div>
	
	<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/ckeditor.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>
	
    <script>
    
    function MyCustomUploadAdapterPlugin(editor) {
        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            return new UploadAdapter(loader)
        }
    }

    let editor;
    ClassicEditor.create( document.querySelector( '#editor' ), {
        language: "ko",
        extraPlugins: [MyCustomUploadAdapterPlugin]
      } )
      .then( newEditor => {
    	    editor = newEditor;
      } )
      .catch( error => {
        console.error( error );
      } );
    </script>
</div>

<footer></footer>

</body>
</html>