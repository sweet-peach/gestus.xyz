<script>
import {formData} from "$lib/stores/projectFormStore.js";
import NotEmptyInput from "../Items/NotEmptyInput.svelte";

$: toCheck = {};
export function check(){
    let isAllValid = true;
    for(const key in toCheck){
        if(!toCheck[key]()){
            isAllValid = false;
        }
    }
    return isAllValid;
}
</script>

<NotEmptyInput
        title="Project phase"
        bind:check={toCheck.isProjectPhaseValid}
        bind:value={$formData.phase} />

<div class="item">
    <h3>Project characteristics</h3>
    <div class="checkboxes">
        <label class="primary-checkbox">
            <input bind:checked={$formData.isActive} class="checkbox" type="checkbox" id="isActive"/>
            <label class="checkbox-text" for="isActive">Projects is active</label>
        </label>
        <label class="primary-checkbox">
            <input bind:checked={$formData.inCooperation} class="checkbox" type="checkbox" id="inCooperation"/>
            <label class="checkbox-text" for="inCooperation">Project in cooperation</label>
        </label>
    </div>
</div>

<style>
    .checkboxes {
        display: flex;
        flex-direction: column;
        gap: 5px;
        align-items: baseline;
    }
</style>