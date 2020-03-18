<template>
  <div v-if="display">
    <img :src="url" width="50px" height="50px">
  </div>
</template>

<script>
// var projectURL = '' // Vue Test
var projectURL = '/onAccountX/vue/dist'

export default {
  name: 'Loading',
  props: {
    display: {
      type: Boolean,
      default: false,
      require: true
    },
    code: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      url: projectURL + '/static/loading_dots.gif',
      PARAMS: {
        Default: {
          value: 0,
          url: projectURL + '/static/loading_dots.gif'
        },
        Circle: {
          value: 1,
          url: projectURL + '/static/loading_circle.gif'
        },
        Cat: {
          value: 2,
          url: projectURL + '/static/loading_cat.gif'
        }
      }
    }
  },
  updated () {
    this.uiCategory()
  },
  methods: {
    uiCategory () {
      var self = this
      var loading = this.PARAMS
      switch (self.code) {
        case loading.Default.value:
          self.url = loading.Default.url
          break
        case loading.Circle.value:
          self.url = loading.Circle.url
          break
        case loading.Cat.value:
          self.url = loading.Cat.url
          break
      }
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
