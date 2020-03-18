<template>
  <div class="list-group" style="height: 1000px">
    <ul class="list-group">
      <li><a class="list-group-item list-group-item-secondary"><router-link to="/ui/member">[跳轉到 members]</router-link></a></li>
      <!-- <li><a class="list-group-item list-group-item-secondary" @click="enable = PARAM.Layout.default">Project</a></li>
      <li><a class="list-group-item list-group-item-action" @click="enable = PARAM.Layout.members">Members</a></li>
      <li><a class="list-group-item list-group-item-action" @click="enable = PARAM.Layout.profile">My Profile</a></li> -->
    </ul>
  </div>
</template>

<script>
export default {
  name: 'menux'
}
</script>

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
