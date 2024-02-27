<script>
    import {scaleLinear, scaleBand} from 'd3-scale';
    import {page} from '$lib/stores/appStore.js'
    import {onMount} from "svelte";
    import {getToken} from "$lib/services/authService.js";
    import LogsService from "$lib/api/LogsService.js";

    page.set([{'title': 'Statistic', 'url': '/statistic'}]);

    let users = [
    ];
    let logsService;

    async function getStats() {
        users = await logsService.getAll();
    }

    onMount(() => {
        logsService = new LogsService(getToken());
        getStats();
    })

    let isServer = typeof window === 'undefined';
    let width, height;
    if (!isServer) {
        width = window.innerWidth * 0.9;
        height = window.innerHeight * 0.5;
    }
    const padding = {top: 20, right: 15, bottom: 100, left: 60};

    let xScale, yScale
    $: {
        try {
            xScale = scaleBand()
                .domain(users.map(user => user.userName))
                .range([padding.left, width - padding.right])
                .padding(0.1);

            yScale = scaleLinear()
                .domain([0, Math.max(...users.map(user => user.logCount)) + 50])
                .range([height - padding.bottom, padding.top]);
        } catch (e) {

        }

    }


</script>

<h2>Top 5 Most Active Users</h2>

{#if !isServer}
    <div class="chart" bind:clientWidth={width} bind:clientHeight={height}>
        <svg width={width} height={height}>
            <g class="axis y-axis">
                {#each Array.from({length: Math.ceil(yScale.domain()[1] / 50)}).map((_, i) => i * 50) as tick}
                    <g class="tick" transform="translate(0, {yScale(tick)})">
                        <line x2={width - padding.left - padding.right}/>
                        <text x="-10" y="0">{tick}</text>
                    </g>
                {/each}
            </g>

            <g class="axis x-axis" transform="translate(0,{height - padding.bottom})">
                {#each users as user}
                    <g class="tick" transform="translate({xScale(user.userName) + xScale.bandwidth() / 2},0)">
                        <text style="text-anchor: middle;" dy="0.75em">{user.userName}</text>
                    </g>
                {/each}
            </g>

            <g class="bars">
                {#each users as user}
                    <rect
                            x={xScale(user.userName)}
                            y={yScale(user.logCount)}
                            width={xScale.bandwidth()}
                            height={height - padding.bottom - yScale(user.logCount)}
                    />
                {/each}
            </g>
        </svg>
    </div>
{/if}

<style>
    h2 {
        text-align: center;
        font-family: 'Arial', sans-serif;
        color: #333;
    }

    .chart {
        width: auto;
        margin: 20px auto;
    }

    svg {
        display: block;
        max-width: 100%;
        height: auto;
    }

    .tick {
        font-family: 'Arial', sans-serif;
        font-size: 0.75em;
        color: #666;
    }

    .tick line {
        stroke: #e2e2e2;
    }

    .tick text {
        fill: #666;
    }

    .bars rect {
        fill: #4a90e2;
        stroke: #3182bd;
        opacity: 0.75;
    }

    .y-axis text {
        text-anchor: end;
    }

    .x-axis text {
        text-anchor: middle;
    }

</style>
