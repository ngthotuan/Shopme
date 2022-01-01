const buttonLoadCountries = $('#buttonLoadCountries');
const dropDownCountries = $('#dropDownCountries');
const countryName = $('#countryName');
const countryCode = $('#countryCode');
const btnAddCountry = $('#btnAddCountry');
const btnNewCountry = $('#btnNewCountry');
const btnUpdateCountry = $('#btnUpdateCountry');
const btnDeleteCountry = $('#btnDeleteCountry');
let addCountryMode = true;

function showCountryButtons() {
    if (addCountryMode) {
        btnAddCountry.show();
        btnNewCountry.hide();
        btnUpdateCountry.hide();
        btnDeleteCountry.hide();
    } else {
        btnAddCountry.hide();
        btnNewCountry.show();
        btnUpdateCountry.show();
        btnDeleteCountry.show();
    }
}

function loadCountries() {
    const url = contextPath + 'countries/list';
    $.getJSON(url, function (data) {
        dropDownCountries.empty();
        $.each(data, function (index, country) {
            dropDownCountries.append(`<option data-id="${country.id}" data-code="${country.code}">${country.name}</option>`);
        });
        showToast('Success', 'Countries loaded successfully!');
        buttonLoadCountries.val('Refresh Country List');
    }).fail(function () {
        showToast('Error', 'Error loading countries!');
    });
}

function selectCountry() {
    addCountryMode = false;
    showCountryButtons();
    const selectedCountry = dropDownCountries.find(':selected').get(0);
    countryName.val(selectedCountry.text);
    countryCode.val(selectedCountry.dataset.code);
}

function initCountryForm() {
    addCountryMode = true;
    showCountryButtons();
    countryName.val('');
    countryCode.val('');
    countryName.focus();
}

function handleNewCountry() {
    initCountryForm();
}

function handleAddCountry() {
    const url = contextPath + 'countries/save';
    const country = {
        name: countryName.val(),
        code: countryCode.val()
    };
    if (country.name.length === 0) {
        showToast('Error', 'Country name is required!');
        countryName.focus();
        return;
    }
    if (country.code.length === 0) {
        showToast('Error', 'Country code is required!');
        countryCode.focus();
        return;
    }
    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(country),
        contentType: 'application/json',
        success: function (c) {
            showToast('Success', 'Country saved successfully!');
            dropDownCountries.append(`<option data-id="${c.id}" data-code="${c.code}">${c.name}</option>`);
            initCountryForm();
        },
        error: function () {
            showToast('Error', 'Country not added!');
        }
    });
}


function handleUpdateCountry() {
    const url = contextPath + 'countries/save';
    const selectedCountry = dropDownCountries.find(':selected');
    const country = {
        id: selectedCountry.data('id'),
        name: countryName.val(),
        code: countryCode.val()
    };
    if (country.name.length === 0) {
        showToast('Error', 'Country name is required!');
        countryName.focus();
        return;
    }
    if (country.code.length === 0) {
        showToast('Error', 'Country code is required!');
        countryCode.focus();
        return;
    }
    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(country),
        contentType: 'application/json',
        success: function (c) {
            showToast('Success', 'Country updated successfully!');
            selectedCountry.text(c.name);
            selectedCountry.attr('data-code', c.code);
            initCountryForm();
        },
        error: function () {
            showToast('Error', 'Country not updated!');
        }
    });
}

function handleDeleteCountry() {
    const selectedCountry = dropDownCountries.find(':selected');
    const url = contextPath + 'countries/delete/' + selectedCountry.data('id');

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function () {
            showToast('Success', 'Country deleted successfully!');
            initCountryForm();
            selectedCountry.remove();
        },
        error: function () {
            showToast('Error', 'Country not deleted!');
        }
    });
}

$(function () {
    showCountryButtons();
    buttonLoadCountries.click(loadCountries);
    dropDownCountries.change(selectCountry);
    btnNewCountry.click(handleNewCountry);
    btnAddCountry.click(handleAddCountry);
    btnUpdateCountry.click(handleUpdateCountry);
    btnDeleteCountry.click(handleDeleteCountry);
});