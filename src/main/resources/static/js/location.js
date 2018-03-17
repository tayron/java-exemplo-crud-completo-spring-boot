$(document).ready(function() {// Isso aqui fica temporariamente aqui em breve tem que verificar onde ficar� definitivamente.
        var param = new URLSearchParams(window.location.search).get('lang');
        var prefix = "#lang_";
        var target = ""; 
        if(param == undefined){
                target = prefix + "pt_BR";
        }else{
                target = prefix + param;
        }
        $("#imgNavSel").attr("src", $(target).find("img").attr("src"));
        $("#lanNavSel").html($(target).find("span").html().substring(0, 3).toUpperCase());
});