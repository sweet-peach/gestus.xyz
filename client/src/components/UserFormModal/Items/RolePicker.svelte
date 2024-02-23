<script>
    import "../userFormModal.scss";

    export let title;
    export let value;
    export const roles = [
        {value: 'ADMIN', label: 'Admin'},
        {value: 'MODIFIER', label: 'Modifier'},
        {value: 'READER', label: 'User'},
    ];

    let error = false;

    $: if (value) {
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
        <span>Role</span>
        {#if error}
            <span class="error-text">Select a user role</span>
        {/if}
    </div>
    <select bind:value={value}>
        {#each roles as {value, label}}
            <option value={value}>{label}</option>
        {/each}
    </select>
</div>