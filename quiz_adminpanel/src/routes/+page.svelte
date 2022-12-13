<script>
	import '../app.css';
	import '../lib/Categorys.svelte';
	import Categorys from '../lib/Categorys.svelte';

	let sidebarElements = [{ title: 'HA' }, { title: 'B' }];

	function addCategory() {
		sidebarElements = [...sidebarElements, { title: 'CC' }];
	}
</script>

<main>
	<div id='wrapper' class="h-screen bg-gray-700 w-screen flex">
		<div
			id="sidebarwrapper"
			class="flex flex-col h-screen w-min content-between justify-between bg-gray-800 text-white"
		>
			<div
				id="sidebar"
				class="flex flex-col space-y-4 flex-1 p-5 float-left overflow-scroll scrollbar-hide "
			>
				{#each sidebarElements as element}
					<div
						id="sidebarelement"
						class="rounded-xl flex bg-gray-700 min-h-16 w-16 items-center justify-center  border-black border-2"
					>
						<span id="category">{element.title}</span>
					</div>
				{/each}
			</div>
			<div id="addcategory" class="p-5 border-black border-t-4">
				<button
					class="rounded-xl flex bg-gray-600 h-16 w-16 items-center justify-center border-black border-2"
					on:click={addCategory}
				>
					<div class="i-twemoji-plus text-3xl" />
				</button>
			</div>
		</div>
		<Categorys />
	</div>
</main>
