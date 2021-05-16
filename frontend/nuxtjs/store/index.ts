// TODO: jsのサンプルを元に学習中なので、tsっぽい書き方は後回し。

export const state = {
  todos: [
    {
      id: 1,
      task: 'task1'
    },
    {
      id: 2,
      task: 'task2'
    },
    {
      id: 3,
      task: 'task3'
    },
    {
      id: 4,
      task: 'task4'
    },
    {
      id: 5,
      task: 'task5'
    },
    {
      id: 5,
      task: 'task5'
    },
  ]
};

export const getters = {
  getTodo() {
    return state.todos;
  }
};

export const mutations = {
  addTodo(state: any, payload: any) {
    state.todos.push(payload.todo);
  },
  updateTodo(state: any, payload: any) {
    state.todos.forEach((todo: any, index: number) => {
      if (todo.id === payload.todo.id) {
        state.todos.splice(index, 1, payload.todo);
      }
    });
  },
  removeTodo(state: any, payload: any) {
    state.todos.forEach((todo: any, index: number) => {
      if (todo.id === payload.todo.id) {
        state.todos.splice(index, 1);
      }
    });
  }
};
