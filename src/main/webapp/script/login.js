jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
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
        var url = "/auth/login"
        $.ajax({
            url: url,
            type: "POST",
            data: $("#login-form").serialize(),
            success: function() {
            }
        });
    })

    $("#change-pwd-button").click(function(event){
        event.preventDefault(); // 阻止默认的表单提交行为
        if (!$("#username_c").val()){
            alert("email不能为空！");
            return;
        } else if (!$("#pwd_o").val()) {
            alert("原始密码不能为空！");
            return;
        } else if (!$("#pwd_n").val()) {
            alert("请填入新密码！");
            return;
        } else if (!$("#pwd_n2").val()) {
            alert("请再次填入新密码以确认！");
            return;
        } else if ($("#pwd_n2").val() != $("#pwd_n").val()) {
            alert("您设置的新密码两次输入不一致！请重新输入！");
            return;
        }
        var url = "/auth/change-pwd"
        var successModal = $("#successModal");
        var failModal = $("#failModal");
        $.ajax({
            url: url,
            type: "POST",
            data: $("#change-pwd-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true) {
                    successModal.foundation('reveal', 'open');
                } else {
                    failModal.find("#fail-reason").text("错误原因：" + data.info);
                    failModal.foundation('reveal', 'open');
                }
            }
        });
    })
});

