// 마커를 담을 배열입니다
var markers = [];

 // 마커 별표 이미지의 이미지 주소입니다
var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
// 마커 별표 이미지의 이미지 크기 입니다
var imageSize = new kakao.maps.Size(24, 35);
// 마커 별표 이미지를 생성합니다
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);
function setCenter(x,y) {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(x, y);

    // 지도 중심을 이동 시킵니다
    map.setCenter(moveLatLon);
    getBoundMarkers();
}
getBoundMarkers();

//별표 마커 생성하기
function startMarkerCreate(x,y,placeName){

    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(x, y), // 마커를 표시할 위치
        title : placeName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지
    });
     // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', function() {
            placeMarkerClick(x,y,placeName,0);
        });

}

function getBoundMarkers(){
// 정상적으로 검색이 완료됐으면 검색 목록과 마커를 표출합니다
        var bounds = map.getBounds();
        // 영역의 남서쪽 좌표를 얻어옵니다
        var swLatLng = bounds.getSouthWest();
        // 영역의 북동쪽 좌표를 얻어옵니다
        var neLatLng = bounds.getNorthEast();

        var boundData={
            sw_x:swLatLng.getLat(),
            sw_y:swLatLng.getLng(),
            ne_x:neLatLng.getLat(),
            ne_y:neLatLng.getLng()
        };
         $.ajax({
            type:'POST',
            url:'/',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(boundData),
        }).done(function(data){
            for (var i = 0; i < data.length; i ++) {
               startMarkerCreate(data[i].positionX,data[i].positionY,data[i].placeName);
            }
        }).fail(function(error){
            console.log( "Ajax failed: " + error['responseText'] );
        });
}
function placeMemoPagination(x,y,placeName,curPage,totalPages){
    var pageListSize=3;
    var startPageNumber=curPage-(curPage%pageListSize)+1;
    var endPageNumber=curPage-(curPage%pageListSize)+pageListSize;
    if(endPageNumber>totalPages){
        endPageNumber=totalPages;
    }

    var pagination='<input type="hidden" id="memoCurPage" value='+curPage+'>';
    if(curPage==0){
        pagination +='<li class="page-item disabled"><a class="page-link">Previous<a></li>';
    }else{
         pagination +='<li class="page-item " onclick="placeMarkerClick('
                        +x+','+y+',\''+placeName+'\','+(curPage-1)+');"><a class="page-link">Previous<a></li>';
    }
    for(var i=startPageNumber;i<=endPageNumber;i++){
        pagination+='<li class="page-item" onclick="placeMarkerClick('
                     +x+','+y+',\''+placeName+'\','+(i-1)+');"><a class="page-link">'+i+'<a></li>';
    }
    if(curPage+1>=totalPages){
        pagination +='<li class="page-item disabled"><a class="page-link">Next<a></li>';
    }else{
         pagination +='<li class="page-item " onclick="placeMarkerClick('
                                 +x+','+y+',\''+placeName+'\','
                                 +(curPage+1)+');"><a class="page-link">Next<a></li>';
    }
    document.getElementById('placeMemoListPaginationUl').innerHTML=pagination;
}
//마커를 클릭했을 경우 그 장소에 대한 후기 리스트를 보여준다
function placeMarkerClick(x,y,placeName,page){
     document.getElementById('searchListDiv').style.display='none';
     document.getElementById('placeMemoListDiv').style.display='block';
     document.getElementById('placeTitle').innerHTML=placeName;
     document.getElementById('placeName').value=placeName;
     document.getElementById('placeX').value=x;
     document.getElementById('placeY').value=y;

     var userCheck=document.getElementById('userCheck').value;
      $.ajax({
            type:'GET',
            url:'/api/v1/'+x+'/'+y+'/'+page,
        }).done(function(data){
            var arr=data.content;
            var list='';
            for(var i=0;i<arr.length;i++){
                list +='<li class="list-group-item">'
                        +'<div class="container overflow-hidden p-0">'
                        +'<div class="row g-0"><div class="col-sm-6 col-md-8">'
                        +arr[i].content+'</div>';
                if(userCheck==arr[i].member.id){
                    list+='<div class="col-6 col-md-4">'
                    +'<a class="updateATag" onClick="updatePlace('+arr[i].id+')">수정하기</a>'
                    +'<a class="deleteATag" onClick="deletePlace('+arr[i].id+')">삭제하기</a>'

                }

                list+='</div></li>';
            }
            document.getElementById('placeMemoListUl').innerHTML=list;

            placeMemoPagination(x,y,placeName,data.pageable.pageNumber,data.totalPages)

        }).fail(function(request, status, error){
            console.log( "code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        });
}
// 마우스 드래그시 조회 영역안 기록 존재하는 곳 별표 마커 표시
kakao.maps.event.addListener(map, 'dragend', function() {
    getBoundMarkers()
});

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();
// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});
// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
    var keyword = document.getElementById('searchKeyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }
    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch( keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(searchData, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        displayPlaces(searchData);
        // 페이지 번호를 표출합니다
        displayPagination(pagination);

    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;

    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

    var listEl = document.getElementById('placesList'),
    menuEl = document.getElementById('searchList'),
    fragment = document.createDocumentFragment(),
    bounds = new kakao.maps.LatLngBounds(),
    listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for ( var i=0; i<places.length; i++ ) {

        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function(marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function() {
                displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });

            itemEl.onmouseover =  function () {
                displayInfowindow(marker, title);
            };

            itemEl.onmouseout =  function () {
                infowindow.close();
            };
        })(marker, places[i].placeName);

        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);

}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {

    var el = document.createElement('li');
    var itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                '<div class="info">' +
                '   <h5>' + places.place_name +
                '<button type="button" class="btn btn-sm btn-primary" '
                +'onclick="writePlace(\''+places.place_name+'\','+places.y+','+places.x+');">기록하기</button>'
                + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone  + '</span>' +
                '</div>';

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('searchListPaginationUl'),
        fragment = document.createDocumentFragment(),
        i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
    }

    for (i=1; i<=pagination.last; i++) {
        var elLi=document.createElement('li');
        elLi.className='page-item';
        var el = document.createElement('a');
        el.innerHTML = i;

        if (i===pagination.current) {
            el.className = 'on page-link';
        } else {
            el.className='page-link'
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                }
            })(i);
        }
        elLi.appendChild(el)
        fragment.appendChild(elLi);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
    }
}




