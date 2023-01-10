<script>
	import { overlayQuestionOpen, overlayEditCategoryOpen } from '$lib/store.js'
	import { goto } from '$app/navigation'

	export let title
	export let id

	let words = title.split('/')

	async function handleDelete() {
		await fetch(`http://localhost:8085/api/category/${id}`, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			}
		})
		goto('/')
	}
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
		<button class="text-lg i-ri-pencil-fill" on:click={() => ($overlayEditCategoryOpen = true)} />
		<button class="text-lg i-ri-refresh-line" />
	</section>

	<section class="flex gap-8 items-center">
		<button
			class="bg-red-500 text-lg transition-all duration-250 i-ri-delete-bin-6-line hover:(i-ri-delete-bin-6-fill bg-red-500) "
			on:click={() => {}}
		/>
		<button
			class="bg-white border-black rounded flex font-semibold border-2 text-sm py-2 px-6 gap-2 items-center"
			on:click={() => ($overlayQuestionOpen = true)}
		>
			<div class="i-ri-add-line" />
			New Question
		</button>
	</section>
</main>
