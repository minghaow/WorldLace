jQuery( document ).ready(function( $ ) {

    updateRegionSelectorRecursive('address-level1', 0);

    $(document).on('change', '.address-region', function(event) {
        event.preventDefault();
        var nextSelectorId = $(this).data('next-level');
        var regionId = $(this).find(":selected").val();
        updateRegionSelectorRecursive(nextSelectorId, regionId);
    });

    function updateRegionSelectorRecursive(selectorId, parentRegionId) {
        while ('' != selectorId) {
            var selector = $('#' + selectorId);
            updateRegionSelector(selector, parentRegionId);
            selectorId = selector.data('next-level');
            parentRegionId = selector.find(":selected").val();
        }
    }

    function updateRegionSelector(selector, parentRegionId) {
        $.ajax({
            url: '/address/nextLevelRegion?regionId=' + parentRegionId,
            async: false,
            success: function (data) {
                if (!data.success) {
                    return;
                }
                createRegionOptions(selector, data.data);
            }
        });
    }

    function createRegionOptions(selector, regions) {
        var originalRegionId = selector.data('original-region-id');
        selector.empty();
        var option = '<option value="{val}" {attr}>{text}</option>';
        var selected = false;
        for (var i = 0; i < regions.length; i++) {
            var region = regions[i];
            var attr = '';
            if (region.regionId == originalRegionId) {
                attr = 'selected="selected"';
                selected = true;
            }
            var optionToInsert = option
                .replace('{val}',region.regionId)
                .replace('{text}', region.regionName)
                .replace('{attr}', attr);
            selector.append(optionToInsert);
        }
        if (!selected) {
            selector.child().attribute('selected', 'selected');
        }
    }

    $(".confirm").on('click', function() {
        event.preventDefault();
        var orderId = $(this).data("id");
        $.ajax({
            url: "/admin/order/confirm",
            type: "POST",
            data: {"orderId":orderId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    alert(1);
                    presentSuccessModal("确认成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal(data.reason, "[  抱歉  ]");
                }
            }
        });
    });

    $(".shipping").on('click', function() {
        event.preventDefault();
        var orderId = $(this).data("id");
        $.ajax({
            url: "/admin/order/shipping",
            type: "POST",
            data: {"orderId":orderId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    presentSuccessModal("发货成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal(data.reason, "[  抱歉  ]");
                }
            }
        });
    });

    $(".cancel").on('click', function() {
        event.preventDefault();
        var orderId = $(this).data("id");
        $.ajax({
            url: "/admin/order/cancel",
            type: "POST",
            data: {"orderId":orderId},
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    presentSuccessModal("订单取消成功！", "即将为您刷新页面...");
                    setTimeout(function(){location.reload();}, 1300);
                } else {
                    presentFailModal(data.reason, "[  抱歉  ]");
                }
            }
        });
    });

    var $successModal = $("#successModal"),
        $successHdr = $("#successHdr"),
        $successMsg = $("#successMsg"),
        $failModal = $("#failModal"),
        $failHdr = $("#failHdr"),
        $failMsg = $("#failMsg");

    function presentSuccessModal(header, message) {
        alert(header + "," + message);
        //$successHdr.html(header);
        //$successMsg.html(message);
        //$successModal.foundation("open");
    }

    function presentFailModal(header, message) {
        alert(header + "," + message);
        //$failHdr.html(header);
        //$failMsg.html(message);
        //$failModal.foundation('open');
    }

    function hideSuccessModal() {
        $successModal.foundation("close");
    }

    function hideFailModal() {
        $failModal.foundation('close');
    }

});

