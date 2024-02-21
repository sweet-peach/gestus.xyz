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
        title="Project type"
        bind:check={toCheck.isProjectTypeValid}
        bind:value={$formData.type} />
<ValidatedTextInput
        title="Project code"
        bind:check={toCheck.isProjectCodeValid}
        bind:value={$formData.code} />
<ValidatedTextInput
        title="Auditor"
        bind:check={toCheck.isAuditorValid}
        bind:value={$formData.auditor} />
<ValidatedTextInput
        title="Rating"
        bind:check={toCheck.isRatingValid}
        bind:value={$formData.rating} />
