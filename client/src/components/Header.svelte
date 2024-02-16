<script>
    import {page} from "$lib/stores/appStore.js";
    import {getContext, onDestroy, onMount} from "svelte";
    import {logout} from "$lib/services/authService.js";

    const user = getContext("user");
    const username = String($user.email).toString().split("@")[0];

    let isUserMenuVisible = false;

    function toggleUserMenu() {
        isUserMenuVisible = !isUserMenuVisible;
    }

    onMount(() => {
        function handleClickOutside(event) {
            if (!event.target.closest('.user-box')) {
                isUserMenuVisible = false;
            }
        }

        window.addEventListener('click', handleClickOutside);

        return () => {
            window.removeEventListener('click', handleClickOutside);
        };
    });

    function handleLogout() {
        logout();
        location.reload();
    }

</script>

<header>
    <h2>{$page}</h2>
    <div class="right-wrapper">
        <button class="search-control">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="search-icon"
                 viewBox="0 0 16 16">
                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
            </svg>
            <span>Search projects...</span>
        </button>
        <div class="user-box">
            <button on:click={toggleUserMenu} class="username">@{username}</button>
            {#if isUserMenuVisible}
                <div class="user-actions">
                    <button class="user-action" on:click={handleLogout}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                             class="bi bi-box-arrow-right" viewBox="0 0 16 16">
                            <path fill-rule="evenodd"
                                  d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0z"/>
                            <path fill-rule="evenodd"
                                  d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708z"/>
                        </svg>
                        <span>Log out</span>
                    </button>
                </div>
            {/if}
        </div>
    </div>
</header>

<style lang="scss">


   .search-control {
      padding: 15px 15px;
      border: 1px solid var(--border-color);
      border-radius: 18px;

      display: flex;
      align-items: center;

      width: 300px;

      span{
         color: var(--secondary-text-color);
         font-size: 16px;
         font-weight: 400;
      }

      svg{
         color: var(--icon-color);
         margin-right: 10px;
      }
   }

   header {
      padding-top: 10px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding-bottom: 40px;
   }

   .right-wrapper {
      display: flex;
      gap: 25px;
   }

   .user-box {
      position: relative;

      .username {
         cursor: pointer;
         font-size: 18px;
         font-weight: 500;
      }
   }
</style>