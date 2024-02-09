<script>


    import {onMount} from "svelte";
    import {getFilesByProjectId} from "$lib/services/fileService.js";
    import {files} from "$lib/stores/appStore.js";

    onMount(async () => {
        $files = await getFilesByProjectId(2)
    })
</script>


<div class="files-wrapper">
    <div class="files-navigation">
        <div class="navigator">Project</div>
        <div class="navigator">Files</div>
        <div class="navigator">Statistics</div>
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
            <div className="name">Name</div>
            <div className="date">Date</div>
            <div className="size">File size</div>
        </div>
        {#each $files as {id, name}}
            <div class="file">
                {id}
            </div>
        {/each}
    </div>
</div>

<style></style>


