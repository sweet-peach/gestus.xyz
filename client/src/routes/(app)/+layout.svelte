<script>
    import Navbar from "../../components/Navbar.svelte";
    import {getContext, onMount} from "svelte";
    import Header from "../../components/Header/Header.svelte";
    let user = getContext("user");
</script>
<div class="page-wrapper">
    {#if $user.role === "ADMIN"}
        <Navbar/>
    {/if}
    <div class="content-wrapper">
        <Header></Header>
        <slot></slot>
    </div>
</div>


<style>
    .page-wrapper {
        height: 100%;
        padding: 25px;
        display: flex;
    }

    .content-wrapper {
        display: flex;
        flex-direction: column;
        flex-grow: 1;
        flex-basis: 0;
        overflow: auto;
    }
</style>
