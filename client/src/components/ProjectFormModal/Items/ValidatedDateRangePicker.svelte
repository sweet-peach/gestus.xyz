<script>
    export let title;
    export let firstDate;
    export let secondDate;

    let error = false;

    $: if(firstDate || secondDate){
        error = false;
    }

    export function check(){
        error = !(firstDate && secondDate);
        return !error;
    }

</script>

<div
        class:error={error}
        class="item">
    <header>
        <h3>{title}</h3>
        {#if error}
            <span class="error-text">This fields can not be empty</span>
        {/if}
    </header>
    <div class="dual-date">
        <div class="date-item">
            <input bind:value={firstDate} class="primary-input" type="datetime-local">
        </div>
        <div class="separator"></div>
        <div class="date-item">
            <input bind:value={secondDate} class="primary-input" type="datetime-local">
        </div>
    </div>
    <slot></slot>
</div>