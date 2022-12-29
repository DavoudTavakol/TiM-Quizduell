<script>
	import { confirmModalOpen, overlayCategoryOpen, overlayQuestionOpen} from '$lib/store.js';

	function backToMenu() {
		confirmModalOpen.set(false);
        overlayCategoryOpen.set(false);
        overlayQuestionOpen.set(false);
	} 

    function backToEdit(){
        confirmModalOpen.set(false);
    }
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-10 absolute items-center justify-center">
    <div class="bg-white w-150 h-60 rounded-xl flex flex-col ">
        <div class="flex border-b h-15 items-center pl-5 text-lg font-semibold">Are you sure?</div>
        <div class="flex p-5 h-30 border-b">If you leave now, all your entries will be deleted.</div>
        <div class="flex flex-row-reverse items-center h-15 pr-5 gap-2">
            <button class=" bg-red-500 rounded font-bold text-sm text-white w-35  h-9 justify-center items-center hover:bg-red-600" on:click={backToMenu}><span>Continue</span></button>
            <button class=" bg-gray-500 rounded font-bold text-sm text-white w-25 h-9 justify-center items-center hover:bg-gray-600" on:click={backToEdit}><span>Cancel</span></button>
        </div>
    </div>
</div>


