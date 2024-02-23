import {writable} from "svelte/store";

export const files = writable([]);
export const currentDir = writable(null);
export const dirStack = writable([]);
export const view = writable('list');
