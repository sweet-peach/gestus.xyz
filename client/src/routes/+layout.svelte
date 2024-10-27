<script>
    import "../global.scss"
    import {setContext} from "svelte";
    import {user} from "$lib/stores/userStore.js";
    import PreviewWidget from "./PreviewWidget.svelte";

    export let data;

    $: user.set(data.user);
    setContext('user', user);
</script>


<slot>
</slot>
<PreviewWidget></PreviewWidget>