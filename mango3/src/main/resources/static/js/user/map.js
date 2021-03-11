const leftDiv = document.querySelector('#left')

let basicLat = 35.86564975581277, basicLng = 128.59322689140217 //반월당역
let gMap = null
let gData = null

const markerList = []

function init(lat, lng) {
	const latLng = new kakao.maps.LatLng(lat, lng)
	
	const options = { //지도를 생성할 때 필요한 기본 옵션
		center: latLng,
		level: 3 //지도의 레벨(확대, 축소 정도)
	}
	const mapContainer = document.querySelector('#map')
	gMap = new kakao.maps.Map(mapContainer, options)
	
	//(event)맵 drag가 멈추면 
	kakao.maps.event.addListener(gMap, 'dragend', function() {
		removeMarkers()
		getData()
	})
	
	getData()
}

function getData() {
	const bounds = gMap.getBounds()
	const neLat = bounds.getNorthEast().getLat()
	const neLng = bounds.getNorthEast().getLng()
	
	const swLat = bounds.getSouthWest().getLat()
	const swLng = bounds.getSouthWest().getLng()
	
	getMapData(swLat, neLat, swLng, neLng)
}

//브라우저가 현재 위치 액세스 할 수 있다면 현재 위치로 지도를 띄운다.
if('geolocation' in navigator) {
	navigator.geolocation.getCurrentPosition(pos => {
	  init(pos.coords.latitude, pos.coords.longitude)
	}, failure => {	  
	  init(basicLat, basicLng)
	});	
} else {
	init(basicLat, basicLng)	
}

//마커 자료 가져오기
function getMapData(swLat, neLat, swLng, neLng) {
	fetch(`/user/getMapData?swLat=${swLat}&neLat=${neLat}&swLng=${swLng}&neLng=${neLng}&`)
	.then(res => res.json())
	.catch(e => {
		alert('자료를 가져오는데 실패하였습니다.')
	})
	.then(data => {
		gData = data
		makeMarkers()
		makeList()
	})
	
}

//모든 마커 삭제
function removeMarkers() {
	for(var i=0; i<markerList.length; i++) {
		const m = markerList[i]
		m.setMap(null)
	}
}

function makeMarkers() {	
	if(!gData) { return }
	
	gData.forEach(item => {
		// 마커가 표시될 위치입니다 
		const mPos  = new kakao.maps.LatLng(item.lat, item.lng)
		
		const marker = new kakao.maps.Marker({
		    position: mPos,
			clickable: true // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
		})
		
		marker.setMap(gMap)
		
		markerList.push(marker) //나중에 마커 삭제 할 때 사용함
		
		kakao.maps.event.addListener(marker, 'click', function() {
			moveToDetail(item.restPk)	       
		});
	})
}

function moveToDetail(restPk) {
	location.href=`/user/detailRestaurant?restPk=${restPk}`
}


function makeList() {
	if(!gData) { return }
	
	leftDiv.innerHTML = null
	
	gData.forEach(item => {
		const divContainer = document.createElement('div')
		divContainer.classList.add('item')
		divContainer.addEventListener('click', function() {
			moveToDetail(item.restPk)
		})
		
		const img = document.createElement('img')
		img.src = `/img/rest/${item.restPk}/review/${item.mainImg}`
		img.onerror = function() {
			img.onerror = null
			img.src = '/img/basic.jpg'
		}
		
		const nmH3 = document.createElement('h3')		
		nmH3.innerText = item.restNm
		
		divContainer.append(img)
		divContainer.append(nmH3)
		
		leftDiv.append(divContainer)
	})

}




