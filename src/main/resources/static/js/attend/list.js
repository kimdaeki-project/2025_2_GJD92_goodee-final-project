document.addEventListener("DOMContentLoaded", () => {
	
	const baseDate = new Date().toISOString().split('T')[0]; // 예: "2025-10-15"
	
	fetch(`/attend/weeklyData?baseDate=${baseDate}`)
	.then(res => res.json())
	.then(data => {
		console.log("주근로시간:", data);
	})
})