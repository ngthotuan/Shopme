let currentIndex = 0; // the number of extra images

$('#extraImage0').change(function () {
    handleExtraInputChange(this, currentIndex);
});

function handleExtraInputChange(input, index) {
    if (!checkFileSize(input)) {
        return;
    }
    showExtraImageThumbnail(input, index);
    if (currentIndex === index) {
        currentIndex++;
        addNextExtraImageSection(index + 1);
        const removeLink = `<button onclick="removeExtraImage(${index})"
                            class="btn fas fa-times-circle fa-2x text-danger" 
                            title="Delete this image"></button>`;
        $('#extraImageHeader' + index).append(removeLink);
    }
}

function showExtraImageThumbnail(fileInput, index) {
    const file = fileInput.files[0];
    const reader = new FileReader();
    reader.onload = function (e) {
        $('#extraThumbnail' + index).attr('src', e.target.result);
    }
    reader.readAsDataURL(file);
}

function addNextExtraImageSection(index) {
    const html = `<div class="col border m-2" id="extraImageSection${index}">
            <div id="extraImageHeader${index}">
                <label>Extra image #${index + 1}: </label>
            </div>
            <div>
                <img alt="Extra image #${index + 1} preview" class="img-fluid" 
                        id="extraThumbnail${index}" src="${defaultThumbnail}">
            </div>
            <div class="mt-1 p-3">
                <input accept="image/*" class="form-file" id="extraImage${index}" 
                onchange="handleExtraInputChange(this, ${index})" name="extraImage" type="file"/>
            </div>
        </div>`;

    $("#productImages").append(html);
}

function removeExtraImage(index) {
    $('#extraImageSection' + index).remove();
}