<script>
    import {createEventDispatcher, onMount} from "svelte";
    const dispatch = createEventDispatcher();

    export let options = [];
    export let placeholder = "Sort by...";

    let internalOptions = [...options];
    let isOpen = false;
    let sortOptions = {
        sortBy: '',
        label:'',
        ascending: true
    };
    $: dispatch('sort', sortOptions);

    const toggleDropdown = () => isOpen = !isOpen;
    const toggleAscending = () => sortOptions.ascending = !sortOptions.ascending;
    const clearSelection = () => {
        isOpen = false
        internalOptions = [...options]
        sortOptions.sortBy = '';
        sortOptions.label = '';
    }

    function selectOption(option) {
        internalOptions = [...options];
        internalOptions = internalOptions.filter(o => o !== option);

        sortOptions.sortBy = option.value;
        sortOptions.label = option.label;
        isOpen = false;
    }

    const handleClickOutside = (event) => {
        if (!event.target.closest('.dropdown') && isOpen) isOpen = false;
    };

    onMount(() => {
        document.addEventListener('click', handleClickOutside);
        return () => document.removeEventListener('click', handleClickOutside);
    });
</script>

<div class="dropdown">
    <button class="dropdown-button {sortOptions.sortBy ? 'selected' : ''}" on:click={toggleDropdown}>
        {#if sortOptions.sortBy && sortOptions.ascending}
            <svg
                    class="sort-order"
                    tabindex="0"
                    role="button"
                    aria-label="{sortOptions.ascending ? 'To descending' : 'To ascending'}"
                    on:keypress={toggleAscending}
                    on:click|stopPropagation={toggleAscending}
                    width="18"
                    height="18"
                    xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">
                <path d="M151.6 42.4C145.5 35.8 137 32 128 32s-17.5 3.8-23.6 10.4l-88 96c-11.9 13-11.1 33.3 2 45.2s33.3 11.1 45.2-2L96 146.3V448c0 17.7 14.3 32 32 32s32-14.3 32-32V146.3l32.4 35.4c11.9 13 32.2 13.9 45.2 2s13.9-32.2 2-45.2l-88-96zM320 480h32c17.7 0 32-14.3 32-32s-14.3-32-32-32H320c-17.7 0-32 14.3-32 32s14.3 32 32 32zm0-128h96c17.7 0 32-14.3 32-32s-14.3-32-32-32H320c-17.7 0-32 14.3-32 32s14.3 32 32 32zm0-128H480c17.7 0 32-14.3 32-32s-14.3-32-32-32H320c-17.7 0-32 14.3-32 32s14.3 32 32 32zm0-128H544c17.7 0 32-14.3 32-32s-14.3-32-32-32H320c-17.7 0-32 14.3-32 32s14.3 32 32 32z"/>
            </svg>
        {:else if sortOptions.sortBy && !sortOptions.ascending}
            <svg
                    class="sort-order"
                    tabindex="0"
                    role="button"
                    aria-label="{sortOptions.ascending ? 'To descending' : 'To ascending'}"
                    on:keypress={toggleAscending}
                    on:click|stopPropagation={toggleAscending}
                    width="18"
                    height="18"
                    xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">
                <path d="M151.6 469.6C145.5 476.2 137 480 128 480s-17.5-3.8-23.6-10.4l-88-96c-11.9-13-11.1-33.3 2-45.2s33.3-11.1 45.2 2L96 365.7V64c0-17.7 14.3-32 32-32s32 14.3 32 32V365.7l32.4-35.4c11.9-13 32.2-13.9 45.2-2s13.9 32.2 2 45.2l-88 96zM320 480c-17.7 0-32-14.3-32-32s14.3-32 32-32h32c17.7 0 32 14.3 32 32s-14.3 32-32 32H320zm0-128c-17.7 0-32-14.3-32-32s14.3-32 32-32h96c17.7 0 32 14.3 32 32s-14.3 32-32 32H320zm0-128c-17.7 0-32-14.3-32-32s14.3-32 32-32H480c17.7 0 32 14.3 32 32s-14.3 32-32 32H320zm0-128c-17.7 0-32-14.3-32-32s14.3-32 32-32H544c17.7 0 32 14.3 32 32s-14.3 32-32 32H320z"/>
            </svg>
        {/if}

        <span>{sortOptions.label || placeholder}</span>
        {#if sortOptions.sortBy}
            <svg
                    tabindex="0"
                    role="button"
                    aria-label="Clear selection"
                    on:keypress={clearSelection}
                    on:click|stopPropagation={clearSelection}
                    width="18"
                    height="18"
                    xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512">
                <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
            </svg>
        {:else }
            <svg class="arrow {isOpen ? 'rotated' : ''}"
                 width="14"
                 height="14"
                 viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z"/>
            </svg>
        {/if}

    </button>
    {#if isOpen}
        <div class="dropdown-content">
            {#each internalOptions as option}
                <button class="dropdown-item" on:click={() => selectOption(option)}>
                    {option.label}
                </button>
            {/each}
        </div>
    {/if}
</div>

<style lang="scss">
   .dropdown {
      position: relative;
      display: inline-block;
   }

   .dropdown-button {
      font-size: 18px;
      font-weight: 500;

      background: var(--secondary-button-color);
      padding: 5px;
      border-radius: 14px;
      cursor: pointer;
      user-select: none;
      display: flex;
      align-items: stretch;

      border: 1px solid var(--secondary-button-color);

      svg {
         box-sizing: content-box;
         padding: 12px
      }

      span {
         display: flex;
         align-items: center;
         padding: 0 20px;
      }

      &:hover {
         background: var(--secondary-button-hover-color);
      }

      &:active {
         background: var(--secondary-button-active-color);
      }


      &.selected {
         background: var(--background-color);
         border: 1px solid var(--border-color);

         span:hover,svg:hover{
            background: var(--secondary-button-hover-color);
         }
         svg{
            padding: 10px;
         }
         svg,span {
            border-radius: 8px;
         }

         span:active,svg:active {
            background: var(--background-color);
         }
      }
   }

   svg.rotated {
      transform: rotate(180deg);
   }

   .dropdown-content {
      position: absolute;
      background-color: var(--background-color);
      border: 1px solid var(--border-color);
      border-radius: 14px;
      margin-top: 5px;
      width: 100%;
      z-index: 1;
      display: flex;
      flex-direction: column;
   }

   .dropdown-item {
      font-size: 16px;
      font-weight: 500;
      margin: 5px;
      padding: 15px 10px;
      cursor: pointer;
      display: flex;

      border-radius: 7px;

      &:hover{
         background: var(--secondary-button-color);
      }
   }


</style>