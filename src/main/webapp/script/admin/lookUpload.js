jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    var lookId = $('#lookId').html();
    $("#" + thisTag).addClass("chosen");

    uploadInit();

    function uploadInit(){
        var sessionId = $("#sessionId").html();
        var isErrorEd = false;
        var $imgInput = $("#look-img-ids");
        $("#uploadify").uploadify({
            'height'        : 240,
            'swf'           : '/script/vendor/uploadify.swf',
            'uploader'      : '/admin/operation/look/uploadImage;jsessionid=' + sessionId + "?lookId=" + lookId,
            'width'         : 360,
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
                    setTimeout(function(){windows.location.href = "/admin/operation/look/look-list"}, 1000);
                } else {
                    presentFailModal("上传失败", "错误原因：" + data.message);
                }
            }
        });
    })

});

