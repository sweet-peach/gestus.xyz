<script>
    import Searcher from "$lib/api/SearchService.js";
    import {getToken} from "$lib/services/authService.js";
    import {isLoading, searchKeywords, searchQuery, searchResults} from "$lib/stores/searchStore.js";
    import {onMount} from "svelte";
    import {goto} from "$app/navigation";

    let isClient = typeof window !== 'undefined';
    let isOpen = true;
    let isInFocus = false;
    let searcher = null;
    $: isSearcherDefined = searcher !== null;

    function closeSearchBar() {
        isOpen = false;
    }

    function deleteKeyword(keywordId) {
        searchKeywords.update(currentKeywords => currentKeywords.filter(keyword => keyword.id !== keywordId));
    }

    function addKeyword(keyword) {
        searchKeywords.update(currentKeywords => [...currentKeywords, keyword])
    }

    function openSearchBar() {
        isOpen = true;
    }

    function goToProject(projectId) {
        isOpen = false;
        goto(`/projects/${projectId}`)
    }

    async function search(query, keywords) {
        $isLoading = true;
        $searchResults = await searcher.search(query, keywords);
        $isLoading = false;
    }

    onMount(async () => {
        searcher = new Searcher(getToken());
    })

    let timeout;
    $: if ((isSearcherDefined && isClient) && ($searchQuery !== undefined && $searchKeywords)) {
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            search($searchQuery, $searchKeywords.map((keyword) => keyword.id));
        }, 200);
    }


</script>

<button on:click={openSearchBar} class="search-control">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="search-icon"
         viewBox="0 0 16 16">
        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
    </svg>
    <span>Search projects...</span>
</button>

{#if isOpen}
    <div on:click={closeSearchBar} class="dark-background"></div>
    <div class="search-container">
        <div class="search-bar-container">
            <div class="search-bar {isInFocus ? 'focus' : ''}">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="search-icon"
                     viewBox="0 0 16 16">
                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                </svg>
                <input bind:value={$searchQuery} on:focus={() => isInFocus = true} on:blur={() => isInFocus = false}
                       type="text"
                       placeholder="Search query...">
            </div>
            <div class="keywords">
                {#each $searchKeywords as keyword}
                    <div class="keyword">
                        {keyword.name}
                        <button on:click={()=> {deleteKeyword(keyword.id)}}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 384 512"
                                 fill="currentColor">
                                <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                            </svg>
                        </button>
                    </div>
                {/each}
            </div>
        </div>

        {#if $isLoading}
            <div class="loading">
                <h3>Loading...</h3>
            </div>
        {:else}
            {#if $searchResults?.projects?.length !== 0}
                <div class="result-container">
                    <h3>Projects</h3>
                    <div class="result-items">
                        {#each $searchResults.projects as project}
                            <button on:click={()=>{goToProject(project.id)}} class="item">{project.name}</button>
                        {/each}
                    </div>
                </div>
            {/if}
            {#if $searchResults?.keywords?.length !== 0}
                <div class="result-container">
                    <h3>Keywords</h3>
                    <div class="result-items">
                        {#each $searchResults.keywords as keyword}
                            {#if !$searchKeywords.find((k) => k.id === keyword.id)}
                                <button on:click={()=>{addKeyword(keyword)}} class="item">{keyword.name}</button>
                            {/if}
                        {/each}
                    </div>
                </div>
            {/if}
        {/if}


        <div class="search-hint">
            You can also search keywords and select them to filter the projects
        </div>
    </div>
{/if}

<style lang="scss">

   .search-icon {
      color: var(--icon-color)
   }

   .search-bar-container {
      display: flex;
      flex-direction: column;
      gap: 10px;

      .search-bar {
         display: flex;
         align-items: center;
         border: 1px solid var(--border-color);
         border-radius: 16px;

         svg {
            margin-left: 15px;
         }

         &.focus {
            border: 1px solid var(--blue-color);
            outline: 1px solid var(--blue-color);
         }

         input {
            border: none;
            font-size: 16px;
            width: 100%;
            padding: 15px;

            color: var(--text-color);

            &::placeholder {
               color: var(--secondary-text-color);
            }
         }

      }
   }

   .search-container {
      z-index: 1;

      padding: 20px;
      background: var(--background-color);
      border-radius: 18px;
      border: 1px solid var(--border-color);

      display: flex;
      flex-direction: column;
      gap: 20px;

      position: absolute;
      top: 0;
      left: 0;
      right: 0;

      .keywords {
         display: flex;
         align-items: center;
         gap: 10px;

         .keyword {
            display: flex;
            align-items: center;
            gap: 10px;


            padding: 10px;
            border-radius: 9px;
            background-color: var(--ternary-background-color);
            color: var(--secondary-text-color);
            font-weight: 500;
            font-size: 14px;

            button {
               display: flex;
               align-items: center;
            }

            svg {
               color: var(--icon-color);
            }
         }
      }

      .result-container {
         h3 {
            color: var(--secondary-text-color);
            font-weight: 500;
            font-size: 14px;
            margin-bottom: 5px;
            margin-left: 5px;
         }

         .result-items {
            display: flex;
            flex-direction: column;


            .item {
               padding: 10px;
               width: 100%;
               text-align: left;
               font-weight: 500;
               font-size: 14px;

               &:hover {
                  background-color: var(--ternary-background-color);
                  border-radius: 9px;
               }
            }
         }
      }

      .loading {
         h3 {
            color: var(--secondary-text-color);
            font-weight: 500;
            font-size: 16px;
         }
      }


      .search-hint {
         color: var(--secondary-text-color);
         font-size: 14px;
         font-weight: 400;
      }
   }

   .dark-background {
      position: fixed;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
      background-color: rgba(0, 0, 0, 0.15);
   }

   .search-control {
      padding: 15px 15px;
      border: 1px solid var(--border-color);
      border-radius: 18px;

      display: flex;
      align-items: center;

      width: 300px;

      span {
         color: var(--secondary-text-color);
         font-size: 16px;
         font-weight: 400;
      }

      svg {
         margin-right: 10px;
      }
   }
</style>