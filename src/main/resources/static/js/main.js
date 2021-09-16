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

$('#placesUpdateBtn').on('click',function(){
    var data={
        content:$('#content').val(),
    };
    var id=$('#id').val();
    $.ajax({
        type:'PUT',
        url:'/api/v1/places/'+id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function(){
        alert('글이 수정되었습니다.');
         myModal.toggle();
    }).fail(function(error){
        console.log( "Ajax failed: " + error['responseText'] );
    });
})

$('#placesDeleteBtn').on('click',function(){

    var id=$('#id').val();
    $.ajax({
        type:'DELETE',
        url:'/api/v1/places/'+id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function(){
        alert('글이 삭제되었습니다.');
         myModal.toggle();
    }).fail(function(error){
        console.log( "Ajax failed: " + error['responseText'] );
    });
})