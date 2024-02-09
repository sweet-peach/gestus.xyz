<script>
    export let data;
</script>

<div class="users-wrapper">

    {#each data.users as {firstName, lastName, email, role}}
        {firstName}
        {lastName}
        {email}
        {role}
        <button>eliminar</button>
    {/each}

</div>