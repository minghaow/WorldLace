jQuery( document ).ready(function( $ ) {

    var isLogined = $("#is-login").html() == "true";
    var notyf = new Notyf({delay:8000});

    $(".option").on('click', function() {
        var originPrice = $(this).data("origin");
        var price = $(this).data("now");
        var itemId = $(this).data("item-id");
        var skuId = $(this).data("sku-id");
        var optionId = $(this).data("option-id");
        $(".price-before").html("￥" + originPrice);
        $(".price-after").html("￥" + price);
        $(".detail-image-wrapper.active .detail-image").attr("src", "http://static.world-lace.com/images/sku/" + itemId + "/" + optionId + ".jpg?x-oss-process=style/item-detail");
        $(".detail-image-wrapper.active .detail-image-big").attr("src", "http://static.world-lace.com/images/sku/" + itemId + "/" + optionId + ".jpg?x-oss-process=style/item-detail");
        $(".add-to-cart").data("sku-id", skuId).data("url", "http://static.world-lace.com/images/sku/" + itemId + "/" + optionId + ".jpg?x-oss-process=style/item-detail");
        $(".option-button-wrapper button").removeClass("checked");
        $(this).addClass("checked");
    });

    $(".add-to-cart").on('click', function(event) {
        event.preventDefault();
        isLogined = $("#is-login").html() == "true";
        if (!isLogined) {
            $('.login-btn').trigger('click');
            return;
        }
        var skuId = $(this).data("sku-id");
        var count = $(this).data("count");
        var end = $("#cart").offset();
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
                    notyf.confirm("Add to cart successfully!");
                } else {
                    notyf.alert("Sorry，" + data.message);
                }
            }
        });
    });

    $(".add-to-wish").on('click', function(event) {
        event.preventDefault();
        isLogined = $("#is-login").html() == "true";
        if (!isLogined) {
            $('.login-btn').trigger('click');
            return;
        }
        var skuId = $(this).data("sku-id");
        $.ajax({
            url: "/wish/addToWish",
            type: "POST",
            data: {"skuId":skuId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    $(".heart-red").addClass("active");
                    notyf.confirm("Add to wishlist successfully!");
                } else {
                    notyf.alert("Sorry，" + data.message);
                }
            }
        });
    });

});
