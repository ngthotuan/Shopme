$(function () {
    $('#logoutLink').click(function (e) {
        e.preventDefault();
        $('#formLogout').submit();
    })
});