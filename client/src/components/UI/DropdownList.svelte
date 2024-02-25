<script>

    export let options;
    export let selectedOption;
    let placeholder = 'Select an option';

    function selectOption(option) {
        selectedOption = option;
        toggleDropdown();
    }

    function toggleDropdown() {
        isOpen = !isOpen;
    }

    let isOpen = false;
</script>


<div class="dropdown">
    <button class="label" on:click={toggleDropdown}>

        <span class="text">{selectedOption ? selectedOption.label : placeholder}</span>

        <svg class="arrow {isOpen ? 'rotated' : ''}"
             width="12"
             height="12"
             viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd"
                  d="M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z"/>
        </svg>
    </button>


    {#if isOpen}
        <div class="menu">
            {#each options as option}
                {#if option !== selectedOption}
                    <button class="item" on:click={() => selectOption(option)}>
                        {option.label}
                    </button>
                {/if}
            {/each}
        </div>
    {/if}
</div>


<style lang="scss">
   .dropdown {
      position: relative;

      .label {
         font-size: 17px;
         font-weight: 500;
         width: 100%;

         background: var(--background-color);
         padding: 5px;
         border-radius: 14px;
         cursor: pointer;
         user-select: none;
         display: flex;
         align-items: stretch;

         border: 1px solid var(--border-color);

         .arrow.rotated {
            transform: rotate(180deg);
         }

         svg {
            box-sizing: content-box;
            padding: 12px
         }

         .text {
            display: flex;
            align-items: center;
            padding: 0 15px;
            color: var(--color-text);
            margin-right: auto;
         }

         svg {
            padding: 10px;
         }

         span:active, svg:active {
            background: var(--background-color);
         }
      }

      .menu {
         position: absolute;
         background-color: var(--background-color);
         border: 1px solid var(--border-color);
         border-radius: 14px;
         margin-top: 5px;
         z-index: 1;
         display: flex;
         flex-direction: column;
         width: 100%;

         .item {
            font-size: 16px;
            font-weight: 500;
            margin: 5px;
            padding: 15px 10px 15px 10px;
            cursor: pointer;
            display: flex;

            border-radius: 7px;

            &:hover {
               background: var(--secondary-button-color);
            }
         }
      }
   }
</style>