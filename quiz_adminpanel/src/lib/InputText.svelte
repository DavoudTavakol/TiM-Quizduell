<script>
	export let label
	export let value = ''
	export let type = 'text'
	export let placeholder = ''
	export let required = false
	export let selected = false
	export let autofocus = false
</script>

<div
	class="rounded flex flex-col bg-gray-200 mb-6 w-full py-2 px-4 ring-blue-400 gap-2"
	class:ring-2={selected}
>
	<label class="flex text-sm text-gray-500 gap-1 items-center" for="username">
		<div class={$$props.class} />
		<span>{label}</span>
		{#if required}
			<span class=" text-sm text-red-400">*</span>
		{/if}
	</label>

	{#if type === 'password'}
		<!-- svelte-ignore a11y-autofocus -->
		<input
			class="bg-transparent focus:outline-none"
			{autofocus}
			type="password"
			name="username"
			{placeholder}
			bind:value
		/>
	{:else}
		<!-- svelte-ignore a11y-autofocus -->
		<input
			class="bg-transparent focus:outline-none"
			{autofocus}
			type="text"
			name="username"
			autocomplete="off"
			{placeholder}
			bind:value
		/>
	{/if}
</div>
