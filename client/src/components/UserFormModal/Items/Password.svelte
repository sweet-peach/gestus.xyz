<script>
    export let title;
    export let value;
    let error = false;

    $: if(value){
        error = false;
    }

    export function check(){
        error = value === "";
        return !error;
    }
</script>

<div
        class:error={error}
        class="input">
    <div class="hint">
        <span>{title}</span>
        {#if error}
            <span class="error-text">Cannot be empty</span>
        {/if}
    </div>
    <input class="primary-input" type="password" bind:value={value}/>
</div>

<style lang="scss">
   .input {
      margin-top: 10px;
      display: flex;
      flex-direction: column;
      gap: 5px;

      .error-text {
         color: var(--red-color);
         font-size: 16px;
         font-weight: 500;
      }

      &.error {
         input {
            border: 1px solid var(--red-color);
            outline: 1px solid var(--red-color);
         }
      }

      .hint {
         display: flex;
         justify-content: space-between;
      }

      span {
         color: var(--secondary-text-color);
         font-weight: 500;
         font-size: 16px;
      }
   }
</style>