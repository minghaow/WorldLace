jQuery( document ).ready(function( $ ) {

    // initialization
    var pageType = $("#page-type").html();
    $("#" + pageType).addClass("chosen");

    $(".unsupported-section").click(function(event){
        event.preventDefault();
        presentSuccessModal("抱歉！", "本功能仍然处于开发状态，敬请期待！");
    });
});
