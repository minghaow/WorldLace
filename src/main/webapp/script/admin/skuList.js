jQuery( document ).ready(function( $ ) {

    $(".delete-btn").click(function(event){
        event.preventDefault();
        var skuId = $(this).data("id");
        var url = "/admin/operation/sku/delete"
        $.ajax({
            url: url,
            type: "GET",
            data: {skuId: skuId},
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
    });

    $(".online-btn").click(function(event){
        event.preventDefault();
        var skuId = $(this).data("id");
        var url = "/admin/operation/sku/online"
        $.ajax({
            url: url,
            type: "GET",
            data: {skuId: skuId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    presentSuccessModal("上线成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal("上线失败", "错误原因：" + data.message);
                }
            }
        });
    });

    $(".offline-btn").click(function(event){
        event.preventDefault();
        var skuId = $(this).data("id");
        var url = "/admin/operation/sku/offline"
        $.ajax({
            url: url,
            type: "GET",
            data: {skuId: skuId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    presentSuccessModal("下线成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal("下线失败", "错误原因：" + data.message);
                }
            }
        });
    });

});

