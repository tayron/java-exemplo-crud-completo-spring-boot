function loadListCities() {
    var federalUnit = $("#federalUnit").val();
    
    if (typeof(Storage) !== "undefined") {
       
        var listCities = localStorage.getItem("listCities-" + federalUnit);
       
        if(listCities){
            setListCities('citySelected', listCities.split(','));
        }else{
            $.get( "/company/listCities/" + federalUnit, function( listCities ) {
                setListCities('citySelected', listCities);
                localStorage.setItem("listCities-" + federalUnit, listCities.join(','));
            });                        
        }        
    } else {
        $.get( "/company/listCities/" + federalUnit, function( listCities ) {
            setListCities('citySelected', listCities);
        });  
    }
};

function setListCities(idElement, listCities){
    var htmlListCities = "";

    listCities.forEach(function(city) {         
        htmlListCities += "<option value = '" + city + "' >" + city +  "</option>";            
    });

    $("#"+idElement).html(htmlListCities);
    
    if(htmlListCities !== ""){
        $("#"+idElement).val($("#"+idElement).data('selected'));        
        $("#"+idElement).removeAttr('disabled');
    }else{                
        $("#"+idElement).removeAttr('disabled');
        $("#"+idElement).attr('disabled');
    }
}

function getUrl()
{
    var url = window.location.href;
    var urlList = url.split("/");
    urlList.splice(0,3);
    return urlList.join("/");
}
