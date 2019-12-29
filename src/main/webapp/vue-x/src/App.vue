<template>
  <div id="app">
    <header>
      <div class="card" align="left">
        <div class="card-header">
          <a class="navbar-brand">
            <img src="./assets/logo.png" width="30" height="30" class="d-inline-block align-top" alt="Vue.js">
            onAccountX
          </a>
        </div>
      </div>
    </header>
    <div style="margin-top: 10px" align="left">
      <router-view/>
    </div>
    <footer>
      <div class="card">
        <div class="card-header">
          2019 &copy; TiramiAsu
        </div>
        <!-- <div class="card-body">
          <blockquote class="blockquote mb-0">
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
            <footer class="blockquote-footer">Someone famous in <cite title="Source Title">Source Title</cite></footer>
          </blockquote>
        </div> -->
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
