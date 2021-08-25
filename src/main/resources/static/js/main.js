$('#placesSaveBtn').on('click',function(){
    var data={
        place_name:$('#place_name').val(),
        content:$('#content').val(),
        position_x:$('#position_x').val(),
        position_y:$('#position_y').val(),
    };
    $.ajax({
        type:'POST',
        url:'/api/v1/places',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function(){
        alert('글이 등록되었습니다.');
         myModal.toggle();
    }).fail(function(error){
        console.log( "Ajax failed: " + error['responseText'] );
    });
})