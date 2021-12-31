const buttonLoadCountries = $('#buttonLoadCountries');
const dropDownCountries = $('#dropDownCountries');
const countryName = $('#countryName');
const countryCode = $('#countryCode');
const btnAdd = $('#btnAdd');
const btnNew = $('#btnNew');
const btnUpdate = $('#btnUpdate');
const btnDelete = $('#btnDelete');
let addMode = true;

function showToast(title, message) {
    $('.toast-header strong').text(title);
    $('.toast-body').text(message);
    $('.toast').toast('show');
}

function showButtons() {
    if (addMode) {
        btnAdd.show();
        btnNew.hide();
        btnUpdate.hide();
        btnDelete.hide();
    } else {
        btnAdd.hide();
        btnNew.show();
        btnUpdate.show();
        btnDelete.show();
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
    addMode = false;
    showButtons();
    const selectedCountry = dropDownCountries.find(':selected').get(0);
    countryName.val(selectedCountry.text);
    countryCode.val(selectedCountry.dataset.code);
}

function initForm() {
    addMode = true;
    showButtons();
    countryName.val('');
    countryCode.val('');
    countryName.focus();
}

function handleNew() {
    initForm();
}

function handleAdd() {
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
            dropDownCountries.append(`<option data-id="${country.id}" data-code="${country.code}">${country.name}</option>`);
            initForm();
        },
        error: function () {
            showToast('Error', 'Country not added!');
        }
    });
}


function handleUpdate() {
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
            initForm();
        },
        error: function () {
            showToast('Error', 'Country not updated!');
        }
    });
}

function handleDelete() {
    const selectedCountry = dropDownCountries.find(':selected');
    const url = contextPath + 'countries/delete/' + selectedCountry.data('id');

    $.ajax({
        url: url,
        type: 'GET',
        success: function () {
            showToast('Success', 'Country deleted successfully!');
            initForm();
            selectedCountry.remove();
        },
        error: function () {
            showToast('Error', 'Country not deleted!');
        }
    });
}

$(function () {
    showButtons();
    buttonLoadCountries.click(loadCountries);
    dropDownCountries.change(selectCountry);
    btnNew.click(handleNew);
    btnAdd.click(handleAdd);
    btnUpdate.click(handleUpdate);
    btnDelete.click(handleDelete);
});