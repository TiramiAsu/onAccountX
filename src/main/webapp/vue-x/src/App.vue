<template>
  <div id="app">

    <header>
      <div class="card" align="left">
        <div class="card-header">
          <a class="navbar-brand"><img src="@/assets/logo.png"
            width="30" height="30" class="d-inline-block align-top" alt="Vue.js">onAccountX</a>
        </div>
      </div>
    </header>

    <article>
      <div class="row">

        <!-- Menu -->
        <div class="col-2">
          <div class="list-group" id="list-tab" role="tablist">

            <router-link to="home">
              <span class="list-group-item list-group-item-action"
                :class="(nowRoute === '/home') ? btn.select : ''" data-toggle="collapse" role="tab">Project</span>
            </router-link>

            <router-link to="subject">
              <span class="list-group-item list-group-item-action"
                :class="(nowRoute === '/subject') ? btn.select : ''" data-toggle="collapse" role="tab">Acc. Subjects</span>
            </router-link>

            <router-link to="member">
              <span class="list-group-item list-group-item-action"
                :class="(nowRoute === '/member') ? btn.select : ''" data-toggle="collapse" role="tab">Members</span>
            </router-link>

            <router-link to="account">
              <span class="list-group-item list-group-item-action"
                :class="(nowRoute === '/account') ? btn.select : ''" data-toggle="collapse" role="tab">Accounts</span>
            </router-link>

            <router-link to="profile">
              <span class="list-group-item list-group-item-action"
                :class="(nowRoute === '/profile') ? btn.select : ''" data-toggle="collapse" role="tab">My Profile</span>
            </router-link>

          </div>
        </div>

        <!-- Main -->
        <div class="col">

          <div v-if="$route.path === '/home'">
            <img src="@/assets/tables.png" width="80%">
          </div>

          <div v-else>
            <router-view/>
          </div>

        </div>

        <!-- space -->
        <div class="col-1"></div>

      </div>
    </article>

    <footer style="margin-top: 20%">
      <div class="card card-header" id="copyright">2020 &copy; TiramiAsu</div>
    </footer>

  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      nowRoute: '/home',
      btn: {
        select: 'active'
      }
    }
  },
  updated () {
    this.nowRoute = this.$route.path
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
#copyright {
  text-align: center;
}
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 2px 2px;
}
a {
  color: #42b983;
}
</style>
