const mongoose = require('mongoose');

const studentSchema = mongoose.Schema({
    _id: Number,
    username: String,
    password: String,
    firstName: String,
    lastName: String,
    graduateYear: Number,
    scholarship: Number
}, {collection:('student')});

module.exports = studentSchema;