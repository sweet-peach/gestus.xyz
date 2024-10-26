<script>
    import SmallLoader from "../UI/SmallLoader.svelte";
    import {clickOutside} from "$lib/use/clickOutside.js";
    import {TYPE} from "$lib/stores/userFormStore.js";
    import {createEventDispatcher, onMount} from "svelte";
    import UsersService from "$lib/api/UsersService.js";
    import {getToken} from "$lib/services/authService.js";
    import ValidatedTextInput from "../UserFormModal/Items/ValidatedTextInput.svelte";
    import Password from "./Items/Password.svelte";
    import RolePicker from "./Items/RolePicker.svelte";
    import "./userFormModal.scss";

    let lastFormType, usersService;
    const dispatch = createEventDispatcher();

    export let form;
    export let type;
    export let isOpen;

    let errorText = "";
    let text = {
        title: "",
        confirm: ""
    };

    $: if (isOpen) {
        toCheck = {}
        const titles = {CREATE: "Create new user", UPDATE: "Edit user details"};
        const confirms = {CREATE: "Create", UPDATE: "Save"};

        text.title = titles[type];
        text.confirm = confirms[type];

        if (lastFormType === TYPE.UPDATE && type === TYPE.CREATE) {
            form = {}
        }

        lastFormType = type;
    }

    $: toCheck = {};
    function validate () {
        let isAllValid = true;
        for (const key in toCheck) {
            if (!toCheck[key]()) {
                isAllValid = false;
            }
        }
        if (isAllValid) {
            handleValidationPassed()
        }
    }
    async function handleValidationPassed() {
        errorText = "";
        try {
            if (type === TYPE.CREATE) {
                actionPromise = usersService.create(form);
                const response = await actionPromise;
                dispatch('create',response);
            } else {
                actionPromise = usersService.update(form.id,form);
                const response = await actionPromise;

                dispatch('update',response);
            }
            isOpen = false;
        } catch (e) {
            errorText = e.message;
            throw new Error(e.message)
        }

    }

    let actionPromise;
    function handleSubmitButton(){
        validate()
    }


    onMount(()=>{
        usersService = new UsersService(getToken());
    })

</script>
{#if isOpen}
    <div class="modal-container">
        <div class="modal-box" use:clickOutside on:outclick={()=> isOpen = false}>
            <h1>{text.title}</h1>
            <ValidatedTextInput bind:check={toCheck.isFirstNameValid} bind:value={form.firstName} title="First name"/>
            <ValidatedTextInput bind:check={toCheck.isLastNameValid} bind:value={form.lastName} title="Last name"/>
            <ValidatedTextInput bind:check={toCheck.isEmailValid} bind:value={form.email} title="Email"/>
            <RolePicker bind:check={toCheck.isRoleValid} title="User role" bind:value={form.role}></RolePicker>
            <Password bind:check={toCheck.isPasswordValid} bind:value={form.password} title="Password"></Password>
            <div class="buttons">
                <button class="secondary-button" on:click={()=>{isOpen = false}}>Cancel</button>
                <button
                        on:click={handleSubmitButton}
                        class="primary-button">
                    {#await actionPromise}
                        <SmallLoader></SmallLoader>
                    {:then response}
                        {text.confirm}
                    {:catch error}
                        Retry
                    {/await}
                </button>
            </div>
            <div class="error-text">
                {errorText}
            </div>
        </div>
    </div>
{/if}