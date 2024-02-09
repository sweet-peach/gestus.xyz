import {writable} from "svelte/store";

export const page = writable("Unknown page");
export const files = writable([]);
