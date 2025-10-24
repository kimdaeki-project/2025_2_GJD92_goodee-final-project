function DaumPostcode() {
  new daum.Postcode({
    oncomplete: function(data) {
      var roadAddr = data.roadAddress;

      document.getElementById('staffPostcode').value = data.zonecode;
      document.getElementById('staffAddress').value = roadAddr;
    }
  }).open();
}

document.querySelector("#attach").addEventListener("change", (event) => {
	if (event.target.files && event.target.files[0]) {
			const reader = new FileReader();
			
			reader.onload = function (event) {
				document.querySelector("#preview").src = event.target.result;
			};
			
			reader.readAsDataURL(event.target.files[0]);
		} else {
			document.querySelector("#preview").src = "";
		}
})

