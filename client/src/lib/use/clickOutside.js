export function clickOutside(node) {
    const handleClick = (event) => {
        if (!node.contains(event.target)) {
            node.dispatchEvent(new CustomEvent('outclick', {
                detail: event.target,
            }));
        }
    };

    document.addEventListener('click', handleClick, true);

    return {
        destroy() {
            document.removeEventListener('click', handleClick, true);
        }
    };
}
