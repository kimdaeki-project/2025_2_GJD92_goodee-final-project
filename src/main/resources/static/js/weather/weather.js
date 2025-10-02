/**
 *  Open-Meteo 날씨 API
 */

document.addEventListener("DOMContentLoaded", () => {
	const p = document.querySelector("p:nth-of-type(2)");
	const tempText = p ? p.innerText : undefined;
	if (!tempText) return;
	
	const temp = parseFloat(tempText.replace("기온: ", "").replace("℃", ""));
	const iconDiv = document.getElementById("weather-icon");
	
	let icon = "☁️";
	  if (temp >= 30) icon = "☀️";
	  else if (temp >= 20) icon = "🌤️";
	  else if (temp >= 10) icon = "🌦️";
	  else icon = "❄️";

	  iconDiv.innerText = icon;
	
});