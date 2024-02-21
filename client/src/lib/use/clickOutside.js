export function clickOutside(node) {
    let isMouseStartedOutside = false;

    const handleMouseDown = (event) => {
        isMouseStartedOutside = !node.contains(event.target);
    };

    const handleMouseUp = (event) => {
        if (isMouseStartedOutside && !node.contains(event.target)) {
            node.dispatchEvent(new CustomEvent('outclick', {
                detail: event.target,
            }));
        }
        isMouseStartedOutside = false;
    };

    document.addEventListener('mousedown', handleMouseDown, true);
    document.addEventListener('mouseup', handleMouseUp, true);

    return {
        destroy() {
            document.removeEventListener('mousedown', handleMouseDown, true);
            document.removeEventListener('mouseup', handleMouseUp, true);
        }
    };
}
