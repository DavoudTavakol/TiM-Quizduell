<script>
	import { page } from '$app/stores';

	$: id = $page.params.id;
</script>

<main>
	<span>Wir sind aud der Kategorie mit ID: {id}</span>

	<span>ToDO: Data Fetsch und Table View</span>
</main>
