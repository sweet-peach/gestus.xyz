
## About this project

This platform combines a **Spring Boot** backend with a **SvelteKit** frontend. We utilize **PostgreSQL** for data storage and **Elasticsearch** for enhanced search capabilities.

With this platform, you can create projects, store files in created projects, and assign them various attributes such as "Project auditor," "Project execution Date," "Project keywords," and more.

The platform has three main user roles:

1. **Reader**: Can view projects and download files but cannot create or modify projects or files.
2. **Modifier**: Has all the capabilities of a reader, plus the ability to create new projects, modify existing ones, and upload files.
3. **Admin**: Possesses all modifier privileges and can also manage user accounts (create, investigate, and modify users).


## Installation and configuration

### Prerequisites

Before working with this project, ensure you have the following installed:
- Java 17
- Node.js 16 or above and npm
- PostgreSQL
- Elasticsearch
- Git

### Clone the project

### Backend setup - Spring Boot
1. Clone the project repository and navigate to its root directory
```bash
git clone https://github.com/sweet-peach/gestus.xyz.git
```
```bash
cd ./gestus
```

2. Navigate to server folder
```bash
cd ./server
``` 

3. Create a file named ```env.properties``` and copy the contents of ```env.properties.template``` into it.

4. In `env.properties`, set the `PROFILE` field to specify the environment:

   - **Development**: Using the development profile server will **enable CORS**, allowing requests from the origin specified in the ```CORS_ALLOWED_ORIGIN``` field. For development, you should enter the default client server value: http://localhost:5173/.
   - **Production**: Using the production profile server will **disable CORS** by default. However, you can enable it if needed by modifying the project and creating a ProductionCorsConfig file in ```/server/src/main/java/xyz/gestus/gestus/core/config/security/```.

5. Copy your PostgreSQL server connection information and update the following fields in your env.properties file accordingly:

     ```properties
     DATABASE_NAME=
     DATABASE_USERNAME=
     DATABASE_PASSWORD=
     DATABASE_IP=
     DATABASE_PORT=
     ```

     **Note**: There's no need to manually create database tables; Spring Boot will handle schema generation automatically.

6. Copy your Elasticsearch server connection information and update the following fields in your env.properties file accordingly:

     ```properties
     ELASTIC_URL=
     ELASTIC_USERNAME=
     ELASTIC_PASSWORD=
     ```

     **Note**: If you're connecting via HTTPS, make sure Elasticsearch is started with a valid SSL certificate. If using a self-signed certificate, additional configuration may be required for Spring Boot to establish a secure connection.

7. Generate a JWT secret key of 64 length or above. You can use a tool like [jwtsecret.com](https://jwtsecret.com/generate) to create one and add this secret to your `env.properties` file in field `JWT_SECRET`.
8. The server attempts to create a root user each time it boots. Currently, the default root user credentials are:
- **Email**: `admin@example.com` 
- **Password**: `admin` 

To change these defaults, update the `ROOT_USER_EMAIL` and `ROOT_USER_PASSWORD` in `env.properties`

### Frontend setup - SvelteKit

1. Navigate to client folder

```
cd ./client
```

1. Create the `.env` file and copy the content from `env.template` into this file.

2. In the `.env` file, set the `VITE_API_URL` variable to point to your backend server. Use the following format:
     ```dotenv
     VITE_API_URL=[protocol (http/https)]://[domain or IP]:[port]
     ```
Examples:
- VITE_API_URL=http://127.0.0.1:8080
- VITE_API_URL=https://yourdomain.com

3. Install dependencies:
```bash
npm install
```

## Development

After successfully installing and configuring the project, ensure that in the backend (Spring Boot), the PROFILE is set to development and `CORS_ALLOWED_ORIGIN` is directed to the frontend address . Additionally, in the frontend .env file, set the `VITE_API_URL` to point to the backend address.

#### Prerequisites for development

- **Lombok**: Install and configure Lombok in your IDE.

### Steps
1. Ensure that PostgreSQL and Elasticsearch are working and reachable.
 
2. Go to the frontend directory and start the server using:
```bash
npm run dev
```  
- The server will start on the default port **5173**.

3. Go to the backend directory, build and run the server using:

```bash
./gradlew build
```

- After a successful build, start the server:

```bash     
./gradlew bootRun
```

- The server will start on the default port **8080**.

## Deployment

For deployment, I’ll assume the project will be hosted on an Ubuntu server under your domain. In the example, I’ll use yourdomain.com be sure to replace it with your actual domain. I’ll outline the steps, though they may vary depending on your platform.

### Steps

After successfully installing and configuring the project, make sure to set the `PROFILE` to `production` on the backend (Spring Boot) and in the frontend, set `VITE_API_URL` pointing to backend server address. Which should be:

```properties
VITE_API_URL:https://yourdomain.com
```

1. Ensure that PostgreSQL and Elasticsearch are working and reachable.

2. Go to the client directory
3. There are several [SvelteKit adapters](https://kit.svelte.dev/docs/adapters) available for deployment. In this guide, however, we'll focus on using the Node adapter. To get started, let's install it:
```bash
npm install -D @sveltejs/adapter-node
```
4. Update `svelte.config.js` to use the new installed Node adapter.
- It should now look like this
```
import adapter from '@sveltejs/adapter-node';
import sveltePreprocess from 'svelte-preprocess';
import dotenv from 'dotenv';
dotenv.config();
/** @type {import('@sveltejs/kit').Config} */
const config = {
	preprocess: sveltePreprocess({ sass: true }),
	kit: {
		adapter: adapter()
	}
};

export default config;
```
5. Build and run the frontend server:
```bash
npm run build
node build
```
- The server will start on the default port **3000**.
6. Go to the server directory, and run the server using:

```bash
./gradlew build
```
- If you encounter the error ./gradlew: Permission denied, you can resolve it by granting execute permissions to gradlew. Run the following command in your terminal:
```bash     
chmod +x gradlew
```
- After a successful build, you can start the server:

```bash     
./gradlew bootRun
```

- The server will start on the default port **8080**.

Now to serve both the frontend and backend from the same domain in a production environment, you can use **Nginx** as a reverse proxy. All server requests that start with `/api/` will be redirected to your backend server, while other requests will be served by the frontend. 

6. Before installing any new software, it's good practice to update your system packages.
```bash
sudo apt update && sudo apt upgrade -y
```

7. Install nginx:

```bash
sudo apt install nginx -y
```

8. Install certbot to use Let's Encrypt:
```bash
sudo apt install certbot python3-certbot-nginx
```

9. Obtain an SSL certificate:
```bash
sudo certbot --nginx
```

- Instructions: Follow the interactive prompts to select your domain and agree to the terms of service.
- Note: Ensure that your domain's DNS records point to your server's IP address before running this command.

10. Create a new Nginx configuration file for your domain and add the following configuration.
```bash
sudo nano /etc/nginx/sites-available/yourdomain.com
```
Replace `yourdomain.com` with actual domain name.

```properties
   server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;
    return 301 https://$host$request_uri;  # Redirect all HTTP requests to HTTPS
}

server {
    listen 443 ssl;
    server_name yourdomain.com www.yourdomain.com;

    # SSL Certificates
    ssl_certificate /etc/letsencrypt/live/yourdomain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/yourdomain.com/privkey.pem;

    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_prefer_server_ciphers on;
    ssl_ciphers HIGH:!aNULL:!MD5;

    # Reverse Proxy for Spring Boot Backend
    location /api/ {
        proxy_pass http://localhost:8080;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Host $host;
    }

    # Reverse Proxy for SvelteKit Frontend
    location / {
        proxy_pass http://localhost:3000;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }
}

```
- Explanation:
   - Port 80 Server Block: Redirects all HTTP traffic to HTTPS.
   - Port 443 Server Block: Handles HTTPS traffic and specifies SSL settings.
   - SSL Certificates: Points to the SSL certificates obtained from Let's Encrypt.
   - Reverse Proxy Settings:
      - `/api/`: Proxies requests to the Spring Boot backend on port `8080`.
      - `/`: Proxies all other requests to the SvelteKit frontend on port `3000`.

11. Create a symbolic link to enable the site configuration:
```bash
sudo ln -s /etc/nginx/sites-available/yourdomain.com /etc/nginx/sites-enabled/
```
12. Test the Nginx configuration for syntax errors:
```bash
sudo nginx -t
```
13. Restart Nginx to apply the changes:
```bash
sudo systemctl restart nginx
```
14. Adjust firewall settings
```bash
sudo ufw allow 80
sudo ufw allow 443
```
- If you are using ssh, be sure to update the firewall settings to allow SSH as well, or you might get locked out:
```bash
sudo ufw allow ssh
```
15. Enable the firewall:
```bash
sudo ufw enable
```
- Check the status of your firewall and confirm the changes:
```bash
sudo ufw status
```
15. Turn on auto-renewal for the SSL certificate:
```bash
sudo certbot renew --dry-run
```

By visiting `yourdomain.com`, you’ll now see the Gestus login page. You can log in using the root user credentials specified in the backend `env.properties` file.

## License
This project is licensed under the MIT license

## Acknowledgments
- [Nginx documentation](https://nginx.org/en/docs/)
- [Let's Encrypt](https://letsencrypt.org/)
- [Certbot Documentation](https://eff-certbot.readthedocs.io/en/stable/)
