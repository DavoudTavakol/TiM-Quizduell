import { writable } from 'svelte/store'

export const logedIn = writable(false)

export const overlayQuestionOpen = writable(false)

export const overlayCategoryOpen = writable(false)

export const confirmModalOpen = writable(false)

export const overlayEditCategoryOpen = writable(false)
