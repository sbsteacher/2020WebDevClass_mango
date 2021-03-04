async function clkGetLatLng () {
	const frm = document.querySelector('#frm')
	const addr = frm.addrElem.value
	
	if(!addr) {
		alert('주소를 입력해 주세요')
		return
	}
	
	var result = await getAddrLatLng(addr)
	frm.lat.value = result.lat
	frm.lng.value = result.lng
}

function getAddrLatLng (addr) {
	return new Promise((resolve, reject) => {
		fetch(`/api/getAddrLatLng?addr=${addr}`)
		.then(res => res.json())
		.then(myJson => {
			if(myJson) {
				resolve(myJson)
			} else {
				reject()
			}
		})
	})
	
}