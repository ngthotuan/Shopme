function showToast(title, message) {
    $('.toast-header strong').text(title);
    $('.toast-body').text(message);
    $('.toast').toast('show');
}