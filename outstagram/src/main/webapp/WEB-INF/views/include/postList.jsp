<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<ul class="thumbnails">
	<li class="span4" data-post-info="{{}}">
		<div class="thumbnail">
			<a href="#" class="thumb" title="image 크게 보기"> 
			<img class="thumbnail-size posted-thumb" src="{{}}" alt="등록된 게시물">
			</a>
			<h3>{{}}</h3>
			<p class="post-context">test</p>
			<p>
				<a href="#" class="btn like-btn {{}}">좋아요 <span>{{}}</span>개</a> 
				<a href="#" class="btn view-btn">조회 <span>{{}}</span>회</a>
			</p>
		</div>
	</li>
	<li class="span4" data-post-info="3593">
		<div class="thumbnail">
			<a href="#" class="thumb" title="image 크게 보기"> 
			<img class="thumbnail-size posted-thumb" src="http://localhost:8002/image?postSeq=3593" alt="등록된 게시물">
			</a>
			<h3>Sejun</h3>
			<p class="post-context">test123</p>
			<p>
				<a href="#" class="btn like-btn">좋아요 <span>0</span>개</a> 
				<a href="#" class="btn view-btn">조회 <span>1</span>회</a>
			</p>
		</div>
	</li>
	<li class="span4" data-post-info="3590">
		<div class="thumbnail">
			<a href="#" class="thumb" title="image 크게 보기"> 
			<img class="thumbnail-size posted-thumb" src="http://localhost:8002/image?postSeq=3590" alt="등록된 게시물">
			</a>

			<h3>danielberehulak</h3>
			<p class="post-context">{{}}</p>
			<p>
				<a href="#" class="btn like-btn">좋아요 <span>981</span>개</a> 
				<a href="#" class="btn view-btn">조회 <span>3467</span>회</a>
			</p>
		</div>
	</li>
</ul>