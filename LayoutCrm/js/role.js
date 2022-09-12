$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/crm/role",
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
                    <a href="#" class="btn btn-sm btn-primary">Sửa</a>
                    <a href="#" role-id=${value.id} class="btn btn-sm btn-danger btn-delete">Xóa</a>
                </td>
            </tr>`

            $("#example tbody").append(row)
        })
    })

    $("body").on('click', '.btn-delete', function(){
        var roleId = $(this).attr('role-id')
        var This = $(this)
        $.ajax({
            url:'http://localhost:8080/crm/role?id=' + roleId,
            method:'DELETE',
            crossDomain: true
        }).done(function(result){
            if(result.isSuccess == true) {
                This.closest('tr').remove()
            }
            else console.log('Failed to delete')
        })
    })

    $('#btn-save-role').click(function(e) {
        e.preventDefault()
        var role = $('#role').val()
        var description = $('#description').val()
        $.ajax({
            url:'http://localhost:8080/crm/role',
            method:'POST',
            data: {
                role: role,
                description: description
            }
        }).done(function(result){
                if(result.isSuccess == true) {
                    $(location).prop('href', 'role-table.html').done(function(){
                        $.toast({
                            heading: 'Success',
                            text: 'And these were just the basic demos! Scroll down to check further details on how to customize the output.',
                            showHideTransition: 'slide',
                            position: 'top-center',
                            icon: 'success'
                        })
                    })
                    
                    
                    $('').addClass('highlighted');
                    setTimeout(function () {
                        $('div.highlightable').removeClass('highlighted');
                    }, 2000);
                }
                else console.log('Failed to add new role')
        })
    })
})


