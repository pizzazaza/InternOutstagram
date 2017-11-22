<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.intern.outstagram.domain.dto.PostViewDto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../../../favicon.ico">

<title>Outstagram</title>

<!-- Bootstrap core CSS -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->

<link href="/resources/css/w3css.css" rel="stylesheet">
<link href="/resources/css/offcanvas.css" rel="stylesheet">

</head>
<body>

	<div class="navbar">
		<div class="navbar-inner">
			<a class="brand" href="/">Outstagram</a>
			<ul class="nav">
				<li class="post-category new active" data-category="new"><a href="#">New</a></li>
				<li class="post-category like" data-category="like"><a href="#">Like</a></li>
				<li class="post-category recommend" data-category="recommend"><a href="#">Recommend</a></li>
				<!-- 
				<li><a href="#" onClick="window.open('/upload','_blank','toolbar=no,location=no,status=no,menubar=no,width=414,height=600,top=100,left=100')">Upload</a></li>
				 -->
			</ul>
		</div>
	</div>

	<div class="container">

		<div class="jumbotron span12 margin-no">
			<div class="span3 margin-no">
				<h1 class="margin-no">Outstagram</h1>
			</div>
			<div class="offset10 btn-upload">
				<a href="#" class="btn btn-large" id="upload-btn" data-user-info="${userSeq}" data-post-offset="${offset}"
					onClick="window.open('/upload','_blank','toolbar=no,location=no,'+
					'status=no,menubar=no,width=414,height=600,top=100,left=100')">Upload</a>
			</div>
		</div>
		<div id="post-body">
			<c:forEach var="post" items="${postViewDtoList}" varStatus="status">
			<c:if test="${status.index%3==0}">
				<ul class="thumbnails">
			</c:if>
				<li class="span4" data-post-info="${post.getSeq()}">
					<div class="thumbnail">
						<p >${post.getUserId()}</p>
						<a href="#" class="thumb" title="image 크게 보기"> 
						<img class="thumbnail-size posted-thumb" src="${post.getPath()}" alt="등록된 게시물">
						</a>
						<h4 class="post-context">${post.getContext()}</h4>
						<p>
							<c:choose>
							<c:when test="${!post.getIsLike()}">
							<a href="#" class="btn like-btn">좋아요 <span>${post.getLike()}</span>개</a> 
							</c:when>
							<c:when test="${post.getIsLike()}">
							<a href="#" class="btn like-btn active">좋아요 <span>${post.getLike()}</span>개</a> 
							</c:when>
							</c:choose>
							<a href="#"class="btn view-btn">조회 <span>${post.getView()}</span>회</a>
						</p>
					</div>
				</li>
			<c:if
				test="${status.index%3==2 or fn:length(postViewDtoList) == status.count}">
				</ul>
			</c:if>
			</c:forEach>
		</div>
		<hr>
	</div>

	<footer>
		<p> © Intern 2017 </p>
	</footer>
	<jsp:include page="include/popUpLayer.jsp" flush="false" />


	<script src="/resources/js/jquery.min.js"></script>
	<script src="/resources/js/handlebars.min.js"></script>

	<script src="/resources/js/bootstrap.min.js"></script>

	<script src="/resources/js/popUplayer.js"></script>
</body>

</body>
</html>