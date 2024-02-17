<script>
    import {errorStore} from "$lib/stores/errorStore.js";
    let showError = false;
    let message = '';

    const close = () => {
        errorStore.set({ show: false, message: '' });
    };

    errorStore.subscribe(({ show, message: newMessage }) => {
        showError = show;
        message = newMessage;
    });
</script>

{#if showError}
    <div class="error-notification">
        <p>{message}</p>
        <button on:click={close}>Close</button>
    </div>
{/if}

<style>
    .error-notification {

    }
</style>
