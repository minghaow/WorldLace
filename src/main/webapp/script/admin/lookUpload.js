jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    var lookId = $('#lookId').html();
    var $skuId = $('#skuId');
    var skuId = $skuId.val();
    var $skuForm = $('#sku-form');
    $("#" + thisTag).addClass("chosen");

    uploadInit();

    function uploadInit(){
        var sessionId = $("#sessionId").html();
        var isErrorEd = false;
        var $imgInput = $("#look-img-ids");
        $("#uploadify").uploadify({
            'height'        : 50,
            'swf'           : '/script/vendor/uploadify.swf',
            'uploader'      : '/admin/operation/look/uploadImage;jsessionid=' + sessionId + "?lookId=" + lookId,
            'width'         : "100%",
            'fileSizeLimit' : '5MB',
            'fileTypeDesc' : 'Image Files',
            'fileTypeExts' : '*.gif; *.jpg; *.png',
            'buttonText' : '点我上传',
            'successTimeout' : 2000,
            'overrideEvents' : ['onDialogClose', 'onUploadSuccess', 'onUploadError', 'onSelectError'],
            'onFallback':function(){
                if(!isErrorEd){
                    alert('您未安装FLASH控件，无法上传图片！请<a href="http://get.adobe.com/cn/flashplayer/" target="_blank">安装FLASH</a>控件后再试。');
                    isErrorEd = true;
                }
            },
            'onUploadSuccess' : function(file, data, response) {
                var _res = eval('(' + data + ')');
                var $wrapper = $(".image-wrapper");
                var src = _res.url;
                $('#uploadModal').foundation('reveal', 'close');
                $('#lookId').html(_res.lookId);
                $('#lookId-form').val(_res.lookId);
                lookId = _res.lookId;
                $wrapper.append('<div class="large-4 columns end" data-equalizer-watch><img src="' + src + ' " class="look-img"/></div>');
                uploadInit();
            },
            'onSelectError' : function(file, errorCode, errorMsg) {
                alert("fail!");
            }
        });
    }

    uploadSkuInit();

    function uploadSkuInit(){
        var sessionId = $("#sessionId").html();
        var isErrorEd = false;
        $("#uploadifySku").uploadify({
            'height'        : 50,
            'swf'           : '/script/vendor/uploadify.swf',
            'uploader'      : '/admin/operation/sku/uploadImage;jsessionid=' + sessionId + "?skuId=" + skuId,
            'width'         : "100%",
            'fileSizeLimit' : '5MB',
            'fileTypeDesc' : 'Image Files',
            'fileTypeExts' : '*.gif; *.jpg; *.png',
            'buttonText' : '点我上传',
            'successTimeout' : 2000,
            'overrideEvents' : ['onDialogClose', 'onUploadSuccess', 'onUploadError', 'onSelectError'],
            'onFallback':function(){
                if(!isErrorEd){
                    alert('您未安装FLASH控件，无法上传图片！请<a href="http://get.adobe.com/cn/flashplayer/" target="_blank">安装FLASH</a>控件后再试。');
                    isErrorEd = true;
                }
            },
            'onUploadSuccess' : function(file, data, response) {
                var _res = eval('(' + data + ')');
                var $wrapper = $(".sku-image-wrapper");
                var src = _res.url;
                $skuForm.val($skuForm.val() + "," +  _res.skuId);
                $skuId.val(_res.skuId);
                skuId = _res.skuId;
                $wrapper.append('<div class="large-4 columns end" data-equalizer-watch><img src="' + src + ' " class="look-img"/></div>');
                uploadSkuInit();
            },
            'onSelectError' : function(file, errorCode, errorMsg) {
                alert("fail!");
            }
        });
    }

    $("#sku-finish-btn").click(function(event){
        event.preventDefault();
        var url = "/admin/operation/sku/upload"
        $.ajax({
            url: url,
            type: "GET",
            data: $("#sku-upload-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    //$skuForm.val($skuForm.val() + "," +  _res.skuId);
                    presentSuccessModal("上传成功！", "请继续编辑搭配...");
                    $('#addSkuModal').foundation('reveal', 'close');
                } else {
                    presentFailModal("上传失败", "错误原因：" + data.message);
                }
            }
        });
    })

    $("#finish-btn").click(function(event){
        event.preventDefault();
        var url = "/admin/operation/look/upload"
        $.ajax({
            url: url,
            type: "GET",
            data: $("#upload-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    presentSuccessModal("上传成功！", "即将为您跳转到线下搭配列表页...");
                    setTimeout(function(){window.location.href = "/admin/operation/look/look-list?status=OFFLINE"}, 1000);
                } else {
                    presentFailModal("上传失败", "错误原因：" + data.message);
                }
            }
        });
    })

});

