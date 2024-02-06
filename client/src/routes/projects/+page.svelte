<script>

</script>




<header>
    <div class="div-header">
        <i class="fa-solid fa-bars"></i>
        <h2>Projects</h2>
    </div>
        <div class="div-header div-buscador">
            <input type="text" name="" id="" placeholder="Buscador de proyectos">
            <p>Usuario</p>
        </div>    
</header>


<section class="config">
    <div class="div-config">
        <button>Crear nuevo proyecto</button>
        <select name="" id="" placeholder="Ordenar por">
            <option hidden selected>Ordenar por</option>
            <option value="">1</option>
            <option value="">2</option>
        </select>
    </div>
</section>

<div class="info-user">
    <div class="name-rol">
        <p class="p-project">Proyecto</p>
        <p class="p-update">Ultima actualizacion...</p>
    </div>
    <div class="ellipsis">
        <i class="fa-solid fa-ellipsis-vertical"></i>
    </div>
</div>
<style>
    :global(html,body){
        margin-top: 20px;
        width: 100%;
        height: 100%;
    }
    :global(body){
        background: var(--color-primary-background);
    }
    .fa-bars{
        font-size: 25px;
        margin-left: 200px;
    }
    .div-header{
        width: 20%;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
    }
    header{
        width: 100%;
        margin-top: 10px;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .div-header input{
        border-radius: 25px;
        border: none;
        padding: 10px;
    }

    .div-buscador{
        margin-right: 50px;
    }


    .config{
        display: flex;
        justify-content: center;
        margin-top: 45px;
    }
    .div-config{
        width: 80%;
        display: flex;
        justify-content: space-between;
    }
    .div-config button{
        background: var(--color-primary);
        border: none;
        padding: 15px 35px;
        font-weight: bold;
        border-radius: 20px;
        font-size: 20px;
    }
    select{
        border: none;
        border-radius: 20px;
        background: #EEE;
        padding: 15px 25px;
    }
    .info-user{
        margin-top: 30px;
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
        font-size: 30px;
        display: flex;
        justify-content: flex-end;
    }

    .p-project{
        font-weight: bold;
    }
</style>