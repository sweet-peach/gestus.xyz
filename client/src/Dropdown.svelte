<script>
    import { createEventDispatcher, onMount, onDestroy } from 'svelte';

    const dispatch = createEventDispatcher();
    let dropdownVisible = false;
  
    function toggleDropdown() {
      dropdownVisible = !dropdownVisible;
    }
  
    function handleDelete() {
      console.log("eliminao")
    }
  
    function handleModify() {
      console.log("modificao")

    }
  
    // Cerrar el dropdown si se hace clic fuera de él
    function handleClickOutside(event) {
      if (!event.target.closest('.dropdown') && dropdownVisible) {
        dropdownVisible = false;
      }
    }
  
    onMount(() => {
  if (typeof window !== 'undefined') {
    window.addEventListener('click', handleClickOutside);
    console.log("eliminao");
  }
});

onDestroy(() => {
  if (typeof window !== 'undefined') {
    window.removeEventListener('click', handleClickOutside);
    console.log(nose);
  }
});

  </script>
  
  <div class="dropdown" on:click|stopPropagation>
    <button class="dropdown-trigger" on:click={toggleDropdown}>
      <i class="fa-solid fa-ellipsis-vertical"></i>
    </button>
    {#if dropdownVisible}
    <ul class="dropdown-content" style="display: {dropdownVisible ? 'block' : 'none'}">
        <li>
          <button on:click={handleDelete}>Eliminar</button>
        </li>
        <li>
          <button on:click={handleModify}>Modificar</button>
        </li>
      </ul>
    {/if}
  </div>
  
  <style>
    .dropdown {
      position: relative;
      display: inline-block;
    }
  
    .dropdown-content {
      display: none;
      position: absolute;
      background-color: #f9f9f9;
      min-width: 160px;
      box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
      padding: 12px 16px;
      z-index: 1;
    }
  
    .dropdown-content li {
      color: black;
      padding: 10px 20px;
      text-decoration: none;
      display: block;
    }
  
    .dropdown-content li:hover {
      background-color: #f1f1f1;
    }
  
    .dropdown-trigger:focus + .dropdown-content,
    .dropdown-content:hover {
      display: block;
    }
  </style>
  