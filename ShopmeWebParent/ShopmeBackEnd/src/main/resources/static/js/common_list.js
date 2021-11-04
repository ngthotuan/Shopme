$(function () {
    $('.link-delete').click(function (e) {
        e.preventDefault();
        showModalConfirmDelete($(this));
    })
});

function showModalConfirmDelete(link) {
    const id = link.data('id');
    const entity = link.data('entity');
    $('#confirmText').html(`Do you want to delete ${entity} <br>ID: <b>${id}</b>`);
    $('#confirmYes').attr('href', link.attr('href'));
    $('#deleteConfirmation').modal('show');
}