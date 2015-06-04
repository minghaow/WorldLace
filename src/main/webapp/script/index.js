jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    var problemId = 0;
    $(".-order-release").hide();
    $(".-order-detail").hide();
    $("#" + thisTag).addClass("chosen");

    $("#login-button").click(function(event){
        if (!$("#username").val()){
            alert("email不能为空！");
            event.preventDefault(); // 阻止默认的表单提交行为
            return;
        }
        else if (!$("#password").val()) {
            alert("password不能为空！");
            event.preventDefault(); // 阻止默认的表单提交行为
            return;
        }
    })

    $(".-order-take-button").click(function(event){
        event.preventDefault();
        var id = $(this).attr('id');
        var url = "/order/hold";
        $.post(url, {"id":id},
            function(result){
                if (result.success == true) {
                    $("#-order-take-" + id).hide();
                    $("#-order-release-" + id).show();
                    $("#order-" + id).css("background-color", "#43ac6a");
                    $(".-order-detail-" + id).hide();
                } else {
                    if (result.reason) {
                        alert(result.reason);
                    }
                    location.reload();
                }
            }, "json");
    });

    $(".-order-info-button").click(function(event){
        event.preventDefault();
        $(".-order-detail-" + $(this).attr('id')).toggle();
    });

    // 报错
    $('.problem').change(function(event){
        event.preventDefault();
        event.preventDefault();
        var showId = $(this).data('id');
        var id = $(this).attr('id');
        var value = $(this).children('option:selected').val();
        var reason = $(this).children('option:selected').html();
        if (value == "OTHER") {
            problemId = id;
            $('#problemModal').foundation('reveal', 'open');
        } else if (confirm("确定订单\"" + showId + "\"" + reason + ", 无法下单？")) {
            sendProblemRequest(id, value, "");
        }
    });

    // 报错确认按钮
    $('.problem-confirm-button').click(function(event){
        event.preventDefault();
        var problemReasonInput = $('#problem-reason');
        var reason = problemReasonInput.val();
        problemReasonInput.val("");
        sendProblemRequest(problemId, "OTHER", reason);
    });

    // 报错取消按钮
    $('.problem-cancel-button').click(function(event){
        event.preventDefault();
        window.location.reload();
    });

    function sendProblemRequest(id, value, reason) {
        var url = "/order/problem";
        $.post(url, {"id": id, "verifyContent": value, "message": reason},
            function (result) {
                if (result.success == true) {
                    $('#problemModal').foundation('reveal', 'close');
                    $("#order-" + id).remove();
                    $(".-order-detail-" + id).remove();
                } else {
                    alert("报错失败！");
                }
            }, "json");
        setTimeout(function(){window.location.reload();}, 500);
    }

});

