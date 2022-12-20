<script>
	import '@unocss/reset/tailwind.css';
	import Login from '$lib/Login.svelte';
	import { logedIn } from '$lib/store.js';
	import Sidebar from '$lib/Sidebar.svelte';
	import '../app.css';
</script>

<main>
	{#if true}
		<section class="flex">
			<Sidebar />
			<slot />
		</section>
	{:else}
		<Login />
	{/if}
</main>

<style uno:preflights uno:safelist global></style>
