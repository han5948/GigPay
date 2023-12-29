<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link href="<c:url value="/resources/common/css/dragdrop.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/common/js/dragdrop.js" />" charset="utf-8"></script>
<script type="text/javascript">

	$(document).ready(function() {
		fnOnLoad();
	});
</script>


<div class="content">
	<h2>파일업로드-HTML5</h2>
	<div id="files">
		<ul id="fileList">
			<li id="intro">
				<p class="file-info">업로드 대상 파일들을 드래그해주세요.</p>
			</li>
			<!-- 하기와 같은 정보가 동적으로 추가된다.
			<li>
				<div class="icon css"></div>
				<p class="file-info">파일명</p>
				<p class="file-info">(파일크기)</p>
			</li>
			<li>
				<div class="icon html"></div>
				<p class="file-info">파일명</p>
				<p class="file-info">(파일크기)</p>
			</li>
			 -->
		</ul>
	</div>
	<p><div id="wastebasket">업로드 취소할 파일들은 이곳으로 옮겨주세요</div></p>
	<P><div id="summary" data-total-count="0" data-total-size="0">0개 파일 [총 0 byte]</span></div></p>
	<p><center><progress id="progressBar" max="100" value="0"></progress><span id="progress"> 0%</span></center></p>
	<P><div id="resultList"><center>업로드 결과</center></div></p>
	<center><input type='button' onclick="fnAllClear();" value='전체삭제'/>
	<input id="uploadBtn" type="button" value="업로드" onclick="fnUpload()"/></center>
</div>