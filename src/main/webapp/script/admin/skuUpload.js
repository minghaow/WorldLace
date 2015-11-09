jQuery( document ).ready(function( $ ) {

    // initialization
    var $skuId = $('#skuId');
    var skuId = $skuId.val();

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
                $skuId.val(_res.skuId);
                skuId = _res.skuId;
                $wrapper.append('<li><img src="' + src + '"/></a></li>');
                uploadSkuInit();
            },
            'onSelectError' : function(file, errorCode, errorMsg) {
                alert("fail!");
            }
        });
    }

    $("#sku-finish-btn").click(function(event){
        event.preventDefault();
        var url = "/admin/operation/sku/upload";
        $.ajax({
            url: url,
            type: "GET",
            data: $("#sku-upload-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    presentSuccessModal("上传成功！", "请继续编辑搭配...");
                    setTimeout(function(){hideSuccessModal();}, 1000);

                } else {
                    presentFailModal("上传失败", "错误原因：" + data.reason);
                }
            }
        });
    });

});

