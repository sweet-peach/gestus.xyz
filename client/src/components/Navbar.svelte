<div class="nav-bar-wrapper">
    <div class="nav-bar">
        <a href="/" class="nav-item selected">
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-archive"
                 viewBox="0 0 16 16">
                <path d="M0 2a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1v7.5a2.5 2.5 0 0 1-2.5 2.5h-9A2.5 2.5 0 0 1 1 12.5V5a1 1 0 0 1-1-1zm2 3v7.5A1.5 1.5 0 0 0 3.5 14h9a1.5 1.5 0 0 0 1.5-1.5V5zm13-3H1v2h14zM5 7.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5"/>
            </svg>
            <p>Projects</p>
        </a>

        <a href="/users" class="nav-item">
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-people"
                 viewBox="0 0 16 16">
                <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1zm-7.978-1L7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002-.014.002zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0M6.936 9.28a6 6 0 0 0-1.23-.247A7 7 0 0 0 5 9c-4 0-5 3-5 4q0 1 1 1h4.216A2.24 2.24 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816M4.92 10A5.5 5.5 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0m3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4"/>
            </svg>
            <p>Users</p>
        </a>

        <a href="/logs" class="nav-item">
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                 class="bi bi-clock-history" viewBox="0 0 16 16">
                <path d="M8.515 1.019A7 7 0 0 0 8 1V0a8 8 0 0 1 .589.022zm2.004.45a7 7 0 0 0-.985-.299l.219-.976q.576.129 1.126.342zm1.37.71a7 7 0 0 0-.439-.27l.493-.87a8 8 0 0 1 .979.654l-.615.789a7 7 0 0 0-.418-.302zm1.834 1.79a7 7 0 0 0-.653-.796l.724-.69q.406.429.747.91zm.744 1.352a7 7 0 0 0-.214-.468l.893-.45a8 8 0 0 1 .45 1.088l-.95.313a7 7 0 0 0-.179-.483m.53 2.507a7 7 0 0 0-.1-1.025l.985-.17q.1.58.116 1.17zm-.131 1.538q.05-.254.081-.51l.993.123a8 8 0 0 1-.23 1.155l-.964-.267q.069-.247.12-.501m-.952 2.379q.276-.436.486-.908l.914.405q-.24.54-.555 1.038zm-.964 1.205q.183-.183.35-.378l.758.653a8 8 0 0 1-.401.432z"/>
                <path d="M8 1a7 7 0 1 0 4.95 11.95l.707.707A8.001 8.001 0 1 1 8 0z"/>
                <path d="M7.5 3a.5.5 0 0 1 .5.5v5.21l3.248 1.856a.5.5 0 0 1-.496.868l-3.5-2A.5.5 0 0 1 7 9V3.5a.5.5 0 0 1 .5-.5"/>
            </svg>
            <p>Logs</p>
        </a>

        <a href="/" class="nav-bottom">
            Gestus
        </a>
    </div>
    <div class="separate-line">
    </div>
</div>


<style lang="scss">
  .nav-bar-wrapper {
    display: flex;
  }

  .nav-bar {
    font-size: 18px;
  }

  .separate-line {
    margin: 0 20px;
    border: 1px solid var(--border-color);
    border-radius: 2px;
  }

  .nav-bar {
    padding: 10px 0;
    height: 100%;
    display: flex;
    flex-direction: column;
    gap: 40px;
  }

  .nav-item.title {
    font-size: 18px;
    font-weight: 600;
  }

  .nav-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    cursor: pointer;
    transition: 0.1s ease-in-out;

    svg {
      color: var(--icon-color);
      margin-bottom: 5px;
    }

    p {
      font-weight: 500;
      font-size: 18px;
      color: var(--secondary-text-color);
    }

    &:hover{
      background: var(--ternary-background-color);
      border-radius: 20px;
      svg{
        color: var(--selected-icon-color);
      }
      p{
        color: var(--selected-secondary-text-color)
      }
    }
  }

  .nav-item.selected{
    cursor: default;
    &:hover{
      background: none;
    }
    svg {
      color: var(--selected-icon-color);
    }
    p{
      color: var(--selected-secondary-text-color);
    }
  }

  .nav-bottom{
    margin-top: auto;
    color: var(--secondary-text-color);
  }
</style>