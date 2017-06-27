/**
 * Created by ad on 2017/6/27.
 */
$(document).ready(function () {
    $("input[type='file']").change(function(){
        var file = this.files[0];
        if (window.FileReader) {
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = function (e) {
                $("#1").attr("value",e.target.result);
            };
        }
    });
});