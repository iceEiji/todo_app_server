# todo_app_server

## Required

* nodejs
* See package.json for the version.

## Getting Started

1. Please git clone this repository.
2. Run install module.
  ```
  npm install
  ```
3. Run build.
  ```
  npx tsc
  ```
4. Start app server.
  ```
  npm run start
  ```
5. Stop app server.
  * Ctrl+C

### If use docker

1. Start app server in Docker container.
  ```
  make start-docker-stack
  ```
2. Stop Docker container and app server.
  ```
  make stop-docker-stack
  ```

#### Q&A

* If you get an error below, run "docker swarm init".
```
this node is not a swarm manager. Use "docker swarm init" or "docker swarm join" to connect this node to swarm and try again
```
