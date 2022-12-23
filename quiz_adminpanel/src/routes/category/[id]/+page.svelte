<script>
	import { page } from '$app/stores';
	import Navbar from '../../../lib/Navbar.svelte';
	export let data;

	let { questions } = data;

	$: id = $page.params.id;
</script>

<main class="flex flex-col h-screen w-full overflow-scroll overflow-x-hidden">
	<Navbar title="Category/Schätzfragen" />
	<div class="flex flex-col">
		<div class="overflow-x-auto sm:-mx-6 lg:-mx-8">
			<div class="min-w-full inline-block sm:px-6 lg:px-8">
				<div class="overflow-hidden">
					<table class="min-w-full">
						<thead class="border-b bg-gray-100">
							<tr>
								<th class="table-header">
									<input type="checkbox" />
								</th>
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
							</tr>
						</thead>
						<tbody>
							{#each questions as question, i}
								<tr class="border-b bg-gray-100" class:bg-white={i % 2 === 0}>
									<td class="table-data">
										<input type="checkbox" />
									</td>
									<td class="font-mono table-data">{question.id}</td>
									<td class="table-data">{question.question}</td>

									{#each question.answer as answer}
										<td class="table-data">
											{answer}
										</td>
									{/each}
								</tr>
							{/each}
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</main>

<style>
	.table-header {
		@apply font-medium text-sm text-left py-4 px-6 text-gray-900;
	}
	.table-data {
		@apply font-light text-sm py-4 px-6 text-gray-900 whitespace-nowrap;
	}
</style>
