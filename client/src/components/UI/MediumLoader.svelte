<script>
    export let color = '#000'
    export let text = 'Loading...'
</script>


<div class="loader-box">
    <div style="border-top: 4px solid {color};" class="loader"></div>
    <span>{text}</span>
</div>

<style lang="scss">

    @keyframes spin {
        from {
            transform: rotate(0deg);
        }
        to {
            transform: rotate(360deg);
        }
    }

    .loader-box {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;

       .loader {
          border: 4px solid rgba(0, 0, 0, 0.1);
          border-radius: 50%;
          width: 40px;
          height: 40px;
          animation: spin 0.65s linear infinite;

          margin-bottom: 20px;
       }

       span{
          font-size: 20px;
          font-weight: 500;
       }
    }
</style>

