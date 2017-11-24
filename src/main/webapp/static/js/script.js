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

var addCities = [];

$(document).ready(function () {
    loadingCargo();
    driversSelectDisable();
    clearRouteArray()
});

$("#js-radio-to-click-loading").on('click', loadingCargo);
$("#js-radio-to-click-unloading").on('click', unLoadingCargo);

$(".city-of-route-close").on('click', clearRouteArray);
$('#order-route-modal').on('hidden.bs.modal', clearRouteArray);

function addToRouteArray(orderId) {
    var cityOfRoute = $("#js-city-of-route").find("option:selected");
    var cityId = cityOfRoute.val();
    if (cityId !== $('#js-default-selected-option').val()) {
        addCities.push(cityId);

        $.ajax({
            headers: {
                "X-CSRF-TOKEN": csrf,
                'Content-Type': 'application/json'
            },
            url: basePath + '/rest/order-route/' + orderId + '/add-city',
            method: 'post',
            data: JSON.stringify(addCities),
            dataType: 'json',
            success: function (data) {
                console.log("success");
                updateRouteView(data);
                setSelectedValue();
                hideCityFromSelect(cityId);
            }
        }).then(clearRouteArray);
    }
}

function setSelectedValue() {
    $('#js-city-of-route').val($('#js-default-selected-option').val());
}

function hideCitiesInRouteFromSelect(cities) {
    $.each($.parseJSON(cities), function (i, obj) {
        hideCityFromSelect(obj.id);
    });
}

function hideCityFromSelect(cityId) {
    $("#js-city-option-" + cityId).hide();
}

function showCityInSelect(cityId) {
    $("#js-city-option-" + cityId).show();
}

function removeFromRouteArray(orderId, cityId) {
    $.ajax({
        headers: {
            "X-CSRF-TOKEN": csrf,
            'Content-Type': 'application/json'
        },
        url: basePath + '/rest/order-route/' + orderId + '/remove-city',
        method: 'post',
        data: JSON.stringify(cityId),
        dataType: 'json',
        success: function (data) {
            updateRouteView(data);
            showCityInSelect(cityId);
        }
    });

}

function updateRouteView(data) {
    var resultString = '<div id="js-route-list" class="col-lg-12">';
    var i;
    for (i = 0; i < data.length; i++) {
        if (i > 0) {
            resultString += '  -> '
        }
        resultString += data[i].name + '<a href="#"><i data-id="' + data[i].id + '" class="fa fa-times js-remove-city"></i></a>';
    }
    resultString += '</div>';
    $("#js-route-list")
        .replaceWith(resultString);
}

function clearRouteArray() {
    addCities = [];
}

function driversSelectDisable() {
    var truckData = $('.js-current-truck-id');
    if (truckData.length === 0) {
        $('.js-driver-before-truck').attr("disabled", "disabled");
    }
}
