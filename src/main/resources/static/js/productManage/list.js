// 모달 닫기

	
const modalEl = document.getElementById('productModal');
const productModal = new bootstrap.Modal(modalEl);
const searchInput = document.getElementById("searchInput");
const productTable = document.getElementById("productTable")
let products =null;


document.addEventListener("shown.bs.modal", ()=> {
	fetch("/productManage/loadProducts", {method : "GET" })
	.then(r => r.json()
	.then(r => {
		console.log(r)
		products = r;
		renderProduct(r)
	})
	)
})
	

searchInput.addEventListener("input", e => {
	const keyword = e.target.value;
	console.log("입력됨")
	console.log(keyword)
	
	const filteredProduct = products.filter(p => 
			p.productName(keyword)
		);
		renderStaff(filteredProduct);
})

function renderProduct(r) {
	
	productTable.innerHTML = ""
	
	r.forEach((product)=>{
		
		const tr = document.createElement("tr");
		tr.innerHTML = `
		<td>${product.productCode}</td>
		<td>${product.productTypeDTO.productTypeName}</td>
		<td>${product.productName}</td>
		<td>
			<button type="button"
				class="btn btn-sm btn-primary select-product"
				onclick="selectProduct(
					${product.productCode},
					${product.productTypeDTO.productTypeCode},
					'${product.productTypeDTO.productTypeName}',
					'${product.productName}'
				)">선택</button>		
		</td>
		`
			productTable.append(tr);
	})
}

function selectProduct(code, typeCode, typeName, name){
    // 부모 form input에 넣기
    document.querySelector("input[name='productCode']").value = code;
    document.querySelector("input[name='productTypeDTO.productTypeCode']").value = typeCode;
    document.querySelector("input[name='productTypeDTO.productTypeName']").value = typeName;
    document.querySelector("input[name='productName']").value = name;
	
	productModal.hide();
	
}