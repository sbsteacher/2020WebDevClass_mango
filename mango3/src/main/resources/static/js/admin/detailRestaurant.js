
const menuInfoFrm = document.querySelector('#menuInfoFrm')

let map = null

function clkAddMenuInfo () {
	const div = document.createElement('div')
	div.innerHTML = `<label>메뉴명 : <input type="text" name="menuNm" required></label>
					<label>금액 : <input type="number" name="menuPrice" required></label>`
	menuInfoFrm.append(div)			
}

function clkSubmitMenuInfo () {
	menuInfoFrm.submit()
}


