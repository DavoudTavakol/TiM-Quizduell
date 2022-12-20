<script>
	import { createEventDispatcher } from 'svelte';
	import tippy from 'svelte-tippy';

	const dispatch = createEventDispatcher();

	export let title;
	export let selected;
	export let index;
</script>

<button
	class="border-black rounded-xl cursor-pointer flex h-11 w-11 "
	class:border-2={selected === index}
	class:hover:bg-gray-200={selected !== index}
	on:click={() => dispatch('select', index)}
	use:tippy={{ theme: 'own', content: title, placement: 'right', duration: 0 }}
>
	{#if title === 'Home'}
		<div class="m-auto text-2xl i-carbon-home" />
	{:else}
		<span class="m-auto">{title}</span>
	{/if}
</button>
