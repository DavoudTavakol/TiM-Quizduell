<script>
	import {
		confirmModalOpen,
		overlayCategoryOpen,
		overlayQuestionOpen,
		overlayEditCategoryOpen,
		overlayEditQuestionOpen
	} from '$lib/store.js'
	import { clickOutside } from '$lib/clickOutside.js'

	function backToMenu() {
		confirmModalOpen.set(false)
		overlayCategoryOpen.set(false)
		overlayQuestionOpen.set(false)
		overlayEditCategoryOpen.set(false)
		overlayEditQuestionOpen.set(false)
	}

	function backToEdit() {
		confirmModalOpen.set(false)
	}
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-110 absolute items-center justify-center">
	<div
		class="bg-white rounded-xl flex flex-col h-60 w-150"
		use:clickOutside
		on:click_outside={backToEdit}
	>
		<div class="border-b flex font-semibold h-15 text-lg pl-5 items-center">Are you sure?</div>
		<div class="border-b flex h-30 p-5">If you leave now, all your entries will be lost.</div>
		<div class="flex flex-row-reverse h-15 pr-5 gap-2 items-center">
			<button
				class="rounded font-bold bg-red-500 h-9 text-sm text-white w-35 duration-250  ransition-all justify-center items-center hover:bg-red-600"
				on:click={backToMenu}><span>Exit</span></button
			>
			<button
				class="rounded font-bold bg-gray-900 h-9 text-sm text-white w-25 duration-250 ransition-all justify-center items-center hover:bg-gray-800"
				on:click={backToEdit}><span>Cancel</span></button
			>
		</div>
	</div>
</div>
