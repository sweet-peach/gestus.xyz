<script>
    import {formData} from "$lib/stores/projectFormStore.js";

    export let title = "";
    export let keywords = [];

    let input;
    let error = false;

    let currentKeyword = "";
    $: if (currentKeyword) {
        error = false;
        const lastChar = currentKeyword[currentKeyword.length - 1];
        if (lastChar === ",") {
            currentKeyword = currentKeyword.slice(0, currentKeyword.length - 1);

            if (!$formData.keywords.includes(currentKeyword)) {
                formData.update((data) => {
                    data.keywords.push(currentKeyword);
                    return data;
                })
                currentKeyword = "";
            } else {
                error = true;
            }
        }
    }

    function deleteKeyword(keyword) {
        formData.update((data) => {
            data.keywords = data.keywords.filter((k) => k !== keyword);
            return data;
        })
    }

    function focus() {
        input.focus();
    }
</script>

<div class="item">
    <header>
        <h3>{title}</h3>
        {#if error}
            <span class="error-text">This keyword already exist</span>
        {/if}
    </header>
    <div on:click={focus} tabindex="0" class="keywords-input" role="button" aria-label={`Focus`} on:keypress={focus}>
        <div class="keywords" aria-label="Keywords list">
            {#each keywords as keyword}
                <div on:click|stopPropagation role="none" class="keyword">
                    {keyword}
                    <button on:click={()=> { deleteKeyword(keyword) }}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 384 512"
                             fill="currentColor">
                            <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                        </svg>
                    </button>
                </div>
            {/each}
            <input bind:this={input} bind:value={currentKeyword} type="text">
        </div>
    </div>
    <p class="hint">Use the comma "," as a separator</p>
</div>