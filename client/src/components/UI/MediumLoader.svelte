<script>
    export let color = '#000'
</script>

<style>

    @keyframes spin {
        from {
            transform: rotate(0deg);
        }
        to {
            transform: rotate(360deg);
        }
    }

    .loader {
        border: 4px solid rgba(0, 0, 0, 0.1);
        border-radius: 50%;
        width: 40px;
        height: 40px;
        animation: spin 0.85s linear infinite;
    }
</style>

<div style="border-top: 4px solid {color};" class="loader"></div>
