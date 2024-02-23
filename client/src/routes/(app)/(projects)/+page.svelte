<script>
    import {onMount} from "svelte";
    import {projects, sortOptions} from "$lib/stores/projectsStore.js";
    import {getToken} from "$lib/services/authService.js";
    import ProjectsService from "$lib/api/ProjectsService.js";
    import {page} from "$lib/stores/appStore.js";
    import ProjectsViewActions from "../../../components/Actions/ProjectsView/ProjectsViewActions.svelte";
    import MediumLoader from "../../../components/UI/MediumLoader.svelte";
    import ProjectsList from "../../../components/ProjectsList.svelte";
    import ProjectFormModal from "../../../components/ProjectFormModal/ProjectFormModal.svelte";

    $page = "Projects"

    let projectsService;
    let projectsPromise;

    async function getProjects() {
        try {
            $projects = await projectsService.getAll($sortOptions.sortBy, $sortOptions.sortDirection);
        } catch (error) {
            throw new Error(error);
        }
    }

    async function handleProjectCreate(event) {
        const project = event.detail.project;
        projects.update(projects => {
            projects.push(project);
            return projects;
        })
    }

    async function handleProjectUpdate(event) {
        const updatedProject = event.detail.project;

        projects.update(projects => {
            return projects.map(project => {
                if (updatedProject.id === project.id) {
                    return updatedProject;
                }
                return project;
            })
        })
    }

    async function handleProjectDelete(event) {
        const deleteProject = event.detail.project;
        projects.update(projects => {
            return projects.filter(project => project.id !== deleteProject.id);
        })
    }

    onMount(async () => {
        projectsService = new ProjectsService(getToken());

        return sortOptions.subscribe(async () => {
            projectsPromise = getProjects();
        });
    });


</script>
<ProjectFormModal on:create={handleProjectCreate} on:update={handleProjectUpdate}></ProjectFormModal>
<ProjectsViewActions/>
<div class="projects-list">
    {#await projectsPromise}
        <div class="loader-container">
            <MediumLoader color="var(--primary-color)"/>
        </div>
    {:then response}
        <ProjectsList on:delete={handleProjectDelete} projects={$projects}></ProjectsList>
    {:catch error}
        <p>{error.message}</p>
    {/await}
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