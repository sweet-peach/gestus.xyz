<script>
    import {onMount} from 'svelte';
    import {Pie} from 'svelte-chartjs';
    import StatisticService from "$lib/api/StatisticService.js";
    import {getToken} from "$lib/services/authService.js";
    import {project} from "$lib/stores/projectStore.js";
    import Chart from 'chart.js/auto';
    import MediumLoader from "../../../../components/UI/MediumLoader.svelte";


    let statisticService;

    let data = {
        datasets: [{
            data: [],
            borderWidth: 4,
        }],
        labels: [],
    };

    let options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
            }
        }
    };

    let statisticPromise = new Promise(() => {
    })

    async function getStatistic() {
        statisticPromise = statisticService.getProjectExtensionStatistic($project.id);
        const response = await statisticPromise;

        for (const key in response) {
            const part = response[key];
            data.labels.push("." + part.extension);
            data.datasets[0].data.push(part.count);
        }
    }

    onMount(() => {
        statisticService = new StatisticService(getToken());
        getStatistic();
    })

</script>
<div class="statistic-container">
    <div class="activity">
        <h1>File extension distribution</h1>
        <p>Graphic statistic</p>
    </div>
    <div class="pie">
        {#await statisticPromise}
            <MediumLoader color="var(--primary-color)"></MediumLoader>
        {:then value}
            <Pie {data} {options}/>
        {:catch error}
            <p>{error.message}</p>
            <button on:click={getStatistic}>Retry</button>
        {/await}
    </div>
</div>

<style lang="scss">
   .statistic-container {
      padding: 24px;
      width: 100%;

      .activity {
         h1 {
            font-size: 22px;
            font-weight: 500;
         }

         p {
            font-size: 18px;
            font-weight: 400;
            color: var(--secondary-text-color);
         }
      }

      .pie {
         width: 100%;
         height: 100%;
         max-width: 500px;
         display: flex;
         align-items: center;
      }

   }
</style>
