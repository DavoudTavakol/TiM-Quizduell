<script>
	import '../app.css';
	let test = 'test';
</script>

<div>Navbar</div>
<slot />
<div>Footer</div>

<h1 class=" btn">Hallo</h1>

<style>
	.btn {
		@apply bg-green-500;
		@apply text-red-500;
	}
</style>
