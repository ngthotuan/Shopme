$(function () {
    $('input[name=extraImage]').change(function () {
        handleExtraInputChange(this);
    });
})

function handleExtraInputChange(input) {
    if (!checkFileSize(input)) {
        return;
    }
    // show thumbnail
    const parent = $(input).parent();
    const file = input.files[0];
    const reader = new FileReader();
    reader.onload = function (e) {
        parent.children('img').attr('src', e.target.result);
    }
    reader.readAsDataURL(file);

    const last = $('.extra-image').last();
    if (last.is(parent)) {
        // add remove button
        const removeBtn = `<button onclick="removeExtraImage(this)"
                            class="btn fas fa-times-circle fa-2x text-danger" 
                            title="Delete this image"></button>`;
        parent.children('label').append(removeBtn);

        // addNextExtraImageSection
        const html = `<div class="col border m-2 extra-image">
            <label>Extra image: </label><br>
            <img alt="Extra image preview" class="img-fluid" src="${defaultThumbnail}"><br>
            <input accept="image/*" onchange="handleExtraInputChange(this)" class="form-file m-2" name="extraImage" type="file"/>
        </div>`;
        $("#productImages").append(html);
    }
}

function removeExtraImage(btn) {
    $(btn).parents('.extra-image').remove();
}