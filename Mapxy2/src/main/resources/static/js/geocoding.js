/*출발지 텍스트 값에 대해 위도&경도 값 반환(Google Map API)*/
function getCoordinates() {
    const location = document.getElementById('departureInput').value;
    const geocoder = new google.maps.Geocoder();

    geocoder.geocode({ address: location }, (results, status) => {
        if (status === 'OK') {
            const lat = results[0].geometry.location.lat();
            const lng = results[0].geometry.location.lng();
            document.getElementById('altitudeInput').value = lat;
            document.getElementById('longtitudeInput').value = lng;
            /*document.getElementById('output').innerText = `Latitude: ${lat}, Longitude: ${lng}`;*/
        } else {
            document.getElementById('output').innerText = 'Geocode was not successful for the following reason: ' + status;
        }
    });
}
