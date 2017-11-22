<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
	<meta charset="utf-8">
	<meta name="description" content="image 등">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
	<title>사진 등록</title>
	<link href="/resources/css/imageRegister.css" rel="stylesheet">	 
    <link href="/resources/css/style.css" rel="stylesheet">	

</head>

<body>
	<div id="container">
		<div class="ct">
			<div class="ct_wrap">
				<div class="top_title review_header">
					<a href="#" class="btn_back" title="이전 화면으로 이동"> <i class="fn fn-backward1"></i> </a>
					<h2><span class="title">게시물 작성</span></h2>
				</div>
				<!-- 리뷰 입력 -->
				<div class="review_contents write write_act" data-user-id="${userId}">
					<!-- [D] review_write_info 클릭 시 자신을 숨기고 review_textarea 에 focus를 보낸다. -->
					<a href="#" class="review_write_info">
						<span class="middot">
							내용을 입력해주세요 
						</span><br>
						
						<span class="left_space">(해시테그를 통해 글을 분류해주세요.)</span>
					</a>
					<textarea cols="30" rows="10" class="review_textarea"></textarea>
				</div>
				<!-- //리뷰 입력 -->

				<!-- 리뷰 작성 푸터 -->
				<div class="review_write_footer_wrap">
					<div class="review_write_footer">
						<label class="btn_upload" for="reviewImageFileOpenInput">
							<i class="fn fn-image1" aria-hidden="true"></i>
							<span class="text_add_photo">사진 추가</span>
						</label>
						<input type="file" class="hidden_input" id="reviewImageFileOpenInput" accept="image/*" multiple>
						<div class="guide_review">
							<span>0</span>/400
							<span>(최소5자이상)</span>
						</div>
					</div>

					<!-- 리뷰 포토 -->
					<div class="review_photos review_photos_write">
						<div class="item_preview_thumbs">
							<ul class="lst_thumb">
							
							</ul>
						</div>
					</div>
					<!-- //리뷰 포토 -->

				</div>
				<!-- //리뷰 작성 푸터 -->

				<!-- 리뷰 등록 -->
				<div class="box_bk_btn">
					<button class="bk_btn"><span class="btn_txt">사진 등록</span></button>
				</div>
				<!-- //리뷰 등록 -->
			</div>
		</div>
	</div>
	<footer>
		<div class="gototop">
			<a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span> </a>
		</div>
		<div id="footer" class="footer">
			<span class="copyright">© Naver Intern 2017</span>
		</div>
	</footer>
	<script src="/resources/js/jquery.min.js"></script>
	<script src="/resources/js/handlebars.min.js"></script>
	<script src="/resources/js/imageRegister.js"></script>
</body>

</html>