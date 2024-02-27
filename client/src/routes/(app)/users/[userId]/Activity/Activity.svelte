<script>
    import Calendar from "./Calendar.svelte";
    import {onMount} from "svelte";
    import StatisticService from "$lib/api/StatisticService.js";
    import {getToken} from "$lib/services/authService.js";
    import MediumLoader from "../../../../../components/UI/MediumLoader.svelte";

    let statisticService;

    export let user;


    let selectedYear = new Date().getFullYear();
    let totalLogs = 0;
    let contributionsPromise;
    async function getStatistic() {
        try {
            contributionsPromise = statisticService.getUserActivityByYear(user.id,selectedYear)
            userContributions = await contributionsPromise;
            totalLogs = userContributions.reduce((acc, curr) => acc + curr.count, 0);
        } catch (e) {
            throw new Error(e.message)
        }
    }

    onMount(() => {
        statisticService = new StatisticService(getToken())
        getStatistic();
    });

    let userContributions = []
</script>

<div class="activity">
    <h1>Activity calendar</h1>
    {#await contributionsPromise}
        <MediumLoader></MediumLoader>
    {:then response}
        <div class="text-box">
            <p>Made <span>{totalLogs}</span> actions this year</p>
        </div>
        <Calendar bind:contributions={userContributions}></Calendar>
    {:catch error}
        <p>{error}</p>
        <button class="primary-button" on:click={getStatistic}>Try again</button>
    {/await}

</div>

<style lang="scss">
   .activity {
      margin-top: 20px;
      display: flex;
      padding: 20px;
      flex-direction: column;

      h1 {
         font-size: 22px;
         font-weight: 500;
         margin-bottom: 5px;
      }

      .text-box {
         p {
            font-size: 18px;
            font-weight: 400;
            color: var(--secondary-text-color);

            span {
               color: var(--text-color);
            }
         }

         margin-bottom: 10px;
      }
   }

</style>
