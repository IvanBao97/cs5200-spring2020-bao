const mongoose = require('mongoose')

const quizWidgetSchema = mongoose.Schema({
    questions:{
        type: Number,
        ref: 'QuestionModel'
    }
}, {collection: 'quiz-widgets'})

module.exports = quizWidgetSchema;