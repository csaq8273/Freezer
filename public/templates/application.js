/**
 * Created by Ivan on 20.01.2015.
 */

function locationChanged(location){
    $.ajax({
        type: "POST",
        url: "/skier",
        data: {"setLocation" : location},
        success: function(data){
            $("#freezers" ).html(data);
        }
    });
    $("#curLoc" ).html(location);
}


$(document).ready(function(){

});