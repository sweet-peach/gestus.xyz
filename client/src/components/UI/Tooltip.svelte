<script>
    export let text = 'Tooltip text'
    export let isVisible = false;
    export let hoverElement;

    let dimensions;
    let position = {x: 0, y: 0}

    function getDimensions(node) {
        const {width, height} = node.getBoundingClientRect();
        dimensions = {width, height};
    }

    $:if (isVisible){
        if (dimensions && hoverElement){
            const {width, height, x, y} = hoverElement.getBoundingClientRect()

            position.x = x + width / 2 - dimensions.width / 2;
            position.y = y - dimensions.height - 10;
        }
    }

    function adjustPosition() {
        isVisible = true;

    }
</script>

{#if isVisible}
    <div
            use:getDimensions
            style="top: {position.y}px; left: {position.x}px;"
            class="tooltip">
        {text}
    </div>
{/if}

<style lang="scss">
   .tooltip {
      position: absolute;
      display: block;
      z-index: var(--overlay-index);

      margin-bottom: 5px;
      padding: 7px;
      width: auto;
      border-radius: 3px;
      background-color: #000;
      background-color: hsla(0, 0%, 20%, 0.9);
      color: #fff;
      content: attr(data-tooltip);
      text-align: center;
      font-size: 14px;
      line-height: 1.2;
      white-space: nowrap;

      &:after {
         position: absolute;
         top: 100%;
         left: 50%;
         width: 0;
         border-top: 5px solid #000;
         border-top: 5px solid hsla(0, 0%, 20%, 0.9);
         border-right: 5px solid transparent;
         border-left: 5px solid transparent;
         content: " ";
         font-size: 0;
         line-height: 0;
         visibility: visible;
         opacity: 1;
         transform: translate(-50%, 0)
      }
   }
</style>
