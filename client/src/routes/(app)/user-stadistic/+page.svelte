<script>
import Navbar from "../../../components/Navbar.svelte";
let showLogoutButton = false;

function handleMouseEnter() {
  showLogoutButton = true;
}

function handleMouseLeave() {
  showLogoutButton = false;
}
</script>

<!-- ADMIN VIEW -->
<section class="sect-principal-admin">
    <Navbar/>
    <section class="sect-derecha">
        <header>
            <div class="div-header">
                <h2>LogsApi</h2>
            </div>
                <div class="div-header div-buscador">
                    <div class="search-container">
                        <i class="fa-solid fa-search"></i>
                        <input type="text" placeholder="Buscador de proyectos" class="search-input">
                      </div>

                    <div class="div-p-user" on:mouseenter={handleMouseEnter} on:mouseleave={handleMouseLeave}>
                        <p class="p-user">Usuario</p>
                        <button class="button-hover {showLogoutButton ? 'visible' : 'hidden'}">Cerrar sesión</button>
                    </div>
                </div>
        </header>

        <section class="files-info-options">
            <div class="general-user-info">
                <div class="user-info">
                    <div class="name-act">
                        <p class="p-project">Hugo</p>
                        <p>Lector</p>
                    </div>
                </div>
                <i class="fa-solid fa-ellipsis-vertical"></i>
            </div>
            <div class="vision-project">
                <button class="button-highlight">LogsApi</button>
                <button class="button-highlight">Estadisticas</button>
            </div>
    
            

            <div class="general-info">
                <div class="info">
                    <h2>Numero de acciones este mes</h2>
                    <p>400</p>
                </div>
                <div class="info">
                    <h2>Numero de archivos este mes</h2>
                    <p>120</p>
                </div>
                <div class="info">
                    <h2>Espacio ocupado total</h2>
                    <p>20GB</p>
                </div>
                <div class="info">
                    <h2>Extensión más frecuente</h2>
                    <p>.exe</p>
                </div>
            </div>
        </section>
    </section>
</section>

<style>
    .p-project{
        font-weight: bolder;
        font-size: 20px;
        margin-bottom: 2%;
    }
    .general-user-info{
        justify-content: space-between;
    }
    .general-user-info i {
        font-size: 30px;
    }

.general-user-info{
        margin-top: 40px;
        margin-bottom: 40px;
        display: flex;
        align-items: center;
    }

    
    .sect-derecha{
        width: 90%;
    }

    h2{
        padding: 20px 0;
    }
    .info{
        justify-content: space-between;
        display: flex;
        align-items: center;
        gap: 20px;
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
        padding: 20px 40px;
    }
    .files-info-options{
        
        width: 90%;
    }
    :global(body){
        width: 100%;
        height: 100%;
    }
    .sect-principal-admin{
        display: flex;
    }
    

</style>