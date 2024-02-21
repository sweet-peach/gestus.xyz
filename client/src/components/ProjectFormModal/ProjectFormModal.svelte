<script>
    import "./projectFormModalStyles.scss";
    import {isOpen, formType, formData, TYPE, resetFormDateStore} from "$lib/stores/projectFormStore.js";
    import {clickOutside} from "$lib/use/clickOutside.js";
    import FirstStage from "./Stages/FirstStage.svelte";
    import SecondStage from "./Stages/SecondStage.svelte";
    import ThirdStage from "./Stages/ThirdStage.svelte";
    import ProjectsService from "$lib/api/ProjectsService.js";
    import {createEventDispatcher, onMount} from "svelte";
    import {getToken} from "$lib/services/authService.js";
    import SmallLoader from "../UI/SmallLoader.svelte";

    const dispatch = createEventDispatcher();
    const stages = [FirstStage, SecondStage, ThirdStage];
    let projectsService, lastFormType, currentStageIndex = 0, stage = null;
    let isLoading = false;
    let error = "";
    const text = {title: '', confirm: ''};

    onMount(() => {
        projectsService = new ProjectsService(getToken());
    })

    $: if ($isOpen) {
        const titles = {CREATE: "Create new project", UPDATE: "Edit project"};
        const confirms = {CREATE: "Create", UPDATE: "Save"};

        text.title = titles[$formType];
        text.confirm = confirms[$formType];

        console.log("formType", $formType);

        if (lastFormType === TYPE.UPDATE && $formType === TYPE.CREATE) {
            resetFormDateStore();
        }

        lastFormType = $formType;
        currentStageIndex = 0;
    }

    function closeModal() {
        isOpen.set(false);
    }


    async function createProject() {
        const response = await projectsService.create($formData);

        dispatch('create', {
            project: response
        })
    }

    async function updateProject() {
        const response = await projectsService.update($formData.id, $formData);

        dispatch('update', {
            project: response
        })
    }

    async function handleValidationPassed() {
        if (currentStageIndex < stages.length - 1) {
            return currentStageIndex += 1;
        }
        error = "";
        isLoading = true
        try {
            if ($formType === TYPE.CREATE) {
                await createProject();
            }
            if ($formType === TYPE.UPDATE) {
                await updateProject();
            }
            closeModal();
        } catch (e) {
            console.log("error");
            error = e.message;
        }
        isLoading = false;
    }

    async function validateCurrentStage() {
        stage?.validate();
    }


</script>

{#if $isOpen}
    <div class="modal-container">
        <div use:clickOutside on:outclick={closeModal} class="modal-box">
            <header>
                <h1>{text.title}</h1>
                <button on:click={closeModal}>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" width="20" height="20"
                         viewBox="0 0 384 512">
                        <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                    </svg>
                </button>
            </header>
            <div class="progress-bar">
                {#each stages as step, i}
                    <div
                            class:passed={i < currentStageIndex}
                            class:active={i === currentStageIndex}
                            class="step">
                        <div class="stepper">
                            <div class="circle"></div>
                        </div>
                        <div class="separator"></div>
                    </div>
                {/each}
            </div>
            <div class="editor">
                <svelte:component
                        on:validationPassed={handleValidationPassed}
                        bind:this={stage}
                        this={stages[currentStageIndex]}
                />
            </div>
            <div class="action-buttons">
                <div class="error-text">{error} &nbsp;</div>
                {#if currentStageIndex > 0}
                    <button on:click={() => currentStageIndex--} class="secondary-button">
                        Back
                    </button>
                {/if}
                <button on:click={validateCurrentStage} class="primary-button">
                    {#if isLoading}
                        <SmallLoader/>
                    {:else if error}
                        Retry
                    {:else}
                        {currentStageIndex === stages.length - 1 ? text.confirm : "Continue"}
                    {/if}
                </button>
            </div>
        </div>
    </div>
{/if}

