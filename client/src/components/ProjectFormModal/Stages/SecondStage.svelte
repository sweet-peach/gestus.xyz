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
        title="Project type"
        bind:check={toCheck.isProjectTypeValid}
        bind:value={$formData.type} />
<NotEmptyInput
        title="Project code"
        bind:check={toCheck.isProjectCodeValid}
        bind:value={$formData.code} />
<NotEmptyInput
        title="Auditor"
        bind:check={toCheck.isAuditorValid}
        bind:value={$formData.auditor} />
<NotEmptyInput
        title="Rating"
        bind:check={toCheck.isRatingValid}
        bind:value={$formData.rating} />
