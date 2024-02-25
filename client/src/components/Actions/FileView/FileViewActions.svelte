<script>
    import '../actions.scss'
    import {view} from '$lib/stores/filesStore.js'
    import ContextMenu from "../../UI/ContextMenu.svelte";
    import CreateFolderModal from "../../CreateFolderModal.svelte";
    import {createEventDispatcher} from "svelte";
    import FileUploadsStatus from "../../FileUploadsStatus.svelte";

    const dispatch = createEventDispatcher();

    function changeView(newView) {
        if (newView === $view) {
            return;
        }
        $view = newView;
    }

    let contextMenuToggleElement, isContextMenuVisible, isFolderModalVisible = false;

    function handleActionClick(event) {
        contextMenuToggleElement = event.currentTarget;
        isContextMenuVisible = !isContextMenuVisible;
    }

    function handleCreateFolderClick() {
        isFolderModalVisible = true;
        isContextMenuVisible = false;
    }

    function handleUploadFile(event) {
        const files = [...event.target.files];
        files.forEach(file => {
            dispatch('uploadFile', file)
        });
    }

</script>

<CreateFolderModal bind:isModalVisible={isFolderModalVisible}></CreateFolderModal>

<FileUploadsStatus></FileUploadsStatus>

<ContextMenu toggleElement={contextMenuToggleElement} bind:isVisible={isContextMenuVisible}>
    <button on:click={handleCreateFolderClick} class="menu-item">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-folder-plus"
             viewBox="0 0 16 16">
            <path d="m.5 3 .04.87a2 2 0 0 0-.342 1.311l.637 7A2 2 0 0 0 2.826 14H9v-1H2.826a1 1 0 0 1-.995-.91l-.637-7A1 1 0 0 1 2.19 4h11.62a1 1 0 0 1 .996 1.09L14.54 8h1.005l.256-2.819A2 2 0 0 0 13.81 3H9.828a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 6.172 1H2.5a2 2 0 0 0-2 2m5.672-1a1 1 0 0 1 .707.293L7.586 3H2.19q-.362.002-.683.12L1.5 2.98a1 1 0 0 1 1-.98z"/>
            <path d="M13.5 9a.5.5 0 0 1 .5.5V11h1.5a.5.5 0 1 1 0 1H14v1.5a.5.5 0 1 1-1 0V12h-1.5a.5.5 0 0 1 0-1H13V9.5a.5.5 0 0 1 .5-.5"/>
        </svg>
        <span>Create new folder</span>
    </button>
    <label for="upload-file" class="menu-item">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
             class="bi bi-file-earmark-arrow-up" viewBox="0 0 16 16">
            <path d="M8.5 11.5a.5.5 0 0 1-1 0V7.707L6.354 8.854a.5.5 0 1 1-.708-.708l2-2a.5.5 0 0 1 .708 0l2 2a.5.5 0 0 1-.708.708L8.5 7.707z"/>
            <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
        </svg>
        <span>Upload new file</span>
    </label>
</ContextMenu>

<input on:click={()=> isContextMenuVisible = false} class="upload-file" multiple={true} on:change={handleUploadFile}
       id="upload-file" type="file"/>

<header class="actions-box">
    <div class="general-actions">
        <button class="primary-button" on:click={handleActionClick}>
            <svg width="17" height="17" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
                <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z"/>
            </svg>
            New
        </button>
    </div>
    <div class="disk-view">
        <button
                class:selected={$view === 'list'}
                on:click={()=> changeView('list')} class="view-option">
            <svg width="20" height="20" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                <path d="M40 48C26.7 48 16 58.7 16 72v48c0 13.3 10.7 24 24 24H88c13.3 0 24-10.7 24-24V72c0-13.3-10.7-24-24-24H40zM192 64c-17.7 0-32 14.3-32 32s14.3 32 32 32H480c17.7 0 32-14.3 32-32s-14.3-32-32-32H192zm0 160c-17.7 0-32 14.3-32 32s14.3 32 32 32H480c17.7 0 32-14.3 32-32s-14.3-32-32-32H192zm0 160c-17.7 0-32 14.3-32 32s14.3 32 32 32H480c17.7 0 32-14.3 32-32s-14.3-32-32-32H192zM16 232v48c0 13.3 10.7 24 24 24H88c13.3 0 24-10.7 24-24V232c0-13.3-10.7-24-24-24H40c-13.3 0-24 10.7-24 24zM40 368c-13.3 0-24 10.7-24 24v48c0 13.3 10.7 24 24 24H88c13.3 0 24-10.7 24-24V392c0-13.3-10.7-24-24-24H40z"/>
            </svg>
        </button>
        <button
                class:selected={$view === 'grid'}
                on:click={()=> changeView('grid')}
                class="view-option">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-grid-fill"
                 viewBox="0 0 16 16">
                <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5zm8 0A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5zm-8 8A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5zm8 0A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5z"/>
            </svg>
        </button>
    </div>
</header>

<style>
    .upload-file {
        display: none;
    }
</style>

