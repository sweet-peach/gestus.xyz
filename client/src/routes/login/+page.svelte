<script>

</script>
<div class="login-container">
    <div class="login-box">
        <h1>Inicia sesion</h1>
        <div class="div-inputs">
            <div class="inputInfo">
                <span>e-mail</span>
                <input type="text">
            </div>
            <div class="inputInfo">
                <span>Contraseña</span>
                <input type="text">
            </div>
            <a href="/projects"><button>Entrar</button></a>
        </div>
        <span>No me acuerdo de mi contraseña</span>
    </div>
</div>

<style>
    :global(html,body){
        width: 100%;
        height: 100%;
    }
    :global(body){
        background: var(--color-secondary-background);
    }
    .login-container{
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .login-box{
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        height: 60%;
        width: 35%;
        border-radius: 54px;
        background: #FFF;
        padding: 30px;
        gap: 10px;
        background: var(--color-background);
    }
    .div-inputs{
        height: 60%;
        width: 100%;
    }
    .div-inputs > * {
        margin-bottom: 20px;
    }
    .div-inputs span {
        margin-bottom: 5px;
    }
    .inputInfo{
        display: flex;
        flex-direction: column;
    }
    .inputInfo span{
        color: #686868;
    }
    button{
        background: var(--color-primary);
        border: none;
        padding: 15px;
        width: 100%;
        border-radius: 23px;
        font-size: 20px;
    }
    input{
        border: 1px solid #EEE;
        padding: 10px;
        border-radius: 23px;
    }
</style>