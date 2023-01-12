<script>
	import { deleteModalOpen } from '$lib/store.js'
	import { page } from '$app/stores'
	import { goto } from '$app/navigation'
	import { onMount } from 'svelte'
	import { clickOutside } from '$lib/clickOutside.js'
	import { PUBLIC_BACKEND_URL } from '$env/static/public'

	let categoryName = ''
	let input

	$: deletable = categoryName === input
	$: categoryId = $page.params.id

	async function handleDelete() {
		await fetch(`${PUBLIC_BACKEND_URL}/api/category/${categoryId}`, {
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
	function handleKeyDown(e) {
		if (e.key === 'Enter') {
			if (deletable) {
				handleDelete()
			}
		}
		if (e.key === 'Escape') {
			backToMenu()
		}
	}

	onMount(() => getCategoryName())

	async function getCategoryName() {
		let url = `${PUBLIC_BACKEND_URL}/api/category/${categoryId}`
		let res = await fetch(url)
		let data = await res.json()
		categoryName = data.categoryName
	}
</script>

<div
	class="flex h-screen bg-gray-400/50 w-screen z-110 absolute items-center justify-center"
	on:keydown={handleKeyDown}
>
	<div
		class="bg-white rounded-xl flex flex-col h-130 p-10 w-150  items-center justify-around"
		use:clickOutside
		on:click_outside={backToMenu}
	>
		<div class="bg-red-500 text-8xl i-ri-delete-bin-2-fill" />
		<h1 class="font-bold text-2xl text-gray-500">Are you Sure?</h1>
		<span class="text-center text-gray-400"
			>The category "{categoryName}" including all its<br /> questions will be deleted.</span
		>
		<section>
			<div class="flex flex-col gap-2">
				<span class="text-gray-600"
					>Enter "<span class="font-semibold underline">{categoryName}</span>" to continue.</span
				>
				<!-- svelte-ignore a11y-autofocus -->
				<input
					type="text"
					class="border-b outline-none py-2 px-4"
					bind:value={input}
					autofocus
					placeholder="Enter category name"
				/>
			</div>
		</section>
		<div class="flex gap-5">
			<button
				class="rounded font-bold bg-gray-900 h-10 text-sm text-white transition-all w-26 duration-250 justify-center items-center hover:bg-gray-800"
				on:click={backToMenu}><span>Back</span></button
			>
			<div class:cursor-not-allowed={!deletable}>
				<button
					class="rounded font-bold bg-red-500 h-10 text-sm text-white w-26 duration-250  ransition-all justify-center items-center hover:bg-red-600"
					class:notDeletable={!deletable}
					on:click={handleDelete}><span>Delete</span></button
				>
			</div>
		</div>
	</div>
</div>

<style>
	.notDeletable {
		background-color: gray;
		pointer-events: none;
	}
</style>
