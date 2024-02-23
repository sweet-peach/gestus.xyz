<script>
    import "$lib/styles/projectCard.scss";
    import {project} from "$lib/stores/projectStore.js";

</script>

<div class="project-options">
    <div class="option"><h2>Auditor</h2><span>{$project.auditor}</span></div>
    <div class="option"><h2>Project code</h2><span>{$project.code}</span></div>
    <div class="option"><h2>Creation date</h2><span>{$project.creationDate.replace("T"," ")}</span></div>
    <div class="option"><h2>Execution start</h2><span>{$project.executionStart.replace("T"," ")}</span></div>
    <div class="option"><h2>Execution end</h2><span>{$project.executionEnd.replace("T"," ")}</span></div>
    <div class="option"><h2>In cooperation</h2><span>{$project.inCooperation}</span></div>
    <div class="option"><h2>Phase</h2><span>{$project.phase}</span></div>
    <div class="option"><h2>Rating</h2><span>{$project.rating}</span></div>
    <div class="option"><h2>Type</h2><span>{$project.type}</span></div>
</div>


<style lang="scss">
    .project{
       cursor: default;
       padding: 30px 20px;
    }

    .project-options{
        display: flex;
        justify-content: space-between;
       flex-direction: column;
       width: 100%;
       .option{
          display: flex;
          justify-content: space-between;
          align-items: center;
          width: 100%;

          padding: 25px 20px;
          h2{
             font-size: 18px;
             font-weight: 500;
             color: var(--text-color);
          }
          span{
             color:var(--secondary-text-color);
          }
          border-bottom: 1px solid var(--border-color);
       }
    }
</style>

