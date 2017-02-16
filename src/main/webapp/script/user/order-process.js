jQuery( document ).ready(function( $ ) {
    var notyf = new Notyf({delay:8000});

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

    $(document).on('click', '#jz2', function(event) {
        event.preventDefault();
        if ($('#address-name').val() == "") {
            notyf.alert("请填写收货人姓名");
        } else if ($('#address-phone').val() == "") {
            notyf.alert("请填写收货人手机号");
        } else if ($('#address-level1').find(":selected").val() == "") {
            notyf.alert("请选择收货人所在省");
        } else if ($('#address-level2').find(":selected").val() == "") {
            notyf.alert("请选择收货人所在市");
        } else if ($('#address-level3').find(":selected").val() == "") {
            notyf.alert("请选择收货人所在地区");
        } else if ($('#address-detail').val() == "") {
            notyf.alert("请填写收货人具体地址");
        } else {
            $.post('/order/pay', {
                orderId: $('#orderid').html(),
                name: $('#address-name').val(),
                phone: $('#address-phone').val(),
                level1: $('#address-level1').find(":selected").val(),
                level2: $('#address-level2').find(":selected").val(),
                level3: $('#address-level3').find(":selected").val(),
                detail: $('#address-detail').val()
            }, function(result) {
                if (!result.success) {
                    alert(result.message);
                } else {
                    $("#alipaysubmit").submit();
                }
            });
        }
    });

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

});
