<script>
    import {onMount} from "svelte";
    import {createAxiosClient} from "$lib/axiosClient.js";

    let isExplanationOpen = false;
    let email = ''
    let password = ''

    const client = createAxiosClient('',false);
    async function getCredentials(){
        const data = await client.get('/api/preview/credentials');
        email = data.email;
        password = data.password;
    }

    onMount(()=>{
        getCredentials();
    })

</script>
<div class="preview-box">
    <button class="icon-box" on:click={() => isExplanationOpen = !isExplanationOpen}>
        <svg xmlns="http://www.w3.org/2000/svg" width="52" height="52" fill="currentColor" viewBox="0 0 256 256">
            <path d="M192,96c0,28.51-24.47,52.11-56,55.56V160a8,8,0,0,1-16,0V144a8,8,0,0,1,8-8c26.47,0,48-17.94,48-40s-21.53-40-48-40S80,73.94,80,96a8,8,0,0,1-16,0c0-30.88,28.71-56,64-56S192,65.12,192,96Zm-64,96a16,16,0,1,0,16,16A16,16,0,0,0,128,192Z"></path>
        </svg>
    </button>
    {#if isExplanationOpen}
        <div class="explanation-box">
            <button on:click={()=> isExplanationOpen = false} class="close">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#000000" viewBox="0 0 256 256">
                    <path d="M205.66,194.34a8,8,0,0,1-11.32,11.32L128,139.31,61.66,205.66a8,8,0,0,1-11.32-11.32L116.69,128,50.34,61.66A8,8,0,0,1,61.66,50.34L128,116.69l66.34-66.35a8,8,0,0,1,11.32,11.32L139.31,128Z"></path>
                </svg>
            </button>
            <h4>Project Overview</h4>
            <p>This website is an open-source document management platform that combines a Spring Boot backend with a
                SvelteKit frontend. It utilizes PostgreSQL for secure data storage and Elasticsearch for enhanced search
                capabilities, making document retrieval efficient and fast.</p>
            <br>
            <h4>How to Access</h4>
            <p>To explore the platform, use the following root credentials:</p>
            <p><b>Email:</b> {email}</p>
            <p><b>Password:</b> {password}</p>
            <br>
            <a class="primary-button" href="https://github.com/sweet-peach/gestus.xyz">Explore on GitHub</a>
        </div>
    {/if}
</div>


<style lang="scss">
  .preview-box {
    position: fixed;
    left: 15px;
    bottom: 15px;
    opacity: 0.9;
  }

  .explanation-box {
    position: absolute;
    bottom: calc(100% + 10px);
    left: calc(100% + 10px);
    width: 800px;
    background: var(--background-color);
    border: 1px solid var(--border-color);
    border-radius: 7px;
    padding: 15px;
    & .close{
      position: absolute;
      top: 10px;
      right: 10px;
    }
    & h4 {
      margin-bottom: 5px;
    }

    & p {
      text-align: justify;
      margin-bottom: 5px;
    }
  }

  @media (max-width: 900px) {
    .explanation-box {
      width: 600px;
    }
  }

  @media (max-width: 720px) {
    .explanation-box {
      width: 400px;
    }
  }

  .icon-box {
    width: 80px;
    height: 80px;
    background: var(--background-color);
    border-radius: 7px;
    border: 1px solid var(--border-color);
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;

    & svg {
      color: var(--text-color);
    }
  }
</style>