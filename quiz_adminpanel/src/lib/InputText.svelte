<script>
	export let label;
	export let value = '';
	export let type = 'text';
</script>

<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
	<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
		<div class={$$props.class} />
		<span>{label}</span>
	</label>
	<input
		class="bg-transparent focus:outline-none"
		type="text"
		name="username"
		id="username"
		bind:value
	/>
</div>
