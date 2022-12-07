<script>
	let count = 0;

	function add() {
		count += 1;
	}
</script>

<div>
	{count}
</div>

<button on:click={add}>Add</button>
