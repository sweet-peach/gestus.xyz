<script>
    import ProjectFileViewActions from "../../../../components/Actions/FileView/FileViewActions.svelte";
    import {files, currentDir, view, dirStack} from "$lib/stores/filesStore.js";
    import {project} from "$lib/stores/projectStore.js";
    import {uploads} from "$lib/stores/uploadsStore.js";
    import FilesService from "$lib/api/FileService.js";
    import {onMount} from "svelte";
    import {getToken} from "$lib/services/authService.js";
    import MediumLoader from "../../../../components/UI/MediumLoader.svelte";
    import SmallLoader from "../../../../components/UI/SmallLoader.svelte";
    import ContextMenu from "../../../../components/UI/ContextMenu.svelte";


    let filesService,filesPromise,deletePromise;
    let isContextMenuVisible = false;
    let contextMenuCauserEvent;
    let selectedFileId;


    async function getFiles() {
        try {
            filesPromise = filesService.getFiles($project.id, $currentDir);
            const response = await filesPromise;
            $files = response;
        } catch (e) {
            throw new Error(e.message);
        }
    }

    function openFolder(folderId){
        dirStack.update(stack => {
            stack.push($currentDir);
            return stack;
        });
        $currentDir = folderId;

    }
    function goBack(){
        $currentDir = $dirStack[$dirStack.length - 1];
        dirStack.update(stack => {
            stack.pop();
            return stack;
        });
    }

    async function handleDeleteFile(){
        try {
            deletePromise = filesService.deleteFile($project.id,selectedFileId);
            const response = await deletePromise;
            $files = $files.filter(file => file.id !== selectedFileId);
            isContextMenuVisible = false;
        } catch (e) {
            throw new Error(e.message);
        }
    }

    async function toggleContextModal(event, fileId){
        contextMenuCauserEvent = event;
        isContextMenuVisible = true;
        selectedFileId = fileId
    }

    function onUploadProgress(progressEvent,fileName){
        const percentage = Math.round((progressEvent.loaded / progressEvent.total) * 100);

        uploads.update(uploads => {
            uploads.forEach(upload => {
                if(upload.name === fileName){
                    upload.progress = percentage;
                }
            });
            return uploads;
        });
    }

    async function uploadFile(event){
        try {
            const file = {
                name: event.detail.name,
                progress: 0,
                error: false,
                message: ''
            };
            await uploads.update(uploads => {
                uploads.push(file);
                return uploads;
            });
            const response = await filesService.uploadFile($project.id, $currentDir, event.detail,(event)=>onUploadProgress(event,file.name));
            $files = [...$files, response];
        } catch (e) {
            uploads.update(uploads => {
                uploads.forEach(upload => {
                    if(upload.name === event.detail.name){
                        upload.error = true;
                        upload.message = e.message;
                    }
                });
                return uploads;
            });
            throw new Error(e.message);
        }
    }

    onMount(() => {
        filesService = new FilesService(getToken());
        const unsubscribe = currentDir.subscribe(value => {
            getFiles();
        });
        return unsubscribe;
    })
</script>

<ContextMenu causerClickEvent={contextMenuCauserEvent} bind:isVisible={isContextMenuVisible}>
    <button on:click={handleDeleteFile} class="menu-item">
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

<ProjectFileViewActions on:uploadFile={uploadFile}/>

{#await filesPromise}
    <div class="loader-box">
        <MediumLoader color="var(--primary-color)"/>
    </div>
{:then response}
    {#if $view === 'list'}
        <div class="files-list">
            <header>
                <div class="name">Name</div>
                <div class="date">Date</div>
                <div class="size">Size</div>
            </header>
            {#if $currentDir}
                <div
                        class="back list-item"
                        on:click={goBack}
                        on:keydown={goBack}
                        aria-label="Go back"
                        tabindex="0"
                        role="button">
                    <div class="icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                             class="bi bi-folder-fill" viewBox="0 0 16 16">
                            <path d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a2 2 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3m-8.322.12q.322-.119.684-.12h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981z"/>
                        </svg>
                    </div>
                    <span class="name">...</span>
                </div>
            {/if}
            {#each $files as file}
                {#if file.type === 'dir'}
                    <div
                            class="folder list-item"
                            on:click={()=>{openFolder(file.id)}}
                            on:keydown={()=> openFolder(file.id)}
                            aria-label="Open folder"
                            tabindex="0"
                            role="button"
                            >
                        <div class="icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                                 class="bi bi-folder-fill" viewBox="0 0 16 16">
                                <path d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a2 2 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3m-8.322.12q.322-.119.684-.12h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981z"/>
                            </svg>
                        </div>
                        <span class="name">{file.name}</span>
                        <span class="date">{file.date.replace('T', ' ').slice(0, 10)}</span>
                        <span class="size">---</span>
                        <button on:click|stopPropagation={(event)=>toggleContextModal(event,file.id)} class="action">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-three-dots-vertical" viewBox="0 0 16 16">
                                <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
                            </svg>
                        </button>
                    </div>
                {:else}
                    <div class="file list-item">
                        <div class="icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                                 class="bi bi-file-earmark-fill" viewBox="0 0 16 16">
                                <path d="M4 0h5.293A1 1 0 0 1 10 .293L13.707 4a1 1 0 0 1 .293.707V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2m5.5 1.5v2a1 1 0 0 0 1 1h2z"/>
                            </svg>
                        </div>
                        <span class="name">{file.name}</span>
                        <span class="date">{file.date.replace('T', ' ').slice(0, 10)}</span>
                        <span class="size">{file.size}</span>
                        <button on:click|stopPropagation={(event)=>toggleContextModal(event,file.id)} class="action">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-three-dots-vertical" viewBox="0 0 16 16">
                                <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
                            </svg>
                        </button>
                    </div>
                {/if}
            {/each}

        </div>
    {:else if $view === 'grid'}
        <div class="files-grid">


        </div>
    {/if}
{:catch error}
    <div>{error.message}</div>
    <button class="primary-button" on:click={getFiles}>Retry</button>
{/await}

<style lang="scss">
   .loader-box {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100%;
   }

   .files-list {
      header {
         display: grid;
         grid-template-columns: 100px 4fr 1fr 1fr 100px;
         padding-bottom: 10px;
         border-bottom: 1px solid var(--border-color);
         font-weight: 500;
         font-size: 18px;

         .name {
            grid-column-start: 2;
         }

         .date {
            grid-column-start: 3;
         }

         .size {
            grid-column-start: 4;
         }
      }

      .list-item {
         display: grid;
         grid-template-columns: 100px 4fr 1fr 1fr 100px;
         align-items: center;

         padding: 15px 0;

         border-bottom: 1px solid var(--border-color);

         &.folder{
            cursor: pointer;
            &:hover{
               background: var(--ternary-background-color);
            }
         }

         &.back{
            cursor: pointer;
            svg{
                color: var(--blue-color);
            }
            &:hover{
               background: var(--ternary-background-color);
            }
         }

         span {
            font-size: 18px;
            font-weight: 400;
         }

         .icon {
            grid-column-start: 1;
            justify-self: center;
         }

         .name {
            grid-column-start: 2;
         }

         .date {
            grid-column-start: 3;
         }

         .size {
            grid-column-start: 4;
         }

         .action {
            grid-column-start: 5;
            justify-self: center;
            display: flex;
            &:hover{
               svg{
                  color:var(--selected-icon-color);
               }
            }
         }
      }
   }

</style>


