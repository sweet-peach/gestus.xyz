<script>
    import {getTimePassed, getTimeUntil} from "$lib/services/dataService.js";
    import {onMount} from "svelte";
    import {getToken} from "$lib/services/authService.js";
    import ProjectsService from "$lib/api/ProjectsService.js";

    let projects = [];

    async function getProjects(){
        const projectsService = new ProjectsService(getToken());
        projects = await projectsService.getAll();
    }

    onMount(async ()=>{
        await getProjects();
    })


</script>

<div class="project-wrapper">
    <header class="config-box">
<!--        <button class="primary-button" on:click={openModal}>Crear nuevo proyecto</button>-->
    </header>
    {#each projects as {name, isActive, updateDate, executionEnd}, i}
        <div class="projects-box">
            <div class="project">
                <div class="text-box">
                    <h2>{name}</h2>
                    <p class="p-last">Last update {getTimePassed(updateDate)} ago</p>
                </div>
                <div class="project-description-box">
                    <div class="project-description">
                        {#if isActive}
                            <i class="fa-solid fa-circle green"></i>
                            <p>Active</p>
                        {:else }
                            <i class="fa-solid fa-circle red"></i>
                            <p>Closed</p>
                        {/if}
                    </div>
                    <div class="project-description">
                        It closes in: {getTimeUntil(executionEnd)}
                    </div>
                </div>
            </div>
        </div>
    {/each}
</div>


<style>
    .p-last{
        padding: 15px 0;
    }
    .project {
        padding: 20px 0;
        border-bottom: 1px solid var(--border-color);
    }
    .project-description{
        width: max-content;
        display: flex;
        padding-right: 20px;
    }
    .project-description-box {
        display: flex;
    }
    .green{
        color: var(--color-primary);
    }
    .red{
        color: red;
    }
    i{
        padding-right: 10px;
    }
</style>