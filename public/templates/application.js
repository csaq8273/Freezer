/**
 * Created by Ivan on 20.01.2015.
 */

var haslocation=false;

function locationChanged(location){
    haslocation=true;
    $.ajax({
        type: "POST",
        url: "/skier",
        data: {"setLocation" : location},
        success: function(data){
            $("#freezers" ).html(data+ " other Freezers.");
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
    postSearch();
});

$(document).on('submit', '#meet_form', function(e) {
    e.preventDefault();
    postMeet();
});


$.mobile.popup.prototype.options.history = false;


function postSearch(){
    if(haslocation==false){
        alert("You first have to set your Location!")
        location.href= "#home";
    }else {
        $.ajax({
            type: "post",
            url: "/search",
            data: $('#search_form').serialize(),
            dataType: 'json',
            success: function (response, textStatus, XMLHttpRequest) {
                if (response.error) {
                    console.log('ERROR: ' + response.error);
                } else {
                    mediaSource = response.url;
                    $("#number_of_skier").html("Search result: " + response.length + " Skiers");
                    $("#skier_list").html("");
                    if (response.length == 0) {
                        $("#skier_list").append('<li data-role="list-divider">No skiers Found!</li>' +
                        '                    <li></li>');
                    }
                    response.forEach(function (entry) {
                        var birth = new Date(entry.birthdate);
                        var interests = "";
                        if (entry.interests.length == 0) interests += "nothing";
                        else {
                            entry.interests.forEach(function (interest) {
                                interests += interest.name + ", ";
                            });
                        }
                        $("#skier_list").append('<a href="javascript:getMeet(' + entry.id + ')" data-role="button" > <li data-role="list-divider">' + entry.username + '</li>' +
                        '                    <li>' +
                        '                        <h2>' + entry.firstname + ' ' + entry.lastname + '</h2> ' +
                        '                        <p>' + birth.getYear() + ' Years old. ' + ((entry.current_location == null) ? 'offline' : 'Currently at ' + entry.current_location.name) + '</p>' +
                        '                        <p>Interested in ' + interests + '</p>' +
                        '                            ' +
                        '                            </li></a>');
                    });
                    $("#skier_list").listview().listview('refresh');
                    location.href = "#skierList";

                }
            }
        });
    }
}

function getMeet(id){
    $.ajax({
        type: "get",
        url: "/meeting/"+id,
        success: function(response, textStatus, XMLHttpRequest) {
            if (response.error) {
                console.log('ERROR: ' + response.error);
            } else {
                mediaSource = response.url;
                $("body").append(response);
                $('#meeting_dialog').dialog();
                $("body").append('<div style="position: fixed; top:0px; left:0px; width: 100%; height: 100%; background: #000;opacity:0.5;z-index: 100;"></div>');
                $("#meeting_dialog").css({"z-index":1000});
                $("#meeting_dialog").children().css({"z-index":1000});
                $("#meeting_dialog").children($(".ui-content")).css({"background":"#fff"});
              //  location.href="#skierList&ui-state=dialog";
            }
        }
    });
}


function postMeet(){
    $.ajax({
        type: "post",
        url: "/meet/"+$("#otherSkier").val(),
        data: {
            "time":$("#time").val(),
            "otherSkier":$("#otherSkier").val(),
            "lift":$("#lift").val()
        },
        dataType: 'json',
        success: function(response, textStatus, XMLHttpRequest) {
            if (response.error) {
                console.log('ERROR: ' + response.error);
            } else {
                mediaSource = response.url;
                location.reload();
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

$(document).ready(function(){
    if(location.href.indexOf("skierList")!=-1){
        if($("#number_of_skier").html().indexOf("Search")==-1){

            $.mobile.changePage("/")
        }
    }
});