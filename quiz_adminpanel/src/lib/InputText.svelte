<script>
	export let label
	export let value = ''
	export let type = 'text'
	export let placeholder = ''
	export let required = false
</script>

<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
	<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
		<div class={$$props.class} />
		<span>{label}</span>
		{#if required}
			<span class=" text-sm text-red-400">*</span>
		{/if}
	</label>

	{#if type === 'password'}
		<input
			class="bg-transparent focus:outline-none"
			type="password"
			name="username"
			{placeholder}
			bind:value
		/>
	{:else}
		<input
			class="bg-transparent focus:outline-none"
			type="text"
			name="username"
			{placeholder}
			bind:value
		/>
	{/if}
</div>
