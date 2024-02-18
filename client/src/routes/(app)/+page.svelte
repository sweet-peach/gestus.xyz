<script>
    import {getTimePassed, getTimeUntil} from "$lib/services/dataService.js";
    import {onMount} from "svelte";
    import {sortOptions} from "$lib/stores/projectsStore.js";
    import {getToken} from "$lib/services/authService.js";
    import ProjectsService from "$lib/api/ProjectsService.js";
    import {page} from "$lib/stores/appStore.js";
    import ProjectsViewActions from "../../components/Actions/ProjectsView/ProjectsViewActions.svelte";
    import MediumLoader from "../../components/UI/MediumLoader.svelte";
    import ContextMenu from "../../components/UI/ContextMenu.svelte";

    page.set("Projects");
    let projectsService;
    let projects = [];
    let isLoading = false;

    async function getProjects() {
        isLoading = true;
        try {
            projects = await projectsService.getAll($sortOptions.sortBy, $sortOptions.sortDirection);
        } catch (e) {
            console.log("error");
            console.log(e);
        } finally {
            isLoading = false;
        }
    }


    onMount(async () => {
        projectsService = new ProjectsService(getToken());

        sortOptions.subscribe(async (value) => {
            try {
                await getProjects();
            } catch (e) {
                console.log("error");
                console.log(e);
            }
        });
    });
    let menuVisible = false;
    let x = 0;
    let y = 0;
    let event1;
    function openMenu(event) {
        console.log("Showing menu");
        x = event.clientX;
        y = event.clientY;
        event1 = event;
        menuVisible = true;
        event.stopPropagation();
    }

</script>

<ProjectsViewActions/>
<ContextMenu event={event1} bind:show={menuVisible}>
    <button class="menu-item">
        <span>Delete</span>
    </button>
</ContextMenu>
<div class="projects-list">
    {#if isLoading}
        <div class="loader-container">
            <MediumLoader color="var(--primary-color)"/>
        </div>
    {:else }
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
                                <div class="circle green"></div>
                                <p>Active</p>
                            {:else }
                                <i class="circle red"></i>
                                <p>Closed</p>
                            {/if}
                        </div>
                        <div class="project-description">
                            It closes in: {getTimeUntil(executionEnd)}
                        </div>
                    </div>
                    <div class="actions">
                        <button on:click={openMenu}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-three-dots" viewBox="0 0 16 16">
                                <path d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3"/>
                            </svg>
                        </button>
                    </div>
                </div>
            </div>
        {/each}

    {/if}
</div>


<style lang="scss">
   .p-last {
      padding: 15px 0;
   }

   .projects-list {
      flex: 1;
      overflow: auto;

      .loader-container {
         display: flex;
         justify-content: center;
         height: 100%;
         align-items: center;
      }
   }

   .project {
      position: relative;
      padding: 20px 0;
      border-bottom: 1px solid var(--border-color);

      .actions{
         position: absolute;
         top: 50%;
         right: 20px;

         transform: translateY(-50%);
      }
   }

   .project-description {
      width: max-content;
      display: flex;
      padding-right: 20px;
      align-items: center;
      gap: 5px;
   }

   .project-description-box {
      display: flex;
   }

   .circle {
      width: 13px;
      height: 13px;
      border-radius: 50%;

      &.green {
         background: var(--green-color);
      }

      &.red {
         background: var(--red-color);
      }
   }

   i {
      padding-right: 10px;
   }
</style>