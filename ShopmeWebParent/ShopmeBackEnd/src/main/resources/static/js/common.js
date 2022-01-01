function customizeDropdownMenu() {
    $(".navbar .dropdown").hover(
        function () {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function () {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        }
    );
}

function customizeTabs() {
    // Javascript to enable link to tab
    const hash = location.hash.replace(/^#/, ''); // ^ means starting, meaning only match the first hash
    if (hash) {
        $('.nav-tabs a[href="#' + hash + '"]').tab('show');
    }
    // Change hash for page-reload
    $('.nav-tabs a').on('shown.bs.tab', function (e) {
        console.log('shown.bs.tab', e);
        window.location.hash = e.target.hash;
    });
}

$(function () {
    $('#logoutLink').click(function (e) {
        e.preventDefault();
        $('#formLogout').submit();
    });

    customizeDropdownMenu();
    customizeTabs();
});
