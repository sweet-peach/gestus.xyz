import {writable} from "svelte/store";

export const errorStore = writable({ show: false, message: '' });