<script>
	import { page } from '$app/stores'
	import { fade } from 'svelte/transition'
	import {
		overlayQuestionOpen,
		loading,
		overlayEditQuestionOpen,
		editQuestionID
	} from '$lib/store.js'
	import Navbar from '$lib/Navbar.svelte'
	export let data

	$: id = $page.params.id
	$: ({ categoryName, questions } = data)
	$: hasQuestions = questions.length > 0

	function handleEdit(id) {
		overlayEditQuestionOpen.set(true)
		$editQuestionID = id
	}
</script>

<main class="flex flex-col h-screen bg-gray-50 w-full overflow-scroll overflow-x-hidden">
	<Navbar {id} title={categoryName} />

	{#if $loading}
		<div class="flex bg-gray-100 flex-1 w-full items-center justify-center" in:fade>
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
									<th class="table-header">
										<div class="flex gap-2 items-center">
											<div class="i-ri-key-line" />
											<span>ID</span>
										</div>
									</th>
									<th class="table-header">
										<div class="flex gap-2 items-center">
											<div class="i-ri-question-line" />
											<span>Question</span>
										</div>
									</th>
									<th class="table-header"> A </th>
									<th class="table-header"> B </th>
									<th class="table-header"> C </th>
									<th class="table-header"> D </th>
									<th class="table-header"> Edit </th>
									<th class="table-header"> Delete </th>
								</tr>
							</thead>
							<tbody>
								{#each questions as question, i}
									<tr class="border-b bg-gray-100" class:bg-gray-50={i % 2 === 0}>
										<td class="font-mono table-data">{question.id}</td>
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
											<button
												class="bg-gray-700 text-lg transition-all duration-250 i-ri-settings-4-line hover:(i-ri-settings-4-fill bg-gray-900 rotate-90) "
												on:click={handleEdit(question.id)}
											/>
										</td>
										<td class="table-data">
											<button
												class="bg-red-500 text-lg transition-all duration-250 i-ri-delete-bin-6-line hover:(i-ri-delete-bin-6-fill bg-red-500 rotate-6) "
												on:click={() => {}}
											/>
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
		@apply font-medium text-sm text-left py-4 px-6 text-gray-900;
	}
	.table-data {
		@apply font-light text-sm py-4 px-6 text-gray-900 whitespace-nowrap;
	}
</style>
