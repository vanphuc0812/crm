var api_url_user = "http://localhost:8080/crm/users"
var api_url_role = "http://localhost:8080/crm/roles"

$(document).ready(function () {
    $("#example tbody").empty()
    $.ajax({
        url: api_url_user,
        method: "GET"
    }).done(function(result){
        $.each(result,function(index,value){
            $.ajax({
                url: api_url_role+"/"+value.roleId,
                method: "GET"
            }).done(function(result){
                var role = result.description
                var row =
                    `
                    <tr>
                    <td>${value.id}</td>
                    <td>${value.fullName}</td>
                    <td>${value.email}</td>
                    <td>${role}</td>
                    <td>
                        <a href="#" user-id ="${value.id}" class="btn btn-sm btn-primary btn-edit">Sửa</a>
                        <a href="#" user-id ="${value.id}" class="btn btn-sm btn-danger btn-delete">Xóa</a>
                        <a href="user-details.html" user-id ="${value.id}" class="btn btn-sm btn-info btn-view">Xem</a>
                    </td>      
                    `
                $("#example tbody").append(row)
            })
        })
    })
// 
//-----------------------------------------------------Delete--------------------------------------------------
// 
    $("body").on('click', '.btn-delete', function(){
        var userId = $(this).attr('user-id')
        var This = $(this)
        $.ajax({
            url: api_url_user + '?id=' + userId,
            method:'DELETE',
            crossDomain: true
        }).done(function(result){
            if(result.isSuccess == true) {
                This.closest('tr').addClass('highlight-delete');
                setTimeout(function () {
                    This.closest('tr').removeClass('highlight-delete');
                    This.closest('tr').remove()
                }, 2000);
            }
            else console.log('Failed to delete')
        })
    })
// // 
// //-----------------------------------------------------Add-----------------------------------------------------
// // 
    // $('#add-new-user').click(function(e) {
    //     e.preventDefault()
    //     var name = $('#name').val()
    //     var email = $('#email').val()
    //     var password = $('#password').val()
    //     var avatar = $('#avatar').val()
    //     var role = $('#role-select').val()
    //     $.ajax({
    //         url: api_url,
    //         method:'POST',
    //         data: {
    //             name: name,
    //             email: email,
    //             password: password,
    //             avatar: avatar,
    //             role: role
    //         }
    //     }).done(function(result){
    //         if(result.isSuccess == true) {
    //             $(location).prop('href', 'user-table.html')//.done(function(){})
    //         }
    //         else $.toast({
    //             heading: 'Failed',
    //             text: 'Failed to add new user',
    //             showHideTransition: 'slide',
    //             position: 'top-center',
    //             icon: 'failed'
    //         })
    //     })
    // })
    


// //-----------------------------------------------------Edit----------------------------------------------------
    var userIdEdit
    var $editSubmit = $('#edit-submit');
    var row
    $("body").on('click', '.btn-edit', function(){
        row = $(this)
        $editSubmit[0].showModal()
        userIdEdit = $(this).attr('user-id')
    })
    $('#btn-cancel-edit').click(function(e) {
        e.preventDefault()
        console.log('cancel')
        $editSubmit[0].close();
    });

    $('#btn-edit-submit').click(function(e) {
        e.preventDefault()
        var role = $('#role').val()
        var description = $('#description').val()
        $.ajax({
            url: api_url,
            method:'PUT',
            processData: false,
            contentType: 'application/json',
            data: JSON.stringify({
                id: roleIdEdit,
                name: role,
                description: description
            })
        }).done(function(result){
            if(result.isSuccess == true) {
                $editSubmit[0].close();
                row.closest('tr').find("td:eq(1)").html(role);
                row.closest('tr').find("td:eq(2)").html(description);
                row.closest('tr').addClass('highlight-edit');
                setTimeout(function () {
                     row.closest('tr').removeClass('highlight-edit');
                }, 1000);
            }
            else console.log('Failed to update role')
        })
    })

})


