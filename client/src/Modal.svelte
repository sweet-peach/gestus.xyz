<!-- Modal.svelte -->
<script>
    export let isOpen;
    export let title;
    export let step;
    export let closeModal;
    export let nextModal;
    export let prevModal;
  </script>
  
  {#if isOpen}
  <div class="modal-overlay">
      <div class="modal">
          <h2>{title}</h2>
          {#if step === 1}
          <div class="project-name">
            <label for="">Nombre del proyecto</label>
            <input type="text" class="input-info">
          </div>
          
          <div class="project-date">
            <div class="dates">
              <label for="">Fecha de ejecucion</label>
            </div>
            <div class="dates">
              <input type="date">
              <p>-</p>
              <input type="date">
            </div>
          </div>
        
          <div class="project-keywords">
            <label for="">Palabras clave</label>
            <input type="text" class="input-info">
            <h6>Utiliza coma como separador</h6>
          </div>
          {/if}
          {#if step === 2}
          <div class="project-name">
            <label for="">Tipo de proyecto</label>
            <select name="" id="">
                <option value="">1</option>
            </select>
          </div>
          
          <div class="project-name">
            <label for="">Auditor</label>
            <input type="text" class="input-info">
            <h6>Utiliza coma como separador</h6>
          </div>
        
          <div class="project-keywords">
            <label for="">Calificación</label>
            <select name="" id="">
                <option value="">1</option>
            </select>
          </div>
          <div class="div-button">
          <button class="button-prev" on:click={prevModal}>Volver</button>
        </div>
          {/if}

          {#if step === 3}
          <div class="project-name">
            <label for="">Fase del proyecto</label>
            <select>
                <option>Inicio</option>
            </select>
          </div>
          
          <div class="project-features">
            <div class="feature">
              <label for="">Fecha de ejecucion</label>
            </div>
            <div class="features">
                <div class="div-features">
                    <input type="checkbox" name="" id="check">
                    <label for="check">Proyecto está activo</label>
                </div>
                <div class="div-features">
                    <input type="checkbox" name="" id="check">
                    <label for="check">Proyecto está activo</label>
                </div>
                <div class="div-features">
                    <input type="checkbox" name="" id="check">
                    <label for="check">Proyecto está activo</label>
                </div>
            </div>
          </div>
        
        <div class="div-button">
            <button class="button-prev" on:click={prevModal}>Volver</button>
          <button class="button-continue" on:click={closeModal}>Finalizar</button>
          
        </div>
          {/if}
          {#if step < 3}
          <div class="div-button">
            <button class="button-continue" on:click={nextModal}>Continuar</button>
        </div>
          {/if}
      </div>
  </div>
  {/if}

  
  <style>
    .button-prev{
        border: none;
        outline: none;
        background: #c9c9c9;
        padding: 15px 20px;
        border-radius: 15px;
    }

label{
        font-size: 15px;
    }
    .div-button{
        gap: 10px;
        display: flex;
        justify-content: flex-end;
    }
    .button-continue{
        background-color: var(--color-primary);
        padding: 12px 20px;
        border: none;
        
    }
    .input-info{
    border-radius: 25px;
    border: none;
    padding: 10px;
    outline: none;
}
    .project-keywords{
        display: flex;
        flex-direction: column;
        gap: 10px;
    }
    .dates{
        display: flex;
        align-items: center;
    }
    .dates p{
        margin: 0 20px;
    }
    
    input[type=date]{
        border-radius: 10px;
        border: none;
        padding: 20px 0;
        outline: none;
    }
    .button-continue,.input-info{
        width: min-content;
        border-radius: 1px;
        box-shadow: 0px 0px 0px 1px rgba(0,0,0,0.1);
        border-radius: 10px;
    }
    .project-date{
        display: flex;
        flex-direction: column;
    }
    .project-name{
        gap: 10px;
        display: flex;
        flex-direction: column;
    }
    .modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        }
  
    .modal {
        gap: 30px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        height: 50%;
        width: 30%;
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }
    
  </style>
  