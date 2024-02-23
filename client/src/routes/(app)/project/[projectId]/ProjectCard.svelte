<script>

import {project} from "$lib/stores/projectStore.js";
import {getTimePassed, getTimeUntil} from "$lib/services/dataService.js";
import ProjectFormModal from "../../../../components/ProjectFormModal/ProjectFormModal.svelte";
import ContextMenu from "../../../../components/UI/ContextMenu.svelte";
import SmallLoader from "../../../../components/UI/SmallLoader.svelte";
import {formData, formType, isOpen, TYPE} from "$lib/stores/projectFormStore.js";
import {goto} from "$app/navigation";
import {onMount} from "svelte";
import ProjectsService from "$lib/api/ProjectsService.js";
import {getToken} from "$lib/services/authService.js";

let contextMenuCauserEvent, deletePromise, projectsService, contextMenuVisible = false;

onMount(() => {
    projectsService = new ProjectsService(getToken());
});
function toggleContextMenu(event) {
    contextMenuVisible = !contextMenuVisible;
    contextMenuCauserEvent = event;
}

function openForm() {
    contextMenuVisible = false;
    isOpen.set(true);
    formType.set(TYPE.UPDATE);
    formData.set($project);
}

async function handleProjectUpdate(event) {
    $project = event.detail.project;
}

async function deleteProject() {
    try {
        deletePromise = projectsService.delete($project.id);
        await deletePromise;
        await goto("/")
    } catch (e) {
        throw new Error(e.message);
    }
}

function handleContextMenuClick(event) {
    toggleContextMenu(event);
}
</script>

<ProjectFormModal on:update={handleProjectUpdate}></ProjectFormModal>
<ContextMenu causerClickEvent={contextMenuCauserEvent} bind:isVisible={contextMenuVisible}>
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

<div class="project">
    <div class="text-box">
        <h2>{$project.name}</h2>
        <p class="p-last">Last update {getTimePassed($project.updateDate)} ago</p>
    </div>
    <div class="project-description-box">
        <div class="project-description">
            {#if $project.isActive}
                <div class="circle green"></div>
                <p>Active</p>
            {:else }
                <i class="circle red"></i>
                <p>Closed</p>
            {/if}
        </div>
        <div class="project-description">
            It closes in: {getTimeUntil($project.executionEnd)}
        </div>
    </div>
    <div class="actions">
        <button on:click|stopPropagation|preventDefault={handleContextMenuClick}>
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor"
                 class="bi bi-three-dots" viewBox="0 0 16 16">
                <path d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3"/>
            </svg>
        </button>
    </div>
</div>

<style>
    .project{
        cursor: default;
        border-bottom: none;
    }
</style>