<script>
	import SidebarElement from '$lib/SidebarElement.svelte'
	import { overlayCategoryOpen } from '$lib/store.js'
	import { goto } from '$app/navigation'
	import tippy from 'svelte-tippy'

	export let categories

	let searchTerm = ''
	let open = false

	$: filteredCategories = categories.filter((category) => {
		return category.categoryName.toLowerCase().includes(searchTerm.toLowerCase())
	})

	function addCategory() {
		$overlayCategoryOpen = true
	}

	function handleClick() {
		open = !open

		if (!open) {
			searchTerm = ''
		}
	}
</script>

<main class="select-none">
	<div
		class="border-r flex flex-col h-screen px-2 pt-6 w-19 "
		class:w-62={open}
		class:items-center={!open}
		class:px-4={open}
	>
		<button class="cursor-pointer h-11 mb-8 w-11" on:click={() => goto('/')}>
			<img src="/logo.svg" alt="logo" />
		</button>
		<div class="flex flex-col flex-1 gap-4 overflow-scroll scrollbar-hide">
			<SidebarElement title="Home" {open} />

			<div class="rounded-xl flex gap-2" class:bg-gray-100={open}>
				<button
					class="rounded-xl cursor-pointer flex min-h-[48px] w-full group hover:bg-gray-200"
					on:click={handleClick}
				>
					<div
						class="m-auto transition-all text-2xl duration-250 i-carbon-search group-hover:rotate-6"
						class:group-hover:i-ri-arrow-drop-left-line={open}
						class:group-hover:scale-110={open}
					/>
				</button>
				{#if open}
					<!-- svelte-ignore a11y-autofocus -->
					<input
						type="text"
						autofocus
						bind:value={searchTerm}
						placeholder="Search..."
						class="rounded-xl font-mono outline-none bg-gray-100 text-sm max-w-[20ch] px-2 test"
					/>
				{/if}
			</div>

			{#each filteredCategories as element}
				<SidebarElement
					title={element.categoryName}
					id={element.id}
					iconURL={element.iconURL}
					{open}
				/>
			{/each}
		</div>

		<div class="border-t flex w-full py-5 justify-center">
			<button
				on:click={addCategory}
				class="flex py-1 w-12"
				use:tippy={{
					theme: 'own',
					content: 'Add category',
					placement: open ? 'top' : 'right',
					duration: 0
				}}
			>
				<div class="m-auto text-2xl i-twemoji-plus" />
			</button>
		</div>
	</div>
</main>

<style>
</style>
