<template>
  <div class="hello container-fluid row">
    <img src="/onAccountX/vue/dist/static/profiles.png" width="80%" alt="My Profiles">
  </div>
</template>

<script>
import SPA from './SPA.vue'

export default {
  name: 'Profile',
  components: {
    'spa': SPA
  },
  data () {
    return {
      PARAM: {
        Layout: {
          loadingTest: -1,
          default: 0,
          members: 1,
          accounts: 2
        }
      },
      enable: 0
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
