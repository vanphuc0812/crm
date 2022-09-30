var api_url_role = "http://localhost:8080/crm/roles"

$(document).ready(function () {
    $("#role").empty()
        $.ajax({
            url: api_url_role,
            method:'GET',
            async: false,
            crossDomain: true
        }).done(function(result){
            $.each(result,function(index,value){
                var option = `<option role-id="${value.id}">${value.description}</option>`;
                $("#role").append(option)
            })
        })
})