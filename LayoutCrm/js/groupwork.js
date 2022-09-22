var api_url_groupwork = "http://localhost:8080/crm/jobs"

$(document).ready(function () {
    $("#example tbody").empty()
    $.ajax({
        url: api_url_groupwork,
        method: "GET"
    }).done(function(result){
        $.each(result,function(index,value){
            console.log(value)
            var row = `<tr>
                <td>${value.id}</td>
                <td>${value.name}</td>
                <td>${value.startDate}</td>
                <td>${value.endDate}</td>
                <td>
                    <a href="#" job-id=${value.id} class="btn btn-sm btn-primary btn-edit">Sửa</a>
                    <a href="#" job-id=${value.id} class="btn btn-sm btn-danger btn-delete">Xóa</a>
                    <a href="groupwork-details.html" job-id=${value.id} class="btn btn-sm btn-info btn-view">Xem</a>
                </td>
            </tr>`

            $("#example tbody").append(row)
        })
    })
// 
//-----------------------------------------------------Delete--------------------------------------------------
// 
    $("body").on('click', '.btn-delete', function(){
        var jobId = $(this).attr('job-id')
        var This = $(this)
        $.ajax({
            url: api_url_groupwork + '?id=' + jobId,
            method:'DELETE',
            crossDomain: true
        }).done(function(result){
            if(result.isSuccess == true) {
                This.closest('tr').addClass('highlight-delete');
                setTimeout(function () {
                    This.closest('tr').removeClass('highlight-delete');
                    This.closest('tr').remove()
                }, 1000);
            }
            else console.log('Failed to delete')
        })
    })
// 
//-----------------------------------------------------Add-----------------------------------------------------
// 
    $('#btn-save-job').click(function(e) {
        e.preventDefault()
        var name = $('#name').val()
        var start_date = $('#start_date').val()
        var end_date = $('#end_date').val()
        $.ajax({
            url: api_url_groupwork,
            method:'POST',
            data: {
                name: name,
                start_date: start_date,
                end_date: end_date
            }
        }).done(function(result){
            if(result.isSuccess == true) {
                $(location).prop('href', 'groupwork.html')
            }
            else $.toast({
            })
        })
    })
    


//-----------------------------------------------------Edit----------------------------------------------------
    var job_id_edit
    var $editSubmit = $('#edit-dialog');
    var row
    $("body").on('click', '.btn-edit', function(){
        row = $(this)
        $editSubmit[0].showModal()
        job_id_edit = $(this).attr('job-id')
    })
    $('#btn-cancel-edit').click(function(e) {
        e.preventDefault()
        console.log('cancel')
        $editSubmit[0].close();
    });

    $('#btn-edit-submit').click(function(e) {
        e.preventDefault()
        var name = $('#name').val()
        var startDate = $('#start_date').val()
        var endDate = $('#end_date').val()
        $.ajax({
            url: api_url_groupwork,
            method:'PUT',
            processData: false,
            contentType: 'application/json',
            data: JSON.stringify({
                id: job_id_edit,
                name: name,
                startDate: startDate,
                endDate: endDate
            })
        }).done(function(result){
            if(result.isSuccess == true) {
                $editSubmit[0].close();
                row.closest('tr').find("td:eq(1)").html(name);
                row.closest('tr').find("td:eq(2)").html(startDate);
                row.closest('tr').find("td:eq(3)").html(endDate);
                row.closest('tr').addClass('highlight-edit');
                setTimeout(function () {
                     row.closest('tr').removeClass('highlight-edit');
                }, 1000);
            }
            else console.log('Failed to update role')
        })
    })

})


