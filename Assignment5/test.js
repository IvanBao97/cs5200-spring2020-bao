require('./data/db')();
const universityDao = require('./data/daos/university.dao.server');


const truncateDatabase = () => universityDao.truncateDatabase().then(x => console.log("\ntruncate Database"))

const populateDatabase = () => universityDao.populateDatabase().then(x => console.log("\npopulate Database"))

const testStudentsInitialCount = () => universityDao.findAllStudents()
    .then(doc => console.log("\nInitial number of students is " + doc.length))


const testQuestionsInitialCount = () => universityDao.findAllQuestions()
    .then(doc => console.log("\nInitial number of questions is " + doc.length))


const testAnswersInitialCount = () => universityDao.findAllAnswers()
    .then(doc => console.log("\nInitial number of answers is " + doc.length + " answers"))


const testDeleteAnswer = () => universityDao.deleteAnswer(890)
    .then(x => {
        console.log("\nRemove Bob’s answer for the multiple choice question “What does ORM stand for?”")
        return universityDao.findAllAnswers()
    })
    .then(doc => {
        console.log("Total number of answers is " + doc.length)
        return universityDao.findAnswersByStudent(234)
    })
    .then(doc => console.log("Bob's total number of answers is " + doc.length))


const testDeleteQuestion = () => universityDao.deleteQuestion(321)
    .then(x => {
        console.log("\nDelete Question \"Is the following schema valid?\"")
        return universityDao.findAllQuestions()
    })
    .then(doc => console.log("Total number of questions is " + doc.length))


const testDeleteStudent = () => universityDao.deleteStudent(234)
    .then(x => {
        console.log("\nRemove student Bob ")
        return universityDao.findAllStudents()
    })
    .then(doc => console.log("Total number of students is " + doc.length))


truncateDatabase()
    .then(() => populateDatabase())
    .then(() => testStudentsInitialCount())
    .then(() => testQuestionsInitialCount())
    .then(() => testAnswersInitialCount())
    .then(() => testDeleteAnswer())
    .then(() => testDeleteQuestion())
    .then(() => testDeleteStudent())
    .then(() =>console.log("\nValidation has already finished"))