<script>
    import "../userFormModal.scss";
    import DropdownList from "../../UI/DropdownList.svelte";

    export let title;
    export let value;
    export const roles = [
        {value: 'ADMIN', label: 'Admin'},
        {value: 'MODIFIER', label: 'Modifier'},
        {value: 'READER', label: 'Reader'},
    ];

    let option;

    let error = false;

    $: if (option) {
        value = option.value;
        error = false;
    }

    export function check() {
        error = value === "";
        return !error;
    }

</script>

<div
        class:error={error}
        class="input">
    <div class="hint">
        <span>{title}</span>
        {#if error}
            <span class="error-text">Select a user role</span>
        {/if}
    </div>
    <DropdownList
            selectedOption={option}
            options={roles}/>
</div>