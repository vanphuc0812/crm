var api_url_task = "http://localhost:8080/crm/tasks"
var api_url_user = "http://localhost:8080/crm/users"
var api_url_job = "http://localhost:8080/crm/jobs"
var api_url_status = "http://localhost:8080/crm/status"

$(document).ready(function () {
    $("#example tbody").empty()
    $.ajax({
        url: api_url_task,
        method: "GET"
    }).done(function(result){
        $.each(result,function(index,value){
            var user
            var job
            var status
            $.ajax({
                url: api_url_user+"/"+value.userId,
                async: false,
                method: "GET"
            }).done(function(result){
                user = result.fullName
            })
            $.ajax({
                url: api_url_job+"/"+value.jobId,
                async: false,
                method: "GET"
            }).done(function(result){
                job = result.name
            })
            $.ajax({
                url: api_url_status+"/"+value.statusId,
                async: false,
                method: "GET"
            }).done(function(result){
                status = result.name
            })
            var row = `<tr> 
                    <td>${value.id}</td>
                    <td>${value.name}</td>
                    <td>${job}</td>
                    <td>${user}</td>
                    <td>${value.startDate}</td>
                    <td>${value.endDate}</td>
                    <td>${status}</td>
                    <td>
                        <a href="#" task-id=${value.id} class="btn btn-sm btn-primary btn-edit">Sửa</a>
                        <a href="#" task-id=${value.id} class="btn btn-sm btn-danger btn-delete">Xóa</a>
                        <a href="groupwork-details.html" task-id=${value.id} class="btn btn-sm btn-info btn-view">Xem</a>
                    </td>
                </tr>`
            $("#example tbody").append(row)
        })
    })
// 
//-----------------------------------------------------Delete--------------------------------------------------
// 
    $("body").on('click', '.btn-delete', function(){
        var taskId = $(this).attr('task-id')
        var This = $(this)
        $.ajax({
            url: api_url_task + '?id=' + taskId,
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
    $('#btn-save-task').click(function(e) {
        e.preventDefault()
        var name = $('#task').val()
        var job = $('#job option:selected').attr('job-id')
        var user = $('#user option:selected').attr('user-id')
        var startDate = $('#start-date').val()
        var endDate = $('#end-date').val()
        var status = $('#status option:selected').attr('status-id')
        $.ajax({
            url: api_url_task,
            method:'POST',
            data: {
                name: name,
                jobId: job,
                userId: user,
                startDate: startDate,
                endDate: endDate,
                statusId: status
            }
        }).done(function(result){
            if(result.isSuccess == true) {
                $(location).prop('href', 'task.html')
            }
            else console.log("failed")
        })
    })
    


//-----------------------------------------------------Edit----------------------------------------------------
    var task_id_edit
    var $editSubmit = $('#edit-dialog');
    var row
    $("body").on('click', '.btn-edit', function(){
        row = $(this)
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

        $editSubmit[0].showModal()
        task_id_edit = $(this).attr('task-id')
    })
    $('#btn-cancel-edit').click(function(e) {
        e.preventDefault()
        $editSubmit[0].close();
    });

    $('#btn-submit-edit').click(function(e) {
        e.preventDefault()
        var name = $('#task').val()
        var job = $('#job option:selected').attr('job-id')
        var user = $('#user option:selected').attr('user-id')
        var startDate = $('#start-date').val()
        var endDate = $('#end-date').val()
        var status = $('#status option:selected').attr('status-id')
        $.ajax({
            url: api_url_task,
            method:'PUT',
            processData: false,
            contentType: 'application/json',
            data: JSON.stringify({
                id: task_id_edit,
                name: name,
                jobId: job,
                userId: user,
                startDate: startDate,
                endDate: endDate,
                statusId: status
            })
        }).done(function(result){
            if(result.isSuccess == true) {
                $editSubmit[0].close();
                row.closest('tr').find("td:eq(1)").html(name);
                row.closest('tr').find("td:eq(2)").html(job);
                row.closest('tr').find("td:eq(3)").html(user);
                row.closest('tr').find("td:eq(4)").html(startDate);
                row.closest('tr').find("td:eq(5)").html(endDate);
                row.closest('tr').find("td:eq(6)").html(status);
                row.closest('tr').addClass('highlight-edit');
                setTimeout(function () {
                     row.closest('tr').removeClass('highlight-edit');
                }, 1000);
            }
            else console.log('Failed to update task')
        })
    })
})


