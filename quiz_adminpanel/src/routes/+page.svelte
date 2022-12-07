<script>
	import Counter from '$lib/Counter.svelte';
	let hover = false;
</script>

<div class="flex flex-col h-screen w-screen">
	<!-- Link zu Icon Library: https://icones.netlify.app/collection/carbon -->
	<div class="flex flex-col m-auto ">
		<section class="flex space-x-8  text-4xl">
			<div class="animation-speed i-carbon-sun hover:rotate-45" />
			<div class="i-logos-svelte-icon hover:animate-spin" />
			<div
				class="smile-emotion"
				class:laugh-emotion={hover}
				on:mouseenter={() => (hover = true)}
				on:mouseleave={() => (hover = false)}
			/>
		</section>
		<Counter />
	</div>
</div>

<style>
	.animation-speed {
		@apply transition-all duration-300;
	}
	.smile-emotion {
		@apply i-twemoji-grinning-face-with-smiling-eyes;
	}
	.laugh-emotion {
		@apply i-twemoji-face-with-tears-of-joy;
	}
</style>
