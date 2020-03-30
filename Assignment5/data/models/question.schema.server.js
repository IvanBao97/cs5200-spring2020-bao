const mongoose = require( 'mongoose' );

const questionSchema = mongoose.Schema ({
    _id: Number,
    question: String,
    points: Number,
    questionType: String,
    choices: String,
    correct: Number,
    isTrue: Boolean
}, { collection: 'questions' });

module.exports = questionSchema;