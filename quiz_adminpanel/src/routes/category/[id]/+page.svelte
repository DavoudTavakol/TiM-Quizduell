<script>
	import { page } from '$app/stores'
	import { invalidateAll } from '$app/navigation'
	import { fly } from 'svelte/transition'
	import {
		overlayQuestionOpen,
		loading,
		overlayEditQuestionOpen,
		editQuestionID
	} from '$lib/store.js'
	import { onMount } from 'svelte'
	import Navbar from '$lib/Navbar.svelte'

	export let data

	$: ({ categoryName, questions, desc } = data)
	$: categoryId = $page.params.id
	$: hasQuestions = questions.length > 0

	$: categoryId, resetValues()
	let checkboxValues = []
	let copied = ''
	let searchTerm = ''

	$: filteredQuestions = questions.filter((question) => {
		return question.question.toLowerCase().includes(searchTerm.toLowerCase())
	})

	onMount(() => {
		invalidateAll()
	})

	function resetValues() {
		invalidateAll()
		searchTerm = ''
		checkboxValues = []
		for (let i = 0; i < questions.length; i++) {
			checkboxValues.push(false)
		}
	}

	function handleEdit(questionId) {
		overlayEditQuestionOpen.set(true)
		$editQuestionID = questionId
	}

	function copyToClipboard(text) {
		navigator.clipboard.writeText(text)
		copied = text
		setTimeout(() => {
			copied = ''
		}, 1000)
	}

	async function handleDelete(questionId, i) {
		let url = 'http://localhost:8085/api/questions/delete'
		await fetch(url, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				categoryId: categoryId,
				id: questionId
			})
		})
		checkboxValues[i] = false
		invalidateAll()
	}
</script>

<main class="flex flex-col h-screen bg-gray-50 w-full overflow-scroll overflow-x-hidden relative">
	<Navbar id={categoryId} title={categoryName} {desc} bind:value={searchTerm} />

	{#if $loading}
		<div
			class="flex h-screen bg-gray-100 flex-1 w-full z-10 items-center justify-center absolute"
			transition:fly
		>
			<div
				class="border-gray rounded-full border-4 border-t-4 border-t-gray-600 h-20 mr-3 animate-spin w-20"
			/>
		</div>
	{:else if !hasQuestions}
		<div class="flex flex-1 w-full items-center justify-center">
			<section class="flex flex-col mb-60 gap-10 items-center">
				<h1 class="font-semibold text-xl text-gray-700">
					No questions in
					<span class="underline">{categoryName}</span>, please add the first question in this
					category.
				</h1>
				<button
					class="rounded flex font-semibold bg-gray-900 text-white text-sm py-2 px-6 gap-2 items-center group"
					on:click={() => ($overlayQuestionOpen = true)}
				>
					<div
						class="transition-all duration-250 i-ri-add-line group-hover:(rotate-180 scale-110) "
					/>
					New Question
				</button>
			</section>
		</div>
	{:else}
		<div class="flex flex-col">
			<div class="overflow-x-auto sm:-mx-6 lg:-mx-8">
				<div class="min-w-full inline-block sm:px-6 lg:px-8">
					<div class="overflow-hidden">
						<table class="min-w-full">
							<thead class="border-b bg-gray-100">
								<tr>
									<th class="w-20 table-header">
										<div class="flex gap-2 items-center">
											<div class="text-lg i-ri-key-line" />
											<span>ID</span>
										</div>
									</th>
									<th class="table-header">
										<div class="flex gap-2 items-center">
											<div class="text-lg i-ri-question-line" />
											<span>Question</span>
										</div>
									</th>
									<th class="table-header"> Answer A </th>
									<th class="table-header"> Answer B </th>
									<th class="table-header"> Answer C </th>
									<th class="table-header"> Answer D </th>
									<th class="w-40 table-header"> DELETE </th>
								</tr>
							</thead>
							<tbody>
								{#each filteredQuestions as question, i}
									<tr
										class="border-b cursor-pointer bg-gray-100 hover:bg-gray-200"
										class:bg-gray-50={i % 2 === 0}
										on:click={handleEdit(question.id)}
									>
										<td class="table-data">
											<button
												class="rounded-full flex font-mono bg-gray-300 h-7 py-1 px-3 transition-all w-24 duration-250 items-center justify-center hover:bg-gray-400"
												on:click|stopPropagation={copyToClipboard(question.id)}
											>
												{#if copied === question.id}
													<span class="text-lg text-gray-500 i-ri-clipboard-fill" />
												{:else}
													<span>{question.id.slice(0, 6)}...</span>
												{/if}
											</button>
										</td>
										<td class="table-data">{question.question}</td>

										{#each question.answer as answer}
											<td class="table-data">
												<span
													class:text-green-500={answer.isAnswerCorrect}
													class:font-semibold={answer.isAnswerCorrect}
												>
													{answer.answer}
												</span>
											</td>
										{/each}

										<td class="table-data">
											<div class="flex w-27 gap-4 items-center relative select-none">
												<button
													class="cursor-pointer bg-red-500 text-lg transition-all text-red-500 duration-250 i-ri-delete-bin-6-line hover:(i-ri-delete-bin-6-fill bg-red-500 rotate-6 text-red-500) "
													class:checkBoxChecked={checkboxValues[i]}
													on:click|stopPropagation={() => (checkboxValues[i] = !checkboxValues[i])}
												/>

												{#key checkboxValues[i]}
													<button
														class="deleteBtn group hidden"
														class:flex={checkboxValues[i]}
														on:click|stopPropagation={handleDelete(question.id, i)}
														transition:fly|local={{ x: 50, y: 0, delay: 100 }}
													>
														DELETE
													</button>
												{/key}
											</div>
										</td>
									</tr>
								{/each}
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	{/if}
</main>

<style>
	.table-header {
		@apply font-medium text-sm text-left py-4 px-6 text-gray-600;
	}
	.table-data {
		@apply font-light text-sm py-4 px-6 text-gray-900 whitespace-nowrap;
	}
	.checkBoxChecked {
		@apply bg-red-500 text-red-500;
		--un-icon: url('https://api.iconify.design/ri:delete-bin-6-fill.svg?color=%23ef4444');
		mask: var(--un-icon) no-repeat;
		mask-size: 100%;
		-webkit-mask: var(--un-icon) no-repeat;
		-webkit-mask-size: 100% 100%;
		background-color: currentColor;
		width: 1em;
		height: 1em;
	}
	.deleteBtn {
		@apply rounded font-semibold bg-red-500 text-xs text-white py-[3px] px-3 transition-all ring-2 ring-red-500 gap-2 duration-250 items-center;
	}
	.deleteBtn:hover {
		@apply bg-red-600 ring-red-600;
	}
</style>
