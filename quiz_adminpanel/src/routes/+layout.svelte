<script>
	import '@unocss/reset/tailwind.css';
	import Login from '$lib/Login.svelte';
	import OverlayQuestion from '$lib/OverlayQuestion.svelte';
	import { logedIn, overlayQuestionOpen, overlayCategoryOpen } from '$lib/store.js';
	import Sidebar from '$lib/Sidebar.svelte';
	import '../app.css';
	import { onMount } from 'svelte';
	import OverlayCategory from '$lib/OverlayCategory.svelte';


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
		<section class="flex relative overflow-x-hidden">
			<Sidebar {categories} />
			{#if $overlayQuestionOpen}
				<OverlayQuestion />
			{/if}
			{#if $overlayCategoryOpen}
				<OverlayCategory />
			{/if}
			<slot />
		</section>
	{:else}
		<Login />
	{/if}
</main>

<style uno:preflights uno:safelist global></style>
