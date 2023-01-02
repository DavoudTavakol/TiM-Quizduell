<script>
	import { overlayQuestionOpen } from '$lib/store.js';
	export let title;

	let words = title.split('/');
</script>

<main class="flex bg-gray-100 min-h-32 w-full p-6 items-center justify-between">
	<section class="flex gap-5 items-center">
		<h1 class="space-x-2 text-lg text-gray-400">
			<span>
				{words[0]}
			</span>
			<span> / </span>
			<span class="text-black">
				{words[1]}
			</span>
		</h1>
		<button class="text-lg i-ri-settings-4-line" />
		<button class="text-lg i-ri-refresh-line" />
	</section>

	<section>
		<button
			class="bg-white border-black rounded flex font-semibold border-2 text-sm py-2 px-6 gap-2 items-center"
			on:click={() => ($overlayQuestionOpen = true)}
		>
			<div class="i-ri-add-line" />
			New Question
		</button>
	</section>
</main>
