<script>
    import '../actions.scss'
    import {isOpen, formType, TYPE} from "$lib/stores/userFormStore.js";


    function handleOpenForm(){
        isOpen.set(true);
        formType.set(TYPE.CREATE);
    }

</script>

<header class="actions-box">
    <button on:click={handleOpenForm} class="primary-button">Create new user</button>
</header>