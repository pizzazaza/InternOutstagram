(function(window) {
    "use strict"

    var mainPage = (function () {
        var likeBtnFunction = function(){
            $('.like-btn').on('click', function(e){
                e.preventDefault();
                var userSeq = $('#upload-btn').data('user-info');
                var likeBtn = $(this);
                var post = likeBtn.closest('li');
                var postSeq = post.data('post-info');
                var like = likeBtn.find('span');
                var likeNum = like[0].innerText;
                likeNum = likeNum-0;
                
                //var params = new Object();
                var state;
                var likeCount
                
                if(likeBtn.hasClass('active')){
                    like.text(likeNum-1);
                    likeBtn.removeClass('active');
                    likeCount = likeNum-1;
                    state = false;
                }
                else{
                    like.text(likeNum+1);
                    likeBtn.addClass('active');
                    likeCount = likeNum+1;
                    state = true;
                }
                var params = {userSeq: userSeq,likeCount: likeCount,postSeq: postSeq, state: state};
            
                params = JSON.stringify(params);

                $.ajax({
                    method: "post",
                    url: "/post/like",
                    Accept: 'application/json',
                    contentType: "application/json",
                    data: params,
                    success: function (res) {
         
                    }
                });
                
            });
        }
        var categoryMoveFunction = function(){
            var pageli = '<li class="span4" data-post-info="{{postSeq}}">'+
            '<div class="thumbnail">'+
                '<p>{{userId}}</p>'+
                '<a href="#" class="thumb" title="image 크게 보기"> '+
                    '<img class="thumbnail-size posted-thumb" src="{{path}}" alt="등록된 게시물">'+
                '</a>'+
                '<h4 class="post-context">{{context}}</h4>'+
                '<p>'+
                    '<a href="#" class="btn like-btn {{isLike}}">좋아요 <span>{{like}}</span>개</a> '+
                    '<a href="#" class="btn view-btn">조회 <span>{{view}}</span>회</a>'+
                '</p>'+
                '</div>'+
            '</li>';

            var liTemplate = Handlebars.compile(pageli);


            $('.post-category').on('click', function(){
                if($(this).hasClass('active')){
                    return;
                }
                var userSeq = $('#upload-btn').data('user-info');
                var category = $(this);
                var cList = category.siblings();
                for(var i = 0; i < 3; i++){
                    if($(cList[i]).hasClass('active')){
                        $(cList[i]).removeClass('active');
                        break;
                    }
                }
                category.addClass('active');
                var navCase = category.data('category')+"?userSeq="+userSeq;
                
                $.ajax({
                    method: "get",
                    url: "/timeline/"+navCase,
                    contentType: "application/json",
                    success: function(res){
                       // res = JSON.parse(res);
                        var resSize = res.length;
                        var ul = [];
                        var lis = [];
                        $('#post-body').empty();
                        for(var u = 0; u < resSize/3; u++){
                            
                            for(var l = 0; l < 3; l++){
                                if(u*3+l >= resSize)
                                break;
                                lis[l] = liTemplate(res[u*3+l])
                            }
                            ul[u] = '<ul class="thumbnails">' + lis.join('') + '</ul>';
                            lis = [];
                        }
                        $('#post-body').append(ul.join(''));
                        $('#upload-btn').data('post-offset', 12);
                        eventOffFunction();
                        likeBtnFunction();
                        thumbnailClickFunction();
                        lazyLoadingFunction();
                        categoryMoveFunction();
                    }

                });
            });
        }

        var lazyLoadingFunction = function(){
            var pageli = '<li class="span4" data-post-info="{{postSeq}}">'+
            '<div class="thumbnail">'+
                '<p>{{userId}}</p>'+
                '<a href="#" class="thumb" title="image 크게 보기"> '+
                    '<img class="thumbnail-size posted-thumb" src="{{path}}" alt="등록된 게시물">'+
                '</a>'+
                '<h4 class="post-context">{{context}}</h4>'+
                '<p>'+
                    '<a href="#" class="btn like-btn {{isLike}}">좋아요 <span>{{like}}</span>개</a> '+
                    '<a href="#" class="btn view-btn">조회 <span>{{view}}</span>회</a>'+
                '</p>'+
                '</div>'+
            '</li>';

            var liTemplate = Handlebars.compile(pageli);

            $(window).scroll(function(e){
                var documentHeight = $(document).height();
                if (documentHeight - window.innerHeight <= window.scrollY) {
                    var category = $('.nav > .active').data('category');
                    if(category === "recommend"){
                        return;
                    }
                    $(window).unbind('scroll');
                    

                    var userSeq = $('#upload-btn').data('user-info');
                    var offset = $('#upload-btn').data('post-offset');
                    
                    var navCase = category + "?userSeq=" + userSeq + "&offset=" + offset;

                    $.ajax({
                        method: "get",
                        url: "/timeline/"+navCase,
                        contentType: "application/json",
                        success: function(res){
                            //res = JSON.parse(res);
                            var resSize = res.length;
                            var ul = [];
                            var lis = [];
                          
                            for(var u = 0; u < resSize/3; u++){
                                
                                for(var l = 0; l < 3; l++){
                                    if(u*3+l >= resSize)
                                        break;
                                    lis[l] = liTemplate(res[u*3+l])
                                }
                                ul[u] = '<ul class="thumbnails">' + lis.join('') + '</ul>';
                                lis = [];
                            }
                            $('#post-body').append(ul.join(''));
                           
                            $('#upload-btn').data('post-offset', offset+resSize);
                            eventOffFunction();
                            likeBtnFunction();
                            thumbnailClickFunction();
                            lazyLoadingFunction();
                            categoryMoveFunction();
                        
                        }
                    });
                }
            }).bind(this);
        }

        var thumbnailClickFunction = function(){
            $('.thumbnail-size').on('click', function(e) {
                e.preventDefault();
                var openTime = new Date();
                var userSeq = $('#upload-btn').data('user-info');
                var seq = -1;
                var thumbnail = $(this);
                var post = thumbnail.closest('li');
                var postSeq = post.data('post-info');
                var count = 1;//$(this).siblings('.img_count').text();
                //var commentId = $(this).attr('id');
                var viewBtn = $(this).closest('.thumbnail').find('.view-btn').find('span');
                var viewCount =  viewBtn[0].innerText - 0;
                viewCount = viewCount + 1;
                viewBtn.text(viewCount);
                
                var countElement = $('.img-popup-layer.count span')
                
                $('div.img-popup-layer').show();
                $('#original-popup-layer').css('top', window.pageYOffset+'px');

                var params = {userSeq: userSeq, postSeq: postSeq, viewCount: viewCount};
                params = JSON.stringify(params);

                //view set
                $.ajax({
                    method: "post",
                    url: "/post/view",
                    Accept: 'application/json',
                    contentType: "application/json",
                    data: params,
                    success: function (res) {
                       
                        seq = res;
          
                    }
                });

                var findElement = $('.img-popup-layer.img-viewer');
                
                var isDragging = false;
                var isChanged = false;
                var dX;
                var curX;
                var startX;
                var originOffset;
                var images = new Array();
                var curImage = 0;
                findElement.find('img').attr('src', $(this).attr('src'));
                //findElement.find('p').text($($(this).parent().siblings()[1]).text());
               
                //view 클릭 조회 카운트 증가 이미지관련 정보 받고 context도 받아서 나중에 뿌리기
                
                $.ajax({
                    method: 'GET',
                    url: '/post?postSeq=' + postSeq,
                    contentType: 'application/json',
                    success: function (res) {
                        res = JSON.parse(res)
                        res = res.fileSeq;
                        count = res.length;
                        countElement.text('1 / ' + count);
                        for (var i = 0; i < res.length; i++) {
                            images[i] = res[i];
                        }
                        if (count > 1) {
                            findElement.on('mousedown touchstart', function (e) {
                                e.preventDefault();
                                isDragging = true;
                                if (e.type == 'touchstart') {
                                    e = e.originalEvent.touches[0];
                                }
                                startX = e.clientX;
                                curX = e.clientX - this.offsetLeft;
                                originOffset = this.offsetLeft;
                            });
                            findElement.on('mousemove touchmove', function (e) {
                                if (e.type == 'touchmove') {
                                    e = e.originalEvent.touches[0];
                                    if(e.identifier !== this.identifier){
                                        return;
                                    }
                                }
                                if (isDragging && !isChanged) {
                                    
                                    //var dX = e.clientX - curX;
                                    curX = e.clientX;
                                    dX = curX - startX;
                            
                                    if (dX > 0) {
                                        if (curImage === 0) {
                                            return;
                                        }
                                    }
                                    if (dX < 0) {
                                    
                                        if (curImage === count - 1) {
                                            return;
                                        }
                                    }
                                
                                    findElement.css({
                                        "left": dX + "px"
                                    });
                                    if (e.type == 'mousemove') {
                                        e.preventDefault();
                                    }
                                }
                            });
                            findElement.on('mouseup touchend', function (e) {
                                var fileUrl = 'http://localhost:8001/fileserver?fileSeq=';
                                if (dX < 360) {
                                    curImage++;
                                    if (curImage < count) {
                                        $(this).find('img').attr('src', fileUrl + images[curImage]);
                                        countElement.text((curImage + 1) + ' / ' + count);
                                        isDragging = false;
                                        isChanged = true;
                                        findElement.css({
                                            "left": originOffset + "px"
                                        });
                                    } else {
                                        curImage = count - 1;
                                    }
                                } else if (dX > -360) {
                                    curImage--;
                                    if (curImage >= 0) {
                                        $(this).find('img').attr('src', fileUrl + images[curImage]);
                                        countElement.text((curImage + 1) + ' / ' + count);
                                        isDragging = false;
                                        isChanged = true;
                                        findElement.css({
                                            "left": originOffset + "px"
                                        });
                                    } else {
                                        curImage = 0;
                                    }

                                }
                                isDragging = false;
                                isChanged = false;
                                findElement.css({
                                    "left": originOffset + "px"
                                });
                                dX = 0;
                            });
                        }
                    }
                });
        
                $('.img-popup-layer.exit').on('click', function(e) {
                    e.preventDefault();
                    $('div.img-popup-layer').hide();
                    var closeDate = new Date();
                    
                    var stayTime = (closeDate.getTime() - openTime.getTime())/1000;
            
                    var timeData = {seq: seq, stayTime: stayTime};
                    timeData = JSON.stringify(timeData);
                
                    $.ajax({
                        method: "post",
                        url: "/post/viewtime",
                        Accept: 'application/json',
                        contentType: "application/json",
                        data: timeData,
                        success: function (res) {
         
                        }
                    });
                });
                window.onbeforeunload = function() {
                    $('div.img-popup-layer').hide();
                    var closeDate = new Date();
                    
                    var stayTime = (closeDate.getTime() - openTime.getTime())/1000;
                
                    var timeData = {seq: seq, stayTime: stayTime};
                    timeData = JSON.stringify(timeData);
            
                    $.ajax({
                        method: "post",
                        url: "/post/viewtime",
                        Accept: 'application/json',
                        contentType: "application/json",
                        data: timeData,
                        success: function (res) {
                
                        }
                    });
                };
            
            });
        }

        var eventOffFunction = function(){
            $(window).unbind('scroll');
            $('.like-btn').off('click');
            $('.post-category').off('click');
            $('.thumbnail-size').off('click');
        }

        return {
            likeBtnFunction: likeBtnFunction,
            categoryMoveFunction: categoryMoveFunction,
            thumbnailClickFunction: thumbnailClickFunction,
            lazyLoadingFunction: lazyLoadingFunction
        }
    })();
    mainPage.likeBtnFunction();
    mainPage.categoryMoveFunction();
    mainPage.thumbnailClickFunction();
    mainPage.lazyLoadingFunction();

})(window);