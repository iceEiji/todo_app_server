<template>
  <v-layout column justify-center align-center>
    <v-card v-if="todos">
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
      <v-data-table
        :headers="headers"
        :items="todos"
        :items-per-page="5"
        :search="search"
        sort-by="id"
        :sort-desc="true"
        class="elevation-1"
      ></v-data-table>
    </v-card>
  </v-layout>
</template>

<script lang="ts">
import Vue from "vue";
export default Vue.extend({
  data() {
    return {
      search: "",
      headers: [
        { text: "ID", value: "id" },
        { text: "タスク", value: "task" },
        { text: "操作", value: "actions" },
      ],
      todo: {},
    };
  },
  computed: {
    todos() {
      return this.$store.getters.getTodos;
    },
  },
});
</script>
