<script>
	import { invalidateAll } from '$app/navigation'
	import { confirmModalOpen, overlayCategoryOpen } from '$lib/store.js'
	import { fly, fade } from 'svelte/transition'
	import InputText from '$lib/InputText.svelte'
	import { clickOutside } from '$lib/clickOutside.js'

	let title = ''
	let desc = ''
	let iconURL = ''
	let showHint = false
	let playShake = false

	$: hasData = title.length > 0 || desc.length > 0 || iconURL.length > 0

	function handleClose() {
		if (hasData) {
			confirmModalOpen.set(true)
		} else {
			overlayCategoryOpen.set(false)
		}
	}

	function resetData() {
		title = ''
		desc = ''
		iconURL = ''
	}

	async function addCategory() {
		if (title.length > 0) {
			await fetch('http://localhost:8085/api/category/create', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					categoryName: title,
					desc: desc,
					iconURL: iconURL
				})
			})
			console.log('Category added: ' + title)
			resetData()
			$overlayCategoryOpen = false
			invalidateAll()
		} else {
			showHint = true
			playShake = true
			setTimeout(() => (playShake = false), 820)
		}
	}
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-100 absolute">
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
			<h1 class="text-lg py-6 px-8">Add new <span class="font-semibold">Category</span></h1>
			<div class="py-4 px-8">
				<form class="" action="POST">
					<InputText label={'Name'} bind:value={title} class="i-ri-text" required={true} />
					<InputText label={'Description'} bind:value={desc} class="i-ri-text" />
					<InputText label={'Icon Url'} bind:value={iconURL} class="i-ri-link" />
				</form>

				<div>
					<button
						class="rounded flex font-bold bg-gray-900 text-sm text-white w-full p-4 transition-all gap-3 duration-250 items-center justify-center group"
						class:apply-shake={playShake}
						type="submit"
						on:click={addCategory}
					>
						<span>Add</span>
						<div
							class="text-lg transition-all duration-250 i-carbon-arrow-right group-hover:translate-x-1 "
						/>
					</button>
					{#if showHint}
						<span
							class="flex font-semibold text-center text-sm w-full p-4 text-red-500 justify-center items-center"
							transition:fade>Please add a Name to continue!</span
						>
					{/if}
				</div>
			</div>
		</section>
	</div>
</div>

<style>
	@keyframes shake {
		10%,
		90% {
			transform: translate3d(-1px, 0, 0);
		}
		20%,
		80% {
			transform: translate3d(2px, 0, 0);
		}
		30%,
		50%,
		70% {
			transform: translate3d(-4px, 0, 0);
		}
		40%,
		60% {
			transform: translate3d(4px, 0, 0);
		}
	}

	.apply-shake {
		animation: shake 0.82s cubic-bezier(0.36, 0.07, 0.19, 0.97) both;
	}
</style>
