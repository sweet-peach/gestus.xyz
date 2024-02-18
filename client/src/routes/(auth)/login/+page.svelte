<script>
    import {login} from "$lib/services/authService.js";
    import MediumLoader from "../../../components/UI/MediumLoader.svelte";
    import {goto} from "$app/navigation";

    let email = "";
    let password = "";
    let errorText = "";

    let isEmailIncorrect = null;
    let isPasswordIncorrect = null;
    let isButtonBlocked = true;
    let isLoading = false;

    function checkPassword() {
        isPasswordIncorrect = password.length === 0;
    }

    function checkEmail() {
        let emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        isEmailIncorrect = !emailRegex.test(email);
    }

    $: if (isEmailIncorrect === false && isPasswordIncorrect === false) {
        isButtonBlocked = false;
    }

    let passwordTimeout;

    function handlePasswordInput() {
        errorText = "";
        clearTimeout(passwordTimeout);
        passwordTimeout = setTimeout(checkPassword, 200);
    }

    let emailTimeout;

    function handleEmailInput() {
        errorText = "";
        clearTimeout(emailTimeout);
        emailTimeout = setTimeout(checkEmail, 200);
    }

    async function handleLoginButtonClick() {
        if (isButtonBlocked || isLoading) return;

        isLoading = true;
        isButtonBlocked = true;

        let response;
        try {
            response = await login(email, password);
        } catch (error) {
            errorText = error.message;
        }
        if (response?.success === true){
            return goto('/');
        }

        isButtonBlocked = false;
        isLoading = false;
    }

</script>


<div class="login-container">

    <div
            class="login-box"
            class:faded={isLoading}
    >
        {#if isLoading}
            <div class="loader">
                <MediumLoader/>
            </div>
        {/if}
        <h1>Welcome back</h1>
        <div class="inputs">
            <div class="input {isEmailIncorrect ? 'error' : ''}">
                <div class="hint">
                    <span>Email</span>
                    {#if isEmailIncorrect}
                        <span class="error-text">Incorrect email</span>
                    {/if}
                </div>
                <input autocomplete="email" id="name" bind:value={email} on:input={handleEmailInput} type="text">
            </div>
            <div class="input {isPasswordIncorrect ? 'error' : ''}">
                <div class="hint">
                    <span>Password</span>
                    {#if isPasswordIncorrect}
                        <span class="error-text">Password is obligatory</span>
                    {/if}
                </div>
                <input autocomplete="current-password" id="password" bind:value={password}
                       on:input={handlePasswordInput} type="password">
            </div>
        </div>
        <button class:blocked={isButtonBlocked}
                class="primary-button obscured"
                on:click={handleLoginButtonClick}>
            Enter
        </button>
        <div class="error-text">{errorText} &nbsp;</div>
    </div>
</div>

<style lang="scss">

   .loader {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 30px;
      display: flex;
      align-items: center;
      justify-content: center;
   }

   .error-text {
      color: var(--red-color);
      font-size: 16px;
      font-weight: 500;
   }

   .login-container {
      background: var(--secondary-background-color);
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
   }

   .login-box {
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      border-radius: 35px;
      background: #FFF;
      padding: 25px;
      width: 550px;
      background: var(--background-color);

      gap: 10px;

      h1 {
         text-align: left;
         font-size: 20px;
         font-weight: 600;
      }

      button {
         margin-top: 20px;
         width: 100%;
      }
   }


   .inputs {
      display: flex;
      flex-direction: column;
      gap: 15px;

      width: 100%;

      .input {
         display: flex;
         flex-direction: column;
         gap: 5px;

         .error-text {
            color: var(--red-color);
            font-size: 14px;
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
            font-size: 14px;
         }

         input {
            border: .5px solid rgba(0, 0, 0, 0.144);
            padding: 10px 20px;
            font-size: 17px;
            font-weight: 500;
            border-radius: 12px;
         }
      }
   }
</style>