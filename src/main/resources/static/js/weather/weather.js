document.addEventListener("DOMContentLoaded", () => {
      const div = document.getElementById('weather-info');
  
  // 서버에서 날씨 데이터 가져옴 (AJAX 요청)
  fetch('/weather/api')
    .then(res => res.json())  // 응답(JSON)을 파싱
    .then(data => {

      if (data.error) {
        div.innerText = '⚠️ 날씨 정보를 불러올 수 없습니다.';
        return;
      }

	  // 날씨 코드에 따라 아이콘
      let icon = '❄️';
      if (data.weathercode == 0) icon = '☀️';  // 맑음
      else if (data.weathercode <= 3) icon = '🌤️';  // 약간 구름
      else if (data.weathercode <= 45) icon = '☁️';  // 흐림
      else if (data.weathercode <= 67) icon = '🌧️';  // 비
      else if (data.weathercode <= 82) icon = '⛈️';  // 폭우/뇌우

	  // 받아온 데이터 출력
	  div.innerHTML = `
	  <div class="d-flex flex-column justify-content-center align-items-center" style="padding: 20px;">
	    <div style="font-size:80px;">
	      ${icon}
	    </div>
	    <div style="font-size:28px; font-weight:bold; margin-top:5px;">
	      ${data.city}
	    </div>
	    <div style="font-size:18px;">
	      기온: ${data.temperature}℃<br>
	      풍속: ${data.windspeed}m/s<br>
		  	습도: ${data.humidity}%
	    </div>
	 </div>	
	  `;
    })
    .catch(err => {
      console.error(err);
      document.getElementById('weather-info').innerText = '❌ 오류 발생';
    });
});
