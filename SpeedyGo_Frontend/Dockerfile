# Step 1: Use Node.js 20-alpine as a lightweight base image
FROM node:20-alpine

# Step 2: Set the working directory in the container
WORKDIR /usr/src/app

# Step 3: Copy package.json and package-lock.json to install dependencies first
COPY package*.json ./

# Step 4: Install dependencies
RUN npm install --force

# Step 5: Copy the rest of your application code
COPY . .

# Step 6: Build the app for production
RUN npm run build

# Step 7: Install `serve` globally to serve the app
RUN npm install -g serve

# Step 8: Expose the port that `serve` uses by default
EXPOSE 3000

# Step 9: Command to run the app using `serve`
CMD ["serve", "-s", "build"]
