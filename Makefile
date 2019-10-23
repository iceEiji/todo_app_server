DOCKER := docker

DOCKER_STACK_NAME := todo_app_server
DOCKER_COMPOSE_FILE := docker-compose.yml

.PHONY: start-docker-stack stop-docker-stack

start-docker-stack:
	$(DOCKER) stack deploy -c $(DOCKER_COMPOSE_FILE) $(DOCKER_STACK_NAME)

stop-docker-stack:
	$(DOCKER) stack rm $(DOCKER_STACK_NAME)
