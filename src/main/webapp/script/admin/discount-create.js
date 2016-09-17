jQuery( document ).ready(function( $ ) {

    $("#discount-btn").on('click', function() {
        event.preventDefault();
        var type = $("#discount-type").val();
        var code = $("#discount-code").val();
        var amount = $("#discount-amount").val();
        var percent = $("#discount-percent").val();
        var limit = $("#discount-limit").val();
        var times = $("#discount-times").val();
        $.ajax({
            url: "/admin/discount/create/confirm",
            type: "POST",
            data: {"type":type, "code":code, "amount":amount, "percentage":percent, "limit":limit, "times":times},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    presentSuccessModal("生成成功！", "此优惠已可以使用");
                } else {
                    presentFailModal(data.reason, "[  抱歉  ]");
                }
            }
        });
    });

    $(".shipping").on('click', function() {
        event.preventDefault();
        var orderId = $(this).data("id");
        $.ajax({
            url: "/admin/order/shipping",
            type: "POST",
            data: {"orderId":orderId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    presentSuccessModal("发货成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal(data.reason, "[  抱歉  ]");
                }
            }
        });
    });

    $(".cancel").on('click', function() {
        event.preventDefault();
        var orderId = $(this).data("id");
        $.ajax({
            url: "/admin/order/cancel",
            type: "POST",
            data: {"orderId":orderId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    presentSuccessModal("订单取消成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal(data.reason, "[  抱歉  ]");
                }
            }
        });
    });

    var $successModal = $("#successModal"),
        $successHdr = $("#successHdr"),
        $successMsg = $("#successMsg"),
        $failModal = $("#failModal"),
        $failHdr = $("#failHdr"),
        $failMsg = $("#failMsg");

    function presentSuccessModal(header, message) {
        alert(header + "," + message);
        //$successHdr.html(header);
        //$successMsg.html(message);
        //$successModal.foundation("open");
    }

    function presentFailModal(header, message) {
        alert(header + "," + message);
        //$failHdr.html(header);
        //$failMsg.html(message);
        //$failModal.foundation('open');
    }

    function hideSuccessModal() {
        $successModal.foundation("close");
    }

    function hideFailModal() {
        $failModal.foundation('close');
    }

});

