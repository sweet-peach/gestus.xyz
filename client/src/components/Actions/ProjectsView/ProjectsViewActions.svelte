<script>
    import '../actions.scss'
    import SortingDropdownList from "../../UI/SortingDropdownList.svelte";
    import {sortOptions} from "$lib/stores/projectsStore.js";
    import {isOpen, formType, TYPE} from "$lib/stores/projectFormStore.js";
    import {user} from "$lib/stores/userStore.js";

    function handleSortChange(event) {
        const dataFromChild = event.detail;
        sortOptions.set({
            sortBy: dataFromChild.sortBy,
            sortDirection: dataFromChild.ascending ? "ASC" : "DESC"
        })
    }

    function openForm() {
        isOpen.set(true);
        formType.set(TYPE.CREATE);
    }

</script>
<header class="actions-box">
    <SortingDropdownList placeholder="Sort by" on:sort={handleSortChange}
                         options={[{label: "Name",value: "name"},{label: "Date",value: "date"}]}/>
    {#if $user.role === "ADMIN" || $user.role === "MODIFIER"}
        <button on:click={openForm} class="primary-button">Create new project</button>
    {/if}
</header>


<style>
    .actions-box {
        border-bottom: 1px solid var(--border-color);
    }
</style>
