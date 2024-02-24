<script>
    import '../actions.scss'
    import {isOpen, formType, TYPE} from "$lib/stores/userFormStore.js";
    import SortingDropdownList from "../../UI/SortingDropdownList.svelte";
    import {createEventDispatcher} from "svelte";

    function handleOpenForm() {
        isOpen.set(true);
        formType.set(TYPE.CREATE);
    }

    const dispatch = createEventDispatcher();
    function handleSortChange(event) {
        dispatch('sort', event.detail)
    }

    const sortOptions = [
        {label: "First name", value: "firstName"},
        {label: "Last name", value: "lastName"},
        {label: "Email", value: "email"},
        {label: "Role", value: "role"}
    ]

</script>

<header class="actions-box">
    <SortingDropdownList placeholder="Sort by" on:sort={handleSortChange}
                         options={sortOptions}/>
    <button on:click={handleOpenForm} class="primary-button">Create new user</button>
</header>