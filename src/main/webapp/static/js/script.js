function loadingCargo() {
    $('.js-unload-cargo-div').hide();
    $('.js-unload-cargo-input').attr("disabled", "disabled");
    $('.js-load-cargo-div').show();
    $('.js-load-cargo-input').removeAttr("disabled");
}

function unLoadingCargo() {
    $('.js-unload-cargo-div').show();
    $('.js-unload-cargo-input').removeAttr("disabled");
    $('.js-load-cargo-div').hide();
    $('.js-load-cargo-input').attr("disabled", "disabled");
}

$(document).ready(function () {
    loadingCargo();
    driversSelectDisable();
});
$("#js-radio-to-click-loading").on('click', loadingCargo);
$("#js-radio-to-click-unloading").on('click', unLoadingCargo);


function driversSelectDisable() {
    var truckData = $('.js-current-truck-id');
    if (truckData.length === 0) {
        $('.js-driver-before-truck').attr("disabled", "disabled");
    }
}
