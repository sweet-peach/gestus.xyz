<script>
    import {getTimePassed, getTimeUntil} from "$lib/services/dataService.js";
    import {onMount} from "svelte";
    import {sortOptions} from "$lib/stores/projectsStore.js";
    import {getToken} from "$lib/services/authService.js";
    import ProjectsService from "$lib/api/ProjectsService.js";
    import ProjectsActions from "../../components/Actions/ProjectsActions.svelte";
    import {page} from "$lib/stores/appStore.js";

    page.set("Projects");
    let projectsService;
    let projects = [];

    async function getProjects() {
        projectsService = new ProjectsService(getToken());
        console.log($sortOptions.sortBy, $sortOptions.sortDirection);
        new ProjectsService(getToken());
        projects = await projectsService.getAll($sortOptions.sortBy, $sortOptions.sortDirection);
        console.log(projects);
    }


    onMount(async () => {
        await getProjects();
        sortOptions.subscribe(async (value) => {
            try {
                await getProjects();
            } catch (e) {
                console.log("error");
                console.log(e);
            }
        });
    });

</script>

<ProjectsActions/>
<div class="projects-list">
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
    .p-last {
        padding: 15px 0;
    }

    .projects-list{
        flex: 1;
        overflow: auto;
    }

    .project {
        padding: 20px 0;
        border-bottom: 1px solid var(--border-color);
    }

    .project-description {
        width: max-content;
        display: flex;
        padding-right: 20px;
    }

    .project-description-box {
        display: flex;
    }

    .green {
        color: var(--color-primary);
    }

    .red {
        color: red;
    }

    i {
        padding-right: 10px;
    }
</style>