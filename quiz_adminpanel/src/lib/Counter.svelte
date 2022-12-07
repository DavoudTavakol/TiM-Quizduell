<script>
	let count = 0;

	function add() {
		count += 1;
	}
	function sub() {
		count -= 1;
	}
</script>

<div class="flex font-mono w-full p-4 justify-around">
	<button class="btn" on:click={sub}>-</button>
	<span class="p-2">{count}</span>
	<button class="btn" on:click={add}>+</button>
</div>

<style>
	.btn {
		@apply border rounded-full py-2 px-4;
	}
</style>
