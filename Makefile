DOCKER := docker

DOCKER_STACK_NAME := todo_app_server
DOCKER_COMPOSE_FILE := docker-compose.yml

.PHONY: help start-docker-stack stop-docker-stack docker-build
.DEFAULT_GOAL := help

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

start-docker-stack: ## Start Docker container.
	$(DOCKER) stack deploy -c $(DOCKER_COMPOSE_FILE) $(DOCKER_STACK_NAME)

stop-docker-stack: ## Stop Docker container.
	$(DOCKER) stack rm $(DOCKER_STACK_NAME)

docker-build:
	docker build --no-cache -t todo-app-server .

k8s-deploy:
	kubectl apply -f k8s/deployment.yml
	kubectl apply -f k8s/service.yml

k8s-clean:
	kubectl delete -f k8s/deployment.yml
	kubectl delete -f k8s/service.yml
