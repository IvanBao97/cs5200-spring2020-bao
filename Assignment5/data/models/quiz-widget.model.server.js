const mongoose = require('mongoose')
const quizWidgetSchema = require('./quiz-widget.schema.server')

module.exports = mongoose.model('QuizWidgetModel', quizWidgetSchema)