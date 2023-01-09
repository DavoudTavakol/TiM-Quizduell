<script>
	import InputText from '$lib/InputText.svelte'
	import { logedIn } from '$lib/store.js'

	let username = ''
	let password = ''

	function handleLogin() {
		if (username === 'admin' && password === 'admin') {
			$logedIn = true
			localStorage.setItem('logedIn', true)
		}
	}
</script>

<main class="flex flex-col h-screen w-screen items-center justify-center relative">
	<div
		class="border flex bg-gray-200 h-12 text-xs w-full top-0 absolute justify-center items-center"
	>
		This is the Test Admin Panel for TIM Quizduell, login with Username:&nbsp<span
			class="font-semibold underline">admin</span
		>&nbsp and Password:&nbsp<span class="font-semibold underline">admin</span>
	</div>

	<img src="/logo.svg" alt="logo" class="w-11" />
	<h2 class="mt-10 mb-5">Admin sign in</h2>

	<form action="login" method="POST" class="w-[30%]" on:submit|preventDefault={handleLogin}>
		<InputText label="Username" bind:value={username} />
		<InputText label="Password" bind:value={password} type={'password'} />

		<button
			class="bg-black rounded flex font-bold text-sm text-white w-full p-4 transition-all gap-3 duration-250 items-center justify-center"
			type="submit"
		>
			<span>Login</span>
			<div class="text-lg i-carbon-arrow-right" />
		</button>
	</form>
</main>
