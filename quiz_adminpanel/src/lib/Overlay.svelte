<script>
	import { overlayOpen } from '$lib/store.js';
	import { fly } from 'svelte/transition';
</script>

<div class="flex h-screen bg-gray-100/60 w-screen z-10 absolute">
	<div
		class="bg-white rounded-xl h-full m-auto right-0 w-50 absolute"
		transition:fly={{ x: 200, duration: 800 }}
	>
		Hallo
		<button on:click={() => ($overlayOpen = false)}>Close</button>
	</div>
</div>
