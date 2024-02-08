<script>

</script>
<section class="sect-principal">
    <section class="sect-izq">
        <div>
            <i class="fa-solid fa-box-archive"></i>
            <h3>Projects</h3>
        </div>
        
        <div>
            <i class="fa-solid fa-users"></i>
            <h3>Users</h3>
        </div>
        
        <div>
            <i class="fa-solid fa-clock-rotate-left"></i>
            <h3>Logs</h3>
        </div>
    </section>
    <section class="sect-derecha">
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

    </section>
</section>
<style>

.sect-principal{
    display: flex;
}
.sect-izq{
        padding: 40px 0px;
        gap: 30px;
        display: flex;
        align-items: center;
        flex-direction: column;
        width: 10%;
        height: 100vh;
    }
    .sect-izq i{
        font-size: 40px;
        padding: 10px;
        opacity: .7;
    }
    .sect-izq > div {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }
    .sect-derecha{
        width: 90%;
        padding-top: 30px;
        padding-left: 30px;
    }

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
        justify-content: space-between;
        width: 85%;
        padding-top: 30px;
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