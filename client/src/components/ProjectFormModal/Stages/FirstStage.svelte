<script>
    import {formData} from "$lib/stores/projectFormStore.js";
    import {createEventDispatcher} from "svelte";
    import ValidatedTextInput from "../Items/ValidatedTextInput.svelte";
    import ValidatedDateRangePicker from "../Items/ValidatedDateRangePicker.svelte";
    import Keywords from "../Items/Keywords.svelte";

    const dispatch = createEventDispatcher();

    $: toCheck = {};
    export function validate () {
        let isAllValid = true;
        for (const key in toCheck) {
            if (!toCheck[key]()) {
                isAllValid = false;
            }
        }
        if (isAllValid) {
            dispatch('validationPassed');
        }
    }

</script>

<ValidatedTextInput
        title="Name of the project"
        bind:value={$formData.name}
        bind:check={toCheck.isNameValid}
/>
<ValidatedDateRangePicker
        title="Execution date in (UTC)"
        bind:firstDate={$formData.executionStart}
        bind:secondDate={$formData.executionEnd}
        bind:check={toCheck.isExecutionsDatesValid}
>
    <div class="date-hints">
        <p class="hint start">Start</p>
        <p class="hint end">End</p>
    </div>
</ValidatedDateRangePicker>
<Keywords
        title="Project keywords"
        bind:keywords={$formData.keywords}
></Keywords>
