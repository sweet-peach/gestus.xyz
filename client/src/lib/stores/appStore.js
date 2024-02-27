import {writable} from "svelte/store";

export const page = writable([{title: "", url: "/"}]);
export const files = writable([]);
