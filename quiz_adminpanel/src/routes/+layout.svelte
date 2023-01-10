<script>
	import '@unocss/reset/tailwind.css'
	import Login from '$lib/Login.svelte'
	import AddOverlayQuestion from '$lib/Overlays/AddOverlayQuestion.svelte'
	import AddOverlayCategory from '$lib/Overlays/AddOverlayCategory.svelte'
	import EditOverlayCategory from '$lib/Overlays/EditOverlayCategory.svelte'
	import EditOverlayQuestion from '$lib/Overlays/EditOverlayQuestion.svelte'
	import Confirm from '$lib/Confirm.svelte'

	import {
		logedIn,
		overlayQuestionOpen,
		overlayCategoryOpen,
		confirmModalOpen,
		overlayEditCategoryOpen,
		overlayEditQuestionOpen
	} from '$lib/store.js'
	import Sidebar from '$lib/Sidebar.svelte'
	import '../app.css'
	import { onMount } from 'svelte'

	export let data

	$: ({ categories } = data)

	onMount(() => {
		if (window.localStorage.getItem('logedIn') === 'true') {
			$logedIn = true
		}
	})
</script>

<svelte:head>
	<title>TiM Adminpanel</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="icon" type="image/png" href="/favicon.png" />
</svelte:head>

<main>
	{#if $logedIn}
		<section class="flex relative overflow-x-hidden">
			<Sidebar {categories} />
			{#if $overlayQuestionOpen}
				<AddOverlayQuestion />
			{/if}
			{#if $overlayCategoryOpen}
				<AddOverlayCategory />
			{/if}
			{#if $overlayEditCategoryOpen}
				<EditOverlayCategory />
			{/if}
			{#if $overlayEditQuestionOpen}
				<EditOverlayQuestion />
			{/if}
			{#if $confirmModalOpen}
				<Confirm />
			{/if}
			<slot />
		</section>
	{:else}
		<Login />
	{/if}
</main>

<style uno:preflights uno:safelist global></style>
