let url = 'http://localhost:8085/api/category';

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

export async function load() {
	const fetchCategories = async () => {
		let res = await fetch(url);
		let data = await res.json();
		return data.categories;
	};

	return {
		categories: fetchCategories()
	};

	// return DummyRes;
}
