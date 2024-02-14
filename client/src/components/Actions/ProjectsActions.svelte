<script>
    import './actions.css'
    import Dropdown from "../UI/SortDropdown.svelte";
    import {sortOptions} from "$lib/stores/projectsStore.js";

    let bindSortOptions = {};

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
