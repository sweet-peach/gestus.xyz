import {writable} from "svelte/store";

export const sortOptions = writable({
    sortBy: 'name',
    sortDirection: 'asc',
});

export const projects = writable([]);
