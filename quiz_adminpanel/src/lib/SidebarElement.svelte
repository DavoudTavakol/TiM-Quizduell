<script>
	import tippy from 'svelte-tippy'
	import { page } from '$app/stores'

	export let title
	export let iconURL = ''
	export let id = null
	export let open

	let invalidLink = false

	function handleLoadError() {
		invalidLink = true
	}

	$: console.log('SidebarElement', title + ' ' + invalidLink)

	$: link = id ? `/category/${id}` : '/'
	$: active = $page.url.pathname === link
</script>

<a
	href={link}
	class="rounded-xl flex border-2 gap-4 items-center whitespace-nowrap group"
	class:hover-bg-gray-200={open}
	class:border-transparent={!active}
	class:border-black={active}
	class:hover:bg-gray-200={!active && !open}
	class:pr-2={open}
>
	<div
		id="tippyElement"
		class="flex min-h-11 min-w-11 max-w-11 max-h-11"
		use:tippy={{
			theme: 'own',
			content: title,
			placement: 'right',
			duration: 0,
			trigger: open ? 'manual' : 'mouseenter focus'
		}}
	>
		{#if title === 'Home'}
			<div class="m-auto transition-all text-2xl duration-250 i-carbon-home group-hover:rotate-6" />
		{:else if iconURL && !invalidLink}
			<img
				src={iconURL}
				on:error={handleLoadError}
				class="m-auto h-[32px] transition-all w-[24px] duration-250 group-hover:rotate-6"
				alt="Sidebar Element Icon"
			/>
		{:else}
			<div
				class="m-auto transition-all text-2xl duration-250 i-carbon-folder group-hover:rotate-6"
			/>
		{/if}
	</div>

	{#if open}
		<span class="max-w-[16ch] text-gray-700 overflow-hidden">{title}</span>
	{/if}
</a>
