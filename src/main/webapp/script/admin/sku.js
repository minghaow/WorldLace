jQuery( document ).ready(function( $ ) {

    var page = $("#page").html();
    $(".next-page").click(function(event){
        event.preventDefault();
        var pageNext = parseInt(page)+1;
        window.location.href = "/admin/sku/list?page=" + pageNext;
    });

    $(".previous-page").click(function(event){
        event.preventDefault();
        var pageNext = parseInt(page)-1;
        window.location.href = "/admin/sku/list?page=" + pageNext;
    });

    $(".confirm").on('click', function() {
        event.preventDefault();
        var id = $("#skuItemId").val();
        var title = $("#title").val();
        var subTitle = $("#subtitle").val();
        var price = $("#price").val();
        var imgCount = $("#img-count").val();
        var hasGif = $("#has-gif").val();
        var description1 = $("#description1").val();
        var points = $("#points").val();
        var infos = $("#infos").val();
        var shipSpeed = $("#ship-speed").val();
        var notice = $("#notice").val();
        var description2 = $("#description2").val();
        var packageInfo = $("#packageInfo").val();
        $.ajax({
            url: "/admin/sku/item-upload/upload",
            type: "GET",
            data: {
                "id":id,
                "title":title,
                "subTitle":subTitle,
                "price":price,
                "imgCount":imgCount,
                "hasGif":hasGif,
                "description1":description1,
                "points":points,
                "infos":infos,
                "shipSpeed":shipSpeed,
                "notice":notice,
                "description2":description2,
                "packageInfo":packageInfo
            },
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    alert("上传成功！即将为您刷新页面...");
                    setTimeout(function(){window.location.href = "/admin/sku/list"}, 1300);
                } else {
                    alert(data.reason + "[  抱歉  ]");
                }
            }
        });
    });

    $(".delete").on('click', function() {
        event.preventDefault();
        var id = $(this).data("id");
        $.ajax({
            url: "/admin/sku/list/delete",
            type: "GET",
            data: {"id":id},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    alert("删除成功！即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    alert("删除失败！即将为您刷新页面...");
                }
            }
        });
    });

});

