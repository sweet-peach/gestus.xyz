<script>
    import '../actions.css'
    import Dropdown from "../../UI/SortingDropdownList.svelte";
    import {sortOptions} from "$lib/stores/projectsStore.js";

    function handleDataFromChild(event) {
        const dataFromChild = event.detail;
        sortOptions.set({
            sortBy: dataFromChild.sortBy,
            sortDirection: dataFromChild.ascending ? "ASC" : "DESC"
        })
    }


</script>
<header class="actions-box">
    <Dropdown placeholder="Sort by" on:sort={handleDataFromChild} options={["Name", "Date"]}/>
    <button class="primary-button">Create new project</button>
</header>


<style>
    .actions-box{
        border-bottom: 1px solid var(--border-color);
    }
</style>
