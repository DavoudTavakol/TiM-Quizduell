import { PUBLIC_BACKEND_URL } from '$env/static/public'
let url = PUBLIC_BACKEND_URL + '/api/category'

let DummyRes = {
	categoryCount: 3,
	connected: false
}

export async function load() {
	try {
		let res = await fetch(url)
		let data = await res.json()
		return {
			categoryCount: data.countCategories,
			questionCount: data.countQuestions,
			connected: true
		}
	} catch (err) {
		console.log('Fehler beim Laden der Homepage: ' + err)
		return DummyRes
	}
}
