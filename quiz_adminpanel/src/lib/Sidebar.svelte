<script>
	import SidebarElement from '$lib/SidebarElement.svelte';

	let sidebarElements = [{ title: 'Home' }, { title: 'Kotlin', id: 222 }];

	function addCategory() {
		sidebarElements = [...sidebarElements, { title: 'CC', id: Math.random() * 10000 }];
	}
</script>

<main>
	<div class="border-r flex flex-col h-screen py-6 px-2 w-19 items-center">
		<img src="https://pocketbase.io/images/logo.svg" alt="logo" class="mb-8" />
		<div class="flex flex-col flex-1 gap-4 overflow-scroll scrollbar-hide">
			{#each sidebarElements as element}
				<SidebarElement title={element.title} id={element.id} />
			{/each}
		</div>

		<button on:click={addCategory} class="border-t flex w-full pt-5">
			<div class="m-auto text-2xl i-twemoji-plus" />
		</button>
	</div>
</main>
