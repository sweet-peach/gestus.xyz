<script>
    import {page} from "$lib/stores/appStore.js";
    import {getContext, onMount} from "svelte";
    import {logout} from "$lib/services/authService.js";
    import ProjectsSearch from "./ProjectsSearch.svelte";

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
    <h2>
        {#each $page as segment, index}
            <span>
                {#if index > 0}
                    <span> / </span>
                {/if}
                <a href={segment.url}>{segment.title}</a>
            </span>
        {/each}
    </h2>
    <div class="right-wrapper">
        <ProjectsSearch></ProjectsSearch>
        <div class="user-box">
            <button on:click={toggleUserMenu} class="username">@{username}</button>
            {#if isUserMenuVisible}
                <div class="context-menu">
                    <button class="menu-item" on:click={handleLogout}>
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

   .context-menu {
      position: absolute;
      top: 100%;
      right: 0;
   }

   header {
      position: relative;

      h2 {
         font-weight: 600;
      }

      padding-top: 10px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding-bottom: 20px;
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