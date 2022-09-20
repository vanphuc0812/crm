$(document).ready(function(){
    // Apply alt class on alternate table row
    // $("table tbody tr:nth-child(2n)").addClass("alt");


    $("table tbody tr:nth-child(2n)").addClass('highlight-add');
    setTimeout(function () {
        $("table tbody tr:nth-child(2n)").removeClass('highlight-add');
    }, 2000);
    
});


