document.addEventListener("DOMContentLoaded", () => {
      const div = document.getElementById('weather-info');
	  
  fetch('/weather/api')
    .then(res => res.json())
    .then(data => {

      if (data.error) {
        div.innerText = '⚠️ 날씨 정보를 불러올 수 없습니다.';
        return;
      }

      let icon = '❄️';
      if (data.weathercode == 0) icon = '☀️';
      else if (data.weathercode <= 3) icon = '🌤️';
      else if (data.weathercode <= 45) icon = '☁️';
      else if (data.weathercode <= 67) icon = '🌧️';
      else if (data.weathercode <= 82) icon = '⛈️';

	  div.innerHTML = `
	    <div style="font-size:4rem;">
	      ${icon}
	    </div>
	    <div style="font-size:1.2rem; font-weight:bold; margin-top:5px;">
	      ${data.city}
	    </div>
	    <div style="font-size:1rem; margin-top:10px;">
	      기온  ${data.temperature}℃<br>
	      풍속  ${data.windspeed} m/s
	    </div>
	  `;
    })
    .catch(err => {
      console.error(err);
      document.getElementById('weather-info').innerText = '❌ 오류 발생';
    });
});
