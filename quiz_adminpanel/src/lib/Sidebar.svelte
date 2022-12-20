<script>
	import SidebarElement from '$lib/SidebarElement.svelte';

	let selected = 0;
	let sidebarElements = [
		{ index: 0, title: 'Home' },
		{ index: 1, title: 'B' }
	];

	function addCategory() {
		sidebarElements = [...sidebarElements, { index: sidebarElements.length + 1, title: 'CC' }];
	}

	function handleDispatch(event) {
		selected = event.detail;
	}
</script>

<main>
	<div class="border-r flex flex-col h-screen py-6 px-2 w-19 justify-between items-center">
		<section>
			<img src="https://pocketbase.io/images/logo.svg" alt="logo" class="mb-8" />
			<div class="flex flex-col gap-4 overflow-scroll scrollbar-hide">
				{#each sidebarElements as element}
					<SidebarElement
						title={element.title}
						{selected}
						index={element.index}
						on:select={handleDispatch}
					/>
				{/each}
			</div>
		</section>

		<button on:click={addCategory}>
			<div class="text-3xl i-twemoji-plus" />
		</button>
	</div>
</main>
