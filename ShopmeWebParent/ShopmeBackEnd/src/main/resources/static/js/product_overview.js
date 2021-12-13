$(function () {
    getCategories();
    $("#brand").change(getCategories);

    $("#shortDescription").richText();
    $("#fullDescription").richText();

    $("#btnSubmit").click(function (e) {
        e.preventDefault();
        const form = $("#formProduct")[0];
        if (form.checkValidity()) {
            const params = {
                id: $("#id").val(),
                name: $("#name").val(),
            }
            $.post(`${moduleURL}/check_duplicate`, params)
                .done(function (data) {
                    if (data === 'OK') {
                        form.submit();
                    } else if (data === 'DuplicateName') {
                        showModal('Warning', `There are another product with name ${params.name}`);
                    } else {
                        showModal('Error', 'There are an error when checking duplicate');
                    }
                })
                .fail(function (e) {
                    showModal('Error', 'Service unavailable');
                    console.log(e);
                });
        } else {
            form.reportValidity();
        }
    });
});

function getCategories() {
    const id = $("#brand").val();
    const categoryDiv = $("#category");
    $.getJSON(`${brandURL}/${id}/categories`, function (data) {
        categoryDiv.empty();
        $.each(data, function (key, value) {
            categoryDiv.append(`<option value="${value.id}">${value.name}</option>`);
        });
    });
}