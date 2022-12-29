<script>
	import Navbar from '$lib/Navbar.svelte';
	
</script>


<main class="h-full w-full py-10 px-8">
	<Navbar title="Home" />
	<div class="flex flex-col">
		<section class="flex text-xl w-full px-10">
			<div class="rounded flex bg-gray-100 m-2 w-full p-10 justify-between items-center">
				<section>
					<h2>Backend</h2>
					<span class="text-xs text-gray-500">Running on: https://localhost:8080</span>
				</section>

				<span class="flex h-3 w-3 relative">
					<span
						class="rounded-full h-full bg-green-300 w-full opacity-75 animate-ping absolute inline-flex"
					/>
					<span class="rounded-full bg-green-400 h-3 w-3 relative inline-flex" />
				</span>
			</div>

			<div class="rounded flex bg-gray-100 m-2 w-full p-10 justify-between items-center">
				<section>
					<h2>Database</h2>
					<span class="text-xs text-gray-500">Running on: https://mongodb:atlas.com</span>
				</section>

				<span class="flex h-3 w-3 relative">
					<span
						class="rounded-full h-full bg-green-300 w-full opacity-75 animate-ping absolute inline-flex"
					/>
					<span class="rounded-full bg-green-400 h-3 w-3 relative inline-flex" />
				</span>
			</div>
		</section>

		<section class="flex text-xl w-full px-10">
			<div class="rounded flex bg-gray-100 m-2 w-full p-10 justify-between items-center">
				<span class="text-base">We Currently have 150 questions in 10 Categorys</span>
			</div>
		</section>
	</div>
</main>
