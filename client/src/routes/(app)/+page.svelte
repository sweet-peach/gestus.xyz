<script>
    import {onMount} from "svelte";
    import {sortOptions} from "$lib/stores/projectsStore.js";
    import {getToken} from "$lib/services/authService.js";
    import ProjectsService from "$lib/api/ProjectsService.js";
    import {page} from "$lib/stores/appStore.js";
    import ProjectsViewActions from "../../components/Actions/ProjectsView/ProjectsViewActions.svelte";
    import MediumLoader from "../../components/UI/MediumLoader.svelte";
    import ProjectsList from "../../components/ProjectsList.svelte";
    import ProjectFormModal from "../../components/ProjectFormModal/ProjectFormModal.svelte";
    import {projects} from "$lib/stores/projectsStore.js";

    page.set("Projects");
    let projectsService;
    let isLoading = false;
    let error = null;

    async function getProjects() {
        isLoading = true;
        try {
            projects.set(await projectsService.getAll($sortOptions.sortBy, $sortOptions.sortDirection));
            console.log($projects);
        } catch (e) {
            error = e;
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


</script>
<ProjectFormModal></ProjectFormModal>
<ProjectsViewActions/>
<div class="projects-list">
    {#if isLoading}
        <div class="loader-container">
            <MediumLoader color="var(--primary-color)"/>
        </div>
    {:else if error}
        <p>{error.message}</p>
    {:else }
        <ProjectsList projects={$projects}></ProjectsList>
    {/if}
</div>


<style lang="scss">

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

</style>