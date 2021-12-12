$(function () {
    $('#btnCancel').click(function () {
        window.location = moduleURL;
    })

    $('#fileImage').change(function () {
        if (!checkFileSize(this)) {
            return;
        }
        showImageThumbnail(this);
    })


})

function checkFileSize(fileInput) {
    const fileSize = fileInput.files[0].size;
    if (fileSize > MAX_FILE_SIZE) {
        fileInput.setCustomValidity(`You must choose an image less than ${MAX_FILE_SIZE / 1024 / 1024} MB!`);
        fileInput.reportValidity();
        return false;
    } else {
        fileInput.setCustomValidity('');
        return true;
    }
}

function showImageThumbnail(fileInput) {
    const file = fileInput.files[0];
    const reader = new FileReader();
    reader.onload = function (e) {
        $('#thumbnail').attr('src', e.target.result);
    }
    reader.readAsDataURL(file);
}

function showModal(title, message) {
    $('#modalTitle').text(title)
    $('#modalContent').text(message)
    $('#modalDialog').modal('show');
}