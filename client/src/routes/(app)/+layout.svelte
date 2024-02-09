<script>
    import Navbar from "../../components/Navbar.svelte";
    import {getContext} from "svelte";

    const user = getContext("user");
    console.log($user)
</script>

<div class="page-wrapper">
    {#if $user.role === "ADMIN"}
        <Navbar/>
    {/if}
    <div>
        <header>
            <div class="div-header">
                <i class="fa-solid fa-bars"></i>
                <h2>Projects</h2>
            </div>
            <div class="div-header div-buscador">
                <div class="search-container">
                    <i class="fa-solid fa-search"></i>
                    <input type="text" placeholder="Buscador de proyectos" class="search-input">
                </div>
                <div class="div-p-user" >
                    <p class="p-user">Usuario</p>
                    <button class="button-hover">Cerrar sesión</button>
                </div>
            </div>
        </header>
        <slot></slot>
    </div>
</div>

<style>
    .page-wrapper{
        height: 100%;
        padding: 25px;
        display: flex;
    }
</style>
