<script>

</script>
<header>
    <div class="div-header">
        <h2>Usuarios</h2>
    </div>
        <div class="div-header div-buscador">
            <div class="search-container">
                <i class="fa-solid fa-search"></i>
                <input type="text" placeholder="Buscador de proyectos" class="search-input">
              </div>
            <p>Usuario</p>
        </div>    
</header>

<div class="info-user">
    <div class="name-rol">
        <p class="p-name">Nombre</p>
        <p class="p-rol">Rol</p>
    </div>
    <div class="ellipsis">
        <i class="fa-solid fa-ellipsis-vertical"></i>
    </div>
</div>


<style>
    :global(body){
        width: 100%;
        height: 100%;
    }
    .fa-ellipsis-vertical{
        font-size: 25px;
    }
    
    .info-user{
        display: flex;
        align-items: center;
        justify-content: space-evenly;
    }
    .info-user div{
        width: 35%;
    }
    .info-user div p{
        font-size: 20px;
    }
    .name-rol{
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
    }
    .ellipsis{
        display: flex;
        justify-content: flex-end;
    }

    .p-name{
        font-weight: bold;
    }
</style>