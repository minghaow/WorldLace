jQuery( document ).ready(function( $ ) {

    var isLogined = $("#is-login").html() == "true";
    var notyf = new Notyf({delay:3000});

    $(".option").on('click', function() {
        var originPrice = $(this).data("origin");
        var price = $(this).data("now");
        var itemId = $(this).data("item-id");
        var skuId = $(this).data("sku-id");
        var optionId = $(this).data("option-id");
        $(".price-before").html("￥" + originPrice);
        $(".price-after").html("￥" + price);
        $(".detail-image-wrapper.active .detail-image").attr("src", "http://image-cdn.zaitaoyuan.com/images/item/itemOption/" + itemId + "/" + optionId + ".jpg@!item-head-4");
        $(".detail-image-wrapper.active .detail-image-big").attr("src", "http://image-cdn.zaitaoyuan.com/images/item/itemOption/" + itemId + "/" + optionId + ".jpg@!item-head-4");
        $(".add-to-cart").data("sku-id", skuId).data("url", "http://image-cdn.zaitaoyuan.com/images/item/itemOption/" + itemId + "/" + optionId + ".jpg@!item-head-thumb-1");
        $(".option-button-wrapper button").removeClass("checked");
        $(this).addClass("checked");
    });

    $(".add-to-cart").on('click', function(event) {
        event.preventDefault();
        if (!isLogined) {
            $('.login-btn').trigger('click');
            return;
        }
        var skuId = $(this).data("sku-id");
        var count = $(this).data("count");
        var end = $("#sticky-cart").offset();
        var start =  $(".add-to-cart").offset();
        $.ajax({
            url: "/item/addToCart",
            type: "POST",
            data: {"skuId":skuId, "count": count},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    $(".cart-count").html(data.cnt);
                    var img = $(".add-to-cart").data("url");
                    var flyer = $('<img class="u-flyer" src="'+img+'">');
                    flyer.fly({
                        start: {
                            left: start.left,
                            top: start.top - window.scrollY
                        },
                        end: {
                            left: end.left,
                            top: end.top - window.scrollY,
                            width: 0,
                            height: 0
                        },
                        onEnd: function(){
                            this.destory();
                        }
                    });
                    notyf.confirm("添加购物车成功!");
                } else {
                    notyf.alert("抱歉，" + data.message);
                }
            }
        });
    });

});
