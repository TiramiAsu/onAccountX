<template>
  <div class="hello">
    <div v-if="enable == -1">
        <img src="/onAccountX/vue/dist/static/profiles.png" width="80%" alt="My Profiles">
    </div>
    <div v-if="enable == 0">
      <!-- Vue Test -->
      <!-- <img src="/static/loading_cat.gif" width="80%"> -->
      <img src="/onAccountX/vue/dist/static/loading_cat.gif" width="80%">
    </div>
    <memberui v-if="enable == 1"/>
  </div>
</template>

<script>
import loading from './Loading.vue'
import memberUI from './Member.vue'

export default {
  name: 'SPA',
  components: {
    'memberui': memberUI,
    'loading': loading
  },
  props: {
    enable: {
      type: Number,
      default: 0,
      required: true
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
