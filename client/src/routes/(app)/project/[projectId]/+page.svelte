<script>
    import {onMount} from "svelte";
    import {files, page} from "$lib/stores/appStore.js";
    import ProjectView from "./ProjectView.svelte";
    import FilesView from "./FilesView.svelte";
    import {project} from '$lib/stores/projectStore.js';
    export let data;
    $project = data;

    page.set('Browsing project');

    const navigations = [
        {
            title: "Project",
            component: ProjectView
        },
        {
            title: "Files",
            component: FilesView
        }
    ];

    let selectedNavigation = navigations[0];

    onMount(async () => {
        console.log($files);
    })
</script>


<div class="files-wrapper">
    <div class="navigation">
        {#each navigations as navigation}
            <button
                    on:click={() => selectedNavigation = navigation}
                    class:selected={selectedNavigation === navigation}
                    class="navigator"><span>{navigation.title}</span></button>
        {/each}
        <div class="line"></div>
    </div>
    <div class="content">
        <svelte:component this={selectedNavigation.component}/>
    </div>
</div>

<style lang="scss">
   .files-wrapper {
      .navigation {
         display: flex;
         width: 100%;
         position: relative;

         .line {
            position: absolute;
            width: 100%;
            height: 1px;
            bottom: 0;
            z-index: var(--back-index);
            background-color: var(--ternary-background-color);
         }

         .navigator {
            padding: 15px 50px;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 4px;

            span {
               color: var(--secondary-text-color);
               font-weight: 500;
               font-size: 18px;
            }

            &:hover {
               span {
                  color: var(--text-color);
               }
            }

            &.selected {
               margin-bottom: 0;
               border-bottom: 4px solid var(--primary-color);

               span {
                  color: var(--text-color);
               }
            }
         }
      }

   }
</style>


