<script>

    import {clickOutside} from "$lib/use/clickOutside.js";
    import {onMount} from "svelte";
    import SmallLoader from "./UI/SmallLoader.svelte";
    import FileService from "$lib/api/FileService.js";
    import {getToken} from "$lib/services/authService.js";
    import {currentDir, files} from '$lib/stores/filesStore.js'
    import {project} from '$lib/stores/projectStore.js'

    export let isModalVisible = true;
    let isInputDisabled = false, isFolderNameCorrect = false;
    let folderName = '';
    let fileService;
    let errorText = '';

    $: if (folderName === '') {
        errorText = '';
        isFolderNameCorrect = false;
    } else {
        errorText = '';
        isFolderNameCorrect = true
    }

    let createPromise;

    async function handleCreateFolder() {
        if(!isFolderNameCorrect) return;
        isInputDisabled = true;
        try {
            createPromise = fileService.createFolder($project.id, $currentDir, folderName);
            const response = await createPromise;
            files.update(file => {
                file.push(response)
                return file;
            })
            isModalVisible = false;
        } catch (error) {
            errorText = error.message;
            throw new Error(error);
        } finally {
            isInputDisabled = false;
        }
        isInputDisabled = false;
    }

    onMount(() => {
        fileService = new FileService(getToken());
    })

</script>

{#if isModalVisible}
    <div class="modal-container">
        <div class="modal-box" use:clickOutside on:outclick={()=> isModalVisible = false}>
            <h1>Create new folder</h1>
            <div class="input">
                <div class="hint">
                    <span>Folder name</span>
                    <span class="error-text">{errorText} </span>
                </div>
            </div>
            <input disabled={isInputDisabled} class="primary-input" bind:value={folderName}
                   type="text"/>
            <div class="buttons">
                <button class="secondary-button" on:click={()=>{isModalVisible = false}}>Cancel</button>
                <button
                        on:click={handleCreateFolder}
                        class:blocked={!isFolderNameCorrect}
                        class="primary-button">
                    {#await createPromise}
                        <SmallLoader></SmallLoader>
                    {:then response}
                        Create
                    {:catch error}
                        Retry
                    {/await}
                </button>
            </div>
        </div>
    </div>
{/if}

<style lang="scss">
   .modal-container {
      .modal-box {
         gap: 10px;

         h1 {
            font-size: 20px;
            font-weight: 500;
         }

         .buttons {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
         }
      }
   }

   .input {
      margin-top: 10px;
      display: flex;
      flex-direction: column;
      gap: 5px;

      .error-text {
         color: var(--red-color);
         font-size: 16px;
         font-weight: 500;
      }

      &.error {
         input {
            border: 1px solid var(--red-color);
            outline: 1px solid var(--red-color);
         }
      }

      .hint {
         display: flex;
         justify-content: space-between;
      }

      span {
         color: var(--secondary-text-color);
         font-weight: 500;
         font-size: 16px;
      }
   }
</style>