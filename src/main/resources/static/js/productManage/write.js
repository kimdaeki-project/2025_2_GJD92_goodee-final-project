// 모달 닫기

	
const modalEl = document.getElementById('productModal');
const productModal = new bootstrap.Modal(modalEl);
const searchInput = document.getElementById('searchInput');
const productTable = document.getElementById('productTable')
let products =null;


document.addEventListener("shown.bs.modal", ()=> {
	fetch("/productManage/loadProducts", {method : "GET" })
	.then(r => r.json()
	.then(r => {
		console.log(r)
		products = r;
		renderProduct(products)
	})
	)
})
	

searchInput.addEventListener("input", e => {
	const keyword = e.target.value.trim().toLowerCase();
	
	console.log(keyword)
	
	const filteredProduct = products.filter(p => 
			p.productName.toLowerCase().includes(keyword) || // 물품이름
			p.productTypeDTO.productTypeName.toLowerCase().includes(keyword) || // 물품타입이름
			p.productCode.toString().includes(keyword) // 물품코드
		);
		
		renderProduct(filteredProduct); // 결과물 랜더링
})

function renderProduct(r) {
	if(!productTable) return; // 태그확인 안전장치
	
	productTable.innerHTML = ""
	
	r.forEach((product)=>{
		
		const tr = document.createElement("tr");
		tr.innerHTML = `
		<td>${product.productCode}</td>
		<td>${product.productTypeDTO.productTypeName}</td>
		<td>${product.productName}</td>
		<td>
			<button type="button"
				class="btn btn-sm bg-gradient-dark select-product"
				onclick="selectProduct(
					${product.productCode},
					${product.productTypeDTO.productTypeCode},
					'${product.productTypeDTO.productTypeName}',
					'${product.productName}',
					'${product.productSpec}'
				)">선택</button>		
		</td>
		`
		productTable.appendChild(tr);
	})
}

function selectProduct(code, typeCode, typeName, name, spec){
    // 부모 form input에 넣기
    document.querySelector("input[name='productCode']").value = code;
    document.querySelector("input[name='productTypeDTO.productTypeCode']").value = typeCode;
    document.querySelector("input[name='productTypeDTO.productTypeName']").value = typeName;
    document.querySelector("input[name='productName']").value = name;
    document.querySelector("input[name='productSpec']").value = spec;
	
	productModal.hide();
	
}

