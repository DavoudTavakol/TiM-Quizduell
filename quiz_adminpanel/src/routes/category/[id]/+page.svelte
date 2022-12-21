<script>
	import { page } from '$app/stores';
	export let data;

	let { questions } = data;

	$: id = $page.params.id;
</script>

<div class="flex flex-col w-full">
	<div class="overflow-x-auto sm:-mx-6 lg:-mx-8">
		<div class="min-w-full py-2 inline-block sm:px-6 lg:px-8">
			<div class="overflow-hidden">
				<table class="min-w-full">
					<thead class="bg-white border-b">
						<tr>
							<th scope="col" class="font-medium text-sm text-left py-4 px-6 text-gray-900">
								<input type="checkbox" />
							</th>
							<th scope="col" class="font-medium text-sm text-left py-4 px-6 text-gray-900">
								ID
							</th>
							<th scope="col" class="font-medium text-sm text-left py-4 px-6 text-gray-900">
								Question
							</th>
							<th scope="col" class="font-medium text-sm text-left py-4 px-6 text-gray-900"> A </th>
							<th scope="col" class="font-medium text-sm text-left py-4 px-6 text-gray-900"> B </th>
							<th scope="col" class="font-medium text-sm text-left py-4 px-6 text-gray-900"> C </th>
							<th scope="col" class="font-medium text-sm text-left py-4 px-6 text-gray-900"> D </th>
						</tr>
					</thead>
					<tbody>
						{#each questions as question, i}
							<tr class="border-b bg-gray-100" class:bg-transparent={i % 2 === 0}>
								<td class="font-medium text-sm py-4 px-6 text-gray-900"
									><input type="checkbox" /></td
								>
								<td class="font-medium text-sm py-4 px-6 text-gray-900">{question.id}</td>
								<td class="font-light text-sm py-4 px-6 text-gray-900 whitespace-nowrap"
									>{question.question}</td
								>

								{#each question.answer as answer}
									<td class="font-light text-sm py-4 px-6 text-gray-900 whitespace-nowrap">
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
