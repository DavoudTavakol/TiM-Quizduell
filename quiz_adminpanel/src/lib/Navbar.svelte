<script>
	import {
		overlayQuestionOpen,
		overlayEditCategoryOpen,
		loading,
		deleteModalOpen
	} from '$lib/store.js'
	import { invalidateAll } from '$app/navigation'
	import tippy from 'svelte-tippy'

	export let title
	export let id
	export let desc
	export let value

	function refetch() {
		$loading = true

		invalidateAll()
		setTimeout(() => ($loading = false), 500)
	}
</script>

<main class="flex bg-gray-100 min-h-30 w-full px-6 pt-6 z-20 items-center justify-between">
	<section class="flex">
		<div class="flex flex-col gap-2">
			<div class="flex gap-5 items-center">
				<h1 class="font-semibold space-x-2 text-lg text-gray-400">
					<span> Category </span>
					<span> / </span>
					<span class="text-gray-800">
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

			<span class="text-xs text-gray-400">Description: &ensp {desc}</span>
		</div>
	</section>

	<section class="flex gap-8 items-center">
		<button
			class="deleteBtn group"
			on:click={() => {
				$deleteModalOpen = true
			}}
		>
			<div
				class="transition-all duration-50 i-ri-delete-bin-6-line group-hover:(i-ri-delete-bin-6-fill scale-110) "
			/>
			Delete Category
		</button>
		<button
			class="rounded flex font-semibold bg-gray-900 border-2 border-gray-900 text-white  text-sm py-2 px-4 gap-2 items-center group hover:(bg-gray-800 border-gray-800) "
			on:click={() => ($overlayQuestionOpen = true)}
		>
			<div
				class="text-lg transition-all duration-250 i-ri-add-line group-hover:(rotate-180 scale-110) "
			/>
			New Question
		</button>
	</section>
</main>
<div class="flex bg-gray-100 w-full px-6 pb-6 relative">
	<div class="bg-gray-600 text-lg top-[9px] left-9 i-carbon-search absolute" />
	<input
		type="text"
		bind:value
		class="rounded-full font-mono outline-none bg-gray-200 text-sm w-full py-2 px-12"
		placeholder="Search for a question..."
	/>
</div>

<style>
	.deleteBtn {
		@apply bg-white rounded flex font-semibold border-red-500 border-2 text-sm py-2 px-4 transition-all text-red-500 gap-2 duration-250 items-center;
	}
	.deleteBtn:hover {
		@apply text-white;
		@apply bg-red-500;
	}
</style>
