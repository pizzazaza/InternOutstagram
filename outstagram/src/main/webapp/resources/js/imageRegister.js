"use strict"

var ThumnailTemplate = '<li class="item" data-file-num="{{fileNum}}"><a href="#" class="anchor">'
        + '<span class="spr_book ico_del">삭제</span>'
		+ '</a> <img src="{{fileLocation}}"'
		+ 'width="130" alt="" class="item_thumb"> <span class="img_border"></span>'
		+ '</li>'
ThumnailTemplate = Handlebars.compile(ThumnailTemplate);

$('.review_write_info').on('click', function () {
    $(this).addClass('invisible');
    $('.review_textarea').focus();
});

$('.review_textarea').keyup(function (e) {
    var textLength = $(this).val().length;
    if (textLength > 400) {
        alert('400자를 초과하여 작성하실 수 없습니다.');
        $(this).val($(this).val().substring(0, 400));
        textLength = 400;
    }
    $('.guide_review span:first-child').text(textLength);
});


var fileNum = 0;
var files = []

function getThumbnailPrivew(html) {
    if (html.files && html.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var fileObj = {};
            fileObj.fileNum = fileNum;
            fileObj.fileLocation = e.target.result;
            console.log(fileObj.fileLocation);
            $('.lst_thumb').prepend(ThumnailTemplate(fileObj));
            $('.lst_thumb :first-child').find('.ico_del').closest('.anchor').on('click', function () {
                files[$(this).closest('.item').data('file-num')] = null;
                $(this).closest('.item').remove();
            });
            files[fileNum] = html.files[0];
            fileNum++;
        }
        reader.readAsDataURL(html.files[0]);
    }
}

$('#reviewImageFileOpenInput').on('change', function (e) {
    getThumbnailPrivew(this);
});

$('.bk_btn').on('click', function (e) {
    var fd = new FormData();
    var $write = $('.write_act');
    fd.append('userId', $write.data('user-id'));
    fd.append('context', $('.review_textarea').val());
    for (var i = 0; i < files.length; i++) {
        if (files[i] != null) {
            fd.append('multipartFile', files[i]);
        }
    }

    $.ajax({
        type: 'post',
        url: '/upload',
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        data: fd,
        success: function (res) {
            alert('게시물이 정상적으로 등록 되었습니다.');
            window.close(); 
        },
        error: function (res) {
            alert('에러가 발생했습니다.');
            //console.log(res);
            window.close(); 
        }
    });
});