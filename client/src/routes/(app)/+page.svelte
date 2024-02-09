<script>


    import {getTimePassed, getTimeUntil} from "$lib/services/dataService.js";

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

    let projects = [];
    onMount(async () => {
        projects = await getProjects([], "");
        console.log(projects);
    })

</script>

<div class="project-wrapper">
    <header class="config-box">
        <button class="primary-button" on:click={openModal}>Crear nuevo proyecto</button>
    </header>
    {#each projects as {name, isActive, updateDate, executionEnd}, i}
        <div class="projects-box">
            <div class="project">
                <div class="text-box">
                    <h2>{name}</h2>
                    <p>Last update {getTimePassed(updateDate)} ago</p>
                </div>
                <div class="project-description-box">
                    <div class="project-description">
                        {#if isActive}
                            <div class="circle green">
                            </div>
                            <p>Active</p>
                        {:else }
                            <div class="circle red">
                            </div>
                            <p>Closed</p>
                        {/if}
                    </div>
                    <div class="project-description">
                        It closes in {getTimeUntil(executionEnd)}
                    </div>
                </div>
            </div>
        </div>
    {/each}
</div>


<style>
    .project {
        padding: 20px 0;
        border-bottom: 1px solid var(--border-color);
    }

    .project-description-box {
        display: flex;
    }
</style>