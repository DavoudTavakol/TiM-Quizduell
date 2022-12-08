<script>
	import '../app.css';

	let sidebarElements = [{ title: 'HA' }, { title: 'B' }];

	function addCategory() {
		sidebarElements = [...sidebarElements, { title: 'CC' }];
	}
</script>

<main>
	<div class="h-screen bg-slate-800 w-screen">
		<div class="flex flex-col h-screen bg-orange-500 w-min content-between justify-between">
			<div
				id="sidebar"
				class="flex flex-col space-y-4 bg-slate-200 flex-1 p-5 float-left overflow-scroll scrollbar-hide"
			>
				{#each sidebarElements as element}
					<div
						id="sidebarelement"
						class="rounded-xl flex bg-stone-500 min-h-16 w-16 items-center justify-center"
					>
						<span id="category">{element.title}</span>
					</div>
				{/each}
			</div>
			<div id="addcategory" class="bg-red-500 p-5">
				<button
					class="rounded-xl flex bg-stone-500 h-16 w-16 items-center justify-center"
					on:click={addCategory}
				>
					<span id="category" class="font-bold">+</span>
				</button>
			</div>
		</div>
	</div>
</main>
