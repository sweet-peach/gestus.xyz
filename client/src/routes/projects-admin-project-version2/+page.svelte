<script>
let showLogoutButton = false;

function handleMouseEnter() {
  showLogoutButton = true;
}

function handleMouseLeave() {
  showLogoutButton = false;
}
</script>
<section class="sect-principal">

        <header>
            <div class="div-header">
                <h2>Projects / Project</h2>
            </div>
                <div class="div-header div-buscador">
                    <input type="text" name="" id="" placeholder="Buscador de proyectos">
                    <div class="div-p-user" on:mouseenter={handleMouseEnter} on:mouseleave={handleMouseLeave}>
                        <p class="p-user">Usuario</p>
                        <button class="button-hover {showLogoutButton ? 'visible' : 'hidden'}">Cerrar sesión</button>
                    </div>
                </div>
        </header>

        <section class="files-info-options">
            <div class="vision-project">
                <button class="button-highlight">Proyecto</button>
                <button class="button-highlight">Archivos</button>
                <button class="button-highlight">Estadisticas</button>
            </div>
    
            <div class="general-project">
                <div class="project-info">
                    <div class="name-act">
                        <p class="p-project">Proyecto</p>
                        <p>Ultima actualización...</p>
                    </div>
                    <div class="project-status">
                        <div class="status">
                            <i class="fa-solid fa-circle"></i>
                            <p>Activo</p>
                        </div>
                        <div class="closing-in">
                        <p>Se cierra en...</p>
                        </div>
                    </div>
                </div>
                <i class="fa-solid fa-gear"></i>
            </div>

            <div class="general-info">
                <div class="info">
                    <h2>Fecha de creacion</h2>
                    <p>1/2/2020</p>
                </div>
                <div class="info">
                    <h2>Inicio de ejecucion</h2>
                    <p>1/2/2020</p>
                </div>
                <div class="info">
                    <h2>Fin de ejecucion</h2>
                    <p>1/2/2020</p>
                </div>
                <div class="info">
                    <h2>Rango</h2>
                    <p>10</p>
                </div>
                <div class="info">
                    <h2>Fase</h2>
                    <p>Activa</p>
                </div>
            </div>
        </section>
        
</section>


<style>
    h2{
        padding: 20px 0;
    }
    .dates, .plus-info{
        padding: 10px 0;
    }
    .info{
        justify-content: space-between;
        display: flex;
        align-items: center;
        gap: 20px;
    }
    .p-project{
        font-weight: bolder;
        font-size: 20px;
        margin-bottom: .5%;
    }
   .fa-circle{
        color: var(--color-primary);
    }
   .fa-gear{
        font-size: 20px;
        opacity: .5;
    }
    .general-project{
        margin-top: 40px;
        margin-bottom: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .project-info{
        height: 10vh;
        width: 100%;
    }
    .project-status, .status{

        display: flex;
    }
    .status{
        width: 120px;
        gap: 10px;
        align-items: center;
    }
    .project-status{
        margin-top: 10px;
    }
    .button-highlight {
        border: none;
        background-color: transparent; 
        border-bottom: 2px solid transparent;
        color: #000;
        padding: 10px 20px; 
        font-size: 16px;
        cursor: pointer; 
        transition: border-bottom-color 0.3s;
    }

    .button-highlight:hover {
        border-bottom-color: var(--color-primary); 

    }
    .vision-project{
        padding-bottom: 30px;
    }
    .vision-project > button{
        padding: 20px 50px;
    }
    .files-info-options{
        
        width: 80%;
    }
    :global(body){
        width: 100%;
        height: 100%;
    }
    .sect-principal{
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .div-header{
        width: 20%;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
    }
    header{
        width: 90%;
        padding-top: 10px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding-bottom: 40px;
    }

    .div-header input{
        border-radius: 25px;
        border: none;
        padding: 10px;
    }

    .div-buscador{
        margin-right: 50px;
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

</style>