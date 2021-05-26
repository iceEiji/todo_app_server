<template>
  <v-layout column justify-center align-center>
    <v-card v-if="todos">
      <!-- 見出し -->
      <v-card-title>
        TODO一覧
        <v-spacer />
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="検索"
          single-line
        ></v-text-field>
      </v-card-title>

      <!-- 新規追加 -->
      <v-btn fab dark small color="dark" class="mb-2" @click="add">
        <v-icon dark> mdi-plus </v-icon>
      </v-btn>

      <!-- 一覧 -->
      <v-data-table
        :headers="headers"
        :items="todos"
        :items-per-page="10"
        :search="search"
        sort-by="id"
        :sort-desc="true"
        class="elevation-1"
      >
        <!-- モーダル -->
        <template v-slot:top>
          <v-dialog v-model="dialog" max-width="500px">
            <v-card>
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <v-text-field v-model="todo.task" label="タスク" />
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn @click="close">閉じる</v-btn>
                <v-btn v-if="isPersistedTodo" class="primary" @click="update">更新する</v-btn>
                <v-btn v-else class="primary" @click="create">追加する</v-btn>
                <v-spacer />
              </v-card-actions>
            </v-card>
          </v-dialog>
        </template>

        <!-- 編集と削除 -->
        <template v-slot:[`item.actions`]="{item}">
          <v-icon small @click="edit(item)">
            mdi-pencil
          </v-icon>
          <v-icon small @click="remove(item)">
            mdi-delete
          </v-icon>
        </template>
      </v-data-table>
    </v-card>
  </v-layout>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";

export default Vue.extend({
  async asyncData({app}) {
    const response = await axios.get('/api/todos/');
    return { todos: response.data }
  },
  data() {
    return {
      search: '',
      headers: [
        { text: 'ID', value: 'id' },
        { text: 'タスク', value: 'task' },
        { text: '操作', value: 'actions' }
      ],
      todo: {
        id: undefined as unknown | number,
        task: undefined as unknown | string
      },
      dialog: false
    }
  },
  computed: {
    isPersistedTodo(): boolean {
      return !!this.todo.id;
    },
    formTitle(): string {
      return this.isPersistedTodo ? 'TODO編集' : 'TODO追加';
    },
  },
  methods: {
    // 以下、UI操作関連
    add(todo: any) {
      this.todo = todo;
      this.dialog = true;
    },
    edit(todo: any) {
      this.todo = Object.assign({}, todo);
      this.dialog = true;
    },
    close() {
      this.todo = { id: undefined, task: undefined };
      this.dialog = false;
    },
    // 以下、データ操作関連
    async create() {
      await axios.post('/api/todos/', this.todo).then(() => {
        console.info(this.$router.app);
        // @ts-ignore
        this.$router.app.refresh();
      });
      this.close();
    },
    async update() {
      await axios.put('/api/todos/' + this.todo.id, this.todo).then(() => {
        // @ts-ignore
        this.$router.app.refresh();
      });
      this.close();
    },
    async remove(todo: any) {
      await axios.delete('/api/todos/' + todo.id, todo).then(() => {
        // @ts-ignore
        this.$router.app.refresh();
      });
    },
  }
});
</script>
