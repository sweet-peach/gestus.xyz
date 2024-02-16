<script>
    import { onMount } from 'svelte';

    let container;
    let content = '';

    function updateContent(event) {
        content = event.target.innerText.trim();
    }

    function handleKeydown(event) {
        // Пример обработки специфических клавиш
        if (event.key === 'Enter') {
            event.preventDefault(); // Предотвращаем перенос строки для однострочного ввода
        }
    }

    onMount(() => {
        container.addEventListener('input', updateContent);
        container.addEventListener('keydown', handleKeydown);

        return () => {
            container.removeEventListener('input', updateContent);
            container.removeEventListener('keydown', handleKeydown);
        };
    });
</script>

<style>
    .custom-input {
        border: 2px solid #ccc;
        padding: 10px;
        margin: 10px 0;
        border-radius: 4px;
        min-height: 20px;
        outline: none;
        transition: border-color 0.3s ease;
    }

    .custom-input:focus {
        border-color: deepskyblue;
        box-shadow: 0 0 5px rgba(0, 0, 255, 0.2);
    }
</style>

<div
        contenteditable="true"
        class="custom-input"
        bind:this={container}
        role="textbox"
        aria-multiline="false"
        aria-label="Custom text input"
        tabindex="0"
></div>

<p>Content: {content}</p>
