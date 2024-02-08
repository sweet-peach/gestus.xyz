<script>

</script>
<header>
    <div class="div-header">
        <h2>Usuarios</h2>
    </div>
        <div class="div-header div-buscador">
            <input type="text" name="" id="" placeholder="Buscador de proyectos">
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
    .div-header{
        width: 20%;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
    }
    header{
        width: 100%;
        padding-top: 10px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 50px;
    }

    .div-header input{
        border-radius: 25px;
        border: none;
        padding: 10px;
    }

    .div-buscador{
        margin-right: 50px;
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