<script>
    import '../global.css'
    import {onMount} from "svelte";
    import {user} from "$lib/stores/userStore.js";
    import {goto} from "$app/navigation";
    import { auth } from '$lib/auth.js';

    let isLoading = true;
    onMount(async () => {
        await auth();
        isLoading = false;
        if($user === null){
            await goto('/login');
        }
    })


</script>

{#if isLoading}
    <div class="loading-box">
        <p class="text">Loading</p>
    </div>
{:else}
    <slot></slot>
{/if}

<style>
    .loading-box{
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .text{
        font-size: 20px;
        font-weight: 500;
    }
</style>