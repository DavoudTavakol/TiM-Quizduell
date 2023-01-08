<script>
	import { confirmModalOpen, overlayCategoryOpen } from '$lib/store.js';
	import { fly } from 'svelte/transition';
	import InputText from '$lib/InputText.svelte';
	import { clickOutside } from '$lib/clickOutside.js';

	let title = '';

	function handleClose() {
		confirmModalOpen.set(true);
	}

	async function addCategory() {
		await fetch('http://localhost:8085/api/category/create', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				categoryName: title
			})
		});
		console.log('Category added: ' + title);
		title = '';
		$overlayCategoryOpen = false;
	}
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-10 absolute">
	<div
		use:clickOutside
		on:click_outside={handleClose}
		class="bg-white h-full m-auto right-0 w-175 absolute"
		transition:fly={{ x: 700, duration: 300 }}
	>
		<button
			class="rounded-l-full bg-gray-500 h-10 top-10 -left-10 w-10 absolute hover:bg-gray-600"
			on:click={handleClose}
		>
			<span class="font-semibold pl-1 text-gray-200"> X </span>
		</button>

		<section>
			<h1 class="text-lg py-6 px-8">Add new <span class="font-semibold">Category</span></h1>
			<div class="py-4 px-8">
				<form class="" action="POST">
					<InputText label={'Name'} bind:value={title} class="i-ri-text" />
					<InputText label={'Description'} class="i-ri-text" />
				</form>

				<div>
					<button
						class="bg-black rounded flex font-bold text-sm text-white w-full p-4 transition-all gap-3 duration-250 items-center justify-center"
						type="submit"
						on:click={addCategory}
					>
						<span>Add</span>
						<div class="text-lg i-carbon-arrow-right" />
					</button>
				</div>
			</div>
		</section>
	</div>
</div>
