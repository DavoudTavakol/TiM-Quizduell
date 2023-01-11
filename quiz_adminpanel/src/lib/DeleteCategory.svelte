<script>
	import { deleteModalOpen } from '$lib/store.js'
	import { page } from '$app/stores'
	import { goto } from '$app/navigation'
	import { onMount } from 'svelte'
	import { clickOutside } from '$lib/clickOutside.js'

	$: categoryId = $page.params.id
	let categoryName = ''
	let input
	$: deletable = categoryName === input

	async function handleDelete() {
		await fetch(`http://localhost:8085/api/category/${categoryId}`, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			}
		})
		goto('/')
		deleteModalOpen.set(false)
	}

	function backToMenu() {
		deleteModalOpen.set(false)
	}

	onMount(() => getCategoryName())

	async function getCategoryName() {
		let url = `http://localhost:8085/api/category/${categoryId}`
		let res = await fetch(url)
		let data = await res.json()
		categoryName = data.categoryName
	}
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-110 absolute items-center justify-center">
	<div
		class="bg-white rounded-xl flex flex-col h-60 w-150"
		use:clickOutside
		on:click_outside={backToMenu}
	>
		<div class="border-b flex font-semibold h-15 text-lg pl-5 items-center">
			Delete Category "{categoryName}"
		</div>
		<div class="border-b flex flex-col space-y-4 h-30 p-5">
			<span>The category "{categoryName}" including its questions will be deleted.</span>
			<div class="flex flex-col">
				<label for="">Enter "{categoryName}" to continue.</label>
				<input type="text" class="border" bind:value={input} />
			</div>
		</div>
		<div class="flex flex-row-reverse h-15 pr-5 gap-2 items-center">
			<div class:cursor-not-allowed={!deletable}>
				<button
					class="rounded font-bold bg-red-500 h-9 text-sm text-white w-35 duration-250  ransition-all justify-center items-center hover:bg-red-600"
					class:notDeletable={!deletable}
					on:click={handleDelete}><span>Delete</span></button
				>
			</div>

			<button
				class="rounded font-bold bg-gray-900 h-9 text-sm text-white transition-all w-25 duration-250 justify-center items-center hover:bg-gray-800"
				on:click={backToMenu}><span>Back</span></button
			>
		</div>
	</div>
</div>

<style>
	.notDeletable {
		background-color: gray;
		pointer-events: none;
	}
</style>
