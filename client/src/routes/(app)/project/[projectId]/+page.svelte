<script>
    import {page} from "$lib/stores/appStore.js";
    import ProjectAboutView from "./AboutView.svelte";
    import FilesView from "./FilesView.svelte";
    import {project} from '$lib/stores/projectStore.js';
    import ProjectCard from "./ProjectCard.svelte";
    import {user} from '$lib/stores/userStore.js';
    import ProjectStatisticView from "./ProjectStatisticView.svelte";
    export let data;
    $project = data;

    page.set([{'title': 'Projects', 'url': '/'},{'title': $project.name, 'url': '/project/' + $project.id}]);

    const navigations = [
        {
            title: "About",
            component: ProjectAboutView
        },
        {
            title: "Files",
            component: FilesView
        }
    ];

    if($user.role === 'ADMIN'){
        navigations.push({
            title: "Statistic",
            component: ProjectStatisticView
        });
    }

    let selectedNavigation = navigations[1];

    </script>


<ProjectCard></ProjectCard>

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
      display: flex;
      flex-direction: column;
      height: 100%;
      margin-top: 20px;
      .content{
         height: 100%;
         display: flex;
         flex-direction: column;
      }
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
            padding: 10px 50px;
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


