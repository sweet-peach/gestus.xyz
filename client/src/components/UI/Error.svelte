<script>
    import {page} from "$app/stores";
    export let message;
    export let confirm;
    export let close;
</script>


<div class="error-box">
    <img src="/request-error.webp" alt="#">
    <p>{message}</p>
    <div class="buttons">
        <button on:click={confirm} class="primary-button">Try again</button>
        <button on:click={close} class="secondary-button">Close</button>
    </div>
</div>

<style lang="scss">
   .error-box {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 10px;

      img {
         width: 150px;
         height: 150px;
         border-radius: 12px;
         z-index: var(--overlay-index);
      }

      p {
         font-size: 20px;
         font-weight: 600;
      }
      .buttons{
         display: flex;
      }
   }
</style>
