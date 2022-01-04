$(function () {
    $('.link-delete').click(showModalConfirmDelete);
    $('.detail-link').click(showModalDetail);
});

function showModalConfirmDelete(e) {
    e.preventDefault();
    const id = $(this).data('id');
    const entity = $(this).data('entity');
    $('#confirmText').html(`Do you want to delete ${entity} <br>ID: <b>${id}</b>`);
    $('#confirmYes').attr('href', $(this).attr('href'));
    $('#deleteConfirmation').modal('show');
}

function showModalDetail(e) {
    e.preventDefault();
    const url = $(this).attr('href');
    $('#detailModal').modal('show')
        .find('.modal-content')
        .load(url);
}