<script>
    import {page} from '$lib/stores/appStore.js';
    import {onMount} from "svelte";
    import UsersService from "$lib/api/UsersService.js";
    import {getToken} from "$lib/services/authService.js";
    import MediumLoader from "../../../components/UI/MediumLoader.svelte";
    import UsersViewActions from "../../../components/Actions/UsersView/UsersViewActions.svelte";
    import UserFormModal from "../../../components/UserFormModal/UserFormModal.svelte";
    import {writable} from "svelte/store";
    import SmallLoader from "../../../components/UI/SmallLoader.svelte";
    import ContextMenu from "../../../components/UI/ContextMenu.svelte";
    import {formData, formType, isOpen, TYPE} from "$lib/stores/userFormStore.js";
    import {project} from "$lib/stores/projectStore.js";

    page.set([{'title': 'Users', 'url': '/users'}]);

    let users = writable([]);
    let usersPromise = new Promise(()=>{});
    let usersService;

    let isContextMenuVisible = false;
    let contextMenuToggleElement;
    let selectedUser;


    async function getUsers(sortBy, sortDirection) {
        usersPromise = usersService.getAll(sortBy, sortDirection);

        $users = await usersPromise;
    }


    function toggleContextMenu(event) {
        contextMenuToggleElement = event.currentTarget;
        isContextMenuVisible = !isContextMenuVisible;
    }

    let deletePromise;

    async function handleDeleteUser(event) {
        try {
            deletePromise = usersService.delete(selectedUser.id);
            const response = await deletePromise;
            users.update(users => users.filter(u => u.id !== selectedUser.id));
            isContextMenuVisible = false;
        } catch (e) {
            throw new Error(e);
        }
    }

    function showFormUpdate(event) {
        $formData = selectedUser;
        $formType = TYPE.UPDATE;
        isOpen.set(true);
        isContextMenuVisible = false;
    }

    function handleUserCreate(event) {
        const user = event.detail;

        users.update(users => [...users, user]);
    }

    function handleUserUpdate(event) {
        const user = event.detail;

        users.update(users => {
            const index = users.findIndex(u => u.id === user.id);
            users[index] = user;
            return users;
        });
    }

    function handleSort(event){
        const {sortBy, ascending} = event.detail;
        getUsers(sortBy, ascending ? 'asc' : 'desc');
    }

    onMount(() => {
        usersService = new UsersService(getToken());
        getUsers();
    })
</script>


<ContextMenu
        toggleElement={contextMenuToggleElement}
        bind:isVisible={isContextMenuVisible}>
    <button on:click={showFormUpdate} class="menu-item">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-pencil-square"
             viewBox="0 0 16 16">
            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
            <path fill-rule="evenodd"
                  d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
        </svg>
        <span>Edit</span>
    </button>
    <button on:click={handleDeleteUser} class="menu-item">
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

<UserFormModal bind:form={$formData} bind:type={$formType} bind:isOpen={$isOpen} on:create={handleUserCreate} on:update={handleUserUpdate}/>
<UsersViewActions on:sort={handleSort}/>

<div class="users-list">
    {#await usersPromise}
        <MediumLoader color="var(--primary-color)"/>
    {:then response}
        {#each $users as user }
            <a href="/users/{user.id}" class="user">
                <div class="text-box">
                    <p>{user.firstName} {user.lastName}
                        <span>({user.role.charAt(0).toUpperCase() + user.role.slice(1).toLowerCase()})</span></p>
                    <span class="email">{user.email}</span>
                </div>
                <button
                        on:click|preventDefault={(event)=>{
                                toggleContextMenu(event);
                                selectedUser = user;
                            }}
                        class="action">
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor"
                         class="bi bi-three-dots-vertical" viewBox="0 0 16 16">
                        <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
                    </svg>
                </button>
            </a>
        {:else}
            <p> No users found </p>
        {/each}
    {:catch error}
        <div class="error-container">
            <div class="error-box">
                <p class="message">{error.message}</p>
                <button class="primary-button" on:click={getUsers}>Retry</button>
            </div>
        </div>
    {/await}
</div>

<style lang="scss">
   .error-container{
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      .error-box{
         display: flex;
         flex-direction: column;
         align-items: center;
         .message{
            font-weight: 500;
            font-size: 22px;
            margin-bottom: 10px;
         }
      }
   }
   .users-list {
      flex: 1;
      display: flex;
      flex-direction: column;
      overflow: auto;

      .user {
         display: flex;
         justify-content: space-between;
         padding: 20px;
         border-top: 1px solid var(--border-color);
         align-items: center;

         &:hover {
            background: var(--ternary-background-color);
         }

         .text-box {
            p {
               font-size: 20px;
               font-weight: 500;
               span {
                  color: var(--secondary-text-color);
                  font-size: 18px;
                  font-weight: 400;
                  padding-bottom: 20px;
               }
               margin-bottom: 5px;
            }
            .email{
               color: var(--secondary-text-color);
               font-size: 18px;
               font-weight: 400;
            }



            h4 {
               color: var(--secondary-text-color);
               font-size: 18px;
            }
         }

         &:last-child {
            border-bottom: 1px solid var(--border-color);
         }

         .action {
            display: flex;
            cursor: pointer;

            &:hover {
               svg {
                  fill: var(--selected-icon-color);
               }
            }
         }
      }
   }
</style>