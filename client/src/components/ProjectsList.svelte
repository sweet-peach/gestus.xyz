<script>
    import {getTimePassed, getTimeUntil} from "$lib/services/datesService.js";
    import ContextMenu from "./UI/ContextMenu.svelte";
    import {formData, formType, isOpen, TYPE} from "$lib/stores/projectFormStore.js";
    import ProjectsService from "$lib/api/ProjectsService.js";
    import {createEventDispatcher, onMount} from "svelte";
    import {getToken} from "$lib/services/authService.js";
    import SmallLoader from "./UI/SmallLoader.svelte";
    import "$lib/styles/projectCard.scss";
    import {user} from "$lib/stores/userStore.js";

    export let projects = [];
    let selectedProject = {};
    const dispatch = createEventDispatcher();
    let contextMenuToggleElement, deletePromise, projectsService;
    let contextMenuVisible = false;

    onMount(() => {
        projectsService = new ProjectsService(getToken());
    });

    function toggleContextMenu(event) {
        contextMenuVisible = !contextMenuVisible;
        contextMenuToggleElement = event.currentTarget;
    }

    function handleContextMenuClick(event, project) {
        toggleContextMenu(event);
        selectedProject = project;
    }

    function openForm() {
        contextMenuVisible = false;
        isOpen.set(true);
        formType.set(TYPE.UPDATE);
        formData.set(selectedProject);
    }

    async function deleteProject() {
        try {
            deletePromise = projectsService.delete(selectedProject.id);
            await deletePromise;
            dispatch('delete', {
                project: selectedProject,
            });
            contextMenuVisible = !contextMenuVisible;
        } catch (e) {
            throw new Error(e.message);
        }
    }
</script>


<ContextMenu toggleElement={contextMenuToggleElement} bind:isVisible={contextMenuVisible}>
    <button on:click={openForm} class="menu-item">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-pencil-square"
             viewBox="0 0 16 16">
            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
            <path fill-rule="evenodd"
                  d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
        </svg>
        <span>Edit</span>
    </button>
    <button on:click={deleteProject} class="menu-item">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash3"
             viewBox="0 0 16 16">
            <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5M11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47M8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5"/>
        </svg>
        {#await deletePromise}
            <SmallLoader/>
        {:then response}
            <span>Delete</span>
        {:catch error}
            <span>Retry</span>
        {/await}
    </button>
</ContextMenu>

{#each projects as project}
    <div class="project hover">
        <a href="/project/{project.id}">
            <div class="text-box">
                <h2>{project.name}</h2>
                <p class="p-last">Last update {getTimePassed(project.updateDate)} ago</p>
            </div>
            <div class="project-description-box">
                <div class="project-description">
                    {#if project.isActive}
                        <div class="circle green"></div>
                        <p>Active</p>
                    {:else }
                        <i class="circle red"></i>
                        <p>Closed</p>
                    {/if}
                </div>
                <div class="project-description">
                    It closes in: {getTimeUntil(project.executionEnd)}
                </div>
            </div>
            {#if $user.role === "ADMIN" || $user.role === "MODIFIER"}
                <div class="actions">
                    <button on:click|stopPropagation|preventDefault={(event) => handleContextMenuClick(event,project)}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor"
                             class="bi bi-three-dots-vertical" viewBox="0 0 16 16">
                            <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
                        </svg>
                    </button>
                </div>
            {/if}
        </a>
    </div>
{:else}
    <div class="no-projects">
        <p>No projects found</p>
    </div>
{/each}

<style lang="scss">
   .no-projects{
      padding: 20px 0;
      font-size: 16px;
      font-weight: 500;
      text-align: center;
   }
</style>
