<script>


    let title = 'Crear nuevo proyecto'; // Define modalTitle here
    let userRole = "";
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

    import {onMount} from "svelte";
    import {getProjects} from "$lib/services/projectService.js";

    onMount(async () => {
        await getProjects([], "")
    })
</script>

<div class="project-wrapper">
    <header class="config-box">
        <button class="primary-button" on:click={openModal}>Crear nuevo proyecto</button>
    </header>

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
</div>

.config-box{getProjects()}