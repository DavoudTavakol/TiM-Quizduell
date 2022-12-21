<script>
	import { logedIn } from '$lib/store.js';

	let username = '';
	let password = '';

	function handleLogin() {
		if (username === 'admin' && password === 'admin') {
			$logedIn = true;
			localStorage.setItem('logedIn', true);
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

	<img src="https://pocketbase.io/images/logo.svg" alt="logo" />
	<h2 class="mt-10 mb-5">Admin sign in</h2>

	<form action="login" method="POST" class="w-[30%]" on:submit|preventDefault={handleLogin}>
		<div class="rounded flex flex-col bg-gray-200 mb-6 py-2 px-4 gap-2">
			<label class="text-xs text-gray-500" for="username">Username</label>
			<input
				class="bg-transparent focus:outline-none"
				type="text"
				name="username"
				id="username"
				bind:value={username}
			/>
		</div>

		<div class="rounded flex flex-col bg-gray-200 mb-10 py-2 px-4 gap-2">
			<label class="text-xs text-gray-500" for="username">Password</label>
			<input
				class="bg-transparent focus:outline-none"
				bind:value={password}
				type="password"
				name="password"
				id="password"
			/>
		</div>

		<button
			class="bg-black rounded flex font-bold text-sm text-white w-full p-4 transition-all gap-3 duration-250 items-center justify-center"
			type="submit"
		>
			<span>Login</span>
			<div class="text-lg i-carbon-arrow-right" />
		</button>
	</form>
</main>
