const productTable = document.getElementById("productTable")
let products =null;

	document.addEventListener("shown.bs.modal", ()=> {
		console.log("ddsdasda")
		fetch("/productManage/loadProducts", {method : "GET" })
		.then(r => r.json()
		.then(r => {
			console.log(r)
			products = r;
			renderProduct(r)
		})
	)
		
	})
	
function renderProduct(r) {
	
	Array.from(r).map(product => {
		
		const tr = document.createElement("tr");
		tr.innerHTML = `
		<td>${product.productCode}</td>
		<td>${product.productTypeDTO.productTypeName}</td>
		<td>${product.productName}</td>
		<td>
			<button type="button"
				class="btn btn-sm btn-primary select-product"
				data-code="${product.productCode}"
				data-type-code="${product.productTypeDTO.productTypeCode}"
				data-type-name="${product.productTypeDTO.productTypeName}"
				data-name="${product.productName}">선택</button>
		</td>
		`
		tr.querySelectorAll(".select-product").forEach(function(btn){
			
		})
		
	})
	
}