<script>
    import "./projectFormModalStyles.scss";
    import {isOpen, formType, formData, TYPE, resetFormDateStore} from "$lib/stores/projectFormStore.js";
    import {projects} from "$lib/stores/projectsStore.js";
    import {clickOutside} from "$lib/use/clickOutside.js";
    import FirstStage from "./Stages/FirstStage.svelte";
    import SecondStage from "./Stages/SecondStage.svelte";
    import ThirdStage from "./Stages/ThirdStage.svelte";
    import MediumLoader from "../UI/MediumLoader.svelte";
    import Error from "../UI/Error.svelte";
    import ProjectsService from "$lib/api/ProjectsService.js";
    import {onMount} from "svelte";
    import {getToken} from "$lib/services/authService.js";

    let projectsService;
    let stage = 2;
    let lastFormType = null;
    let isLoading = false;
    let error = null;

    const TEXT = {}

    $: if ($isOpen) {
        if ($formType === TYPE.CREATE) {
            stage = 0;
            TEXT.title = "Create new project";
            TEXT.confirm = "Create";
        }
        if ($formType === TYPE.EDIT) {
            stage = 0;
            TEXT.title = "Edit project";
            TEXT.confirm = "Save";
        }

        if (lastFormType === TYPE.EDIT && $formType === TYPE.CREATE) {
            error = null;
            resetFormDateStore();
        }

        lastFormType = $formType;
    }


    const STAGES = [{
        component: FirstStage,
    }, {
        component: SecondStage,
    }, {
        component: ThirdStage,
    }]

    function closeForm() {
        isOpen.set(false);
    }


    let lastCalledMethod = null;

    async function apiCallWrapper(apiCall) {
        isLoading = true;
        try {
            let response = await apiCall();
            closeForm();
            return response;
        } catch (e) {
            error = e;
        } finally {
            isLoading = false;
        }
    }

    async function createProject() {
        lastCalledMethod = createProject;
        const response = await apiCallWrapper(() =>projectsService.create($formData));
        projects.update(projects => {
            projects.push(response);
            return projects;
        })
    }

    async function updateProject() {
        lastCalledMethod = updateProject;
        const response = await apiCallWrapper(()=> projectsService.update($formData.id, $formData));
        $projects = $projects.map(project => {
            if (project.id === response.id) {
                return response;
            }
            return project;
        })

    }

    let checkFormValidity;
    async function handleConfirmButtonClick() {
        if (!checkFormValidity()) return;
        if ($formType === TYPE.CREATE) {
            createProject();
        } else if ($formType === TYPE.EDIT) {
            updateProject();
        }
    }

    onMount(() => {
        projectsService = new ProjectsService(getToken());
    })

</script>

{#if $isOpen}
    <div class="modal-container">
        <div use:clickOutside on:outclick={closeForm} class="modal-box">
            {#if isLoading}
                <div class="overlay-container">
                    <MediumLoader color="var(--primary-color)"/>
                </div>
            {/if}
            {#if error}
                <div class="overlay-container">
                    <Error close={()=>{ error = null}} confirm={lastCalledMethod} message={error.message}/>
                </div>
            {/if}
            <header>
                <h1>{TEXT.title}</h1>
                <button on:click={closeForm}>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" width="20" height="20"
                         viewBox="0 0 384 512">
                        <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                    </svg>
                </button>
            </header>
            <div class="progress-bar">
                {#each STAGES as step, i}
                    <div
                            class:passed={i < stage}
                            class:active={i === stage}
                            class="step">
                        <div class="stepper">
                            <div class="circle"></div>
                        </div>
                        <div class="separator"></div>
                    </div>
                {/each}
            </div>
            <div class="editor">
                <svelte:component bind:check={checkFormValidity} this={STAGES[stage].component}/>
            </div>
            <div class="action-buttons">
                {#if stage > 0}
                    <button on:click={() => stage--} class="secondary-button">
                        Back
                    </button>
                {/if}
                {#if stage === STAGES.length - 1 }
                    <button on:click={handleConfirmButtonClick} class="primary-button">
                        {TEXT.confirm}
                    </button>
                {:else}
                    <button on:click={() => {stage++}} class="primary-button">Continue</button>
                {/if}
            </div>
        </div>
    </div>
{/if}

