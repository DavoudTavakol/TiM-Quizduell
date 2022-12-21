<script>
	import { overlayOpen } from '$lib/store.js';
	import { fly } from 'svelte/transition';

	function handleClose() {
		overlayOpen.set(false);
	}
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-10 absolute">
	<div
		class="bg-white h-full m-auto right-0 w-175 absolute"
		transition:fly={{ x: 700, duration: 100 }}
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
					<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
						<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
							<div class="i-ri-text" />
							Question</label
						>
						<input
							class="bg-transparent focus:outline-none"
							type="textarea"
							name="username"
							id="username"
						/>
					</div>

					<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
						<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
							<div class="i-ri-text" />
							Answer A</label
						>
						<input
							class="bg-transparent focus:outline-none"
							type="textarea"
							name="username"
							id="username"
						/>
					</div>

					<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
						<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
							<div class="i-ri-text" />
							Answer B</label
						>
						<input
							class="bg-transparent focus:outline-none"
							type="textarea"
							name="username"
							id="username"
						/>
					</div>

					<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
						<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
							<div class="i-ri-text" />
							Answer C</label
						>
						<input
							class="bg-transparent focus:outline-none"
							type="textarea"
							name="username"
							id="username"
						/>
					</div>

					<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
						<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
							<div class="i-ri-text" />
							Answer D</label
						>
						<input
							class="bg-transparent focus:outline-none"
							type="textarea"
							name="username"
							id="username"
						/>
					</div>

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
