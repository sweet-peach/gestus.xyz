<script>
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
                <h2>Projects / Project</h2>
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
            <div class="vision-project">
                <button class="button-highlight">Proyecto</button>
                <button class="button-highlight">Archivos</button>
                <button class="button-highlight">Estadisticas</button>
            </div>
    
            <div class="options">
                <div class="options-buttons">                
                    <button class="button-new"><i class="fa-solid fa-plus"></i>Nuevo</button>
                    <select name="" id="" placeholder="Ordenar por">
                        <option hidden selected>Ordenar por</option>
                        <option value="">1</option>
                        <option value="">2</option>
                    </select>
                </div>
                <div class="options-icons">
                    <i class="fa-solid fa-border-all"></i>
                    <i class="fa-solid fa-list"></i>
                </div>
            </div>
            <div class="files">
                <div class="file">
                    <i class="fa-regular fa-folder-open"></i>
                    <p>My folder</p>
                </div>
                <div class="info-file">
                    <p>2024-21....</p>
                    <p>450MB</p>
                    <i class="fa-solid fa-ellipsis-vertical"></i>
                </div>
            </div>
        </section>
    </section>
</section>

<!-- MODIFIER VIEW -->
<section class="sect-principal-modifier">
    
    <section class="sect-derecha">

        <header>
            <div class="div-header">
                <h2>Projects / Project</h2>
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
            <div class="vision-project">
                <button class="button-highlight">Proyecto</button>
                <button class="button-highlight">Archivos</button>
                <button class="button-highlight">Estadisticas</button>
            </div>
    
            <div class="options">
                <div class="options-buttons">                
                    <button class="button-new"><i class="fa-solid fa-plus"></i>Nuevo</button>
                    <select name="" id="" placeholder="Ordenar por">
                        <option hidden selected>Ordenar por</option>
                        <option value="">1</option>
                        <option value="">2</option>
                    </select>
                </div>
                <div class="options-icons">
                    <i class="fa-solid fa-border-all"></i>
                    <i class="fa-solid fa-list"></i>
                </div>
            </div>
            <div class="files">
                <div class="file">
                    <i class="fa-regular fa-folder-open"></i>
                    <p>My folder</p>
                </div>
                <div class="info-file">
                    <p>2024-21....</p>
                    <p>450MB</p>
                    <i class="fa-solid fa-ellipsis-vertical"></i>
                </div>
            </div>
        </section>
    </section>
</section>


<!-- USER VIEW -->
<section class="sect-principal-user">
    
    <section class="sect-derecha">

        <header>
            <div class="div-header">
                <h2>Projects / Project</h2>
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
            <div class="vision-project">
                <button class="button-highlight">Proyecto</button>
                <button class="button-highlight">Archivos</button>
                <button class="button-highlight">Estadisticas</button>
            </div>
    
            <div class="options">
                <div class="options-buttons">                
                    <select name="" id="" placeholder="Ordenar por">
                        <option hidden selected>Ordenar por</option>
                        <option value="">1</option>
                        <option value="">2</option>
                    </select>
                </div>
                <div class="options-icons">
                    <i class="fa-solid fa-border-all"></i>
                    <i class="fa-solid fa-list"></i>
                </div>
            </div>
            <div class="files">
                <div class="file">
                    <i class="fa-regular fa-folder-open"></i>
                    <p>My folder</p>
                </div>
                <div class="info-file">
                    <p>2024-21....</p>
                    <p>450MB</p>
                    <i class="fa-solid fa-ellipsis-vertical"></i>
                </div>
            </div>
        </section>
    </section>
</section>

<style>


    .sect-principal-user header{
        width: 100%;
    }
    .sect-principal-user .files-info-options{
        width: 100%;
    }
    .sect-principal-user .files-info-options{
        width: 100%;
    }
    .sect-principal-user .sect-derecha{
        width: 80%;
    }
    .sect-principal-user{
        justify-content: center;
    }




    .sect-principal-modifier, .sect-principal-modifier header{
        width: 100%;
    }
    .sect-principal-modifier .files-info-options{
        width: 100%;
    }
    .sect-principal-modifier .sect-derecha{
        width: 80%;
    }
    .sect-principal-modifier{
        justify-content: center;
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
        padding-left: 30px;
        width: 90%;
    }
    .files, .file, .info-file{
        display: flex;
    }
    .files, .info-file{
        justify-content: space-between;
    }
    .files{
        margin-top: 30px;
    }
    .files i {
        font-size: 30px;
    }
    .info-file{
        width: 300px;
    }
    .file, .info-file{
        align-items: center;
    }
    .file{
        gap: 30px;
    }
    .options{
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .options-icons > i{
        font-size: 30px;
    }
    .options-icons{
        display: flex;
        justify-content: space-between;
        width: 100px;
    }
    .options-buttons{
        width: 300px;
        display: flex;
        justify-content: space-between;
    }
    select{
        border: none;
        border-radius: 20px;
        background: #EEE;
        padding: 15px 25px;
    }
    .button-new{
        
        border-radius: 26px;
        background-color: var(--color-primary);
        padding: 10px 25px;
        border: none;
        outline: none;
    }
    .fa-plus{
        margin-right: 10px;
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
        
        width: 90%;
    }
    :global(body){
        width: 100%;
        height: 100%;
    }
    .sect-principal-modifier, .sect-principal-admin, .sect-principal-user{
        display: flex;
    }
    .sect-principal-modifier, .sect-principal-admin{
        display: none;
    }

</style>