jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    $("#" + thisTag).addClass("chosen");
    var $successModal = $("#successModal"),
        $successHdr = $("#successHdr"),
        $successMsg = $("#successMsg"),
        $failModal = $("#failModal"),
        $failHdr = $("#failHdr"),
        $failMsg = $("#failMsg");

    $(".delete-btn").click(function(event){
        event.preventDefault();
        var lookId = $(this).data("id");
        var url = "/admin/operation/look/delete"
        $.ajax({
            url: url,
            type: "GET",
            data: {lookId: lookId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    presentSuccessModal("删除成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal("删除失败", "错误原因：" + data.message);
                }
            }
        });
    })

    $(".online-btn").click(function(event){
        event.preventDefault();
        var lookId = $(this).data("id");
        var url = "/admin/operation/look/online"
        $.ajax({
            url: url,
            type: "GET",
            data: {lookId: lookId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    presentSuccessModal("上线成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal("删除失败", "错误原因：" + data.message);
                }
            }
        });
    })

    $(".offline-btn").click(function(event){
        event.preventDefault();
        var lookId = $(this).data("id");
        var url = "/admin/operation/look/offline"
        $.ajax({
            url: url,
            type: "GET",
            data: {lookId: lookId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    presentSuccessModal("下线成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal("删除失败", "错误原因：" + data.message);
                }
            }
        });
    })

    function presentSuccessModal(header, message) {
        $successHdr.html(header);
        $successMsg.html(message);
        $successModal.foundation('reveal', 'open');
    }

    function presentFailModal(header, message) {
        $failHdr.html(header);
        $failMsg.html(message);
        $failModal.foundation('reveal', 'open');
    }

});

