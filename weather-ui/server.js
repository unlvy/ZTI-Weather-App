const express = require('express');
const app = express();
const path = require('path');

app.use(express.static(__dirname + '/dist/weather-ui'));

app.listen(process.env.PORT || 8080);

app.get('/*', function(req, res) {
res.sendFile(path.join(__dirname + '/dist/weather-ui/index.html'));
})

console.log('Server running');