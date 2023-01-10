<script>
	import { onMount } from 'svelte'
	import { invalidateAll } from '$app/navigation'

	export let data

	let { connected } = data

	$: ({ categoryCount } = data)
	$: ({ questionCount } = data)

	onMount(() => {
		invalidateAll()
	})

	function handleLogout() {
		localStorage.removeItem('logedIn')
		location.reload()
	}
</script>

<main class="flex flex-col  bg-gray-50 flex-1 w-full text-gray-700">
	<section class="border-b flex bg-gray-100 min-h-32 p-6 items-center justify-between">
		<h1 class="font-semibold text-lg">TiM Adminboard</h1>
		<button class="text-2xl i-ri-login-box-line" on:click={handleLogout} />
	</section>

	<section class="border-b flex text-xl w-full">
		<div class="border-r flex w-full p-10 justify-between items-center">
			<section>
				<h2>Backend</h2>
				<span class="text-xs text-gray-500">
					<span class="font-semibold text-green-300" class:text-red-300={!connected}>
						{connected ? 'Online' : 'Offline'}
					</span>
					<span>: https://localhost:8080</span>
				</span>
			</section>

			<span class="flex h-3 w-3 relative">
				<span
					class="rounded-full h-full bg-green-300 w-full opacity-75 animate-ping absolute inline-flex"
					class:bg-red-300={!connected}
				/>
				<span
					class="rounded-full bg-green-400 h-3 w-3 relative inline-flex"
					class:bg-red-400={!connected}
				/>
			</span>
		</div>

		<div class="border-r flex w-full p-10 justify-between items-center">
			<section>
				<h2>Database</h2>
				<span class="text-xs text-gray-500">
					<span class="font-semibold text-green-300" class:text-red-300={!connected}>
						{connected ? 'Online' : 'Offline'}
					</span>
					<span>: https://cloud.mongodb.com/</span>
				</span>
			</section>

			<span class="flex h-3 w-3 relative">
				<span
					class="rounded-full h-full bg-green-300 w-full opacity-75 animate-ping absolute inline-flex"
					class:bg-red-300={!connected}
				/>
				<span
					class="rounded-full bg-green-400 h-3 w-3 relative inline-flex"
					class:bg-red-400={!connected}
				/>
			</span>
		</div>
	</section>

	<section class="border-b flex text-xl w-full">
		<div class="border-r flex w-full p-10 justify-between items-center">
			<section>
				<h2>Categories:</h2>
			</section>

			<span class="font-mono font-semibold text-2xl">{categoryCount}</span>
		</div>

		<div class="border-r flex w-full p-10 justify-between items-center">
			<section>
				<h2>Questions:</h2>
			</section>

			<span class="font-mono font-semibold text-2xl">{questionCount}</span>
		</div>
	</section>
</main>
