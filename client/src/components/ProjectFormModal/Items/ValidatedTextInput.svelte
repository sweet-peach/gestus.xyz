<script>
    export let title;
    export let value;
    let error = false;

    $: if(value){
        error = false;
    }

    export function check(){
        error = value === "";
        return !error;
    }

</script>
<div
        class:error={error}
        class="item">
    <header>
        <h3>{title}</h3>
        {#if error}
            <span class="error-text">This field can not be empty</span>
        {/if}
    </header>
    <input bind:value={value} class="primary-input" type="text">
</div>
