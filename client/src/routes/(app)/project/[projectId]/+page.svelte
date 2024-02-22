<script>
    import {onMount} from "svelte";
    import {getFilesByProjectId} from "$lib/services/fileService.js";
    import {files} from "$lib/stores/appStore.js";

    onMount(async () => {
        $files = await getFilesByProjectId(2)
        console.log($files);
    })
</script>


<div class="files-wrapper">
    <div class="files-navigation">
        <div class="navigator button-highlight">Project</div>
        <div class="navigator button-highlight">Files</div>
        <div class="navigator button-highlight">Statistics</div>
    </div>
    <div class="files-actions">
        <div class="left-actions">
            <button class="primary-button">New</button>
        </div>
        <div class="right-actions">

        </div>
    </div>
    <div class="files-view">
        <div class="file-header">
            <div class="name">Name</div>
            <div class="date">Date</div>
            <div class="size">File size</div>
        </div>
        {#each $files as {date, name, size, type}}
            <div class="file-list">
                <div class="icon">
                    {#if type === "dir"}
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-folder-fill" viewBox="0 0 16 16">
                            <path d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a2 2 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3m-8.322.12q.322-.119.684-.12h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981z"/>
                        </svg>
                    {:else}
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-fill" viewBox="0 0 16 16">
                            <path d="M4 0h5.293A1 1 0 0 1 10 .293L13.707 4a1 1 0 0 1 .293.707V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2m5.5 1.5v2a1 1 0 0 0 1 1h2z"/>
                        </svg>
                    {/if}
                </div>
                <div class="name">
                    {name}
                </div>
                <div class="date">
                    {String(date).toString().slice(0,10)}
                </div>
                <div class="size">
                    {#if type !== "dir"}
                        {size}
                    {/if}
                </div>
                <div class="action">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-three-dots-vertical" viewBox="0 0 16 16">
                        <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
                    </svg>
                </div>
            </div>
        {/each}
    </div>
</div>

<style lang="scss">

  .file-list {
    padding: 10px 0;
    display: grid;
    grid-template-columns: 1fr 4fr repeat(3, 1fr);

    .name {
      grid-column-start: 2;
    }

    .date {
      grid-column-start: 3;
      justify-self: center;
    }

    .size {
      grid-column-start: 4;
      justify-self: center;
    }

    .action {
      grid-column-start: 5;
      justify-self: center;
    }
  }

  .file-header {
    padding: 20px 0;
    display: grid;
    grid-template-columns: 1fr 4fr repeat(3, 1fr);

    .name {
      grid-column-start: 2;
    }

    .date {
      grid-column-start: 3;
      justify-self: center;
    }

    .size {
      grid-column-start: 4;
      justify-self: center;
    }
  }
  .files-navigation{
    padding-bottom: 20px;
    display: flex;
  }

  .button-highlight {
  border: none;
  border-bottom: 2px solid transparent;
  background-color: transparent;
  color: #000;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}

.button-highlight:hover {
  border-bottom-color: var(--color-primary);
}
.primary-button{
    padding: 20px 50px;
}
</style>


