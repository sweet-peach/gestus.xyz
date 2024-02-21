<script>
import {formData} from "$lib/stores/projectFormStore.js";
import ValidatedTextInput from "../Items/ValidatedTextInput.svelte";
import {createEventDispatcher} from "svelte";

const dispatch = createEventDispatcher();

$: toCheck = {};
export function validate(){
    let isAllValid = true;
    for(const key in toCheck){
        if(!toCheck[key]()){
            isAllValid = false;
        }
    }
    if (isAllValid) {
        dispatch('validationPassed');
    }
}
</script>

<ValidatedTextInput
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