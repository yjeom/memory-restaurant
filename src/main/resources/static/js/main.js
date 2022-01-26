var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'));

$('#placesSaveBtn').on('click',function(){
    var data={
        placeName:$('#placeName').val(),
        content:$('#content').val(),
        positionX:$('#positionX').val(),
        positionY:$('#positionY').val(),
    };
    if(data.content==null||data.content.trim()==''){
        alert("내용을 작성해주세요.");
        return;
    }
    $.ajax({
        type:'POST',
        url:'/api/v1/places',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function(){
        alert('글이 등록되었습니다.');
         myModal.toggle();
         setCenter(data.positionX,data.positionY);

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
    }).done(function(data){
        alert('글이 수정되었습니다.');
        myModal.toggle();
        var page=$('#memoCurPage').val();
        placeMarkerClick(data.positionX,data.positionY,data.placeName,page);
    }).fail(function(error){
        console.log( "Ajax failed: " + error['responseText'] );
    });
})

$('#placesDeleteBtn').on('click',function(){

    var id=$('#id').val();
    deletePlace(id);

})
// 음식점 메모 기록창 열기
function writePlace(name,x,y){
    var userCheck=document.getElementById('userCheck').value;
    if(userCheck=='0'){
        location.href="/member/login";
    }
    document.getElementById('staticBackdropLabel').innerHTML=name;
    document.getElementById('content').value='';
    document.getElementById('placesUpdateBtn').style.display='none'
    document.getElementById('placesDeleteBtn').style.display='none';
    document.getElementById('placesSaveBtn').style.display='block';

    var str='<input type="hidden" id="placeName" name="placeName" value="'+name+'">'
            +'<input type="hidden" id="positionX" name="positionX" value="'+x+'">'
            +'<input type="hidden" id="positionY" name="positionY" value="'+y+'">';

    document.getElementById('placeInformation').innerHTML=str;
    myModal.toggle();

}
function updatePlace(id){
    var userCheck=document.getElementById('userCheck').value;
    if(userCheck=='0'){
        location.href="/member/login";
    }
    $.ajax({
        type:'GET',
        url:'/api/v1/places/'+id,
    }).done(function(data){
        document.getElementById('staticBackdropLabel').innerHTML=data.placeName;
        document.getElementById('content').value=data.content;
        document.getElementById('placesSaveBtn').style.display='none';
        document.getElementById('placesUpdateBtn').style.display='block';
        document.getElementById('placesDeleteBtn').style.display='block';

        var str='<input type="hidden" id="placeName" name="placeName" value="'+data.placeName+'">'
                    +'<input type="hidden" id="positionX" name="positionX" value="'+data.positionX+'">'
                    +'<input type="hidden" id="positionY" name="positionY" value="'+data.positionY+'">'
                    +'<input type="hidden" id="id" name="id" value="'+data.id+'">';

        document.getElementById('placeInformation').innerHTML=str;
        myModal.toggle();
    }).fail(function(error){
              console.log( "Ajax failed: " + error['responseText'] );
    });
}
function deletePlace(id){

    $.ajax({
        type:'DELETE',
        url:'/api/v1/places/'+id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function(){
        alert('글이 삭제되었습니다.');
        if(myModal._isShown){
         myModal.toggle();
        }
        var page=$('#memoCurPage').val();
        var x=$('#placeX').val();
        var y=$('#placeY').val();
        var placeName=$('#placeName').val();
        placeMarkerClick(x,y,placeName,page);
    }).fail(function(error){
        console.log( "Ajax failed: " + error['responseText'] );
    });
}