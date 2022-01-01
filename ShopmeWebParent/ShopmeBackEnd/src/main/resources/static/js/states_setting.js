const btnLoadCountries4State = $('#btnLoadCountries4State');
const ddCountries = $('#ddCountries');
const ddStates = $('#ddStates');
const stateName = $('#stateName');
const btnAddState = $('#btnAddState');
const btnNewState = $('#btnNewState');
const btnUpdateState = $('#btnUpdateState');
const btnDeleteState = $('#btnDeleteState');
let addStateMode = true;

function showStateButtons() {
    if (addStateMode) {
        btnAddState.show();
        btnNewState.hide();
        btnUpdateState.hide();
        btnDeleteState.hide();
    } else {
        btnAddState.hide();
        btnNewState.show();
        btnUpdateState.show();
        btnDeleteState.show();
    }
}

function loadCountries4State() {
    const url = contextPath + 'countries/list';
    $.getJSON(url, function (data) {
        ddCountries.empty();
        $.each(data, function (index, country) {
            ddCountries.append(`<option data-id="${country.id}" data-code="${country.code}">${country.name}</option>`);
        });
        showToast('Success', 'Countries loaded successfully!');
        initStateForm();
        updateStates();
        btnLoadCountries4State.val('Refresh Country List');
    }).fail(function () {
        showToast('Error', 'Error loading countries!');
    });
}

function updateStates() {
    const selectedState = ddCountries.find(':selected');
    const url = contextPath + 'states/list?countryId=' + selectedState.data('id');
    $.getJSON(url, function (data) {
        ddStates.empty();
        $.each(data, function (index, state) {
            ddStates.append(`<option data-id="${state.id}">${state.name}</option>`);
        });
        showToast('Success', `This country has ${data.length} states/provinces`);
    }).fail(function () {
        showToast('Error', 'Error loading states!');
    });
}

function selectState() {
    addStateMode = false;
    showStateButtons();
    const selectState = ddStates.find(':selected');
    stateName.val(selectState.text());
}

function initStateForm() {
    addStateMode = true;
    showStateButtons();
    stateName.val('');
    stateName.focus();
}

function handleNew() {
    initStateForm();
}

function handleAddState() {
    const url = contextPath + 'states/save';
    const selectedCountry = ddCountries.find(':selected');
    const countryId = selectedCountry.data('id');
    const name = stateName.val();

    if (countryId === undefined) {
        showToast('Error', 'Please select a country first!');
        return;
    }
    if (name.length === 0) {
        showToast('Error', 'State name is required!');
        stateName.focus();
        return;
    }
    const state = {
        name,
        country: {
            id: countryId
        }
    };
    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(state),
        contentType: 'application/json',
        success: function (s) {
            showToast('Success', 'State saved successfully!');
            ddStates.append(`<option data-id="${s.id}" >${s.name}</option>`);
            initStateForm();
        },
        error: function () {
            showToast('Error', 'State not added!');
        }
    });
}

function handleUpdateState() {
    const url = contextPath + 'states/save';
    const selectedCountry = ddCountries.find(':selected');
    const selectedState = ddStates.find(':selected');

    const countryId = selectedCountry.data('id');
    const id = selectedState.data('id');
    const name = stateName.val();

    if (countryId === undefined) {
        showToast('Error', 'Please select a country first!');
        return;
    }
    if (name.length === 0) {
        showToast('Error', 'State name is required!');
        stateName.focus();
        return;
    }
    const state = {
        id,
        name,
        country: {
            id: countryId
        }
    };
    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(state),
        contentType: 'application/json',
        success: function () {
            showToast('Success', 'State updated successfully!');
            selectedState.text(name);
            initStateForm();
        },
        error: function () {
            showToast('Error', 'State not updated!');
        }
    });
}

function handleDeleteState() {
    const selectedState = ddStates.find(':selected');
    const url = contextPath + 'states/delete/' + selectedState.data('id');

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function () {
            showToast('Success', 'State deleted successfully!');
            initStateForm();
            selectedState.remove();
        },
        error: function () {
            showToast('Error', 'State not deleted!');
        }
    });
}

$(function () {
    showStateButtons();
    btnLoadCountries4State.click(loadCountries4State);
    ddCountries.change(updateStates);
    ddStates.change(selectState);
    btnAddState.click(handleAddState);
    btnNewState.click(handleNew);
    btnUpdateState.click(handleUpdateState);
    btnDeleteState.click(handleDeleteState);
});