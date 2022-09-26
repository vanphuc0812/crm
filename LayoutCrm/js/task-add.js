var api_url_task = "http://localhost:8080/crm/tasks"
var api_url_user = "http://localhost:8080/crm/users"
var api_url_job = "http://localhost:8080/crm/jobs"
var api_url_status = "http://localhost:8080/crm/status"

$(document).ready(function () {
    $("#job").empty()
        $.ajax({
            url: api_url_job,
            method:'GET',
            async: false,
            crossDomain: true
        }).done(function(result){
            $.each(result,function(index,value){
                var option = `<option job-id="${value.id}">${value.name}</option>`
                $("#job").append(option)
            })
        })
        $("#user").empty()
        $.ajax({
            url: api_url_user,
            method:'GET',
            async: false,
            crossDomain: true
        }).done(function(result){
            $.each(result,function(index,value){
                var option = `<option user-id="${value.id}">${value.fullName}</option>`
                $("#user").append(option)
            })
        })
        $("#status").empty()
        $.ajax({
            url: api_url_status,
            method:'GET',
            async: false,
            crossDomain: true
        }).done(function(result){
            $.each(result,function(index,value){
                var option = `<option status-id="${value.id}">${value.name}</option>`
                $("#status").append(option)
            })
        })
})