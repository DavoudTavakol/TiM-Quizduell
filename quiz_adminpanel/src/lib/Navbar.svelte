<script>
	import { overlayQuestionOpen, overlayEditCategoryOpen, loading } from '$lib/store.js'
	import { goto, invalidateAll } from '$app/navigation'
	import tippy from 'svelte-tippy'

	export let title
	export let id
	export let desc

	async function handleDelete() {
		await fetch(`http://localhost:8085/api/category/${id}`, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			}
		})
		goto('/')
	}

	function refetch() {
		$loading = true

		invalidateAll()
		setTimeout(() => ($loading = false), 500)
	}
</script>

<main class="flex bg-gray-100 min-h-32 w-full p-6 z-20 items-center justify-between">
	<section class="flex">
		<div class="flex flex-col gap-2">
			<div class="flex gap-5 items-center">
				<h1 class="space-x-2 text-lg text-gray-400">
					<span> Category </span>
					<span> / </span>
					<span class="text-gray-900">
						{title}
					</span>
				</h1>
				<button
					class="h-5 text-lg transition-all w-5 duration-250 i-ri-pencil-line hover:(i-ri-pencil-fill w-5 h-5 scale-110) "
					use:tippy={{
						theme: 'own',
						content: 'Edit',
						placement: 'top',
						duration: 0
					}}
					on:click={() => ($overlayEditCategoryOpen = true)}
				/>
				<button
					class="h-5 text-lg transition w-5 duration-250 i-ri-refresh-line hover:(rotate-180 scale-110) "
					use:tippy={{
						theme: 'own',
						content: 'Refresh',
						placement: 'top',
						duration: 0
					}}
					on:click={refetch}
				/>
			</div>

			<span class="text-sm text-gray-400">Description: &emsp {desc}</span>
		</div>
	</section>

	<section class="flex gap-8 items-center">
		<button class="deleteBtn group" on:click={() => {}}>
			<div
				class="transition-all duration-50 i-ri-delete-bin-6-line group-hover:(i-ri-delete-bin-6-fill scale-110) "
			/>
			Delete Category
		</button>
		<button
			class="bg-white border-black rounded flex font-semibold border-2 text-sm py-2 px-6 gap-2 items-center group"
			on:click={() => ($overlayQuestionOpen = true)}
		>
			<div class="transition-all duration-250 i-ri-add-line group-hover:(rotate-180 scale-110) " />
			New Question
		</button>
	</section>
</main>

<style>
	.deleteBtn {
		@apply bg-white rounded flex font-semibold border-red-500 border-2 text-sm py-2 px-6 transition-all text-red-500 gap-2 duration-250 items-center;
	}
	.deleteBtn:hover {
		@apply text-white;
		@apply bg-red-500;
	}
</style>
