jQuery( document ).ready(function( $ ) {

    $(document).on('click', '.count-minus', function(event) {
        event.preventDefault();
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
                }
            });
        }
    });

    $(document).on('click', '.count-plus', function(event) {
        event.preventDefault();
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
        $("#total-count").html(0);
        $("#total-price").html(0);
        $(".checkbox-goods-item").each(function(n){
            if ($(this).prop("checked")) {
                var goodsId = $(this).data("id");
                var _count = $("#item-count-" + goodsId).val();
                var _total = $("#item-total-" + goodsId).html();
                $("#total-count").html(parseInt($("#total-count").html()) + parseInt(_count));
                $("#total-price").html((parseFloat($("#total-price").html()) + parseFloat(_total)).toFixed(2));
            }
        })
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
        } else {
            goodsIdList = goodsIdList.substring(0, goodsIdList.length-1);
        }

        $.ajax({
            url: "/cart/submit",
            type: "POST",
            data: {"goodsIdList":goodsIdList},
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

});
