<script>
	let userName = '';
	let password = '';

	function handleChange() {
		console.log('sendToBackend');
		console.log(userName);
		console.log(password);
	}
</script>

<main>
	<form action="" on:input={handleChange}>
		<label for="">Username</label>
		<input type="text" bind:value={userName} />
		<label for="">password</label>
		<input type="password" bind:value={password} />
		<button>Sign In</button>
	</form>
</main>
