<script>
    import Navbar from "../../components/Navbar.svelte";
    import {getContext, onMount} from "svelte";
    import Header from "../../components/Header.svelte";

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
        width: 100%;
    }
</style>
