const studentModel = require( '../models/student.model.server' )
const questionModel = require( '../models/question.model.server' )
const answerModel = require('../models/answer.model.server')
const quizWidgetModel = require('../models/question.model.server')


truncateDatabase = () => {
    return Promise.all([
        answerModel.deleteMany().exec(),
        questionModel.deleteMany().exec(),
        quizWidgetModel.deleteMany().exec(),
        studentModel.deleteMany().exec()
    ]);
};

createStudent = student =>
    studentModel.create(student)

deleteStudent = studentId =>
    studentModel.findByIdAndRemove(studentId,{useFindAndModify: false})

createQuestion = question =>
    questionModel.create(question)

deleteQuestion = questionId =>
    questionModel.findByIdAndRemove(questionId,{useFindAndModify: false})

answeredQuestion = (studentId, questionId, answer)=> {
    answer.student = studentId;
    answer.question = questionId;
    answerModel.create(answer)
}

deleteAnswer = answerId => answerModel.findByIdAndRemove(answerId,{useFindAndModify: false})

findAllStudents = () => studentModel.find()

findStudentById = id => studentModel.findById(id)

findAllQuestions = () => questionModel.find()

findQuestionById = id => questionModel.findById(id)

findAllAnswers = () => answerModel.find()

findAnswerById = id => answerModel.findById(id)

findAnswersByStudent = studentId => answerModel.find({student: studentId})

findAnswersByQuestion = questionId => answerModel.find({question: questionId})


populateDatabase = () => {
    return Promise.all([
        createStudent({
            _id: 123,
            username: 'alice',
            password: 'alice',
            firstName: 'Alice',
            lastName: 'Wonderland',
            graduateYear: 2020,
            scholarship: 15000
        }),
        createStudent({
            _id: 234,
            username: 'bob',
            password: 'bob',
            firstName: 'Bob',
            lastName: 'Hope',
            graduateYear: 2021,
            scholarship: 12000
        }),
        createQuestion({
            _id: 321,
            question: 'Is the following schema valid?',
            points: 10,
            questionType: 'TRUE_FALSE',
            choices: null,
            correct: null,
            isTrue: false
        }),
        createQuestion({
            _id: 432,
            question: 'DAO stands for Dynamic Access Object.',
            points: 10,
            questionType: 'TRUE_FALSE',
            choices: null,
            correct: null,
            isTrue: false
        }),
        createQuestion({
            _id: 543,
            question: 'What does JPA stand for?',
            points: 10,
            questionType: 'MULTIPLE_CHOICE',
            choices: 'Java Persistence API,Java Persisted Application,JavaScript Persistence API,JSON Persistent Associations',
            correct: 1,
            isTrue: null
        }),
        createQuestion({
            _id: 654,
            question: 'What does ORM stand for?',
            points: 10,
            questionType: 'MULTIPLE_CHOICE',
            choices: 'Object Relational Model,Object Relative Markup,Object Reflexive Model,Object Relational Mapping',
            correct: 4,
            isTrue: null
        }),
        answeredQuestion(
            123,
            321,
            {
                _id: 123,
                trueFalseAnswer: true,
                multipleChoiceAnswer: null,
                //student : findStudentById({_id: 123}),
                //question : findQuestionById({_id: 321})
            }),
        answeredQuestion(
            123,
            432,
            {
                _id: 234,
                trueFalseAnswer: false,
                multipleChoiceAnswer: null,
                //student : findStudentById({_id: 123}),
                //question : findQuestionById({_id: 432})
            }),
        answeredQuestion(
            123,
            543,
            {
                _id: 345,
                trueFalseAnswer: null,
                multipleChoiceAnswer: 1,
                //student : findStudentById({_id: 123}),
                //question : findQuestionById({_id: 543})
            }),
        answeredQuestion(
            123,
            654,
            {
                _id: 456,
                trueFalseAnswer: null,
                multipleChoiceAnswer: 2,
                //student : findStudentById({_id: 123}),
                //question : findQuestionById({_id: 654})
            }),
//bob answered questions
        answeredQuestion(
            234,
            321,
            {
                _id: 567,
                trueFalseAnswer: false,
                multipleChoiceAnswer: null,
                //student : findStudentById({_id: 234}),
                //question : findQuestionById({_id: 321})
            }),
        answeredQuestion(
            234,
            432, {
                _id: 678,
                trueFalseAnswer: true,
                multipleChoiceAnswer: null,
                //student : findStudentById({_id: 234}),
                //question : findQuestionById({_id: 432})
            }),
        answeredQuestion(
            234,
            543,
            {
                _id: 789,
                trueFalseAnswer: null,
                multipleChoiceAnswer: 3,
                //student : findStudentById({_id: 234}),
                //question : findQuestionById({_id: 543})
            }),
        answeredQuestion(
            234,
            654,
            {
                _id: 890,
                trueFalseAnswer: null,
                multipleChoiceAnswer: 4,
                //student : findStudentById({_id: 234}),
                //question : findQuestionById({_id: 654})
            })
    ])
}


module.exports = {
    truncateDatabase, populateDatabase, createStudent, deleteStudent,
    createQuestion, deleteQuestion, answeredQuestion, deleteAnswer,
    findAllStudents, findStudentById, findAllQuestions, findQuestionById,
    findAllAnswers, findAnswerById, findAnswersByStudent, findAnswersByQuestion}