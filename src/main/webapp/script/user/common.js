jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    $("#" + thisTag).addClass("chosen");
    var registerStep = 0;
    var userId = 0;
    var userName = "";
    var phone = "";
    var password = "";

    $(".unsupported-section").click(function(event){
        event.preventDefault();
        presentSuccessModal("抱歉！", "本功能仍然处于开发状态，敬请期待！");
    });

    $(".detail-image-slider .banner-slider ul li").show();

    $('.lr-toggle').on('click', function() {
        $('.lr-toggle').removeClass("shake-crazy");
        $('.lr-container').stop().addClass('active');
    });

    $('.lr-close').on('click', function() {
        $('.lr-toggle').addClass("shake-crazy");
        $('.lr-container').stop().removeClass('active');
    });

    $('.register-btn').on('click', function() {
        $("#animatedModal1").foundation("open");
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

    $('.register-trigger').on('click', function() {
        event.preventDefault();
        if (registerStep == 0) {
            toggleRegisterContent(true, 1);
            registerStep = 1;
            $('.register-trigger').html("下一步").removeClass("final-step");
        } else if (registerStep == 1) {
            $.ajax({
                url: "/auth/register",
                type: "POST",
                data: $("#register-form").serialize(),
                dataType: 'json',
                success: function(data) {
                    if (data.success == true || data.success == "true") {
                        password = $("#password2").val();
                        phone = $("#phone2").val();
                        userId = data.userId;
                        $("#userId").val(userId);
                        toggleRegisterContent(true, 2);
                        registerStep = 2;
                        $('.register-trigger').html("点我完成注册").addClass("final-step");
                    } else {
                        presentFailModal("抱歉！", data.msg);
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
                        presentFailModal("抱歉！", "用户名和密码的组合不正确，请重新输入。");
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
                    $("#username-section").html(data.userInfo.username + ", " + data.helloMsg);
                    $("#login-decide-content").html("登出");
                    $(".cart-count").html(data.cart.goodsCount);
                    $("#login-decide-url").attr("href", "/auth/logout").removeClass("register-btn");
                } else {
                    presentFailModal("抱歉！", "用户名和密码的组合不正确，请重新输入。");
                    setTimeout(function(){$("#animatedModal1").foundation("open");}, 2500);
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
        var skuId = $(this).data("skuId");
        $(".price-before").html("￥" + originPrice);
        $(".price-after").html("￥" + price);
        $(".add-to-cart").data("sku-id", skuId);
    });

    $(".add-to-cart").on('click', function() {
        event.preventDefault();
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

    // Get a list of all svg elements
    icons = document.querySelectorAll('.icon-hook');

// Cycle through list
    for (var i = 0; i < icons.length; i++) {
        icons[i].addEventListener('click', function(event) {
            event.preventDefault();

            var icon = this;
            var currentClass = icon.getAttribute('class'); // The starting class

            console.log(icon);

            if (currentClass.indexOf('active') > -1) {
                // Remove .active
                icon.setAttribute('class', currentClass.replace(' active', ''));
            } else {
                // Add .active
                icon.setAttribute('class', currentClass + ' active');
            }
        }, false);
    }

});
