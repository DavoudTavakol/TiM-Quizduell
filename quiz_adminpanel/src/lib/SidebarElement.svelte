<script>
	import tippy from 'svelte-tippy';
	import { page } from '$app/stores';

	export let title;
	export let id = null;

	$: link = id ? `/category/${id}` : '/';
	$: active = $page.url.pathname === link;
</script>

<a
	href={link}
	class="border-black rounded-xl cursor-pointer flex min-h-11 w-11"
	class:border-2={active}
	use:tippy={{ theme: 'own', content: title, placement: 'right', duration: 0 }}
>
	{#if title === 'Home'}
		<div class="m-auto text-2xl i-carbon-home" />
	{:else}
		<div class="m-auto text-2xl i-carbon-folder" />
	{/if}
</a>
