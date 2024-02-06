<script>
let showLogoutButton = false;

function handleMouseEnter() {
  showLogoutButton = true;
}

function handleMouseLeave() {
  showLogoutButton = false;
}
</script>
<section class="sect-princ">
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
    <section class="sect-der">
        <header>
            <div class="div-header">
                <h2>Projects</h2>
            </div>
                <div class="div-header div-buscador">
                    <input type="text" name="" id="" placeholder="Buscador de proyectos">
                    <div class="div-p-user" on:mouseenter={handleMouseEnter} on:mouseleave={handleMouseLeave}>
                        <p class="p-user">Usuario</p>
                        <button class="button-hover {showLogoutButton ? 'visible' : 'hidden'}">Cerrar sesión</button>
                    </div>
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
    </section>
    
</section>


<style>
    :global(html,body){
        width: 100%;
        height: 100%;
    }
    :global(body){
        background: var(--color-primary-background);
    }
    .sect-princ{
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
        border-right: 1px solid;
    }
    .sect-izq i{
        font-size: 30px;
        padding: 10px;
    }
    .sect-izq > div {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }
    .sect-der{
        width: 90%;
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
        border: 1px solid;
        display: flex;
        justify-content: center;
        margin-top: 45px;
    }
    .div-config{
        width:87%;
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
    .div-p-user{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .button-hover {
    background: none;
    border: none;
    opacity: 0;
    color: red;
    transition: opacity 0.3s;
    padding: 10px;
    position: absolute;
    top: 40px;
    }

    .button-hover.visible {
        opacity: 1;
    }

    .button-hover.hidden {
        opacity: 0;
    }

.info-user{
        margin-top: 30px;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
    }
    .info-user div{
        width: 40%;
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