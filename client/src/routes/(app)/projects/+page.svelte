<script>
import Modal from "../../../ModalProjects.svelte";
import Navbar from "../../../components/Navbar.svelte";
let title = 'Crear nuevo proyecto'; // Define modalTitle here
let userRole= "";
let modalStep = 0;

function openModal() {
  modalStep = 1; // Abre el primer modal
}

function nextModal() {
        if (modalStep < 3) {
            modalStep++;
        } else {
            closeModal(); // Llama a closeModal cuando estás en el último paso
        }
    }

    function prevModal() {
  if (modalStep > 1) {
    modalStep--;
  }
}
function closeModal() {
  modalStep = 0; // Restablece para cerrar los modales
}

let showLogoutButton = false;

function handleMouseEnter() {
  showLogoutButton = true;
}

function handleMouseLeave() {
  showLogoutButton = false;
}


</script>

<!-- MODAL -->


<Modal isOpen={modalStep > 0} step={modalStep} title={title} closeModal={closeModal} nextModal={nextModal} prevModal={prevModal} />


<!-- ADMIN VIEW -->
{#if userRole === 'ADMIN'}

<section class="sect-principal-admin">
    <Navbar/>
    <section class="sect-derecha">
        <section class="config">
            <div class="div-config">
                <button on:click={openModal}>Crear nuevo proyecto</button>
                <select name="" id="" placeholder="Ordenar por">
                    <option hidden selected>Ordenar por</option>
                    <option value="">1</option>
                    <option value="">2</option>
                </select>
            </div>
        </section>
        
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
            <i class="fa-solid fa-ellipsis-vertical"></i>
        </div>
    </section>

</section>

<!-- MODIFIER VIEW-->
{:else if userRole === 'MODIFIER'}

<section class="sect-principal-modifier">
    <Navbar/>

    <section class="sect-derecha">
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
            <i class="fa-solid fa-ellipsis-vertical"></i>
        </div>
    </section>

</section>


<!-- USER VIEW -->
{:else}

<section class="sect-principal-user">
    <section class="sect-derecha">
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
                    <div class="div-p-user" on:mouseenter={handleMouseEnter} on:mouseleave={handleMouseLeave}>
                        <p class="p-user">Usuario</p>
                        <button class="button-hover {showLogoutButton ? 'visible' : 'hidden'}">Cerrar sesión</button>
                    </div>
                </div>    
        </header>
        
        
        <section class="config">
            <div class="div-config">
                <select name="" id="" placeholder="Ordenar por">
                    <option hidden selected>Ordenar por</option>
                    <option value="">1</option>
                    <option value="">2</option>
                </select>
            </div>
        </section>
        
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
            <i class="fa-solid fa-ellipsis-vertical"></i>
        </div>
    </section>
</section>
{/if}

<style>
    
    .p-project{
        font-weight: bolder;
        font-size: 20px;
        margin-bottom: .5%;
    }
    .sect-principal-admin, .sect-principal-modifier, .sect-principal-user{
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
    }
    .fa-ellipsis-vertical{
        font-size: 40px;
    }
    .general-project{
        margin-top: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .project-info{
        height: 10vh;
        width: 80%;
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
    .fa-circle{
        color: var(--color-primary);
    }
    :global(html,body){
        width: 100%;
        height: 100%;
    }
    .fa-bars{
        font-size: 25px;
        margin-left: 200px;
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
        background: none;
        padding: 15px 25px;
        outline: none;
    }
    option{
        margin: 400px;
        outline: none;
        background: none;
        border: none;
        padding: 10px;
    }

    
</style>