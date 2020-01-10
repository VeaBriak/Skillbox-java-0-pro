$(function(){

    const appendTask = function(data){
        var taskCode = '<a href="#" class="task-link" data-id="' +
             data.id + '">' + data.name + '</a><br>';
        $('#task-list')
            .append('<div>' + taskCode +  '</div>');
    };

    //Loading books on load page
//    $.get('/tasks/', function(response)
//    {
//        for(i in response) {
//            appendTask(response[i]);
//        }
//    });

    //Show adding book form
    $('#show-add-task-form').click(function(){
        $('#task-form').css('display', 'flex');
    });

    //Closing adding book form
    $('#task-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting book
    $(document).on('click', '.task-link', function(){
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tasks/' + taskId,
            success: function(response)
            {
                var code = '<span>Дело:' + response.text + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Дело не найдено!');
                }
            }
        });
        return false;
    });

    //Adding book
    $('#save-task').click(function()
    {
        var data = $('#task-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function(response)
            {
                $('#task-form').css('display', 'none');
                var task = {};
                task.id = response;
                var dataArray = $('#task-form form').serializeArray();
                for(i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
            }
        });
        return false;
    });

});