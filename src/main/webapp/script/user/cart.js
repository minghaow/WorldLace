jQuery( document ).ready(function( $ ) {

    var countLock = false;
    var isDiscountCodeVerified = false;

    $(document).on('click', '.count-minus', function(event) {
        event.preventDefault();
        if (countLock) {
            return;
        }
        countLock = true;
        var id = $(this).find("i").data('id');
        var countDiv = $("#item-count-" + id);
        var totalDiv = $("#item-total-" + id);
        var currentValue = parseInt(countDiv.val());
        var currentTotal = parseFloat(totalDiv.html());
        var step = parseFloat(totalDiv.data("step"));
        if (currentValue > 1) {
            $.ajax({
                url: "/cart/goods/minus",
                type: "POST",
                data: {"goodsId":id},
                dataType: 'json',
                success: function(data) {
                    if (data.success == true || data.success == "true") {
                        countDiv.val(currentValue - 1);
                        totalDiv.html((currentTotal - step).toFixed(2));
                        updateCountAndPrice();
                    } else {
                        presentFailModal(data.message, "[  抱歉  ]");
                    }
                    countLock = false;
                }
            });
        }
    });

    $(document).on('click', '.count-plus', function(event) {
        event.preventDefault();
        if (countLock) {
            return;
        }
        countLock = true;
        var id = $(this).find("i").data("id");
        var countDiv = $("#item-count-" + id);
        var totalDiv = $("#item-total-" + id);
        var max = countDiv.data('rest');
        var currentValue = parseInt(countDiv.val());
        var currentTotal = parseFloat(totalDiv.html());
        var step = parseFloat(totalDiv.data("step"));
        if (currentValue < max) {
            $.ajax({
                url: "/cart/goods/add",
                type: "POST",
                data: {"goodsId":id},
                dataType: 'json',
                success: function(data) {
                    if (data.success == true || data.success == "true") {
                        countDiv.val(currentValue + 1);
                        totalDiv.html((step + currentTotal).toFixed(2));
                        updateCountAndPrice();
                    } else {
                        presentFailModal(data.message, "[  抱歉  ]");
                    }
                    countLock = false;
                }
            });
        } else {
            alert("已达商品数量上限");
        }
    });

    $(".checkbox-goods-all").on('change', function() {
        $(".checkbox-goods").prop("checked", $(this).prop('checked'));
        updateCountAndPrice();
    });

    $(".checkbox-goods-item").on('change', function() {
        updateCountAndPrice();
    });

    function updateCountAndPrice() {
        var totalPriceDiv =  $("#total-price");
        $("#total-count").html(0);
        totalPriceDiv.html(0);
        totalPriceDiv.data("discount", 0);
        $("#discount").html((0).toFixed(2));
        $(".checkbox-goods-item").each(function(n){
            if ($(this).prop("checked")) {
                var goodsId = $(this).data("id");
                var _count = $("#item-count-" + goodsId).val();
                var _total = $("#item-total-" + goodsId).html();
                $("#total-count").html(parseInt($("#total-count").html()) + parseInt(_count));
                var newTotal = (parseFloat(totalPriceDiv.html()) + parseFloat(_total)).toFixed(2);
                totalPriceDiv.data("total", newTotal);
                totalPriceDiv.html(newTotal);
                isDiscountCodeVerified = false;
            }
        });
    }

    $(".item-del").on('click', function() {
        event.preventDefault();
        var goodsId = $(this).data("id");
        $.ajax({
            url: "/cart/goods/del",
            type: "POST",
            data: {"skuId":goodsId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    $("#cart-goods-" + goodsId).remove();
                    updateCountAndPrice();
                } else {
                    presentFailModal(data.message, "[  抱歉  ]");
                }
            }
        });
    });

    $("#jz2").on('click', function() {
        event.preventDefault();
        var goodsIdList = "";
        $(".checkbox-goods-item").each(function(n){
            if ($(this).prop("checked")) {
                var goodsId = $(this).data("id");
                goodsIdList += goodsId + ",";
            }
        });

        if (goodsIdList == "") {
            presentFailModal("请选择要结算的商品", "[  谢谢  ]");
            return;
        } else {
            goodsIdList = goodsIdList.substring(0, goodsIdList.length-1);
        }

        var discountCode = $("#discount-code").val();
        if (discountCode != "" && !isDiscountCodeVerified) {
            presentFailModal("如若使用优惠券，请先点击使用按钮", "[  谢谢  ]");
            return;
        }

        $.ajax({
            url: "/cart/submit",
            type: "POST",
            data: {"goodsIdList":goodsIdList, "code":discountCode},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    window.location.href = "/order/process?orderId=" + data.orderId;
                } else {
                    presentFailModal(data.message, "[  抱歉  ]");
                }
            }
        });
    });

    $(".discount-code-btn").on('click', function() {
        event.preventDefault();
        var goodsIdList = "";
        $(".checkbox-goods-item").each(function(n){
            if ($(this).prop("checked")) {
                var goodsId = $(this).data("id");
                goodsIdList += goodsId + ",";
            }
        });

        if (goodsIdList == "") {
            presentFailModal("请选择要结算的商品", "[  谢谢  ]");
            return;
        } else {
            goodsIdList = goodsIdList.substring(0, goodsIdList.length-1);
        }

        var discountCode = $("#discount-code").val();
        if (discountCode == "") {
            presentFailModal("请输入优惠券码后再试", "[  谢谢  ]");
            return;
        }

        var total = $("#total-price").html();
        $.ajax({
            url: "/cart/discount-code",
            type: "POST",
            data: {"total":total, "goodsIdList":goodsIdList, "code":discountCode},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    var totalPriceDiv = $("#total-price");
                    $("#discount").html(data.value);
                    var total = parseFloat(totalPriceDiv.data("total"));
                    var discount = parseFloat(data.value);
                    totalPriceDiv.data("discount", discount).html((total - discount).toFixed(2));
                    isDiscountCodeVerified = true;
                } else {
                    presentFailModal(data.message, "[  抱歉  ]");
                }
            }
        });
    });

});
