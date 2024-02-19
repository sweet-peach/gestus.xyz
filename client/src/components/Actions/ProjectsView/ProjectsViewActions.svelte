<script>
    import '../actions.css'
    import Dropdown from "../../UI/SortingDropdownList.svelte";
    import {sortOptions} from "$lib/stores/projectsStore.js";
    import {isOpen, formType, TYPE} from "$lib/stores/projectFormStore.js";

    function handleDataFromChild(event) {
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
    <Dropdown placeholder="Sort by" on:sort={handleDataFromChild} options={["Name", "Date"]}/>
    <button on:click={openForm} class="primary-button">Create new project</button>
</header>


<style>
    .actions-box {
        border-bottom: 1px solid var(--border-color);
    }
</style>
