<script>
    export let color = '#000'
</script>


<div class="loader-box">
    <div style="border-top: 2px solid {color};" class="loader"></div>
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
          border: 2px solid rgba(0, 0, 0, 0.1);
          border-radius: 50px;
          width: 20px;
          height: 20px;
          animation: spin 0.65s linear infinite;

       }

       span{
          font-size: 20px;
          font-weight: 500;
       }
    }
</style>

