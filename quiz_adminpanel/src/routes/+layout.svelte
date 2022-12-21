<script>
	import '@unocss/reset/tailwind.css';
	import Login from '$lib/Login.svelte';
	import { logedIn } from '$lib/store.js';
	import Sidebar from '$lib/Sidebar.svelte';
	import '../app.css';
	import { onMount } from 'svelte';

	export let data;
	let { categories } = data;

	onMount(() => {
		if (window.localStorage.getItem('logedIn') === 'true') {
			$logedIn = true;
		}
	});
</script>

<main>
	{#if $logedIn}
		<section class="flex">
			<Sidebar {categories} />
			<slot />
		</section>
	{:else}
		<Login />
	{/if}
</main>

<style uno:preflights uno:safelist global></style>
