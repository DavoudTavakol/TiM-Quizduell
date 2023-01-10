<script>
	import { invalidateAll } from '$app/navigation'
	import { onMount } from 'svelte'
	import { page } from '$app/stores'
	import { confirmModalOpen, overlayEditCategoryOpen } from '$lib/store.js'
	import { fly } from 'svelte/transition'
	import InputText from '$lib/InputText.svelte'
	import { clickOutside } from '$lib/clickOutside.js'

	$: id = $page.params.id
	$: hasData = title.length > 0 || desc.length > 0 || iconURL.length > 0

	let title = ''
	let desc = ''
	let iconURL = ''

	onMount(async () => {
		let url = `http://localhost:8085/api/category/${id}`
		let res = await fetch(url)
		let data = await res.json()

		title = data.categoryName
		desc = data.desc
		iconURL = data.iconURL
		console.log(data)
	})

	function handleClose() {
		if (hasData) {
			confirmModalOpen.set(true)
		} else {
			overlayEditCategoryOpen.set(false)
		}
	}

	async function editCategory() {
		console.log('editCategory')
		await fetch(`http://localhost:8085/api/category/update`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				id: id,
				categoryName: title,
				desc: desc,
				iconURL: iconURL
			})
		})
		console.log('Category edited: ' + title)
		$overlayEditCategoryOpen = false
		invalidateAll()
	}
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-10 absolute">
	<div
		use:clickOutside
		on:click_outside={handleClose}
		class="bg-white h-full m-auto right-0 w-175 absolute"
		transition:fly={{ x: 700, duration: 300 }}
	>
		<button
			class="rounded-l-full bg-gray-500 h-10 top-10 -left-10 w-10 absolute hover:bg-gray-600"
			on:click={handleClose}
		>
			<span class="font-semibold pl-1 text-gray-200"> X </span>
		</button>

		<section>
			<h1 class="text-lg py-6 px-8">Edit <span class="font-semibold">Category</span></h1>
			<div class="py-4 px-8">
				<form class="" action="POST">
					<InputText label={'Name'} bind:value={title} class="i-ri-text" required={true} />
					<InputText label={'Description'} bind:value={desc} class="i-ri-text" />
					<InputText label={'Icon Url'} bind:value={iconURL} class="i-ri-link" />
				</form>

				<div>
					<button
						class="bg-black rounded flex font-bold text-sm text-white w-full p-4 transition-all gap-3 duration-250 items-center justify-center"
						type="submit"
						on:click={editCategory}
					>
						<span>Edit</span>
						<div class="text-lg i-carbon-arrow-right" />
					</button>
				</div>
			</div>
		</section>
	</div>
</div>
