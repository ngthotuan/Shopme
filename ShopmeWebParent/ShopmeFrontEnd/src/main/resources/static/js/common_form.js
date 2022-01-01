function showModal(title, message) {
    $('#modalTitle').text(title)
    $('#modalContent').text(message)
    $('#modalDialog').modal('show');
}