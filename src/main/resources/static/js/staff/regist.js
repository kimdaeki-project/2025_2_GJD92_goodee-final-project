function DaumPostcode() {
  new daum.Postcode({
    oncomplete: function(data) {
      var roadAddr = data.roadAddress;

      document.getElementById('staffPostcode').value = data.zonecode;
      document.getElementById('staffAddress').value = roadAddr;
    }
  }).open();
}