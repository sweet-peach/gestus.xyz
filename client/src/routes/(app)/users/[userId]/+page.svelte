<script>
    import {page} from "$lib/stores/appStore.js"
    import StatisticsView from "./StatisticsView.svelte";
    import LogsView from "./LogsView.svelte";
    import UserCard from "./UserCard.svelte";
    import Activity from "./Activity/Activity.svelte";


    export let data;
    const user = data;
    page.set([{'title': 'Users', 'url': '/users'},{'title': `${user.email}`, 'url': `/users/${user.id}`}]);

    const navigations = [
        {
            title: "Logs",
            component: LogsView
        },
        {
            title: "User statistics",
            component: StatisticsView
        }
    ];

    let selectedNavigation = navigations[1];

</script>

<UserCard {user}></UserCard>


<div class="views-wrapper">
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
        <svelte:component {user} this={selectedNavigation.component}/>
    </div>
</div>

<style lang="scss">
   .views-wrapper {
      display: flex;
      flex-direction: column;
      height: 100%;
      margin-top: 20px;
      overflow: hidden;
      .content{
         height: 100%;
         display: flex;
         flex-direction: column;
         overflow: auto;
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






