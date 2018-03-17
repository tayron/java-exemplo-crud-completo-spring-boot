$(document).ready(function() {
    var ufSelected = $("#federalUnit").data('selected') ? $("#federalUnit").data('selected') : 'AC';
    $("#federalUnit").val(ufSelected.toUpperCase());
    
    // company/save && company/edit
    if($("#federalUnit")){
        loadListCities();
    }
    
    $("#federalUnit").change(function() {
        loadListCities();
    });    
});