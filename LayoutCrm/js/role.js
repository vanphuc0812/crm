var api_url = "http://localhost:8080/crm/roles"

$(document).ready(function () {
    $.ajax({
        url: api_url,
        method: "GET"
    }).done(function(result){
        $("#example tbody").empty()
        $.each(result,function(index,value){
            console.log(value)
            var row = `<tr>
                <td>${value.id}</td>
                <td>${value.name}</td>
                <td>${value.description}</td>
                <td>
                    <a href="#" role-id=${value.id} class="btn btn-sm btn-primary btn-edit">Sửa</a>
                    <a href="#" role-id=${value.id} class="btn btn-sm btn-danger btn-delete">Xóa</a>
                </td>
            </tr>`

            $("#example tbody").append(row)
        })
    })
// 
//-----------------------------------------------------Delete--------------------------------------------------
// 
    $("body").on('click', '.btn-delete', function(){
        var roleId = $(this).attr('role-id')
        var This = $(this)
        $.ajax({
            url: api_url + '?id=' + roleId,
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
// 
//-----------------------------------------------------Add-----------------------------------------------------
// 
    $('#btn-save-role').click(function(e) {
        e.preventDefault()
        var role = $('#role').val()
        var description = $('#description').val()
        $.ajax({
            url: api_url,
            method:'POST',
            data: {
                role: role,
                description: description
            }
        }).done(function(result){
            if(result.isSuccess == true) {
                $(location).prop('href', 'role-table.html')//.done(function(){})
            }
            else $.toast({
                heading: 'Failed',
                text: 'Failed to add new role',
                showHideTransition: 'slide',
                position: 'top-center',
                icon: 'success'
            })
        })
    })
    


//-----------------------------------------------------Edit----------------------------------------------------
    var roleIdEdit
    var $editSubmit = $('#edit-submit');
    var row
    $("body").on('click', '.btn-edit', function(){
        row = $(this)
        $editSubmit[0].showModal()
        roleIdEdit = $(this).attr('role-id')
    })
    $('#btn-cancel-edit').click(function(e) {
        e.preventDefault()
        console.log('cancel')
        $editSubmit[0].close();
    });

    $('#btn-edit-role-submit').click(function(e) {
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


