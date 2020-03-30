const mongoose = require('mongoose')

module.exports = mongoose.Schema({
    _id: Number,
    trueFalseAnswer: Boolean,
    multipleChoiceAnswer: Number,
    student: {
        type: Number,
        ref: 'StudentModel'
    },
    question: {
        type: Number,
        ref: 'QuestionModel'
    }
}, {collection: 'answers'})
