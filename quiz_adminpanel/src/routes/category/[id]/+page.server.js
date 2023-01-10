
let DummyRes = {
	categoryName: 'Fragen zum Thema Programmieren in Kotlin',
	questions: [
		{
			question: 'What is your name? DUMMY',
			answer: ['John', 'Doe', 'Jane', 'Michael'],
			categoryId: '942f3c05-bba2-47dc-8b37-11dc8847a1b5',
			id: '0f8e484d-e3e6-442b-95e6-2ca6c0a59e7b'
		},
		{
			question: 'How old are you?',
			answer: ['22', '23', '24', '25'],
			categoryId: '942f3c05-bba2-47dc-8b37-11dc8847a1b5',
			id: '0f8e484d-e3e6-442b-95e6-2ca6c0a59e7b'
		},
		{
			question: 'What do you do for a living?',
			answer: ['Developer', 'Designer', 'Manager', 'Sales'],
			categoryId: '942f3c05-bba2-47dc-8b37-11dc8847a1b5',
			id: '0f8e484d-e3e6-442b-95e6-2ca6c0a59e7b'
		}
	],
	id: '942f3c05-bba2-47dc-8b37-11dc8847a1b5'
}

export async function load({params}) {
	let urlQuestions = 'http://localhost:8085/api/questions/' + params.id
	console.log('Lade Fragen von ' + urlQuestions)
	console.log('Params:' + params.id);

	try {
		let res = await fetch(urlQuestions)
		let data = await res.json()
		return {
			categoryName: data.categoryName,
			questions: data.questions,
			id: params.id
		}
	} catch (err) {
		console.log('Fehler beim Laden der Fragen: ' + err)
		return DummyRes
	}
}
