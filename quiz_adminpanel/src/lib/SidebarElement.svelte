<script>
	import tippy from 'svelte-tippy'
	import { page } from '$app/stores'

	export let title
	export let id = null
	export let open

	$: link = id ? `/category/${id}` : '/'
	$: active = $page.url.pathname === link
</script>

<a
	href={link}
	class="rounded-xl flex border-2 gap-4 items-center whitespace-nowrap"
	class:hover-bg-gray-200={open}
	class:border-transparent={!active}
	class:border-black={active}
	class:hover:bg-gray-200={!active && !open}
	class:pr-2={open}
>
	<div
		id="tippyElement"
		class="flex min-h-11 w-11"
		use:tippy={{
			theme: 'own',
			content: title,
			placement: 'right',
			duration: 0,
			trigger: open ? 'manual' : 'mouseenter focus'
		}}
	>
		{#if title === 'Home'}
			<div class="m-auto text-2xl i-carbon-home" />
		{:else}
			<div class="m-auto text-2xl i-carbon-folder" />
		{/if}
	</div>

	{#if open}
		<span class="font-mono max-w-[16ch] text-gray-500 overflow-hidden">{title}</span>
	{/if}
</a>
