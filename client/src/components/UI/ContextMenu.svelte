<script>
    import {clickOutside} from "$lib/use/clickOutside.js";
    export let isVisible = false;
    export let toggleElement;

    let dimensions;
    let position = {x: 0, y: 0}

    function throttle(func, limit) {
        let lastFunc;
        let lastRan;
        return function() {
            const context = this;
            const args = arguments;
            if (!lastRan) {
                func.apply(context, args);
                lastRan = Date.now();
            } else {
                clearTimeout(lastFunc);
                lastFunc = setTimeout(function() {
                    if ((Date.now() - lastRan) >= limit) {
                        func.apply(context, args);
                        lastRan = Date.now();
                    }
                }, limit - (Date.now() - lastRan));
            }
        }
    }

    function getDimensions(node) {
        const {width, height} = node.getBoundingClientRect();
        dimensions = {width, height};
    }

    function adjustPosition() {
        if(!dimensions || !toggleElement) return;

        const {width, height, x, y} = toggleElement.getBoundingClientRect()
        const browser = {width: window.innerWidth, height: window.innerHeight}

        position.x = (browser.width < x + dimensions.width) ? browser.width - dimensions.width - width: x + width;
        position.y = (browser.height < y + dimensions.height) ? browser.height - dimensions.height - height: y + height;
    }

    let isClient = typeof window !== 'undefined';
    $: if (isClient && isVisible) {
        window.addEventListener('resize', throttle(adjustPosition,250));
    } else if(isClient) {
        window.removeEventListener('resize', throttle(adjustPosition,250));
    }


    $: if (dimensions && (toggleElement)) {
        adjustPosition();
    }

    function closeMenu(event) {
        if (toggleElement.contains(event.detail)) {
            return;
        }
        isVisible = false;
    }
</script>


{#if isVisible}
    <div use:getDimensions
         use:clickOutside
             on:outclick={closeMenu}
         class="context-menu"
         style="top: {position.y}px; left: {position.x}px;">
        <slot></slot>
    </div>
{/if}

<style>
    .context-menu {
        position: absolute;
    }
</style>
