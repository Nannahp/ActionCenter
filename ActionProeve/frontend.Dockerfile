# Build stage
FROM node:lts-alpine AS builder

# Set the working directory
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of your project files
COPY . .

# Build the Vue app for production
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy the built files from the builder stage to Nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy custom Nginx configuration file
#Tror ikke vi bruger dette, da der ikke er en nginx.conf fil i programmet.
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 8081 for Nginx
EXPOSE 8081

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
