<script>
    import {onMount} from "svelte";
    import LogsService from "$lib/api/LogsService.js";
    import {getToken} from "$lib/services/authService.js";
    import MediumLoader from "../../../../components/UI/MediumLoader.svelte";

    export let user;

    let logs = [];
    let logsService;

    let logsPromise;

    async function getLogs() {
        try {
            logsPromise = logsService.getByUserId(user.id)
            logs = await logsPromise;
        } catch (e) {
            throw new Error(e.message);
        }
    }

    onMount(() => {
        logsService = new LogsService(getToken());
        getLogs()
    })
</script>

{#await logsPromise}
    <MediumLoader color="var(--primary-color)"/>
{:then response}
    <div class="logs-list">
        {#each logs as log}
            <div class="log">
                <p class="title">{log.name}</p>
                <p class="date">{log.date.slice(0, 22).replace("T"," ")}</p>
            </div>
        {:else }
            <p>No logs found</p>
        {/each}
    </div>
{:catch error}
    <p>There was an error</p>
    <button on:click={getLogs} class="primary-button">Retry</button>
{/await}

<style lang="scss">
   .logs-list {
      display: flex;
      flex-direction: column;
      gap: 5px;

      .log {
         padding: 20px 10px;
         display: flex;
         justify-content: space-between;
         align-items: center;
         border-bottom: 1px solid var(--border-color);

         .title{
            font-weight: 400;
            font-size: 18px;
         }
         .date{
            color: var(--secondary-text-color);
         }
      }
   }
</style>