<script>
    import {uploads} from "$lib/stores/uploadsStore.js";

    function handleClearUploads() {
        uploads.set([]);
    }
</script>

{#if $uploads.length > 0}
    <div class="upload-status-container">
        <div class="upload-status-box">
            <header>
                <h2>File uploads status</h2>
                <button on:click={handleClearUploads}>
                    <svg
                            width="15px"
                            height="15px"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 384 512">
                        <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                    </svg>
                </button>
            </header>
            {#each $uploads as upload}
                <div class="uploads">
                    <div class="upload">
                        <div class="upload-info">
                            <p class="name">{upload.name.length > 20 ? `${upload.name.slice(0,20)}...` : upload.name}</p>
                            {#if upload.error}
                                <p class="status">Not uploaded</p>
                            {:else }
                                <p class="status">{upload.progress === 100 ? "Uploaded" : upload.progress + "%"}</p>
                            {/if}
                        </div>
                        <div class="upload-progress">
                            <div class="upload-progress-bar" style="width: {upload.progress}%"></div>
                        </div>
                        <div class="error-status">
                            {#if upload.error}
                                <p class="error">{upload.message}</p>
                            {/if }
                        </div>
                    </div>
                </div>
            {/each}
        </div>
    </div>
{/if}

<style lang="scss">
   .upload-status-container {
      position: absolute;
      bottom: 20px;
      right: 20px;

      .upload-status-box {
         width: 500px;
         background: var(--background-color);
         border: 1px solid var(--border-color);
         display: flex;
         flex-direction: column;

         border-radius: 16px;

         header {
            padding: 20px 25px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: var(--primary-color);
            border-radius: 16px 16px 0 0;

            button{
               display: flex;
               align-items: center;
            }

            h2 {
               font-size: 18px;
               font-weight: 500;
            }
         }
      }

      .uploads {
         .upload {
            padding: 20px 25px;
            display: flex;
            flex-direction: column;
            border-bottom: 1px solid var(--border-color);

            &:last-child {
               border-bottom: none;
            }

            .upload-progress {
               width: 100%;
               height: 10px;
               background: var(--ternary-background-color);
               border-radius: 50px;
               position: relative;

               .upload-progress-bar {
                  background: var(--primary-color);
                  height: 100%;
                  border-radius: 5px;
               }
            }

            .error-status{
               margin-top: 10px;
               .error{
                  font-size: 16px;
                  font-weight: 400;
                  color: var(--red-color);
               }
            }

            .upload-info {
               display: flex;
               justify-content: space-between;
               align-items: center;
               margin-bottom: 10px;

               .name {
                  font-size: 18px;
                  font-weight: 400;
                  color: var(--text-color);
               }

               .status {
                  font-size: 16px;
                  font-weight: 400;
                  color: var(--secondary-text-color);
               }

               .error {
                  font-size: 16px;
                  font-weight: 400;
                  color: var(--red-color);
               }
            }
         }
      }
   }
</style>
