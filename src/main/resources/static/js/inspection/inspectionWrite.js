/**
 * 
 */
// 파일 미리보기
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
