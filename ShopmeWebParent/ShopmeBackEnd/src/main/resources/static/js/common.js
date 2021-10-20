$(function () {
    $('#logoutLink').click(function (e) {
        e.preventDefault();
        $('#formLogout').submit();
    });

    $('.navbar .dropdown').hover(function () {
        $(this).find('.dropdown-menu').delay(200).slideDown();
    }, function () {
        $(this).find('.dropdown-menu').delay(100).slideUp();
    })
});