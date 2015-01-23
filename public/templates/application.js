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

function switchInterests(){
    if($("#interests_field").css("visibility")=="hidden")
        $("#interests_field").css({"visibility":"visible"});
    else
        $("#interests_field").css({"visibility":"hidden"})
}

$(document).on('submit', '#search_form', function(e) {
    e.preventDefault();
    postForm();
});

$.mobile.popup.prototype.options.history = false;


function postForm(){
    $.ajax({
        type: "post",
        url: "/search",
        data: $('#search_form').serialize(),
        dataType: 'json',
        success: function(response, textStatus, XMLHttpRequest) {
            if (response.error) {
                console.log('ERROR: ' + response.error);
            } else {
                mediaSource = response.url;
                $("#number_of_skier").html(response.length + " Skiers found");
                $("#skier_list").html("");

                response.forEach(function(entry) {
                    var birth=new Date(entry.birthdate);
                    var interests="";
                    if(entry.interests.length==0) interests+="nothing";
                    else{
                        entry.interests.forEach(function(interest){
                            interests+= interest.name + ", ";
                        });
                    }
                    $("#skier_list").append('<li data-role="list-divider">'+entry.username+'</li>' +
                    '                    <li><a href="/meeting/'+entry.id+'" data-role="button" data-rel="dialog" data-transition="pop"> ' +
                    '                        <h2>'+entry.firstname + ' ' + entry.lastname+'</h2> ' +
                    '                        <p>'+birth.getYear()+' Years old. '+((entry.current_location==null)?'offline':'Currently at '+ entry.current_location.name)+'</p>' +
                    '                        <p>Interested in '+interests+'</p>' +
                    '                            </a>' +
                    '                            </li>');
                    location.href="#skierList";
                });
            }
        }
    });
}

function requestMeeting(id,lift){
    $.ajax({
        type: "post",
        url: "/meeting",
        data: {"request":"true","id":id},
        dataType: 'json',
        success: function(response, textStatus, XMLHttpRequest) {
            if (response.error) {
                console.log('ERROR: ' + response.error);
            } else {
                mediaSource = response.url;

            }
        }
    });
}