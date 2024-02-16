import {writable} from "svelte/store";

export const searchQuery = writable("");
export const isLoading = writable(true);
export const searchKeywords = writable([]);
export const searchResults = writable(null);