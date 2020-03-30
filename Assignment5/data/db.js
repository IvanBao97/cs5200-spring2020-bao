module.exports = function () {

const mongoose = require('mongoose');
const LOCAL_DB = 'mongodb://localhost:27017/whiteboard';
mongoose.connect(LOCAL_DB, {useNewUrlParser: true, useUnifiedTopology: true});

}