<template>
  <div class="hello container-fluid row">
    <div class="list-group col-md-2" style="height: 1000px">
      <ul class="list-group">
        <li class="list-group-item list-group-item-secondary" @click="enable = PARAM.Layout.default">MANAGEMENT</li>
        <li><a class="list-group-item list-group-item-action" @click="enable = PARAM.Layout.members">Member</a></li>
        <li><a class="list-group-item list-group-item-action" @click="enable = PARAM.Layout.profile">My Profile</a></li>
      </ul>
    </div>
    <div class="col-md-10">
      <spa :enable="enable"/>
    </div>
  </div>
</template>

<script>
import SPA from './SPA.vue'

export default {
  name: 'Home',
  components: {
    'spa': SPA
  },
  data () {
    return {
      PARAM: {
        Layout: {
          profile: -1,
          default: 0,
          members: 1
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
