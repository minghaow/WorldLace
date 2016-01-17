jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    $("#" + thisTag).addClass("chosen");

    $(".unsupported-section").click(function(event){
        event.preventDefault();
        presentSuccessModal("抱歉！", "本功能仍然处于开发状态，敬请期待！");
    });

    $(".detail-image-slider .banner-slider ul li").show();

});
