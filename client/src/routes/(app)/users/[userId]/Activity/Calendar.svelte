<script>
    import {generateCalendar, months, days} from '$lib/services/datesService.js';
    import Tooltip from "../../../../../components/UI/Tooltip.svelte";

    let monthsForIterations = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    let maxCount;
    export let contributions = [];
    export let selectedYear = new Date().getFullYear();

    $: maxCount = Math.max(...contributions.map(contribution => contribution.count));
    $: calendar = generateCalendar(selectedYear);

    let isVisible = false;
    let hoverElement = null;
    let tooltipText = '';

    async function showTooltip(event) {
        hoverElement = event.currentTarget;
        isVisible = true;
    }

    function hideTooltip() {
        isVisible = false;
    }
</script>


<Tooltip bind:text={tooltipText} bind:hoverElement={hoverElement} bind:isVisible={isVisible}/>

<div class="calendar">
    <table>
        <tbody>
        <tr class="months">
            <td class="space"></td>
            {#each monthsForIterations as month, iteration}

                <td class="month">{months[new Date(selectedYear,calendar[0][0].month + iteration+1,0).getMonth()].slice(0,3)}</td>
            {/each}
        </tr>
        {#each calendar as row, rowIndex}
            <tr class="cells">
                {#if rowIndex === 1}
                    <td class="day">{days[new Date(selectedYear, row[rowIndex].month, row[rowIndex].day).getDay()].slice(0, 3)}</td>
                {:else if rowIndex === 3}
                    <td class="day">{days[new Date(selectedYear, row[rowIndex].month, row[rowIndex].day).getDay()].slice(0, 3)}</td>
                {:else if rowIndex === 5}
                    <td class="day">{days[new Date(selectedYear, row[rowIndex].month, row[rowIndex].day).getDay()].slice(0, 3)}</td>
                {:else }
                    <td class="day"></td>
                {/if}


                {#each row as {day, month}}
                    {#if contributions.find(contribution => new Date(contribution.date).getDate() === day && new Date(contribution.date).getMonth() === month)}
                        <td class="cell active"
                            style="opacity: {100 * contributions.find(contribution => new Date(contribution.date).getDate() === day && new Date(contribution.date).getMonth() === month).count / maxCount + 30}%"
                            on:mouseenter={(event)=>{
                                    showTooltip(event);
                                    tooltipText = contributions.find(contribution => new Date(contribution.date).getDate() === day && new Date(contribution.date).getMonth() === month).count + ' activity/s on ' + months[month] + ' ' + day;
                                }}
                            on:mouseleave={hideTooltip}
                        ></td>
                    {:else}
                        <td class="cell" on:mouseenter={(event)=>{
                                    showTooltip(event);
                                    tooltipText = `No activity on ${months[month]} ${day}`;
                                }}
                            on:mouseleave={hideTooltip}
                        ></td>
                    {/if}
                {/each}
            </tr>
        {/each}
        </tbody>
    </table>
</div>

<style lang="scss">
   .calendar {
      overflow: auto;

      table {
         padding: 20px;
         border-radius: 14px;

         tbody {
            border-collapse: separate;
            border-spacing: 3px;
         }

         .months {
            display: flex;
            justify-content: space-evenly;
         }

         .cells {
            display: flex;
         }

         .day {
            display: flex;
            color: var(--secondary-text-color);
            width: 50px;
         }

         .cell {
            border: 1px solid;
            margin: 2px;
            width: 15px;
            height: 15px;
            border-radius: 3px;
            border: 1px solid var(--border-color);
            background: var(--ternary-background-color);

            &.active {
               background: var(--primary-color);
            }
         }
      }
   }
</style>