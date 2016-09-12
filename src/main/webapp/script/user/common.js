jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    $("#" + thisTag).addClass("chosen");
    var registerStep = 0;
    var userId = 0;
    var userName = "";
    var phone = "";
    var password = "";
    var isLogined = $("#is-login").html() == "true";
    var isValidPhone = false;

    $(".unsupported-section").click(function(event){
        event.preventDefault();
        presentSuccessModal("抱歉！", "本功能仍然处于开发状态，敬请期待！");
    });

    $(".detail-image-slider .banner-slider ul li").show();

    $('.register-btn').on('click', function() {
        $('.login-btn').trigger('click');
    });

    $('.login-btn').on('click', function() {
        $("#animatedModal1").foundation("open");
        setTimeout(function(){
            $('.lr-close').trigger("click");
            $('#login-form .button').addClass("shake-crazy shake-constant");
        }, 300);
        setTimeout(function(){
            $('#login-form .button').removeClass("shake-crazy shake-constant");
        }, 1000);
    });

    $('#animatedModal1').on('closed.zf.reveal', function() {
        if (registerStep != 0) {
            toggleRegisterContent(false);
            registerStep = 0;
            $('.register-trigger').html("点我注册").removeClass("final-step");
        }
    });

    $("#phone").focus(function(){
        isValidPhone = false;
        $(this).removeClass("successInput alertInput");
    })
    .blur(function(){
        phone = $("#phone").val().replace(/\s+/g,"");
        if (phone.length != 11) {
            inputNotification("#phone", "#phoneHelpText", false, "手机号应为11位数字");
            return;
        }
        $.ajax({
            url: "/auth/isRegistered",
            type: "POST",
            data: {"phone":phone},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    inputNotification("#phone", "#phoneHelpText", true, "&nbsp");
                } else {
                    inputNotification("#phone", "#phoneHelpText", false, data.msg);
                }
            }
        });
    });

    function inputNotification(point, helpPoint, isSuccess, message) {
        if (message == "") {
            message = "&nbsp";
        }
        if (isSuccess) {
            $(point).addClass("successInput");
            $(helpPoint).html(message).animate({opacity: 0});
        } else {
            $(point).addClass("alertInput");
            $(helpPoint).html(message).animate({opacity: 1});
        }
    }

    $("#phone2").focus(function(){
        isValidPhone = false;
        $(this).removeClass("successInput alertInput");
    })
    .blur(function(){
        phone = $("#phone2").val().replace(/\s+/g,"");
        if (phone.length != 11) {
            inputNotification("#phone2", "#phoneHelpText2", false, "手机号应为11位数字");
            return;
        }
        $.ajax({
            url: "/auth/isNotRegistered",
            type: "POST",
            data: {"phone":phone},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    inputNotification("#phone2", "#phoneHelpText2", true, "&nbsp");
                    isValidPhone = true;
                } else {
                    inputNotification("#phone2", "#phoneHelpText2", false, data.msg);
                    isValidPhone = false;
                }
            }
        });
    });

    $('.register-trigger').on('click', function() {
        event.preventDefault();
        if (registerStep == 0) {
            toggleRegisterContent(true, 1);
            registerStep = 1;
            $('.register-trigger').html("下一步").removeClass("final-step");
        } else if (registerStep == 1) {
            password = $("#password2").val();
            if (!isValidPhone) {
                return;
            }
            $.ajax({
                url: "/auth/register",
                type: "POST",
                data: $("#register-form").serialize(),
                dataType: 'json',
                success: function(data) {
                    if (data.success == true || data.success == "true") {
                        userId = data.userId;
                        $("#userId").val(userId);
                        toggleRegisterContent(true, 2);
                        registerStep = 2;
                        $('.register-trigger').html("点我完成注册").addClass("final-step");
                    } else {
                        inputNotification("#password2", "#pwdHelpText2", false, data.msg);
                    }
                }
            });
        } else {
            $.ajax({
                url: "/auth/setName",
                type: "POST",
                data: $("#register-form").serialize(),
                dataType: 'json',
                success: function(data) {
                    if (data.success == true || data.success == "true") {
                        userName = $("#username").val();
                        toggleRegisterContent(true, 3);
                        registerStep = 3;
                        $("#animatedModal1").foundation("close");
                    } else {
                        presentFailModal("抱歉！", data.msg);
                        setTimeout(function(){$("#animatedModal1").foundation("open");}, 2500);
                    }
                }
            });
            $.ajax({
                url: "/auth/login-check",
                type: "POST",
                data: {"password":password, "phone":phone},
                dataType: 'json',
                success: function(data) {
                    if (data.success == true || data.success == "true") {
                        $("#animatedModal1").foundation("close");
                        $("#username-section").html(data.userInfo.username + ", " + data.helloMsg);
                        $("#login-decide-content").html("登出");
                        $(".cart-count").html(data.cart.goodsCount);
                        $("#login-decide-url").attr("href", "/auth/logout").removeClass("register-btn");
                    } else {
                        presentFailModal("请登录！", "注册成功，请登录，谢谢！");
                        setTimeout(function(){$("#animatedModal1").foundation("open");}, 2500);
                    }
                }
            });
        }
    });

    function toggleRegisterContent(isUp, step) {
        if (isUp) {
            if (step == 1) {
                $(".welcome-hide-after").animate({top:"-170px"});
                $(".welcome-hide-before").animate({top:"0px"});
                $(".welcome-hide-before-2").animate({top:"170px"});
            } else {
                $(".welcome-hide-after").animate({top:"-340px"});
                $(".welcome-hide-before").animate({top:"-170px"});
                $(".welcome-hide-before-2").animate({top:"0"});
            }
        } else {
            $(".welcome-hide-after").animate({top:"0"});
            $(".welcome-hide-before").animate({top:"170px"});
            $(".welcome-hide-before-2").animate({top:"340px"});
        }
    }

    $("#login-confirm").on('click', function() {
        event.preventDefault();
        $.ajax({
            url: "/auth/login-check",
            type: "POST",
            data: $("#login-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    $("#animatedModal1").foundation("close");
                    $("#username-section").html(data.userInfo.username + "的订单");
                    $("#login-decide-content").html("登出");
                    $(".cart-count").html(data.cart.goodsCount);
                    $("#login-decide-url").attr("href", "/auth/logout").removeClass("register-btn");
                    $("#user-url").attr("href", "/order/list");
                } else {
                    inputNotification("#password", "#pwdHelpText", false, data.msg);
                }
            }
        });
    });

    $("#register-confirm").on('click', function() {
        event.preventDefault();
        $.ajax({
            url: "/auth/register",
            type: "POST",
            data: $("#register-form2").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    presentSuccessModal("恭喜您！", "欢迎您加入桃源大家庭！请点击登陆。");
                } else {
                    presentFailModal("抱歉！", data.msg);
                }
            }
        });
    });

    $(".option").on('click', function() {
        var originPrice = $(this).data("origin");
        var price = $(this).data("now");
        var itemId = $(this).data("item-id");
        var skuId = $(this).data("sku-id");
        var optionId = $(this).data("option-id");
        $(".price-before").html("￥" + originPrice);
        $(".price-after").html("￥" + price);
        $(".detail-image-h0").attr("src", "http://image-cdn.zaitaoyuan.com/images/item/itemOption/" + itemId + "/" + optionId + ".jpg@!item-head-4");
        $("#detail-image-big-h0").attr("src", "http://image-cdn.zaitaoyuan.com/images/item/itemOption/" + itemId + "/" + optionId + ".jpg@!item-head-4");
        $(".add-to-cart").data("sku-id", skuId);
        $(".option-button-wrapper button").removeClass("checked");
        $(this).addClass("checked");
    });

    $(".lSPager li a img").on('click', function() {
        alert(1);
        $(".detail-image-h0").attr("src", currentImgUrl);
    });

    $(".add-to-cart").on('click', function() {
        event.preventDefault();
        if (!isLogined) {
            $('.login-btn').trigger('click');
            return;
        }
        var skuId = $(this).data("sku-id");
        var count = $(this).data("count");
        $.ajax({
            url: "/item/addToCart",
            type: "POST",
            data: {"skuId":skuId, "count": count},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    $(".cart-count").html(data.cnt);
                    presentSuccessModal("干得漂亮！", "添加购物车成功");
                } else {
                    presentFailModal(data.message, "[  抱歉  ]");
                }
            }
        });
    });

    $("#contact-us-btn").on('click', function() {
        event.preventDefault();
        $.ajax({
            url: "/msg",
            type: "POST",
            data: $("#contact-us-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    $(".cart-count").html(data.cnt);
                    presentSuccessModal("发送成功！", "我们已经收到您的消息，我们会尽快派专人处理，谢谢您的反馈！");
                } else {
                    presentFailModal(data.message, "[  抱歉  ]");
                }
            }
        });
    });

    $(".scroll-to-top").on('click', function() {
        $('html,body').animate({scrollTop: '0px'}, 800);
    });
});
