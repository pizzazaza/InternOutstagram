"use strict"
/*
var Popup = (function () {
    init: function ($element) {
        this.initVariable($element);
        this.productAjax = new AjaxCall();
        this.emptyAndAddProducts(this.categoryId);
        this.$moreButton.on('click', this.moreProducts.bind(this));
        this.changeScroll();
    },
    initVariable: function($element){
        this.documentHeight = $(document).height();
        this.$window = $(window);
        this.url = $element.data('url');
        this.$categoryBlock = $element.find('.event_tab_lst');
        this.$productBlock = $element.find('.section_event_lst');
        this.$anchor = $element.find('.anchor.active');
        this.$rightUl = this.$productBlock.find('.lst_event_box').eq(0);
        this.$leftUl = this.$productBlock.find('.lst_event_box').eq(1);
        this.$moreButton = this.$productBlock.find('._more');
        this.$categoryBlock.on('click', this.toggleCategory.bind(this));
        this.page = 0;
        this.pageCount = 4;
        this.maxPage = this.$productBlock.find('.pink').data('product-count') / this.pageCount;
        this.categoryId = 1;
        this.SCROLL_LOADING_RATIO = 10;
    },
    changeScroll: function () {
        this.$window.scroll(function (e) {
            console.log(this.documentHeight + "도큐먼트 높이");
            console.log(window.innerHeight + "윈도우 이너높이");
            console.log((window.scrollY / 10) + "윈도우 스크롤y을 10으로 나눈 값");
            console.log(window.scrollY + "윈도우 스크롤y");

            if (this.documentHeight - window.innerHeight - (window.scrollY / this.SCROLL_LOADING_RATIO) < window.scrollY) {

                this.moreProducts();
            }
        }.bind(this));
    },
    moreProducts: function () {
        this.page++;
        this.changeProducts(this.categoryId, this.page);
    },


    changeProducts: function (categoryId, page) {
        if (page <= this.maxPage) {
            var productUrl = "/api/products/categories/" + categoryId + "/pages/" + page;
            this.ajaxCall({url: productUrl}, this.productAjax, this.appendProducts);
        }
    },

    ajaxCall: function (obj, ajaxObj, doSomething) {
        ajaxObj.ajax(obj, doSomething.bind(this));
    },
    appendProducts: function (data) {
        this.getProductsByCategory(data);
    },

    getProductsByCategory: function (data) {
        var left = [];
        var right = [];
        for (var i = 0; i < data.length; i++) {
            data[i].fileUrl = this.url + "/file/img?file_id=" + data[i].fileId;
            if (i % 2 === 0) {
                left.push(data[i]);
            } else {
                right.push(data[i]);
            }
        }
        left = productTemplate({data: left});
        right = productTemplate({data: right});
        this.$leftUl.append(left);
        this.$rightUl.append(right);
        this.productAjax.setCachedData(data);
    }
}*/