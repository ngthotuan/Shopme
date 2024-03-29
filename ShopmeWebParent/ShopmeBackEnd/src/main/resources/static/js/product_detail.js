$('#btnAddDetails').click(function () {
    addRemoveButton();
    const html = `<div class="mb-3 row detail-item">
           <div class="col-sm-1 col-form-label">Name:</div>
           <div class="col-sm-4">
               <input class="form-control" maxlength="256" type="text" name="detailNames">
           </div>
           <div class="col-sm-1 col-form-label">Value:</div>
           <div class="col-sm-5">
               <input class="form-control" maxlength="256" type="text" name="detailValues">
           </div>
           <input type="hidden" name="detailIDs" value="-1">
          </div>`;
    $("#productDetails").append(html);
});

function addRemoveButton() {
    const removeDetail = `<div class="col-sm-1">
               <button class="btn" onclick="removeDetailItem(this)" type="button">
                   <span class="fas fa-2x fa-times-circle text-danger"></span>
               </button>
           </div>`;
    $('.detail-item').last().append(removeDetail);
}

function removeDetailItem(btn) {
    $(btn).parents('.detail-item').remove();
}