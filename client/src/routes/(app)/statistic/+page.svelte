<script>
    import {page} from '$lib/stores/appStore.js';
    import {onMount} from "svelte";
    import StatisticService from "$lib/api/StatisticService.js";
    import {getToken} from "$lib/services/authService.js";
    import MediumLoader from "../../../components/UI/MediumLoader.svelte";
    import {Pie, Bar} from "svelte-chartjs";
    import Chart from 'chart.js/auto';

    page.set([{'title': 'Statistic', 'url': '/statistic'}]);

    let statisticService;

    let pieData = {
        datasets: [{
            data: [],
            backgroundColor: [],
            borderColor: [],
            borderWidth: 1,
        }],
        labels: [],
    };

    let graphData = {};

    let pieOptions = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
        }
    };

    let graphOptions = {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    };

    let statisticPromise = new Promise(() => {});
    async function getStatistic() {
        statisticPromise = Promise.all([
            statisticService.getMostActiveUsers(),
            statisticService.getAllProjectsExtensions()
        ]);
        const [mostActiveUsers, allProjectsExtensions] = await statisticPromise;

        allProjectsExtensions.forEach((part) => {
            pieData.labels.push("." + part.extension);
            pieData.datasets[0].data.push(part.count);
            const color = `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 0.5)`;
            pieData.datasets[0].backgroundColor.push(color);
            pieData.datasets[0].borderColor.push(color.replace('0.5', '1'));
        });

        graphData = {
            labels: mostActiveUsers.map(user => user.email),
            datasets: [{
                label: mostActiveUsers.map(user => user.email),
                data: mostActiveUsers.map(user => user.count),
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        };
    }

    onMount(() => {
        statisticService = new StatisticService(getToken());
        getStatistic();
    });
</script>


<div class="statistics-grid">
    {#await statisticPromise}
        <div class="loader">
            <MediumLoader color="var(--primary-color)"/>
        </div>
    {:then [mostActiveUsers, allProjectsExtensions]}
        <div class="pie">
            <h2>All projects extensions</h2>
            {#if allProjectsExtensions.length === 0}
                <p>Insufficient data to compile statistics</p>
            {:else}
                <Pie data={pieData} options={pieOptions}/>
            {/if}
        </div>
        <div class="graph">
            <h2>Most active users</h2>
            {#if mostActiveUsers.length === 0}
                <p>Insufficient data to compile statistics</p>
            {:else}
                <Bar bind:data={graphData} options={graphOptions} />
            {/if}
        </div>
    {:catch error}
        <p>{error.message}</p>
    {/await}
</div>

<style lang="scss">
   .loader {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
   }

   .statistics-grid {
      padding: 40px;
      display: grid;
      grid-gap: 25px;
      grid-template-columns: repeat(auto-fill, minmax(600px, 1fr));

      p{
         font-size: 18px;
         color: var(--secondary-text-color);
      }

      h2 {
         font-weight: 600;
         font-size: 24px;
      }
   }
</style>
