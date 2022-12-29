<script>
	import { overlayQuestionOpen } from '$lib/store.js';
	import { fly } from 'svelte/transition';
	import InputText from '$lib/InputText.svelte';
	import { clickOutside } from '$lib/clickOutside.js';

	function handleClose() {
		overlayQuestionOpen.set(false);
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
			<h1 class="text-lg py-6 px-8">Add new <span class="font-semibold">Question</span></h1>
			<div class="py-4 px-8">
				<form class="" action="POST">
					<InputText label={'Question'} class="i-ri-text" />

					<InputText label={'Answer A'} class="i-ri-text" />

					<InputText label={'Answer B'} class="i-ri-text" />

					<InputText label={'Answer C'} class="i-ri-text" />

					<InputText label={'Answer D'} class="i-ri-text" />

					<div class="rounded flex bg-gray-200 mb-6 py-2 px-4 gap-2  justify-around">
						<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
							<div class="i-ri-text" />
							Select Correct Answer</label
						>
						<input type="radio" />
						<input type="radio" />
						<input type="radio" />
						<input type="radio" />
					</div>
				</form>

				<div>
					<button
						class="bg-black rounded flex font-bold text-sm text-white w-full p-4 transition-all gap-3 duration-250 items-center justify-center"
						type="submit"
					>
						<span>Add</span>
						<div class="text-lg i-carbon-arrow-right" />
					</button>
				</div>
			</div>
		</section>
	</div>
</div>
