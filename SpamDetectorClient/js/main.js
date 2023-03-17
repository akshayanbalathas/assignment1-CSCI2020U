// TODO: onload function should retrieve the data needed to populate the UI

function requestData(callURL) {
  fetch(callURL, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    },
  })
    .then(response => response.json())
    .then(response => processDataToList(response))
    .catch(error => {
      console.log(error);
    });
}

function processDataToList(data) {
  let dataList = [];
  Object.keys(data).forEach(function (key) {
    if (data[key] > 2 && data[key] < 250) {
      let elem = [key, data[key]];
      dataList.push(elem);
    }
  });

  let canvas = document.getElementById('canvas');
}

window.onload = function () {
  let apiCallURL = 'data/wordlist.json';
  requestData(apiCallURL);
};
