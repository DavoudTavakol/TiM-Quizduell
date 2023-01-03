import { get } from 'svelte/store';

let url = 'localhost:8085/api/category';

let DummyRes = {
	categories: [
		{
			id: '54800863-0db5-472e-8799-4c6ee439b665',
			categoryName: 'SchätzfragenDUMMY'
		},
		{
			id: 'c13be76d-ffce-4a91-b3df-9c66fe7d3fa5',
			categoryName: 'Allgemeinwissen'
		},
		{
			id: '5cd67945-ed50-47fd-a476-fddc125bae76',
			categoryName: 'Allgemeinwissen 2'
		}
	]
};

async function getCategorys() {
	await fetch(url)
		.then((res) => res.json())
		.then((categories) => {
			return categories;
		})
		.catch(() => {
			console.log("API Error");
			return DummyRes;
		});
}

export function load() {
	let response = getCategorys();
	return DummyRes;
}
