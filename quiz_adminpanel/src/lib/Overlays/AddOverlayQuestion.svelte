<script>
	import { confirmModalOpen, overlayQuestionOpen } from '$lib/store.js'
	import { fly, fade } from 'svelte/transition'
	import InputText from '$lib/InputText.svelte'
	import { clickOutside } from '$lib/clickOutside.js'
	import { page } from '$app/stores'
	import { invalidateAll } from '$app/navigation'

	let question = ''
	let answerA = ''
	let answerB = ''
	let answerC = ''
	let answerD = ''
	let playShake = false
	let showHint = false
	let options = ['a', 'b', 'c', 'd']
	let selected = options[0]
	let id = $page.params.id

	$: hasData =
		question.length > 0 ||
		answerA.length > 0 ||
		answerB.length > 0 ||
		answerC.length > 0 ||
		answerD.length > 0

	function resetData() {
		question = ''
		answerA = ''
		answerB = ''
		answerC = ''
		answerD = ''
		selected = options[0]
	}

	function handleClose() {
		if (hasData) {
			confirmModalOpen.set(true)
		} else {
			overlayQuestionOpen.set(false)
		}
	}

	function checkIfFilled() {
		return (
			question.length > 0 &&
			answerA.length > 0 &&
			answerB.length > 0 &&
			answerC.length > 0 &&
			answerD.length > 0
		)
	}

	async function addQuestion() {
		if (checkIfFilled()) {
			await fetch('http://localhost:8085/api/questions/create', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					question: question,
					answer: [
						{
							answer: answerA,
							isAnswerCorrect: selected === 'a' ? true : false
						},
						{
							answer: answerB,
							isAnswerCorrect: selected === 'b' ? true : false
						},
						{
							answer: answerC,
							isAnswerCorrect: selected === 'c' ? true : false
						},
						{
							answer: answerD,
							isAnswerCorrect: selected === 'd' ? true : false
						}
					],
					categoryId: id
				})
			})
			console.log('Question added: ' + question)
			resetData()
			invalidateAll()
			overlayQuestionOpen.set(false)
		} else {
			showHint = true
			playShake = true
			setTimeout(() => (playShake = false), 820)
		}
	}
</script>

<div class="flex h-screen bg-gray-400/50 w-screen z-100 absolute">
	<div
		use:clickOutside
		on:click_outside={handleClose}
		class="bg-white h-full m-auto right-0 w-175 absolute"
		transition:fly={{ x: 700, duration: 300 }}
	>
		<button
			class="rounded-l-full bg-gray-500 h-10 top-10 -left-10 w-10 absolute hover:bg-gray-600"
			on:click={handleClose}
		>
			<span class="font-semibold pl-1 text-gray-200"> X </span>
		</button>

		<section>
			<h1 class="text-lg py-6 px-8">Add new <span class="font-semibold">Question</span></h1>
			<div class="py-4 px-8">
				<form class="" action="POST">
					<InputText label={'Question'} class="i-ri-text" bind:value={question} required={true} />

					<div class="flex w-full relative">
						<InputText label={'Answer A'} class="i-ri-text" bind:value={answerA} required={true} />
						<div class="top-5 right-5 absolute">
							<input type="radio" bind:group={selected} name="answers" value={options[0]} />
						</div>
					</div>

					<div class="flex w-full relative">
						<InputText label={'Answer B'} class="i-ri-text" bind:value={answerB} required={true} />
						<div class="top-5 right-5 absolute">
							<input type="radio" bind:group={selected} name="answers" value={options[1]} />
						</div>
					</div>

					<div class="flex w-full relative">
						<InputText label={'Answer C'} class="i-ri-text" bind:value={answerC} required={true} />
						<div class="top-5 right-5 absolute">
							<input type="radio" bind:group={selected} name="answers" value={options[2]} />
						</div>
					</div>

					<div class="flex w-full relative">
						<InputText label={'Answer D'} class="i-ri-text" bind:value={answerD} required={true} />
						<div class="top-5 right-5 absolute">
							<input type="radio" bind:group={selected} name="answers" value={options[3]} />
						</div>
					</div>
				</form>

				<div>
					<button
						class="rounded flex font-bold bg-gray-900 text-sm text-white w-full p-4 transition-all gap-3 duration-250 group items-center justify-center"
						class:apply-shake={playShake}
						type="submit"
						on:click={addQuestion}
					>
						<span>Add</span>
						<div
							class="text-lg transition-all duration-250 i-carbon-arrow-right group-hover:translate-x-1"
						/>
					</button>
					{#if showHint}
						<span
							class="flex font-semibold text-center text-sm w-full p-4 text-red-500 justify-center items-center"
							transition:fade>Please fill in all fields before you continue!</span
						>
					{/if}
				</div>
			</div>
		</section>
	</div>
</div>

<style>
	@keyframes shake {
		10%,
		90% {
			transform: translate3d(-1px, 0, 0);
		}
		20%,
		80% {
			transform: translate3d(2px, 0, 0);
		}
		30%,
		50%,
		70% {
			transform: translate3d(-4px, 0, 0);
		}
		40%,
		60% {
			transform: translate3d(4px, 0, 0);
		}
	}

	.apply-shake {
		animation: shake 0.82s cubic-bezier(0.36, 0.07, 0.19, 0.97) both;
	}
</style>
