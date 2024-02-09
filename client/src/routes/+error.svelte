<script>
    import {page} from '$app/stores';
</script>
<div class="error-wrapper">
    <div class="error-box">
        <img src="/error.png" alt="#">
        <div class="text-box">
            <h1>{$page.status}</h1>
            <p>{$page.error.message}</p>
        </div>

        <a class="primary-button" href="/">Go to main page</a>
    </div>
</div>
<!--<h1>asda{$page.status}: {$page.error.message}</h1>-->

<style lang="scss">
  .error-wrapper {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .error-box {
    display: flex;
    flex-direction: column;
    align-items: center;

    img{
      width: 250px;
      height: 250px;
      border-radius: 12px;
    }

    .text-box {
      display: flex;
      flex-direction: column;
      align-items: center;

      margin-bottom: 20px;

      h1 {
        font-size: 50px;
        font-weight: 400;
      }

      p {
        font-size: 20px;
      }
    }
  }
</style>